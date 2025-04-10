package com.bsuir.newPortalBack.exception.buisness;

public class UserNotFoundException extends BusinessException {
  private static final String DEFAULT_ERROR_CODE = "USER_NOT_FOUND";

  public UserNotFoundException(String username) {
    super("Пользователь с логином '" + username + "' не найден", DEFAULT_ERROR_CODE);
  }

  public UserNotFoundException(int id) {
    super("Пользователь с id '" + id + "' не найден", DEFAULT_ERROR_CODE);
  }
}
