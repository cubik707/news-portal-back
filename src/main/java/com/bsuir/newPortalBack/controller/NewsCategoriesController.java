package com.bsuir.newPortalBack.controller;

import com.bsuir.newPortalBack.dto.newsCategory.NewsCategoryCreateDTO;
import com.bsuir.newPortalBack.dto.newsCategory.NewsCategoryDTO;
import com.bsuir.newPortalBack.dto.response.BaseResponseDTO;
import com.bsuir.newPortalBack.dto.response.SuccessResponseDTO;
import com.bsuir.newPortalBack.service.NewsCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class NewsCategoriesController {

  private final NewsCategoryService newsCategoryService;

  @GetMapping
  public ResponseEntity<SuccessResponseDTO> getAllCategories() {
    List<NewsCategoryDTO> categories = newsCategoryService.getAllCategories();
    return ResponseEntity.ok(
      SuccessResponseDTO.create(
        HttpStatus.OK,
        "Список категорий успешно получен",
        categories
      )
    );
  }

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<SuccessResponseDTO> createCategory(@Valid @RequestBody NewsCategoryCreateDTO dto) {
    NewsCategoryDTO createdCategory = newsCategoryService.createCategory(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(
      SuccessResponseDTO.create(
        HttpStatus.CREATED,
        "Категория была успешно создана",
        createdCategory
      )
    );
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<? extends BaseResponseDTO> updateCategory(
    @PathVariable int id,
    @Valid @RequestBody NewsCategoryCreateDTO dto
  ) {
    NewsCategoryDTO updatedCategory = newsCategoryService.updateCategory(id, dto);
    return ResponseEntity.ok(
      SuccessResponseDTO.create(
        HttpStatus.OK,
        "Категория успешно обновлена",
        updatedCategory
      )
    );
    }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<? extends BaseResponseDTO> deleteCategory(@PathVariable int id) {
    newsCategoryService.deleteCategory(id);
    return ResponseEntity.ok(
      SuccessResponseDTO.create(
        HttpStatus.OK,
        "Категория была успешно удалена",
        null
      )
    );
  }
}