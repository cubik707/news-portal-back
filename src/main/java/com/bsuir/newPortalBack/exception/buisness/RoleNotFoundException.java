package com.bsuir.newPortalBack.exception.buisness;

public class RoleNotFoundException extends BusinessException {
  private static final String DEFAULT_ERROR_CODE = "ROLE_NOT_FOUND";

  public RoleNotFoundException(String roleName) {
    super("Роль не найдена: " + roleName, DEFAULT_ERROR_CODE);
  }
}
