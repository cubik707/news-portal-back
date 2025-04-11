package com.bsuir.newPortalBack.controller;

import com.bsuir.newPortalBack.dto.response.SuccessResponseDTO;
import com.bsuir.newPortalBack.dto.user.UserRegistrationDTO;
import com.bsuir.newPortalBack.dto.user.UserResponseDTO;
import com.bsuir.newPortalBack.mapper.UserResponseMapper;
import com.bsuir.newPortalBack.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final UserResponseMapper userResponseMapper;

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

  @PostMapping
  public ResponseEntity<SuccessResponseDTO> createUser(@RequestBody UserRegistrationDTO userRegistrationDTO) {
    UserResponseDTO createdUser = userResponseMapper.toDTO(userService.register(userRegistrationDTO));
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
