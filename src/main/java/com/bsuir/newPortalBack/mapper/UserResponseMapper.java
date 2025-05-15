package com.bsuir.newPortalBack.mapper;

import com.bsuir.newPortalBack.dto.user.UserResponseDTO;
import com.bsuir.newPortalBack.entities.RoleEntity;
import com.bsuir.newPortalBack.entities.UserEntity;
import com.bsuir.newPortalBack.entities.UserInfoEntity;
import com.bsuir.newPortalBack.enums.UserRole;
import com.bsuir.newPortalBack.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserResponseMapper implements BaseMapper<UserEntity, UserResponseDTO> {

  private final RoleRepository roleRepository;

  @Override
  public UserResponseDTO toDTO(UserEntity entity) {
    if (entity == null) {
      return null;
    }

    UserResponseDTO.UserResponseDTOBuilder userResponseDTO = UserResponseDTO.builder()
      .id(entity.getId())
      .email(entity.getEmail())
      .username(entity.getUsername())
      .isApproved(entity.isApproved())
      .roles(mapRoles(entity.getRoles()));

    if(entity.getUserInfo() != null) {
      userResponseDTO
        .firstName(entity.getUserInfo().getFirstName())
        .lastName(entity.getUserInfo().getLastName())
        .surname(entity.getUserInfo().getSurname())
        .department(entity.getUserInfo().getDepartment())
        .position(entity.getUserInfo().getPosition())
        .avatarUrl(entity.getUserInfo().getAvatarUrl());
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
      .avatarUrl(dto.getAvatarUrl())
      .build();
    UserEntity user = UserEntity.builder()
      .id(dto.getId())
      .username(dto.getUsername())
      .email(dto.getEmail())
      .roles(mapRolesToEntities(dto.getRoles()))
      .userInfo(userInfo)
      .isApproved(dto.isApproved())
      .build();

    // Setting the reverse relationship
    if (userInfo != null) {
      userInfo.setUser(user);
    }
    return user;
  }

  private Set<UserRole> mapRoles(Set<RoleEntity> roleEntities) {
    if (roleEntities == null) {
      return Set.of();
    }

    return roleEntities.stream()
      .map(RoleEntity::getName)
      .collect(Collectors.toSet());
  }

  private Set<RoleEntity> mapRolesToEntities(Set<UserRole> userRoles) {
    if (userRoles == null) {
      return Set.of();
    }

    return userRoles.stream()
      .map(role -> roleRepository.findByName(role)
        .orElseThrow(() -> new RuntimeException("Роль не найдена: " + role)))
      .collect(Collectors.toSet());
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
