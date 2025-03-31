package com.bsuir.newPortalBack.repository;

import com.bsuir.newPortalBack.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
  Optional<RoleEntity> findByName(String name);
}
