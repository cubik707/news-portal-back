package com.bsuir.newPortalBack.mapper;

import com.bsuir.newPortalBack.dto.tag.TagCreateDTO;
import com.bsuir.newPortalBack.entities.TagEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TagCreateMapper implements BaseMapper<TagEntity, TagCreateDTO> {
  @Override
  public TagCreateDTO toDTO(TagEntity entity) {
    if (entity == null) {
      return null;
    }
    return TagCreateDTO.builder()
      .name(entity.getName())
      .build();
  }

  @Override
  public TagEntity toEntity(TagCreateDTO dto) {
    if (dto == null) {
      return null;
    }
    return TagEntity.builder()
      .name(dto.getName())
      .news(new ArrayList<>())
      .build();
  }

  @Override
  public List<TagCreateDTO> toDTOList(List<TagEntity> entityList) {
    return entityList.stream()
      .map(this::toDTO)
      .collect(Collectors.toList());
  }

  @Override
  public List<TagEntity> toEntityList(List<TagCreateDTO> dtoList) {
    return dtoList.stream()
      .map(this::toEntity)
      .collect(Collectors.toList());
  }
}
