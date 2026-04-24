package com.ats.backend.repository;

import com.ats.backend.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {
    
    // Automatically writes: SELECT * FROM resumes WHERE user_id = ?
    Optional<Resume> findByUserId(Long userId);
}