package com.bsuir.newPortalBack.mapper;

import com.bsuir.newPortalBack.dto.UserDTO;
import com.bsuir.newPortalBack.entities.UserEntity;
import com.bsuir.newPortalBack.entities.UserInfoEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper implements BaseMapper<UserEntity, UserDTO> {
  @Override
  public UserDTO toDTO(UserEntity entity) {
    if (entity == null) {
      return null;
    }
    UserDTO.Builder dtoBuilder = UserDTO.builder()
      .username(entity.getUsername())
      .email(entity.getEmail())
      .password(entity.getPasswordHash());

    if (entity.getUserInfo() != null) {
      dtoBuilder
        .lastName(entity.getUserInfo().getLastName())
        .firstName(entity.getUserInfo().getFirstName())
        .surname(entity.getUserInfo().getSurname())
        .position(entity.getUserInfo().getPosition())
        .department(entity.getUserInfo().getDepartment());
    }

    return dtoBuilder.build();
  }

  @Override
  public UserEntity toEntity(UserDTO dto) {
    if (dto == null) {
      return null;
    }

    UserInfoEntity userInfo = new UserInfoEntity.Builder()
      .lastName(dto.getLastName())
      .firstName(dto.getFirstName())
      .surname(dto.getSurname())
      .position(dto.getPosition())
      .department(dto.getDepartment())
      .build();

    return UserEntity.builder()
      .username(dto.getUsername())
      .email(dto.getEmail())
      .userInfo(userInfo)
      .build();
  }

  @Override
  public List<UserDTO> toDTOList(List<UserEntity> entityList) {
    if (entityList == null) {
      return List.of();
    }
    return entityList.stream().map(this::toDTO).collect(Collectors.toList());
  }

  @Override
  public List<UserEntity> toEntityList(List<UserDTO> dtoList) {
    if (dtoList == null) {
      return List.of();
    }
    return dtoList.stream().map(this::toEntity).collect(Collectors.toList());
  }
}

