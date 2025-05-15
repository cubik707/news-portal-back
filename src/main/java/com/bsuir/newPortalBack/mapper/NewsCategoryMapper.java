package com.bsuir.newPortalBack.mapper;

import com.bsuir.newPortalBack.dto.newsCategory.NewsCategoryDTO;
import com.bsuir.newPortalBack.entities.NewsCategoryEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class NewsCategoryMapper implements BaseMapper<NewsCategoryEntity, NewsCategoryDTO> {
  @Override
  public NewsCategoryDTO toDTO(NewsCategoryEntity entity) {
    if (entity == null) {
      return null;
    }
    return NewsCategoryDTO.builder()
      .id(entity.getId())
      .name(entity.getName())
      .build();
  }

  @Override
  public NewsCategoryEntity toEntity(NewsCategoryDTO dto) {
    if (dto == null) {
      return null;
    }
    return NewsCategoryEntity.builder()
      .id(dto.getId())
      .name(dto.getName())
      .newsList(new ArrayList<>())
      .subscribers(new HashSet<>())
      .build();
  }

  @Override
  public List<NewsCategoryDTO> toDTOList(List<NewsCategoryEntity> entityList) {
    return entityList.stream()
      .map(this::toDTO)
      .collect(Collectors.toList());
  }

  @Override
  public List<NewsCategoryEntity> toEntityList(List<NewsCategoryDTO> dtoList) {
    return dtoList.stream()
      .map(this::toEntity)
      .collect(Collectors.toList());
  }
}
