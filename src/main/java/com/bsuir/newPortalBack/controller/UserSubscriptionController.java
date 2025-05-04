package com.bsuir.newPortalBack.controller;

import com.bsuir.newPortalBack.dto.response.SuccessResponseDTO;
import com.bsuir.newPortalBack.dto.user.UserSubscriptionsDTO;
import com.bsuir.newPortalBack.service.UserSubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/subscriptions")
@RequiredArgsConstructor
public class UserSubscriptionController {
  private final UserSubscriptionService userSubscriptionService;

  @GetMapping
  public ResponseEntity<SuccessResponseDTO> getAllSubscriptions() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();
    UserSubscriptionsDTO userSubscriptions = userSubscriptionService.getAllSubscriptions(username);
    return ResponseEntity.ok(
      SuccessResponseDTO.create(
        HttpStatus.OK,
        "Список подписок на категории успешно получен",
        userSubscriptions
      )
    );
  }

  @PostMapping("/{subscriptionId}")
  public ResponseEntity<SuccessResponseDTO> createSubscription(@PathVariable int subscriptionId) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();
    userSubscriptionService.subscribe(username, subscriptionId);
    return ResponseEntity.ok(
      SuccessResponseDTO.create(
        HttpStatus.OK,
        "Пользователь успешно подписался на категорию",
        null
      )
    );
  }

  @DeleteMapping("/{subscriptionId}")
  public ResponseEntity<SuccessResponseDTO> deleteSubscription(@PathVariable int subscriptionId) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();
    userSubscriptionService.unsubscribe(username, subscriptionId);
    return ResponseEntity.ok(
      SuccessResponseDTO.create(
        HttpStatus.OK,
        "Пользователь успешно отписался от категории",
        null
      )
    );
  }
}
