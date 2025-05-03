package com.bsuir.newPortalBack.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user_subscriptions")
@Getter
@Setter
public class UserSubscriptionEntity {
  @EmbeddedId
  private UserSubscriptionId id;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("userId")
  @JoinColumn(name = "user_id")
  private UserEntity user;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("categoryId")
  @JoinColumn(name = "category_id")
  private NewsCategoryEntity category;
}
