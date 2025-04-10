package com.bsuir.newPortalBack.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserResponseDTO {
  private Integer id;
  private String username;
  private String email;
  private String lastName;
  private String firstName;
  private String surname;
  private String position;
  private String department;
}
