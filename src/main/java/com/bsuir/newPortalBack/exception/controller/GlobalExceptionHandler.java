package com.bsuir.newPortalBack.exception.controller;

import com.bsuir.newPortalBack.dto.ErrorResponseDTO;
import com.bsuir.newPortalBack.exception.buisness.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<ErrorResponseDTO> handleUserNotFoundException(
    UserNotFoundException ex, WebRequest request) {

    return ResponseEntity.status(HttpStatus.NOT_FOUND)
      .body(ErrorResponseDTO.builder()
        .timestamp(LocalDateTime.now())
        .status(HttpStatus.NOT_FOUND.value())
        .error(HttpStatus.NOT_FOUND.getReasonPhrase())
        .message(ex.getMessage())
        .path(request.getDescription(false).replace("uri=", ""))
        .details("")
        .build());
  }
}
