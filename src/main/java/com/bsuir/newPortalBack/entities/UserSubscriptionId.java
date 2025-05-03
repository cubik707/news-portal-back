package com.bsuir.newPortalBack.entities;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class UserSubscriptionId implements Serializable {
  private Long userId;
  private Long categoryId;
}
