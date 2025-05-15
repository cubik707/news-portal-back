package com.bsuir.newPortalBack.exception.buisness;

public class NewsCategoryNotFoundException extends BusinessException {
  private static final String DEFAULT_ERROR_CODE = "NEWS_CATEGORY_NOT_FOUND";
  public NewsCategoryNotFoundException(int id) {
    super("Категория с id '" + id + "' не найдена", DEFAULT_ERROR_CODE);
  }
}
