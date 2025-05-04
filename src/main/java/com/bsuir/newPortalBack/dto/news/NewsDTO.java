package com.bsuir.newPortalBack.dto.news;

import com.bsuir.newPortalBack.dto.newsCategory.NewsCategoryDTO;
import com.bsuir.newPortalBack.dto.tag.TagDTO;
import com.bsuir.newPortalBack.dto.user.UserForNewsDTO;
import com.bsuir.newPortalBack.enums.NewsStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class NewsDTO {
  private int id;
  private String title;
  private String content;
  private String image;
  private UserForNewsDTO author;
  private List<TagDTO> tags;
  private NewsStatus status;
  private LocalDateTime publishedAt;
  private NewsCategoryDTO category;
}
