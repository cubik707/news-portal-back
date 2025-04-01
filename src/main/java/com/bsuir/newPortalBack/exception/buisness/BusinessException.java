package com.bsuir.newPortalBack.exception.buisness;

public class BusinessException extends RuntimeException {
  private final String errorCode;

  public BusinessException(String message, String errorCode) {
    super(message);
    this.errorCode = errorCode;
  }

  public String getErrorCode() {
    return errorCode;
  }
}
