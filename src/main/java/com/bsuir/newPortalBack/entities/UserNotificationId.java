package com.bsuir.newPortalBack.entities;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class UserNotificationId implements Serializable {
  private int userId;
  private int notificationId;
}
