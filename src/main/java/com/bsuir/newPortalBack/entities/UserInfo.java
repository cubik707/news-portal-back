package com.bsuir.newPortalBack.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users_info")
@Getter
@Setter
@NoArgsConstructor
public class UserInfo {

  @Id
  @OneToOne
  @JoinColumn(name = "user_id")
  @NotNull
  private User user;

  @Column(nullable = false, length = 100)
  private String surname;

  @Column
  private String avatarUrl;

  @Column(nullable = false, length = 100)
  private String position;

  @Column(nullable = false, length = 100)
  private String department;
}
