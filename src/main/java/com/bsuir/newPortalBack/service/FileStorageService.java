package com.bsuir.newPortalBack.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class FileStorageService {
  @Value("${app.upload.dir}")
  private String uploadDir;

  private final List<String> allowedCategories = List.of("news", "avatars");

  public void validateCategory(String category) {
    if (!allowedCategories.contains(category)) {
      throw new IllegalArgumentException("Invalid category");
    }
  }

  public String generateFileName(String originalName) {
    String sanitizedName = sanitizeFileName(originalName);
    return UUID.randomUUID() + "_" + sanitizedName;
  }

  public String storeFile(MultipartFile file, String category, String fileName) throws IOException {
    Path targetLocation = buildSafePath(category, fileName);

    Files.createDirectories(targetLocation.getParent());
    try (InputStream inputStream = file.getInputStream()) {
      Files.copy(inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING);
    }

    return "/uploads/" + category + "/" + fileName;
  }

  private Path buildSafePath(String category, String fileName) {
    Path baseDir = Paths.get(uploadDir, category).normalize().toAbsolutePath();
    Path targetPath = baseDir.resolve(fileName).normalize();

    if (!targetPath.startsWith(baseDir)) {
      throw new SecurityException("Попытка доступа к недопустимому пути");
    }
    return targetPath;
  }

  private String sanitizeFileName(String name) {
    return name.replaceAll("[^a-zA-Z0-9.\\-]", "_");
  }

  public Path resolveSafePath(String category, String fileName) {
    Path baseDir = Paths.get(uploadDir, category).normalize().toAbsolutePath();
    Path targetPath = baseDir.resolve(fileName).normalize().toAbsolutePath();

    if (!targetPath.startsWith(baseDir)) {
      throw new SecurityException("Invalid file path");
    }

    return targetPath;
  }

  public void deleteFile(Path path) throws IOException {
    if (!Files.exists(path)) {
      throw new FileNotFoundException("File not found");
    }
    Files.delete(path);
  }
}
