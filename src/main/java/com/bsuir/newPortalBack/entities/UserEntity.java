package com.bsuir.newPortalBack.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(unique = true, nullable = false, length = 50)
  private String username;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  private String passwordHash;

  @Column(nullable = false)
  private boolean isApproved = false;

  @Column(nullable = false)
  private LocalDateTime createdAt = LocalDateTime.now();

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
    name = "user_roles",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<RoleEntity> roles = new HashSet<>();

  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference
  private UserInfoEntity userInfo;

  @ManyToMany
  @JoinTable(
    name = "user_subscriptions",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "category_id")
  )
  private Set<NewsCategoryEntity> subscribedCategories = new HashSet<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<UserNotificationEntity> notifications = new ArrayList<>();

  //Builder pattern implementation
  public static class Builder {
    private final UserEntity instance;

    public Builder() {
      instance = new UserEntity();
    }

    public Builder id(int id) {
      instance.setId(id);
      return this;
    }

    public Builder username(String username) {
      instance.setUsername(username);
      return this;
    }

    public Builder email(String email) {
      instance.setEmail(email);
      return this;
    }

    public Builder roles(Set<RoleEntity> roles) {
      instance.setRoles(roles);
      return this;
    }

    public Builder isApproved(boolean isApproved) {
      instance.setApproved(isApproved);
      return this;
    }

    public Builder userInfo(UserInfoEntity userInfo) {
      instance.setUserInfo(userInfo);
      return this;
    }

    public UserEntity build() {
      return instance;
    }
  }

  public static Builder builder() {
    return new Builder();
  }
}
