package com.bsuir.newPortalBack.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserForNewsDTO {
  private int id;
  private String firstName;
  private String lastName;
  private String surname;
  private String avatarUrl;
}
