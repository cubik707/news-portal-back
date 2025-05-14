package com.bsuir.newPortalBack.controller;

import com.bsuir.newPortalBack.config.jwt.JwtUtil;
import com.bsuir.newPortalBack.dto.auth.AuthRequest;
import com.bsuir.newPortalBack.dto.auth.AuthResponse;
import com.bsuir.newPortalBack.dto.response.ErrorResponseDTO;
import com.bsuir.newPortalBack.dto.response.SuccessResponseDTO;
import com.bsuir.newPortalBack.dto.user.UserRegistrationDTO;
import com.bsuir.newPortalBack.dto.user.UserResponseDTO;
import com.bsuir.newPortalBack.entities.UserEntity;
import com.bsuir.newPortalBack.mapper.UserResponseMapper;
import com.bsuir.newPortalBack.security.UserDetailsServiceImpl;
import com.bsuir.newPortalBack.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Tag(name = "auth")
@RestController
@RequiredArgsConstructor
public class AuthController {
  private final AuthenticationManager authenticationManager;
  private final UserDetailsServiceImpl userDetailsService;
  private final JwtUtil jwtUtil;
  private final UserService userService;
  private final UserResponseMapper userResponseMapper;

  @PostMapping("/register")
  public ResponseEntity<?> registerUser(@RequestBody UserRegistrationDTO userRegistrationDTO) {
    userService.register(userRegistrationDTO);

    return ResponseEntity.ok(
      SuccessResponseDTO.create(
        HttpStatus.OK,
        "Пользователь успешно зарегистрирован",
        "Заявка на регистрацию в портале успешно отправлена! Дождитесь, пока администратор ее одобрит."
      ));
  }

  @PostMapping("/auth")
  public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authRequest) {
    authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        authRequest.getUsername(),
        authRequest.getPassword()
      )
    );

    final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
    final String jwt = jwtUtil.generateToken(userDetails);

    return ResponseEntity.ok(
      SuccessResponseDTO.create(
        HttpStatus.OK,
        "Аутентификация прошла успешно",
        new AuthResponse(jwt)
      )
    );
  }

  @GetMapping("/me")
  public ResponseEntity<?> me() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();
    UserEntity user = userService.findByUsername(username);
    UserResponseDTO response = userResponseMapper.toDTO(user);

    return ResponseEntity.ok(
      SuccessResponseDTO.create(
        HttpStatus.OK,
        "Информация о текущем пользователе",
        response
      )
    );
  }

  @GetMapping("/verify-token")
  public ResponseEntity<?> verifyToken(@RequestHeader("Authorization") String authHeader) {
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      return createUnauthorizedResponse("Неверный формат токена");
    }

    String jwt = authHeader.substring(7);

    try {
      String username = jwtUtil.extractUsername(jwt);
      UserDetails userDetails = userDetailsService.loadUserByUsername(username);

      if (!jwtUtil.validateToken(jwt)) {
        return createUnauthorizedResponse("Токен невалиден");
      }

      return createSuccessResponse();

    } catch (ExpiredJwtException ex) {
      return createUnauthorizedResponse("Токен истек");
    } catch (JwtException | AuthenticationException ex) {
      return createUnauthorizedResponse("Ошибка аутентификации");
    }
  }

  private ResponseEntity<SuccessResponseDTO> createSuccessResponse() {
    return ResponseEntity.ok(
      SuccessResponseDTO.create(
        HttpStatus.OK,
        "Токен валиден",
        null
      )
    );
  }

  private ResponseEntity<ErrorResponseDTO> createUnauthorizedResponse(String message) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
      .body(ErrorResponseDTO.create(
        HttpStatus.UNAUTHORIZED,
        message
      ));
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<?> handleBadCredentialsException() {
    System.out.println("✨");
    return ResponseEntity
      .status(HttpStatus.UNAUTHORIZED)
      .contentType(MediaType.APPLICATION_JSON)
      .body(
      ErrorResponseDTO.create(
        HttpStatus.UNAUTHORIZED,
        "Неверные учетные данные"
      ));
  }

}

