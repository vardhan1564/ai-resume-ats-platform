package com.ats.backend.controller;

import com.ats.backend.dto.ResumeDto;
import com.ats.backend.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/resumes")
@CrossOrigin("*")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeService resumeService;

    // Fetch a user's complete resume profile
    @GetMapping("/user/{userId}")
    public ResponseEntity<ResumeDto> getResumeByUserId(@PathVariable Long userId) {
        ResumeDto resumeDto = resumeService.getResumeByUserId(userId);
        return ResponseEntity.ok(resumeDto);
    }

    // Save or update the entire resume in one single, highly-optimized payload
    @PostMapping("/user/{userId}")
    public ResponseEntity<ResumeDto> saveOrUpdateResume(
            @PathVariable Long userId, 
            @RequestBody ResumeDto resumeDto) {
        
        ResumeDto savedResume = resumeService.saveOrUpdateResume(userId, resumeDto);
        return new ResponseEntity<>(savedResume, HttpStatus.CREATED);
    }
}