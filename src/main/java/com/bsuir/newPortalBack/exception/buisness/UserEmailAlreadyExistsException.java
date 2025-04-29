package com.bsuir.newPortalBack.exception.buisness;

public class UserEmailAlreadyExistsException extends BusinessException {

  private static final String DEFAULT_ERROR_CODE = "EMAIL_ALREADY_EXISTS";

  public UserEmailAlreadyExistsException(String email) {
    super("Пользователь с почтой " + email + " уже существует.", DEFAULT_ERROR_CODE);
  }
}
