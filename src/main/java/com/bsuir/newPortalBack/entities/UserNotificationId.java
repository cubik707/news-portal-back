package com.bsuir.newPortalBack.entities;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class UserNotificationId implements Serializable {
  private Long userId;
  private Long notificationId;
}
