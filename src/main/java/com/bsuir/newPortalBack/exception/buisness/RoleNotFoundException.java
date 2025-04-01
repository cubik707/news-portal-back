package com.bsuir.newPortalBack.exception.buisness;

public class RoleNotFoundException extends BusinessException {
  public RoleNotFoundException(String roleName) {
    super("Роль не найдена: " + roleName, "ROLE_NOT_FOUND");
  }
}
