package com.bsuir.newPortalBack.mapper;

import com.bsuir.newPortalBack.dto.news.NewsCreateDTO;
import com.bsuir.newPortalBack.entities.NewsCategoryEntity;
import com.bsuir.newPortalBack.entities.NewsEntity;
import com.bsuir.newPortalBack.entities.TagEntity;
import com.bsuir.newPortalBack.entities.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NewsCreateMapper implements BaseMapper<NewsEntity, NewsCreateDTO> {

  @Override
  public NewsCreateDTO toDTO(NewsEntity entity) {
    if (entity == null) {
      return null;
    }

    return NewsCreateDTO.builder()
      .title(entity.getTitle())
      .content(entity.getContent())
      .image(entity.getImage())
      .authorId(entity.getAuthor().getId())
      .categoryId(entity.getCategory().getId())
      .tags(entity.getTags().stream().map(TagEntity::getName).collect(Collectors.toList()))
      .build();
  }

  @Override
  public NewsEntity toEntity(NewsCreateDTO dto) {
    if (dto == null) {
      return null;
    }

    NewsEntity entity = new NewsEntity();
    entity.setTitle(dto.getTitle());
    entity.setContent(dto.getContent());
    entity.setImage(dto.getImage());

    UserEntity author = new UserEntity();
    author.setId(dto.getAuthorId());
    entity.setAuthor(author);

    NewsCategoryEntity category = new NewsCategoryEntity();
    category.setId(dto.getCategoryId());
    entity.setCategory(category);

    return entity;
  }

  @Override
  public List<NewsCreateDTO> toDTOList(List<NewsEntity> entityList) {
    return entityList.stream()
      .map(this::toDTO)
      .collect(Collectors.toList());
  }

  @Override
  public List<NewsEntity> toEntityList(List<NewsCreateDTO> dtoList) {
    return dtoList.stream()
      .map(this::toEntity)
      .collect(Collectors.toList());
  }
}
