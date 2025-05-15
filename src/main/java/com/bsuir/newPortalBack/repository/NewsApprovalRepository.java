package com.bsuir.newPortalBack.repository;

import com.bsuir.newPortalBack.entities.NewsApprovalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsApprovalRepository extends JpaRepository<NewsApprovalEntity, Integer> {
}
