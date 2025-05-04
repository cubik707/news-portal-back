package com.bsuir.newPortalBack.dto.news;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class NewsCreateDTO {
  private String title;
  private String content;
  private String image;
  private int authorId;
  private List<String> tags;
  private int categoryId;
}
