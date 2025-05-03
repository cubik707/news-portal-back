package com.bsuir.newPortalBack.repository;

import com.bsuir.newPortalBack.entities.UserNotificationEntity;
import com.bsuir.newPortalBack.entities.UserNotificationId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserNotificationRepository extends JpaRepository<UserNotificationEntity, UserNotificationId> {
}
