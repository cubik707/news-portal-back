package com.bsuir.newPortalBack.service;

import com.bsuir.newPortalBack.dto.UserDTO;
import com.bsuir.newPortalBack.entities.RoleEntity;
import com.bsuir.newPortalBack.entities.UserEntity;
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
  private final UserRepository userRepo;
  private final RoleRepository roleRepo;
  private final PasswordEncoder passwordEncoder;
  private final UserMapper userMapper;

  public UserEntity register(UserDTO userDTO) {
    if (userRepo.existsByUsername(userDTO.getUsername())) {
      throw new UserAlreadyExistsException(userDTO.getUsername());
    }

    UserEntity user = userMapper.toEntity(userDTO);
    user.setPasswordHash(passwordEncoder.encode(userDTO.getPassword()));

    // Assign default role
    RoleEntity userRole = roleRepo.findByName(UserRole.USER)
      .orElseThrow(() -> new RoleNotFoundException(UserRole.USER.name()));
    user.getRoles().add(userRole);

    System.out.println("userInfo 📌:" + user.getUserInfo().getPosition());

    user = userRepo.save(user);

    return user;
  }

  public Optional<UserEntity> findByUsername(String username) {
    return userRepo.findByUsername(username);
  }

  public List<UserEntity> getAllUsers() {
    return userRepo.findAll();
  }

  public UserEntity assignRole(String username, UserRole roleName) {
    UserEntity user = userRepo.findByUsername(username)
      .orElseThrow(() -> new RuntimeException("User not found"));
    RoleEntity role = roleRepo.findByName(roleName)
      .orElseThrow(() -> new RuntimeException("Role not found"));

    user.getRoles().add(role);
    return userRepo.save(user);
  }
}
