package com.bsuir.newPortalBack.repository;

import com.bsuir.newPortalBack.entities.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {
}
