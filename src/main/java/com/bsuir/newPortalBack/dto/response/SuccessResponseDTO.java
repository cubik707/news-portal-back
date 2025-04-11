package com.bsuir.newPortalBack.dto.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

@Getter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class SuccessResponseDTO extends BaseResponseDTO {
  private Object data;

  // Simple implementation of the Factory Method pattern
  public static SuccessResponseDTO create(HttpStatus status, String message, Object data) {
    return SuccessResponseDTO.builder()
      .status(status.value())
      .message(message)
      .data(data)
      .build();
  }

}
