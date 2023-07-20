package com.madhurtoppo.usermanagement.repository;

import com.madhurtoppo.usermanagement.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

/** UserRepository */
public interface UserRepository extends JpaRepository<User, String> {
  boolean existsByNameContainingIgnoreCase(String name);
}
