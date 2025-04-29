package com.bsuir.newPortalBack.service;

import com.bsuir.newPortalBack.dto.user.UserRegistrationDTO;
import com.bsuir.newPortalBack.dto.user.UserResponseDTO;
import com.bsuir.newPortalBack.entities.RoleEntity;
import com.bsuir.newPortalBack.entities.UserEntity;
import com.bsuir.newPortalBack.enums.UserRole;
import com.bsuir.newPortalBack.exception.buisness.*;
import com.bsuir.newPortalBack.mapper.UserRegistrationMapper;
import com.bsuir.newPortalBack.mapper.UserResponseMapper;
import com.bsuir.newPortalBack.repository.RoleRepository;
import com.bsuir.newPortalBack.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
  private final EmailService emailService;

  @Value("${spring.mail.username}")
  private String systemEmail;

  public void register(UserRegistrationDTO userRegistrationDTO) {
    UserEntity user = this.createUser(userRegistrationDTO);;

    emailService.sendHtmlMessage(
      user.getEmail(),
      "Регистрация в News Portal",
      """
      <h2>Здравствуйте, %s!</h2>
      <p>Вы успешно зарегистрировались на платформе <strong>News Portal</strong>.</p>
      <p>Сейчас ваша заявка на регистрацию отправлена администратору и ожидает подтверждения.</p>
      <p>Как только администратор одобрит вашу учетную запись, вы сможете войти в систему.</p>
      <br>
      <p style="color:gray; font-size:12px;">Если вы не регистрировались — просто проигнорируйте это письмо.</p>
      """.formatted(user.getUserInfo().getFirstName())
    );
  }

  public UserEntity findByUsername(String username) {
    return userRepo.findByUsername(username)
      .orElseThrow(() -> new UserNotFoundException(username));
  }

  public UserEntity createUser(UserRegistrationDTO userRegistrationDTO) {
    if (userRepo.existsByUsername(userRegistrationDTO.getUsername())) {
      throw new UserAlreadyExistsException(userRegistrationDTO.getUsername());
    }
    if(userRepo.existsByEmail(userRegistrationDTO.getEmail())) {
      throw new UserEmailAlreadyExistsException(userRegistrationDTO.getEmail());
    }
    if (userRegistrationDTO.getEmail().equalsIgnoreCase(systemEmail)) {
      throw new BusinessException("Регистрация с системной почтой запрещена", "ERROR_EMAIL");
    }

    UserEntity user = userRegistrationMapper.toEntity(userRegistrationDTO);
    user.setPasswordHash(passwordEncoder.encode(userRegistrationDTO.getPassword()));

    // Assign default role
    RoleEntity userRole = roleRepo.findByName(UserRole.USER)
      .orElseThrow(() -> new RoleNotFoundException(UserRole.USER.name()));
    user.getRoles().add(userRole);

    return userRepo.save(user);
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

  public UserResponseDTO approveUser(int userId) {
    UserEntity user = userRepo.findById(userId)
      .orElseThrow(() -> new UserNotFoundException(userId));

    emailService.sendHtmlMessage(
      user.getEmail(),
      "Регистрация в News Portal",
      """
      <h2>Здравствуйте, %s!</h2>
      <p>Ваша учетная запись на платформе <strong>News Portal</strong> была успешно <span style="color:green;">одобрена</span> администратором.</p>
        <p>Теперь вы можете войти в систему и пользоваться всеми доступными функциями портала.</p>
        <br>
        <p style="color:gray; font-size:12px;">Если вы не подавали заявку на регистрацию, просто проигнорируйте это письмо.</p>
      """.formatted(user.getUserInfo().getFirstName())
    );

    user.setApproved(true);
    UserEntity updated = userRepo.save(user);
    return userResponseMapper.toDTO(updated);
  }

  public UserResponseDTO assignRole(int id, UserRole roleName) {
    UserEntity user = userRepo.findById(id)
      .orElseThrow(() -> new UserNotFoundException(id));

    RoleEntity role = roleRepo.findByName(roleName)
      .orElseThrow(() -> new RoleNotFoundException(roleName.name()));

    user.getRoles().add(role);
    UserEntity updatedUser = userRepo.save(user);
    return userResponseMapper.toDTO(updatedUser);
  }

  public UserResponseDTO removeRole(int id, UserRole roleName) {
    UserEntity user = userRepo.findById(id)
      .orElseThrow(() -> new UserNotFoundException(id));

    RoleEntity role = roleRepo.findByName(roleName)
      .orElseThrow(() -> new RoleNotFoundException(roleName.name()));

    if (!user.getRoles().contains(role)) {
      throw new RoleNotFoundException("У пользователя нет роли: " + roleName);
    }

    if (roleName == UserRole.USER) {
      throw new IllegalStateException("Нельзя удалить базовую роль USER");
    }

    user.getRoles().remove(role);
    UserEntity updatedUser = userRepo.save(user);
    return userResponseMapper.toDTO(updatedUser);
  }
}
