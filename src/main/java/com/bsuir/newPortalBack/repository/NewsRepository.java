package com.bsuir.newPortalBack.repository;

import com.bsuir.newPortalBack.entities.NewsEntity;
import com.bsuir.newPortalBack.enums.NewsStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsRepository extends JpaRepository<NewsEntity, Integer> {
  List<NewsEntity> findByCategory_Id(int id);
  List<NewsEntity> findByStatus(NewsStatus status);
  List<NewsEntity> findByCategory_IdAndStatus(int categoryId, NewsStatus status);
  List<NewsEntity> findByStatusAndAuthor_Id(NewsStatus status, int authorId);
}
