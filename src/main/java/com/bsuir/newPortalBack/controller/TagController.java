package com.bsuir.newPortalBack.controller;

import com.bsuir.newPortalBack.dto.response.SuccessResponseDTO;
import com.bsuir.newPortalBack.dto.tag.TagCreateDTO;
import com.bsuir.newPortalBack.dto.tag.TagDTO;
import com.bsuir.newPortalBack.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {
  private final TagService tagService;

  @GetMapping
  public ResponseEntity<SuccessResponseDTO> getAllTags() {
    List<TagDTO> tags = tagService.getAllTags();
    return ResponseEntity.ok(
      SuccessResponseDTO.create(
        HttpStatus.OK,
        "Список тэгов успешно получен",
        tags
      )
    );
  }

  @GetMapping("/{id}")
  public ResponseEntity<SuccessResponseDTO> getTagById(@PathVariable int id) {
    TagDTO tag = tagService.getTagById(id);
    return ResponseEntity.ok(
      SuccessResponseDTO.create(
        HttpStatus.OK,
        "Тэг успешно получен",
        tag
      )
    );
  }

  @PostMapping
  @PreAuthorize("hasRole('EDITOR')")
  public ResponseEntity<SuccessResponseDTO> createTag(@RequestBody TagCreateDTO tagCreateDTO) {
    TagDTO tagDTO = tagService.createTag(tagCreateDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(
      SuccessResponseDTO.create(
        HttpStatus.CREATED,
        "Тэг был успешно создан",
        tagDTO
      )
    );
  }

  @GetMapping("/last-three")
  public ResponseEntity<SuccessResponseDTO> getLast3Tags() {
    List<TagDTO> tags = tagService.getLast3Tags();
    return ResponseEntity.ok(
      SuccessResponseDTO.create(
        HttpStatus.OK,
        "Последние 3 тэга успешно получены",
        tags
      )
    );
  }

}
