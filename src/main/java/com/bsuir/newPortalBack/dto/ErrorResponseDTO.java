package com.bsuir.newPortalBack.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorResponseDTO {
  // Exact timestamp when the error occurred
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
  private LocalDateTime timestamp;

  // HTTP status code
  private int status;

  // Error type name (e.g., "Not Found")
  private String error;

  // Detailed error description
  private String message;

  // API endpoint path where error occurred
  private String path;

  // Additional debug details (optional)
  private String details;
}