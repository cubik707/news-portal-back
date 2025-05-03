package com.bsuir.newPortalBack.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
public class FileController {

  @Value("${app.upload.dir}")
  private String uploadDir;

  @PostMapping("/upload")
  public String uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
    String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
    Path path = Paths.get(uploadDir + File.separator + fileName);
    Files.createDirectories(path.getParent()); // Создать папки, если их нет
    Files.write(path, file.getBytes());
    return "/uploads/" + fileName; // Возвращаем путь для сохранения в БД
  }
}
