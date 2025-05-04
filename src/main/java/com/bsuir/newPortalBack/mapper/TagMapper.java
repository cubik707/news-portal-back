package com.bsuir.newPortalBack.mapper;

import com.bsuir.newPortalBack.dto.tag.TagDTO;
import com.bsuir.newPortalBack.entities.TagEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TagMapper implements BaseMapper<TagEntity, TagDTO> {
  @Override
  public TagDTO toDTO(TagEntity entity) {
    if (entity == null) {
      return null;
    }
    return TagDTO.builder()
      .id(entity.getId())
      .name(entity.getName())
      .build();
  }

  @Override
  public TagEntity toEntity(TagDTO dto) {
    if (dto == null) {
      return null;
    }
    return TagEntity.builder()
      .id(dto.getId())
      .name(dto.getName())
      .news(new ArrayList<>())
      .build();
  }

  @Override
  public List<TagDTO> toDTOList(List<TagEntity> entityList) {
    return entityList.stream()
      .map(this::toDTO)
      .collect(Collectors.toList());
  }

  @Override
  public List<TagEntity> toEntityList(List<TagDTO> dtoList) {
    return dtoList.stream()
      .map(this::toEntity)
      .collect(Collectors.toList());
  }
}
