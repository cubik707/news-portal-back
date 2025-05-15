package com.bsuir.newPortalBack.exception.buisness;

public class TagNotFoundException extends BusinessException {

  private static final String DEFAULT_ERROR_CODE = "TAG_NOT_FOUND";

  public TagNotFoundException(int id) {
    super("Тэш с id '" + id + "' не найдена", DEFAULT_ERROR_CODE);
  }
}
