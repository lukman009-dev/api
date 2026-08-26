package com.shehia_management.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.shehia_management.api.entity.User;
import com.shehia_management.api.enums.UserStatus;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByZanId(String zanId);
    Optional<User> findByEmail(String email);
    List<User> findByStatus(UserStatus status);
    List<User> findByShehia(String shehia);
}
