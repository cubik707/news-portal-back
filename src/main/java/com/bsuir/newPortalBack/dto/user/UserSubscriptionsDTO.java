package com.bsuir.newPortalBack.dto.user;

import com.bsuir.newPortalBack.dto.newsCategory.NewsCategoryDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserSubscriptionsDTO {
  private int userId;
  private Set<NewsCategoryDTO> categories;
}
