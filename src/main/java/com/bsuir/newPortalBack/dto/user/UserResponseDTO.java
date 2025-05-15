package com.bsuir.newPortalBack.dto.user;

import com.bsuir.newPortalBack.enums.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

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
  private String avatarUrl;
  private boolean isApproved;
  Set<UserRole> roles;
}
