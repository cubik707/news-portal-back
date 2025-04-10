package com.bsuir.newPortalBack.exception.buisness;

public class UserUpdateException extends BusinessException {
  private static final String DEFAULT_ERROR_CODE = "USER_UPDATE_FAILED";

  public UserUpdateException(String reason) {
    super("Ошибка при обновлении пользователя: " + reason, DEFAULT_ERROR_CODE);
  }
}
