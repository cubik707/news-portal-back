package com.bsuir.newPortalBack.exception.controller;

import com.bsuir.newPortalBack.dto.response.ErrorResponseDTO;
import com.bsuir.newPortalBack.exception.buisness.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<ErrorResponseDTO> handleBusinessException(BusinessException ex) {
    return ResponseEntity
      .status(HttpStatus.BAD_REQUEST)
      .body(ErrorResponseDTO.create(HttpStatus.BAD_REQUEST, ex.getMessage()));
  }

  @ExceptionHandler(UserAlreadyExistsException.class)
  public ResponseEntity<ErrorResponseDTO> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
    return ResponseEntity
      .status(HttpStatus.CONFLICT)
      .contentType(MediaType.APPLICATION_JSON)
      .body(ErrorResponseDTO.create(
      HttpStatus.CONFLICT,
      ex.getMessage()
    ));
  }

  @ExceptionHandler(UserEmailAlreadyExistsException.class)
  public ResponseEntity<ErrorResponseDTO> handleUserAlreadyExistsException(UserEmailAlreadyExistsException ex) {
    return ResponseEntity
      .status(HttpStatus.CONFLICT)
      .contentType(MediaType.APPLICATION_JSON)
      .body(ErrorResponseDTO.create(
        HttpStatus.CONFLICT,
        ex.getMessage()
      ));
  }

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<ErrorResponseDTO> handleUserNotFoundException(
    UserNotFoundException ex, WebRequest request) {

    return ResponseEntity
      .status(HttpStatus.NOT_FOUND)
      .contentType(MediaType.APPLICATION_JSON)
      .body(
        ErrorResponseDTO.create(
          HttpStatus.NOT_FOUND,
          ex.getMessage()
        )
      );
  }

  @ExceptionHandler(UserNotApprovedException.class)
  public ResponseEntity<ErrorResponseDTO> handleUserNotApproved(UserNotApprovedException ex) {
    return ResponseEntity
      .status(HttpStatus.NOT_FOUND)
      .contentType(MediaType.APPLICATION_JSON)
      .body(
        ErrorResponseDTO.create(
          HttpStatus.FORBIDDEN,
          ex.getMessage()
        )
      );
  }
}
