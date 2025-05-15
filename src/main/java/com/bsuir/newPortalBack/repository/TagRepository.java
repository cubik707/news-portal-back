package com.bsuir.newPortalBack.repository;

import com.bsuir.newPortalBack.entities.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<TagEntity, Integer> {
  Optional<TagEntity> findByName(String name);

  @Query("SELECT t FROM TagEntity t WHERE LOWER(t.name) = LOWER(:name)")
  Optional<TagEntity> findByNameCaseInsensitive(@Param("name") String name);

  List<TagEntity> findTop3ByOrderByIdDesc();
}
