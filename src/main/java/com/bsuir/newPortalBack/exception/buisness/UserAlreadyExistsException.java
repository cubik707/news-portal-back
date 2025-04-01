package com.bsuir.newPortalBack.exception.buisness;

public class UserAlreadyExistsException extends BusinessException {
  public UserAlreadyExistsException(String username) {
    super("Пользователь с логином " + username + " уже существует.", "USER_ALREADY_EXISTS");
  }
}
