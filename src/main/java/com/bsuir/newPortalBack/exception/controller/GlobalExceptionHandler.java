package com.bsuir.newPortalBack.exception.controller;

import com.bsuir.newPortalBack.dto.response.ErrorResponseDTO;
import com.bsuir.newPortalBack.exception.buisness.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.FileNotFoundException;
import java.io.IOException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ErrorResponseDTO> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
    ErrorResponseDTO error = ErrorResponseDTO.create(
      HttpStatus.BAD_REQUEST,
      "Data error: " + ex.getMostSpecificCause().getMessage()
    );
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponseDTO> handleAllExceptions(Exception ex) {
    ErrorResponseDTO error = ErrorResponseDTO.create(
      HttpStatus.INTERNAL_SERVER_ERROR,
      "Internal server error: " + ex.getMessage()
    );
    return ResponseEntity.internalServerError().body(error);
  }

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

  @ExceptionHandler(NewsNotFoundException.class)
  public ResponseEntity<ErrorResponseDTO> handleNewsNotFoundException(
    NewsNotFoundException ex, WebRequest request) {
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

  @ExceptionHandler(TagNotFoundException.class)
  public ResponseEntity<ErrorResponseDTO> handleTagNotFoundException(
    TagNotFoundException ex, WebRequest request) {
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

  @ExceptionHandler(NewsCategoryNotFoundException.class)
  public ResponseEntity<ErrorResponseDTO> handleNewsCategoryNotFoundException(
    NewsCategoryNotFoundException ex, WebRequest request) {
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


  @ExceptionHandler({IllegalArgumentException.class, SecurityException.class})
  public ResponseEntity<ErrorResponseDTO> handleBadRequest(Exception ex) {
    return ResponseEntity.badRequest().body(
      ErrorResponseDTO.create(HttpStatus.BAD_REQUEST, ex.getMessage())
    );
  }

  @ExceptionHandler(FileNotFoundException.class)
  public ResponseEntity<ErrorResponseDTO> handleNotFound(Exception ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
      ErrorResponseDTO.create(HttpStatus.NOT_FOUND, ex.getMessage())
    );
  }

  @ExceptionHandler(IOException.class)
  public ResponseEntity<ErrorResponseDTO> handleIOExceptions(Exception ex) {
    return ResponseEntity.internalServerError().body(
      ErrorResponseDTO.create(
        HttpStatus.INTERNAL_SERVER_ERROR,
        "Ошибка при работе с файловой системой")
    );
  }
}
