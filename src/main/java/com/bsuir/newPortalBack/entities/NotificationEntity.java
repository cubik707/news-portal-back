package com.bsuir.newPortalBack.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "notifications")
@Getter
@Setter
public class NotificationEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "news_id")
  private NewsEntity news;

  @Lob
  @Column(nullable = false)
  private String message;

  @Column(nullable = false)
  private boolean isRead = false;

  @CreationTimestamp
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @OneToMany(mappedBy = "notification", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<UserNotificationEntity> userNotifications = new ArrayList<>();
}
