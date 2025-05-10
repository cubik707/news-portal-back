package com.bsuir.newPortalBack.controller;

import com.bsuir.newPortalBack.dto.response.SuccessResponseDTO;
import com.bsuir.newPortalBack.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

@RestController
@RequiredArgsConstructor
public class FileController {

  private final FileStorageService fileService;

  @PostMapping("/upload")
  public ResponseEntity<SuccessResponseDTO> uploadImage(
    @RequestParam("file") MultipartFile file,
    @RequestParam("category") String category
  ) throws IOException {
    fileService.validateCategory(category);
    validateFile(file);

    // Генерация безопасного имени файла
    String fileName = fileService.generateFileName(file.getOriginalFilename());

    // Сохранение файла
    String filePath = fileService.storeFile(file, category, fileName);

    return ResponseEntity.ok(
      SuccessResponseDTO.create(
        HttpStatus.OK,
        "Изображение успешно загружено",
        filePath
      ));
  }

  @DeleteMapping("/delete-image")
  public ResponseEntity<SuccessResponseDTO> deleteImage(
    @RequestParam("category") String category,
    @RequestParam("fileName") String fileName) throws IOException {
    fileService.validateCategory(category);
    Path targetPath = fileService.resolveSafePath(category, fileName);
    fileService.deleteFile(targetPath);

    return ResponseEntity.ok(
      SuccessResponseDTO.create(
        HttpStatus.OK,
        "Изображение успешно удалено",
        null
      ));
  }

  private void validateFile(MultipartFile file) {
    if (file.isEmpty()) {
      throw new IllegalArgumentException("Файл не может быть пустым");
    }
    if (file.getOriginalFilename() == null || file.getOriginalFilename().contains("..")) {
      throw new SecurityException("Недопустимое имя файла");
    }
  }
}
