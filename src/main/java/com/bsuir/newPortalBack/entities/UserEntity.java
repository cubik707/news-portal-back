package com.bsuir.newPortalBack.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
  private List<RoleEntity> roles;

  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference
  private UserInfoEntity userInfo;

  public UserEntity() {
    this.roles = new ArrayList<>();
  }

  //Builder pattern implementation
  public static class Builder {
    private final UserEntity instance;

    public Builder() {
      instance = new UserEntity();
    }

    public Builder username(String username) {
      instance.setUsername(username);
      return this;
    }

    public Builder email(String email) {
      instance.setEmail(email);
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
