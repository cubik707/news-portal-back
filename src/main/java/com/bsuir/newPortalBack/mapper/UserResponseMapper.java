package com.bsuir.newPortalBack.mapper;

import com.bsuir.newPortalBack.dto.user.UserResponseDTO;
import com.bsuir.newPortalBack.entities.UserEntity;
import com.bsuir.newPortalBack.entities.UserInfoEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserResponseMapper implements BaseMapper<UserEntity, UserResponseDTO> {
  @Override
  public UserResponseDTO toDTO(UserEntity entity) {
    if (entity == null) {
      return null;
    }

    UserResponseDTO.UserResponseDTOBuilder userResponseDTO = UserResponseDTO.builder()
      .id(entity.getId())
      .email(entity.getEmail())
      .username(entity.getUsername());

    if(entity.getUserInfo() != null) {
      userResponseDTO
        .firstName(entity.getUserInfo().getFirstName())
        .lastName(entity.getUserInfo().getLastName())
        .surname(entity.getUserInfo().getSurname())
        .department(entity.getUserInfo().getDepartment())
        .position(entity.getUserInfo().getPosition());
    }
    return userResponseDTO.build();
  }

  @Override
  public UserEntity toEntity(UserResponseDTO dto) {
    if(dto == null) {
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
      .id(dto.getId())
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
  public List<UserResponseDTO> toDTOList(List<UserEntity> entityList) {
    if (entityList == null) {
      return List.of();
    }

    return entityList.stream().map(this::toDTO).collect(Collectors.toList());
  }

  @Override
  public List<UserEntity> toEntityList(List<UserResponseDTO> dtoList) {
    if(dtoList == null) {
      return List.of();
    }
    return dtoList.stream().map(this::toEntity).collect(Collectors.toList());
  }
}
