package com.bsuir.newPortalBack.repository;

import com.bsuir.newPortalBack.entities.RoleEntity;
import com.bsuir.newPortalBack.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
  Optional<RoleEntity> findByName(UserRole name);
}
