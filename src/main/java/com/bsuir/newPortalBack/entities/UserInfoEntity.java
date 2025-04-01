package com.bsuir.newPortalBack.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class UserInfoEntity {

  @Id
  private int userId;

  @OneToOne
  @MapsId
  @JoinColumn(name = "user_id")
  @NotNull
  @JsonBackReference
  private UserEntity user;

  @Column(nullable = false, length = 100, name = "last_name")
  private String lastName;

  @Column(nullable = false, length = 100, name = "first_name")
  private String firstName;

  @Column(nullable = false, length = 100)
  private String surname;

  @Column
  private String avatarUrl;

  @Column(nullable = false, length = 100)
  private String position;

  @Column(nullable = false, length = 100)
  private String department;

  //Builder pattern implementation
  public static class Builder {
    private final UserInfoEntity instance;

    public Builder() {
      instance = new UserInfoEntity();
    }

    public Builder user(UserEntity user) {
      instance.setUser(user);
      return this;
    }

    public Builder lastName(String lastName) {
      instance.setLastName(lastName);
      return this;
    }

    public Builder firstName(String firstName) {
      instance.setFirstName(firstName);
      return this;
    }

    public Builder surname(String surname) {
      instance.setSurname(surname);
      return this;
    }

    public Builder avatarUrl(String avatarUrl) {
      instance.setAvatarUrl(avatarUrl);
      return this;
    }

    public Builder position(String position) {
      instance.setPosition(position);
      return this;
    }

    public Builder department(String department) {
      instance.setDepartment(department);
      return this;
    }

    public UserInfoEntity build() {
      return instance;
    }
    
  }
}
