package com.bsuir.newPortalBack.entities;

import com.bsuir.newPortalBack.enums.NewsStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "news")
public class NewsEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(nullable = false)
  private String title;

  @Lob
  @Column(nullable = false)
  private String content;

  @Column
  private String image;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "author_id", nullable = false)
  private UserEntity author;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id", nullable = false)
  private NewsCategoryEntity category;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private NewsStatus status = NewsStatus.draft;

  private LocalDateTime publishedAt;
  private LocalDateTime scheduledAt;

  @CreationTimestamp
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;
}
