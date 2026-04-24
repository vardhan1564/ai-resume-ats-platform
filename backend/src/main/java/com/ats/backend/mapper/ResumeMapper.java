package com.ats.backend.mapper;

import com.ats.backend.dto.*;
import com.ats.backend.entity.*;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ResumeMapper {

    public static ResumeDto mapToResumeDto(Resume resume) {
        if (resume == null) {
            return null;
        }

        return ResumeDto.builder()
                .id(resume.getId())
                .userId(resume.getUser().getId())
                .targetJobTitle(resume.getTargetJobTitle())
                .professionalSummary(resume.getProfessionalSummary())
                .linkedinUrl(resume.getLinkedinUrl())
                .githubUrl(resume.getGithubUrl())
                .portfolioUrl(resume.getPortfolioUrl())
                .experiences(mapExperiences(resume.getExperiences()))
                .educations(mapEducations(resume.getEducations()))
                .skills(mapSkills(resume.getSkills()))
                .projects(mapProjects(resume.getProjects()))
                .certifications(mapCertifications(resume.getCertifications()))
                .build();
    }

    // --- Private Helper Methods for Nested Lists ---

    private static List<ExperienceDto> mapExperiences(List<Experience> experiences) {
        if (experiences == null) return Collections.emptyList();
        return experiences.stream().map(exp -> ExperienceDto.builder()
                .id(exp.getId())
                .companyName(exp.getCompanyName())
                .jobTitle(exp.getJobTitle())
                .startDate(exp.getStartDate())
                .endDate(exp.getEndDate())
                .isCurrentJob(exp.isCurrentJob())
                .description(exp.getDescription())
                .build()).collect(Collectors.toList());
    }

    private static List<EducationDto> mapEducations(List<Education> educations) {
        if (educations == null) return Collections.emptyList();
        return educations.stream().map(edu -> EducationDto.builder()
                .id(edu.getId())
                .institutionName(edu.getInstitutionName())
                .degreeName(edu.getDegreeName())
                .fieldOfStudy(edu.getFieldOfStudy())
                .startDate(edu.getStartDate())
                .endDate(edu.getEndDate())
                .build()).collect(Collectors.toList());
    }

    private static List<SkillDto> mapSkills(List<Skill> skills) {
        if (skills == null) return Collections.emptyList();
        return skills.stream().map(skill -> SkillDto.builder()
                .id(skill.getId())
                .skillName(skill.getSkillName())
                .proficiency(skill.getProficiency())
                .build()).collect(Collectors.toList());
    }

    private static List<ProjectDto> mapProjects(List<Project> projects) {
        if (projects == null) return Collections.emptyList();
        return projects.stream().map(proj -> ProjectDto.builder()
                .id(proj.getId())
                .name(proj.getName())
                .description(proj.getDescription())
                .technologiesUsed(proj.getTechnologiesUsed())
                .projectUrl(proj.getProjectUrl())
                .repositoryUrl(proj.getRepositoryUrl())
                .startDate(proj.getStartDate())
                .endDate(proj.getEndDate())
                .build()).collect(Collectors.toList());
    }

    private static List<CertificationDto> mapCertifications(List<Certification> certifications) {
        if (certifications == null) return Collections.emptyList();
        return certifications.stream().map(cert -> CertificationDto.builder()
                .id(cert.getId())
                .name(cert.getName())
                .issuingOrganization(cert.getIssuingOrganization())
                .issueDate(cert.getIssueDate())
                .expirationDate(cert.getExpirationDate())
                .credentialId(cert.getCredentialId())
                .credentialUrl(cert.getCredentialUrl())
                .build()).collect(Collectors.toList());
    }
}