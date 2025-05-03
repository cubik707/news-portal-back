package com.bsuir.newPortalBack.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "news_categories")
@Getter
@Setter
public class NewsCategoryEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(nullable = false, length = 100)
  private String name;

  @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<NewsEntity> newsList = new ArrayList<>();

  @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<UserSubscriptionEntity> subscriptions = new ArrayList<>();

}
