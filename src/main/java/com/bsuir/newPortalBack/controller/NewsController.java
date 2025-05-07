package com.bsuir.newPortalBack.controller;


import com.bsuir.newPortalBack.dto.news.NewsCreateDTO;
import com.bsuir.newPortalBack.dto.news.NewsDTO;
import com.bsuir.newPortalBack.dto.response.SuccessResponseDTO;
import com.bsuir.newPortalBack.enums.NewsStatus;
import com.bsuir.newPortalBack.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {
  private final NewsService newsService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasRole('EDITOR')")
  public ResponseEntity<SuccessResponseDTO> createNews(@RequestBody NewsCreateDTO createDTO) {
    NewsDTO newsDTO = newsService.createNews(createDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(
      SuccessResponseDTO.create(
        HttpStatus.CREATED,
        "Новость была успешно создана",
        newsDTO
      )
    );
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize("hasAnyRole('EDITOR', 'ADMIN')")
  public ResponseEntity<SuccessResponseDTO> deleteNews(@PathVariable int id) {
    newsService.deleteNews(id);

    return ResponseEntity.ok(
      SuccessResponseDTO.create(
        HttpStatus.OK,
        "Новость была успешно удалена",
        null
      )
    );
  }

  @GetMapping
  public ResponseEntity<SuccessResponseDTO> getAllNews() {
    List<NewsDTO> newsDTOList = newsService.getAllNews();
    return ResponseEntity.ok(
      SuccessResponseDTO.create(
        HttpStatus.OK,
        "Список новостей успешно получен",
        newsDTOList
      )
    );
  }

  @GetMapping("/category/{categoryId}")
  public ResponseEntity<SuccessResponseDTO> getNewsByCategory(@PathVariable int categoryId) {
    List<NewsDTO> newsDTOList = newsService.getNewsByCategory(categoryId);
    return ResponseEntity.ok(
      SuccessResponseDTO.create(
        HttpStatus.OK,
        "Список новостей успешно получен",
        newsDTOList
      )
    );
  }

  @GetMapping("/{id}")
  public ResponseEntity<SuccessResponseDTO> getNewsById(@PathVariable int id) {
    NewsDTO newsDTO = newsService.getNewsById(id);
    return ResponseEntity.ok(
      SuccessResponseDTO.create(
        HttpStatus.OK,
        "Новость успешно получена",
        newsDTO
      )
    );
  }

  @GetMapping("/status")
  public ResponseEntity<SuccessResponseDTO> getNewsByStatus(
    @RequestParam NewsStatus status
  ) {
    List<NewsDTO> newsDTOList = newsService.getNewsByStatus(status);
    return ResponseEntity.ok(
      SuccessResponseDTO.create(
        HttpStatus.OK,
        "Список новостей по статусу успешно получен",
        newsDTOList
      )
    );
  }

  @GetMapping("/category/{categoryId}/status")
  public ResponseEntity<SuccessResponseDTO> getNewsByCategoryAndStatus(
    @PathVariable int categoryId,
    @RequestParam NewsStatus status
  ) {
    List<NewsDTO> newsDTOList = newsService.getNewsByCategoryAndStatus(categoryId, status);
    return ResponseEntity.ok(
      SuccessResponseDTO.create(
        HttpStatus.OK,
        "Список новостей по категории и статусу успешно получен",
        newsDTOList
      )
    );
  }
}
