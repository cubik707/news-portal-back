package com.bsuir.newPortalBack.exception.buisness;

public class NewsNotFoundException extends BusinessException {
  private static final String DEFAULT_ERROR_CODE = "NEWS_NOT_FOUND";
  public NewsNotFoundException(int id) {
    super("Новость с id '" + id + "' не найдена", DEFAULT_ERROR_CODE);
  }
}
