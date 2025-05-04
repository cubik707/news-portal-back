package com.bsuir.newPortalBack.dto.tag;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TagDTO {
  private int id;
  private String name;
}
