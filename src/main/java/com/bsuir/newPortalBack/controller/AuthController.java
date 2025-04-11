package com.bsuir.newPortalBack.controller;

import com.bsuir.newPortalBack.config.jwt.JwtUtil;
import com.bsuir.newPortalBack.dto.auth.AuthRequest;
import com.bsuir.newPortalBack.dto.auth.AuthResponse;
import com.bsuir.newPortalBack.dto.response.ErrorResponseDTO;
import com.bsuir.newPortalBack.dto.response.SuccessResponseDTO;
import com.bsuir.newPortalBack.dto.user.UserRegistrationDTO;
import com.bsuir.newPortalBack.entities.UserEntity;
import com.bsuir.newPortalBack.security.UserDetailsServiceImpl;
import com.bsuir.newPortalBack.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
  private final AuthenticationManager authenticationManager;
  private final UserDetailsServiceImpl userDetailsService;
  private final JwtUtil jwtUtil;
  private final UserService userService;

  @PostMapping("/register")
  public ResponseEntity<?> registerUser(@RequestBody UserRegistrationDTO userRegistrationDTO) {
    UserEntity registeredUser = userService.register(userRegistrationDTO);
    UserDetails userDetails = userDetailsService.loadUserByUsername(registeredUser.getUsername());
    String jwtToken = jwtUtil.generateToken(userDetails);

    return ResponseEntity.ok(
      SuccessResponseDTO.create(
        HttpStatus.OK,
        "Пользователь успешно зарегистрирован",
        new AuthResponse(jwtToken))
    );
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


  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<?> handleBadCredentialsException() {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
      ErrorResponseDTO.create(
      HttpStatus.UNAUTHORIZED,
      "Неверные учетные данные"
    ));
  }
}
