package com.bsuir.newPortalBack.dto.news;

import com.bsuir.newPortalBack.enums.NewsStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class NewsUpdateDTO {
  private int id;
  private String title;
  private String content;
  private Integer categoryId;
  private String image;
  private List<String> tags;
  private NewsStatus status;
}
