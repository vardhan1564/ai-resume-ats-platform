package com.ats.backend.service;

import com.ats.backend.dto.ResumeDto;

public interface ResumeService {
    ResumeDto getResumeByUserId(Long userId);
    // We use a single method to handle both creating a new resume and updating an existing one
    ResumeDto saveOrUpdateResume(Long userId, ResumeDto resumeDto); 
}