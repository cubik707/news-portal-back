package com.bsuir.newPortalBack.controller;

import com.bsuir.newPortalBack.dto.response.SuccessResponseDTO;
import com.bsuir.newPortalBack.dto.user.UserRegistrationDTO;
import com.bsuir.newPortalBack.dto.user.UserResponseDTO;
import com.bsuir.newPortalBack.exception.buisness.BusinessException;
import com.bsuir.newPortalBack.mapper.UserResponseMapper;
import com.bsuir.newPortalBack.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@Tag(name="user")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final UserResponseMapper userResponseMapper;

  @PreAuthorize("hasAuthority('ADMIN')")
  @GetMapping
  public ResponseEntity<SuccessResponseDTO> getAllUsers() {
    List<UserResponseDTO> users = userService.getAllUsers();
    return ResponseEntity.ok(
      SuccessResponseDTO.create(
        HttpStatus.OK,
        "Список пользователей получен успешно",
        users
      )
    );
  }

  @GetMapping("/{id}")
  public ResponseEntity<SuccessResponseDTO> getUserById(@PathVariable int id) {
    UserResponseDTO user = userService.getUserById(id);
    return ResponseEntity.ok(
      SuccessResponseDTO.create(
        HttpStatus.OK,
        "Пользователь найден",
        user
      )
    );
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @PostMapping
  public ResponseEntity<SuccessResponseDTO> createUser(@RequestBody UserRegistrationDTO userRegistrationDTO) {
    UserResponseDTO createdUser = userResponseMapper.toDTO(userService.createUser(userRegistrationDTO));
    return ResponseEntity.status(HttpStatus.CREATED).body(
      SuccessResponseDTO.create(
        HttpStatus.CREATED,
        "Пользователь успешно создан",
        createdUser
      )
    );
  }

  @PutMapping("/{id}")
  public ResponseEntity<SuccessResponseDTO> updateUser(@PathVariable int id, @RequestBody UserRegistrationDTO userRegistrationDTO) {
    UserResponseDTO updatedUser = userService.updateUser(id, userRegistrationDTO);
    return ResponseEntity.ok(
      SuccessResponseDTO.create(
        HttpStatus.OK,
        "Пользователь успешно обновлён",
        updatedUser
      )
    );
  }

  @PatchMapping("/{id}")
  public ResponseEntity<SuccessResponseDTO> updateUserField (@PathVariable int id, @RequestBody Map<String, Object> updateField) {
    if (updateField.size() != 1) {
      throw new BusinessException("Можно обновить только одно поле за раз", "ONLY_ONE_FIELD");
    }
    userService.updateUserField(id, updateField);
    return ResponseEntity.ok(
      SuccessResponseDTO.create(
        HttpStatus.OK,
        "Поле пользователя успешно обновлено",
        null
      ));
  }


  @PreAuthorize("hasAuthority('ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<SuccessResponseDTO> deleteUser(@PathVariable int id) {
    userService.deleteUser(id);
    return ResponseEntity.ok(
      SuccessResponseDTO.create(
        HttpStatus.OK,
        "Пользователь с ID " + id + " успешно удалён",
        null
      )
    );
  }
}
