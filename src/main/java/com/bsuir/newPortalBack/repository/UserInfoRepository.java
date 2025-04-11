package com.bsuir.newPortalBack.repository;

import com.bsuir.newPortalBack.entities.UserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfoEntity, Integer> {
}
