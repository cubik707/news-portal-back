package com.bsuir.newPortalBack.repository;

import com.bsuir.newPortalBack.entities.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Integer> {
}
