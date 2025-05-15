package com.bsuir.newPortalBack.mapper;

import com.bsuir.newPortalBack.dto.newsCategory.NewsCategoryCreateDTO;
import com.bsuir.newPortalBack.entities.NewsCategoryEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NewsCategoryCreateMapper implements BaseMapper<NewsCategoryEntity, NewsCategoryCreateDTO> {

  @Override
  public NewsCategoryCreateDTO toDTO(NewsCategoryEntity entity) {
    if (entity == null) {
      return null;
    }
    return NewsCategoryCreateDTO.builder()
      .name(entity.getName())
      .build();
  }

  @Override
  public NewsCategoryEntity toEntity(NewsCategoryCreateDTO dto) {
    if (dto == null) {
      return null;
    }
    return NewsCategoryEntity.builder()
      .name(dto.getName())
      .build();
  }

  @Override
  public List<NewsCategoryCreateDTO> toDTOList(List<NewsCategoryEntity> entityList) {
    return entityList.stream().map(this::toDTO).collect(Collectors.toList());
  }

  @Override
  public List<NewsCategoryEntity> toEntityList(List<NewsCategoryCreateDTO> dtoList) {
    return dtoList.stream().map(this::toEntity).collect(Collectors.toList());
  }
}
