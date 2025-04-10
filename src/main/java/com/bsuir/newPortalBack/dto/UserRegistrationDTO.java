package com.bsuir.newPortalBack.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationDTO {
  private String username;
  private String email;
  private String password;
  private String lastName;
  private String firstName;
  private String surname;
  private String position;
  private String department;

  //Builder pattern implementation
  public static class Builder {
    private final UserRegistrationDTO instance;

    public Builder() {
      instance = new UserRegistrationDTO();
    }

    public Builder username(String username) {
      instance.setUsername(username);
      return this;
    }

    public Builder email(String email) {
      instance.setEmail(email);
      return this;
    }

    public Builder password(String password) {
      instance.setPassword(password);
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

    public Builder position(String position) {
      instance.setPosition(position);
      return this;
    }

    public Builder department(String department) {
      instance.setDepartment(department);
      return this;
    }

    public UserRegistrationDTO build() {
      return instance;
    }
  }

  public static Builder builder() {
    return new Builder();
  }

}
