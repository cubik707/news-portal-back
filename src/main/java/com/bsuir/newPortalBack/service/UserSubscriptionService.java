package com.bsuir.newPortalBack.service;

import com.bsuir.newPortalBack.dto.user.UserSubscriptionsDTO;
import com.bsuir.newPortalBack.entities.NewsCategoryEntity;
import com.bsuir.newPortalBack.entities.UserEntity;
import com.bsuir.newPortalBack.exception.buisness.NewsCategoryNotFoundException;
import com.bsuir.newPortalBack.exception.buisness.UserNotFoundException;
import com.bsuir.newPortalBack.mapper.UserSubscriptionsMapper;
import com.bsuir.newPortalBack.repository.NewsCategoryRepository;
import com.bsuir.newPortalBack.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserSubscriptionService {

  private final UserRepository userRepo;
  private final NewsCategoryRepository newsCategoryRepo;
  private final UserSubscriptionsMapper subscriptionsMapper;

  @Transactional(readOnly = true)
  public UserSubscriptionsDTO getAllSubscriptions(String username) {
    UserEntity user = userRepo.findByUsername(username)
      .orElseThrow(() -> new UserNotFoundException(username));
    return subscriptionsMapper.toDto(user);
  }

  @Transactional
  public void subscribe(String username, int categoryId) {
    UserEntity user = userRepo.findByUsername(username)
      .orElseThrow(() -> new UserNotFoundException(username));

    NewsCategoryEntity category = newsCategoryRepo.findById(categoryId)
      .orElseThrow(() -> new NewsCategoryNotFoundException(categoryId));

    if (!user.getSubscribedCategories().contains(category)) {
      user.getSubscribedCategories().add(category);
      category.getSubscribers().add(user);
      userRepo.save(user);
    }
  }

  @Transactional
  public void unsubscribe(String username, int categoryId) {
    UserEntity user = userRepo.findByUsername(username)
      .orElseThrow(() -> new UserNotFoundException(username));

    NewsCategoryEntity category = newsCategoryRepo.findById(categoryId)
      .orElseThrow(() -> new NewsCategoryNotFoundException(categoryId));

    if (user.getSubscribedCategories().contains(category)) {
      user.getSubscribedCategories().remove(category);
      category.getSubscribers().remove(user);
      userRepo.save(user);
    }
  }
}
