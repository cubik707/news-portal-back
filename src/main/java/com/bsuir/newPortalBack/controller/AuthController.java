package com.bsuir.newPortalBack.controller;

import com.bsuir.newPortalBack.config.jwt.JwtUtil;
import com.bsuir.newPortalBack.dto.AuthRequest;
import com.bsuir.newPortalBack.dto.AuthResponse;
import com.bsuir.newPortalBack.dto.UserDTO;
import com.bsuir.newPortalBack.entities.UserEntity;
import com.bsuir.newPortalBack.exception.buisness.UserAlreadyExistsException;
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
  public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
    try {
      UserEntity registeredUser = userService.register(userDTO);
      UserDetails userDetails = userDetailsService.loadUserByUsername(registeredUser.getUsername());
      String jwtToken = jwtUtil.generateToken(userDetails);
      return ResponseEntity.ok(new AuthResponse(jwtToken));
    } catch (UserAlreadyExistsException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
  }

  @PostMapping("/auth")
  public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authRequest) {
    try {
      authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
          authRequest.getUsername(),
          authRequest.getPassword()
        )
      );
    } catch (BadCredentialsException e) {
      return ResponseEntity.badRequest().body("Неверное имя пользователя или пароль");
    }

    final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
    final String jwt = jwtUtil.generateToken(userDetails);

    return ResponseEntity.ok(new AuthResponse(jwt));
  }


  @ExceptionHandler(UserAlreadyExistsException.class)
  public ResponseEntity<String> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<String> handleBadCredentialsException() {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Неверные учетные данные");
  }
}
