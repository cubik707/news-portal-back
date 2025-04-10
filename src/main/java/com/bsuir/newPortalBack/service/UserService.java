package com.bsuir.newPortalBack.service;

import com.bsuir.newPortalBack.dto.UserRegistrationDTO;
import com.bsuir.newPortalBack.dto.UserResponseDTO;
import com.bsuir.newPortalBack.entities.RoleEntity;
import com.bsuir.newPortalBack.entities.UserEntity;
import com.bsuir.newPortalBack.enums.UserRole;
import com.bsuir.newPortalBack.exception.buisness.RoleNotFoundException;
import com.bsuir.newPortalBack.exception.buisness.UserAlreadyExistsException;
import com.bsuir.newPortalBack.exception.buisness.UserNotFoundException;
import com.bsuir.newPortalBack.mapper.UserRegistrationMapper;
import com.bsuir.newPortalBack.mapper.UserResponseMapper;
import com.bsuir.newPortalBack.repository.RoleRepository;
import com.bsuir.newPortalBack.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepo;
  private final RoleRepository roleRepo;
  private final PasswordEncoder passwordEncoder;
  private final UserRegistrationMapper userRegistrationMapper;
  private final UserResponseMapper userResponseMapper;


  public UserEntity register(UserRegistrationDTO userRegistrationDTO) {
    if (userRepo.existsByUsername(userRegistrationDTO.getUsername())) {
      throw new UserAlreadyExistsException(userRegistrationDTO.getUsername());
    }

    UserEntity user = userRegistrationMapper.toEntity(userRegistrationDTO);
    user.setPasswordHash(passwordEncoder.encode(userRegistrationDTO.getPassword()));

    // Assign default role
    RoleEntity userRole = roleRepo.findByName(UserRole.USER)
      .orElseThrow(() -> new RoleNotFoundException(UserRole.USER.name()));
    user.getRoles().add(userRole);

    user = userRepo.save(user);

    return user;
  }

  public UserEntity findByUsername(String username) {
    return userRepo.findByUsername(username)
      .orElseThrow(() -> new UserNotFoundException(username));
  }

  public List<UserResponseDTO> getAllUsers() {
    List<UserEntity> users = userRepo.findAll();
    return userResponseMapper.toDTOList(users);
  }

  public UserResponseDTO getUserById(int id) {
    UserEntity user = userRepo.findById(id)
      .orElseThrow(() -> new UserNotFoundException(id));
    return userResponseMapper.toDTO(user);
  }

  public UserResponseDTO updateUser(int id, UserRegistrationDTO updatedUserRegistrationDTO) {
    UserEntity user = userRepo.findById(id)
      .orElseThrow(() -> new UserNotFoundException(id));

    // Update main fields
    user.setUsername(updatedUserRegistrationDTO.getUsername());
    user.setEmail(updatedUserRegistrationDTO.getEmail());
    user.getUserInfo().setFirstName(updatedUserRegistrationDTO.getFirstName());
    user.getUserInfo().setLastName(updatedUserRegistrationDTO.getLastName());
    user.getUserInfo().setSurname(updatedUserRegistrationDTO.getSurname());
    user.getUserInfo().setPosition(updatedUserRegistrationDTO.getPosition());
    user.getUserInfo().setDepartment(updatedUserRegistrationDTO.getDepartment());

    UserEntity updatedUser = userRepo.save(user);
    return userResponseMapper.toDTO(updatedUser);
  }

  public void deleteUser(int id) {
    if (!userRepo.existsById(id)) {
      throw new UserNotFoundException(id);
    }
    userRepo.deleteById(id);
  }

  public UserResponseDTO assignRole(String username, UserRole roleName) {
    UserEntity user = userRepo.findByUsername(username)
      .orElseThrow(() -> new UserNotFoundException(username));

    RoleEntity role = roleRepo.findByName(roleName)
      .orElseThrow(() -> new RoleNotFoundException(roleName.name()));

    user.getRoles().add(role);
    UserEntity updatedUser = userRepo.save(user);
    return userResponseMapper.toDTO(updatedUser);
  }
}
