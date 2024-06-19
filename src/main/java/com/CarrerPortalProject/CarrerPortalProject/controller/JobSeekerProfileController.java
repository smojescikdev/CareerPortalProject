package com.CarrerPortalProject.CarrerPortalProject.controller;

import com.CarrerPortalProject.CarrerPortalProject.model.*;
import com.CarrerPortalProject.CarrerPortalProject.repository.JobSeekerProfileRepository;
import com.CarrerPortalProject.CarrerPortalProject.repository.UsersRepository;
import com.CarrerPortalProject.CarrerPortalProject.services.JobSeekerProfileService;
import com.CarrerPortalProject.CarrerPortalProject.services.UsersService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class JobSeekerProfileController {

    private final UsersRepository usersRepository;
    private final JobSeekerProfileService jobSeekerProfileService;
    private final JobSeekerProfileRepository jobSeekerProfileRepository;

    public JobSeekerProfileController(UsersRepository usersRepository, JobSeekerProfileService jobSeekerProfileService, JobSeekerProfileRepository jobSeekerProfileRepository) {
        this.usersRepository = usersRepository;
        this.jobSeekerProfileService = jobSeekerProfileService;
        this.jobSeekerProfileRepository = jobSeekerProfileRepository;
    }

    @GetMapping("/job-seeker/job-seeker-profile/")
    public String jobSeekerProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUsername = authentication.getName();
            Users user = usersRepository.findByEmail(currentUsername)
                    .orElseThrow(() -> new UsernameNotFoundException("Could not find user"));

            Optional<JobSeekerProfile> jobSeekerProfile = jobSeekerProfileService.getOne(user);
            jobSeekerProfile.ifPresent(profile -> model.addAttribute("profile", profile));
        }

        return "job-seeker/job-seeker-profile";
    }


    @PostMapping("/job-seeker/job-seeker-profile-success")
    public String jobSeekerSuccess(
            @RequestParam String userAccountId,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) Date dateOfBirth,
            @RequestParam(required = false) String resume,
            @RequestParam(required = false) boolean driverLicense,
            @RequestParam(required = false) boolean carOwner,
            @RequestParam(required = false) JobSeekerBasicInformation.LanguageLevel polishLanguageLevel,
            @RequestParam(required = false) JobSeekerBasicInformation.LanguageLevel germanLanguageLevel,
            @RequestParam(required = false) JobSeekerBasicInformation.LanguageLevel englishLanguageLevel
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUsername = authentication.getName();
            Users user = usersRepository.findByEmail(currentUsername)
                    .orElseThrow(() -> new UsernameNotFoundException("Could not find user"));

            int accountId = user.getUserId();
            Optional<JobSeekerProfile> existingProfileOptional = jobSeekerProfileService.findById(accountId);

            if (existingProfileOptional.isPresent()) {
                JobSeekerProfile existingProfile = existingProfileOptional.get();

                if (firstName != null && !firstName.isEmpty()) {
                    existingProfile.setFirstName(firstName);
                }
                if (lastName != null && !lastName.isEmpty()) {
                    existingProfile.setLastName(lastName);
                }
                if (phone != null && !phone.isEmpty()) {
                    existingProfile.setPhone(phone);
                }
                if (email != null && !email.isEmpty()) {
                    existingProfile.setEmail(email);
                }
                if (dateOfBirth != null) {
                    existingProfile.setDateOfBirth(dateOfBirth);
                }
                if (resume != null && !resume.isEmpty()) {
                    existingProfile.setResume(resume);
                }

                JobSeekerBasicInformation basicInformation = existingProfile.getJobSeekerBasicInformation();
                if (polishLanguageLevel != null) {
                    basicInformation.setPolishLanguageLevel(polishLanguageLevel);
                }
                if (germanLanguageLevel != null) {
                    basicInformation.setGermanLanguageLevel(germanLanguageLevel);
                }
                if (englishLanguageLevel != null) {
                    basicInformation.setEnglishLanguageLevel(englishLanguageLevel);
                }

                basicInformation.setDriverLicense(driverLicense);
                basicInformation.setCarOwner(carOwner);

                jobSeekerProfileRepository.save(existingProfile);
                return "job-seeker/job-seeker-profile-success";
            } else {
                return "job-seeker/job-seeker-profile-not-found";
            }
        } else {
            return "job-seeker/job-seeker-profile-not-found";
        }
    }
}