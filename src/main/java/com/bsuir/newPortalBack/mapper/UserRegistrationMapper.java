package com.bsuir.newPortalBack.mapper;

import com.bsuir.newPortalBack.dto.UserRegistrationDTO;
import com.bsuir.newPortalBack.entities.UserEntity;
import com.bsuir.newPortalBack.entities.UserInfoEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserRegistrationMapper implements BaseMapper<UserEntity, UserRegistrationDTO> {
  @Override
  public UserRegistrationDTO toDTO(UserEntity entity) {
    if (entity == null) {
      return null;
    }
    UserRegistrationDTO.Builder dtoBuilder = UserRegistrationDTO.builder()
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
  public UserEntity toEntity(UserRegistrationDTO dto) {
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
    UserEntity user = UserEntity.builder()
      .username(dto.getUsername())
      .email(dto.getEmail())
      .userInfo(userInfo)
      .build();

    // Setting the reverse relationship
    if (userInfo != null) {
      userInfo.setUser(user);
    }

    return user;
  }

  @Override
  public List<UserRegistrationDTO> toDTOList(List<UserEntity> entityList) {
    if (entityList == null) {
      return List.of();
    }
    return entityList.stream().map(this::toDTO).collect(Collectors.toList());
  }

  @Override
  public List<UserEntity> toEntityList(List<UserRegistrationDTO> dtoList) {
    if (dtoList == null) {
      return List.of();
    }
    return dtoList.stream().map(this::toEntity).collect(Collectors.toList());
  }
}

