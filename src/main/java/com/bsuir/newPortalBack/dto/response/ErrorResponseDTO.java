package com.bsuir.newPortalBack.dto.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
public class ErrorResponseDTO extends BaseResponseDTO {
 private String error;

 public static ErrorResponseDTO create(HttpStatus status, String message) {
   return ErrorResponseDTO.builder()
     .timestamp(LocalDateTime.now())
     .status(status.value())
     .error(status.getReasonPhrase())
     .message(message)
     .build();
 }
}