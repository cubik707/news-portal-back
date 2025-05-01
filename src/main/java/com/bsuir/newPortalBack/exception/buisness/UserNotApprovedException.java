package com.bsuir.newPortalBack.exception.buisness;

public class UserNotApprovedException extends BusinessException {
  private static final String DEFAULT_ERROR_CODE = "USER_NOT_APPROVED";

  public UserNotApprovedException(String reason) {
    super("Ошибка в подтверждении пользователя: " + reason, DEFAULT_ERROR_CODE);
  }
}
