package com.bsuir.newPortalBack.exception.buisness;

public class UserDeleteException extends BusinessException {
  private static final String DEFAULT_ERROR_CODE = "USER_DELETION_FAILED";

  public UserDeleteException(String reason) {
    super("Ошибка при удалении пользователя: " + reason, DEFAULT_ERROR_CODE);
  }
}
