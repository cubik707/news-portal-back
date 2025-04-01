package com.bsuir.newPortalBack.service;

import com.bsuir.newPortalBack.dto.UserDTO;
import com.bsuir.newPortalBack.entities.RoleEntity;
import com.bsuir.newPortalBack.entities.UserEntity;
import com.bsuir.newPortalBack.entities.UserInfoEntity;
import com.bsuir.newPortalBack.enums.UserRole;
import com.bsuir.newPortalBack.exception.buisness.RoleNotFoundException;
import com.bsuir.newPortalBack.exception.buisness.UserAlreadyExistsException;
import com.bsuir.newPortalBack.mapper.UserMapper;
import com.bsuir.newPortalBack.repository.RoleRepository;
import com.bsuir.newPortalBack.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
  private UserRepository userRepo;
  private RoleRepository roleRepo;
  private PasswordEncoder passwordEncoder;
  private UserMapper userMapper;

  public UserEntity register(UserDTO userDTO) {
    if (userRepo.existsByUsername(userDTO.getUsername())) {
      throw new UserAlreadyExistsException(userDTO.getUsername());
    }

    UserEntity user = userMapper.toEntity(userDTO);
    user.setPasswordHash(passwordEncoder.encode(userDTO.getPassword()));

    // Assign default role
    RoleEntity userRole = roleRepo.findByName(UserRole.USER.toString())
      .orElseThrow(() -> new RoleNotFoundException(UserRole.USER.toString()));
    user.getRoles().add(userRole);

    user = userRepo.save(user);


    return user;
  }

  public Optional<UserEntity> findByUsername(String username) {
    return userRepo.findByUsername(username);
  }

  public List<UserEntity> getAllUsers() {
    return userRepo.findAll();
  }

  public UserEntity assignRole(String username, String roleName) {
    UserEntity user = userRepo.findByUsername(username)
      .orElseThrow(() -> new RuntimeException("User not found"));
    RoleEntity role = roleRepo.findByName(roleName)
      .orElseThrow(() -> new RuntimeException("Role not found"));

    user.getRoles().add(role);
    return userRepo.save(user);
  }
}
