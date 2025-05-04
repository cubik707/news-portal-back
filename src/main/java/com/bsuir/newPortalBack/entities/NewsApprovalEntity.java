package com.bsuir.newPortalBack.entities;

import com.bsuir.newPortalBack.enums.ApprovalStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "news_approval")
@Getter
@Setter
public class NewsApprovalEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "news_id", nullable = false)
  private NewsEntity news;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "editor_id", nullable = false)
  private UserEntity editor;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private ApprovalStatus status = ApprovalStatus.pending;

  @Lob
  private String comment;

  @CreationTimestamp
  @Column(nullable = false)
  private LocalDateTime reviewedAt;
}
