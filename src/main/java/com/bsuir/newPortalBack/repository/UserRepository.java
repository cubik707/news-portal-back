package com.bsuir.newPortalBack.repository;

import com.bsuir.newPortalBack.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
