package com.bsuir.newPortalBack.exception.controller;

import com.bsuir.newPortalBack.dto.response.ErrorResponseDTO;
import com.bsuir.newPortalBack.exception.buisness.UserAlreadyExistsException;
import com.bsuir.newPortalBack.exception.buisness.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(UserAlreadyExistsException.class)
  public ResponseEntity<ErrorResponseDTO> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorResponseDTO.create(
      HttpStatus.CONFLICT,
      ex.getMessage()
    ));
  }

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<ErrorResponseDTO> handleUserNotFoundException(
    UserNotFoundException ex, WebRequest request) {

    return ResponseEntity.status(HttpStatus.NOT_FOUND)
      .body(
        ErrorResponseDTO.create(
          HttpStatus.NOT_FOUND,
          ex.getMessage()
        )
      );
  }
}
