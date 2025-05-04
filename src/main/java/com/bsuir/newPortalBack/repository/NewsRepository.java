package com.bsuir.newPortalBack.repository;

import com.bsuir.newPortalBack.entities.NewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<NewsEntity, Integer> {
}
