package com.bsuir.newPortalBack.mapper;

import com.bsuir.newPortalBack.dto.newsCategory.NewsCategoryDTO;
import com.bsuir.newPortalBack.dto.user.UserSubscriptionsDTO;
import com.bsuir.newPortalBack.entities.NewsCategoryEntity;
import com.bsuir.newPortalBack.entities.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class UserSubscriptionsMapper {

  private final NewsCategoryMapper newsCategoryMapper;

  public UserSubscriptionsDTO toDto(UserEntity userEntity) {
    UserSubscriptionsDTO dto = new UserSubscriptionsDTO();
    dto.setUserId(userEntity.getId());

    Set<NewsCategoryDTO> categories = userEntity.getSubscribedCategories().stream()
      .map(newsCategoryMapper::toDTO)
      .collect(Collectors.toSet());

    dto.setCategories(categories);
    return dto;
  }

  public void updateSubscriptions(UserSubscriptionsDTO dto, UserEntity userEntity) {
    Set<NewsCategoryEntity> categories = dto.getCategories().stream()
      .map(newsCategoryMapper::toEntity)
      .collect(Collectors.toSet());

    userEntity.getSubscribedCategories().clear();
    userEntity.getSubscribedCategories().addAll(categories);
  }
}
