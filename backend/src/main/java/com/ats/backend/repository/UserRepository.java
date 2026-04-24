package com.ats.backend.repository;

import com.ats.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // Spring Data JPA automatically writes the SQL: SELECT * FROM users WHERE email = ?
    Optional<User> findByEmail(String email);
    
    // Automatically checks if an email exists (useful during registration)
    boolean existsByEmail(String email);
}