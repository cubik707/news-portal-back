package com.bsuir.newPortalBack.mapper;

import com.bsuir.newPortalBack.dto.news.NewsUpdateDTO;
import com.bsuir.newPortalBack.entities.NewsEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NewsUpdateMapper implements BaseMapper<NewsEntity, NewsUpdateDTO> {

  @Override
  public NewsUpdateDTO toDTO(NewsEntity entity) {
    return NewsUpdateDTO.builder()
      .id(entity.getId())
      .title(entity.getTitle())
      .content(entity.getContent())
      .categoryId(entity.getCategory().getId())
      .image(entity.getImage())
      .tags(entity.getTags().stream()
        .map(tag -> tag.getName())
        .collect(Collectors.toList()))
      .status(entity.getStatus())
      .build();
  }

  @Override
  public NewsEntity toEntity(NewsUpdateDTO dto) {
    return NewsEntity.builder()
      .id(dto.getId())
      .title(dto.getTitle())
      .content(dto.getContent())
      .image(dto.getImage())
      .status(dto.getStatus())
      .build();
  }

  @Override
  public List<NewsUpdateDTO> toDTOList(List<NewsEntity> entityList) {
    return entityList.stream()
      .map(this::toDTO)
      .collect(Collectors.toList());
  }

  @Override
  public List<NewsEntity> toEntityList(List<NewsUpdateDTO> dtoList) {
    return dtoList.stream()
      .map(this::toEntity)
      .collect(Collectors.toList());
  }
}
