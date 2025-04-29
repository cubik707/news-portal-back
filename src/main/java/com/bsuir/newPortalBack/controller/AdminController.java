package com.bsuir.newPortalBack.controller;

import com.bsuir.newPortalBack.dto.response.SuccessResponseDTO;
import com.bsuir.newPortalBack.dto.user.UpdateRoleRequestDTO;
import com.bsuir.newPortalBack.dto.user.UserResponseDTO;
import com.bsuir.newPortalBack.enums.UserRole;
import com.bsuir.newPortalBack.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Tag(name = "admin")
@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

  private final UserService userService;

  @PatchMapping("/user/{id}/approve")
  public ResponseEntity<SuccessResponseDTO> approveUser(@PathVariable int id) {
    UserResponseDTO user = userService.approveUser(id);
    return ResponseEntity.ok(
      SuccessResponseDTO.create(
        HttpStatus.OK,
        "Пользователь одобрен",
        user
      )
    );
  }

  @PatchMapping("/user/{id}/roles")
  public ResponseEntity<SuccessResponseDTO> updateUserRoles(
    @PathVariable int id,
    @RequestBody UpdateRoleRequestDTO req
    ) {
    try {
      UserRole role = UserRole.valueOf(req.getRole().toUpperCase());
      UserResponseDTO user = userService.assignRole(id, role);
      return ResponseEntity.ok(
        SuccessResponseDTO.create(
          HttpStatus.OK,
          "Роли пользователя обновлены",
          user
        )
      );
    } catch (IllegalArgumentException e) {
      return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(SuccessResponseDTO.create(
          HttpStatus.BAD_REQUEST,
          "Некорректная роль: " + req.getRole(),
          null
        ));
    }
  }

  @DeleteMapping("/user/{id}/roles")
  public ResponseEntity<SuccessResponseDTO> removeUserRole(
    @PathVariable int id,
    @RequestBody UpdateRoleRequestDTO req
  ) {
    try {
      UserRole userRole = UserRole.valueOf(req.getRole().toUpperCase());
      UserResponseDTO user = userService.removeRole(id, userRole);
      return ResponseEntity.ok(
        SuccessResponseDTO.create(
          HttpStatus.OK,
          "Роль пользователя удалена",
          user
        )
      );
    } catch (IllegalArgumentException e) {
      return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(SuccessResponseDTO.create(
          HttpStatus.BAD_REQUEST,
          "Некорректная роль: " + req.getRole(),
          null
        ));
    }
  }
}
