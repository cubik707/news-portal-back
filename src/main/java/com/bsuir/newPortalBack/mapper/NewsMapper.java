package com.bsuir.newPortalBack.mapper;

import com.bsuir.newPortalBack.dto.news.NewsDTO;
import com.bsuir.newPortalBack.dto.user.UserForNewsDTO;
import com.bsuir.newPortalBack.entities.NewsEntity;
import com.bsuir.newPortalBack.entities.UserEntity;
import com.bsuir.newPortalBack.entities.UserInfoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class NewsMapper implements BaseMapper<NewsEntity, NewsDTO> {

  private final TagMapper tagMapper;
  private final NewsCategoryMapper newsCategoryMapper;

  @Override
  public NewsDTO toDTO(NewsEntity entity) {
    if(entity == null) {
      return null;
    }

    // Convert author (UserEntity + UserInfoEntity) to UserForNewsDTO
    UserForNewsDTO authorDto = null;
    if (entity.getAuthor() != null) {
      UserEntity author = entity.getAuthor();
      UserInfoEntity info = author.getUserInfo();
      // Using Lombok Builder for UserForNewsDTO
      UserForNewsDTO.UserForNewsDTOBuilder userBuilder = UserForNewsDTO.builder()
        .id(author.getId());
      if (info != null) {
        userBuilder
          .firstName(info.getFirstName())
          .lastName(info.getLastName())
          .surname(info.getSurname())
          .avatarUrl(info.getAvatarUrl());
      }
      authorDto = userBuilder.build();
    }

    // Build and return NewsDTO (skip createdAt, updatedAt, scheduledAt)
    return NewsDTO.builder()
      .id(entity.getId())
      .title(entity.getTitle())
      .content(entity.getContent())
      .image(entity.getImage())
      .status(entity.getStatus())
      .publishedAt(entity.getPublishedAt())
      .category(newsCategoryMapper.toDTO(entity.getCategory()))
      .tags(tagMapper.toDTOList(entity.getTags()))
      .author(authorDto)
      .build();
  }

  @Override
  public NewsEntity toEntity(NewsDTO dto) {
    if (dto == null) {
      return null;
    }

    // Convert author (UserForNewsDTO) to UserEntity
    UserEntity userEntity = null;
    if (dto.getAuthor() != null) {
      UserForNewsDTO authorDto = dto.getAuthor();
      UserInfoEntity userInfo = null;
      if (authorDto.getFirstName() != null || authorDto.getLastName() != null) {
        // Assuming builder for UserInfoEntity
        userInfo = new UserInfoEntity.Builder()
          .lastName(authorDto.getLastName())
          .firstName(authorDto.getFirstName())
          .surname(authorDto.getSurname())
          .avatarUrl(authorDto.getAvatarUrl())
          .build();
      }

      // Build UserEntity with possible UserInfoEntity
      UserEntity.Builder userBuilder = new UserEntity.Builder()
        .id(authorDto.getId());
      if (userInfo != null) {
        userBuilder.userInfo(userInfo);
      }
      userEntity = userBuilder.build();
    }

    return NewsEntity.builder()
      .id(dto.getId())
      .title(dto.getTitle())
      .content(dto.getContent())
      .image(dto.getImage())
      .status(dto.getStatus())
      .publishedAt(dto.getPublishedAt())
      .category(newsCategoryMapper.toEntity(dto.getCategory()))
      .tags(tagMapper.toEntityList(dto.getTags()))
      .author(userEntity)
      .build();
  }

  @Override
  public List<NewsDTO> toDTOList(List<NewsEntity> entityList) {
    return entityList.stream().map(this::toDTO).collect(Collectors.toList());
  }

  @Override
  public List<NewsEntity> toEntityList(List<NewsDTO> dtoList) {
    return dtoList.stream().map(this::toEntity).collect(Collectors.toList());
  }
}
