package com.bsuir.newPortalBack.exception.buisness;

public class UserAlreadyExistsException extends BusinessException {
  private static final String DEFAULT_ERROR_CODE = "USER_ALREADY_EXISTS";

  public UserAlreadyExistsException(String username) {
    super("Пользователь с логином " + username + " уже существует.", DEFAULT_ERROR_CODE);
  }
}
