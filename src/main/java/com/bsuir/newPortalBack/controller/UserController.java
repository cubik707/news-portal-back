package com.bsuir.newPortalBack.controller;

import com.bsuir.newPortalBack.dto.SuccessResponseDTO;
import com.bsuir.newPortalBack.dto.UserRegistrationDTO;
import com.bsuir.newPortalBack.dto.UserResponseDTO;
import com.bsuir.newPortalBack.mapper.UserResponseMapper;
import com.bsuir.newPortalBack.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final UserResponseMapper userResponseMapper;

  @GetMapping
  public List<UserResponseDTO> getAllUsers() {
    return userService.getAllUsers();
  }

  @GetMapping("/{id}")
  public UserResponseDTO getUserById(@PathVariable int id) {
    return userService.getUserById(id);
  }

  @PostMapping
  public UserResponseDTO createUser(@RequestBody UserRegistrationDTO userRegistrationDTO) {
    return userResponseMapper.toDTO(userService.register(userRegistrationDTO));
  }

  @PutMapping("/{id}")
  public UserResponseDTO updateUser(@PathVariable int id, @RequestBody UserRegistrationDTO userRegistrationDTO) {
    return userService.updateUser(id, userRegistrationDTO);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<SuccessResponseDTO> deleteUser(@PathVariable int id) {
    userService.deleteUser(id);

    return ResponseEntity.ok()
      .body(SuccessResponseDTO.builder()
        .timestamp(LocalDateTime.now())
        .message("Пользователь с ID " + id + " успешно удален")
        .build());
  }
}
