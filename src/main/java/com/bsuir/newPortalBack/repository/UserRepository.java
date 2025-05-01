package com.bsuir.newPortalBack.repository;

import com.bsuir.newPortalBack.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
  Optional<UserEntity> findByUsername(String username);
  boolean existsByUsername(String username);
  boolean existsByEmail(String email);
}
