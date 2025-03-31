package com.bsuir.newPortalBack.service;

import com.bsuir.newPortalBack.entities.RoleEntity;
import com.bsuir.newPortalBack.entities.UserEntity;
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

  public UserEntity registerUser(String username, String password) {
    if (userRepo.existsByUsername(username)) {
      throw new RuntimeException("User already exists");
    }

    UserEntity user = new UserEntity();
    user.setUsername(username);
    user.setPasswordHash(passwordEncoder.encode(password));

    // Назначаем дефолтную роль
    RoleEntity userRole = roleRepo.findByName("USER")
      .orElseThrow(() -> new RuntimeException("Role USER not found"));
    user.getRoles().add(userRole);

    return userRepo.save(user);
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
