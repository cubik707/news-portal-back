package com.bsuir.newPortalBack.controller;

import com.bsuir.newPortalBack.dto.UserDTO;
import com.bsuir.newPortalBack.entities.UserEntity;
import com.bsuir.newPortalBack.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
  private final UserService userService;

  @PostMapping("/register")
  public ResponseEntity<UserEntity> registerUser(@RequestBody UserDTO userDTO) {
    UserEntity registeredUser = userService.register(userDTO);
    return ResponseEntity.ok(registeredUser);
  }
}
