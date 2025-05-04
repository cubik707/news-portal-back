package com.bsuir.newPortalBack.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tags")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(nullable = false, length = 100)
  private String name;

  @ManyToMany(mappedBy = "tags")
  private List<NewsEntity> news = new ArrayList<>();

}
