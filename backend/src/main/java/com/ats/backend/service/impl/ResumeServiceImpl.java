package com.ats.backend.service.impl;

import com.ats.backend.dto.ResumeDto;
import com.ats.backend.entity.*;
import com.ats.backend.exception.ResourceNotFoundException;
import com.ats.backend.mapper.ResumeMapper;
import com.ats.backend.repository.ResumeRepository;
import com.ats.backend.repository.UserRepository;
import com.ats.backend.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {

    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;

    @Override
    public ResumeDto getResumeByUserId(Long userId) {
        Resume resume = resumeRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Resume not found for user id: " + userId));
        return ResumeMapper.mapToResumeDto(resume);
    }

    @Override
    @Transactional
    public ResumeDto saveOrUpdateResume(Long userId, ResumeDto resumeDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        // Find existing resume, or create a new one if it doesn't exist
        Resume resume = resumeRepository.findByUserId(userId).orElse(new Resume());
        
        resume.setUser(user);
        resume.setTargetJobTitle(resumeDto.getTargetJobTitle());
        resume.setProfessionalSummary(resumeDto.getProfessionalSummary());
        resume.setLinkedinUrl(resumeDto.getLinkedinUrl());
        resume.setGithubUrl(resumeDto.getGithubUrl());
        resume.setPortfolioUrl(resumeDto.getPortfolioUrl());

        // Clear existing collections and map new ones to handle updates cleanly
        updateExperiences(resume, resumeDto);
        updateEducations(resume, resumeDto);
        updateSkills(resume, resumeDto);
        updateProjects(resume, resumeDto);
        updateCertifications(resume, resumeDto);

        Resume savedResume = resumeRepository.save(resume);
        return ResumeMapper.mapToResumeDto(savedResume);
    }

    // --- Private Helper Methods for Clean Updates ---

    private void updateExperiences(Resume resume, ResumeDto dto) {
        resume.getExperiences().clear();
        if (dto.getExperiences() != null) {
            resume.getExperiences().addAll(dto.getExperiences().stream().map(expDto -> {
                Experience exp = new Experience();
                exp.setResume(resume);
                exp.setCompanyName(expDto.getCompanyName());
                exp.setJobTitle(expDto.getJobTitle());
                exp.setStartDate(expDto.getStartDate());
                exp.setEndDate(expDto.getEndDate());
                exp.setCurrentJob(expDto.isCurrentJob());
                exp.setDescription(expDto.getDescription());
                return exp;
            }).collect(Collectors.toList()));
        }
    }

    private void updateEducations(Resume resume, ResumeDto dto) {
        resume.getEducations().clear();
        if (dto.getEducations() != null) {
            resume.getEducations().addAll(dto.getEducations().stream().map(eduDto -> {
                Education edu = new Education();
                edu.setResume(resume);
                edu.setInstitutionName(eduDto.getInstitutionName());
                edu.setDegreeName(eduDto.getDegreeName());
                edu.setFieldOfStudy(eduDto.getFieldOfStudy());
                edu.setStartDate(eduDto.getStartDate());
                edu.setEndDate(eduDto.getEndDate());
                return edu;
            }).collect(Collectors.toList()));
        }
    }

    private void updateSkills(Resume resume, ResumeDto dto) {
        resume.getSkills().clear();
        if (dto.getSkills() != null) {
            resume.getSkills().addAll(dto.getSkills().stream().map(skillDto -> {
                Skill skill = new Skill();
                skill.setResume(resume);
                skill.setSkillName(skillDto.getSkillName());
                skill.setProficiency(skillDto.getProficiency());
                return skill;
            }).collect(Collectors.toList()));
        }
    }

    private void updateProjects(Resume resume, ResumeDto dto) {
        resume.getProjects().clear();
        if (dto.getProjects() != null) {
            resume.getProjects().addAll(dto.getProjects().stream().map(projDto -> {
                Project proj = new Project();
                proj.setResume(resume);
                proj.setName(projDto.getName());
                proj.setDescription(projDto.getDescription());
                proj.setTechnologiesUsed(projDto.getTechnologiesUsed());
                proj.setProjectUrl(projDto.getProjectUrl());
                proj.setRepositoryUrl(projDto.getRepositoryUrl());
                proj.setStartDate(projDto.getStartDate());
                proj.setEndDate(projDto.getEndDate());
                return proj;
            }).collect(Collectors.toList()));
        }
    }

    private void updateCertifications(Resume resume, ResumeDto dto) {
        resume.getCertifications().clear();
        if (dto.getCertifications() != null) {
            resume.getCertifications().addAll(dto.getCertifications().stream().map(certDto -> {
                Certification cert = new Certification();
                cert.setResume(resume);
                cert.setName(certDto.getName());
                cert.setIssuingOrganization(certDto.getIssuingOrganization());
                cert.setIssueDate(certDto.getIssueDate());
                cert.setExpirationDate(certDto.getExpirationDate());
                cert.setCredentialId(certDto.getCredentialId());
                cert.setCredentialUrl(certDto.getCredentialUrl());
                return cert;
            }).collect(Collectors.toList()));
        }
    }
}