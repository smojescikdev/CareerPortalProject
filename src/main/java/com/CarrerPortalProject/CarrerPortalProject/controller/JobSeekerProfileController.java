package com.CarrerPortalProject.CarrerPortalProject.controller;

import com.CarrerPortalProject.CarrerPortalProject.model.JobSeekerProfile;
import com.CarrerPortalProject.CarrerPortalProject.model.Users;
import com.CarrerPortalProject.CarrerPortalProject.repository.UsersRepository;
import com.CarrerPortalProject.CarrerPortalProject.services.JobSeekerProfileService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class JobSeekerProfileController {

    private final UsersRepository usersRepository;
    private final JobSeekerProfileService jobSeekerProfileService;

    public JobSeekerProfileController(UsersRepository usersRepository, JobSeekerProfileService jobSeekerProfileService) {
        this.usersRepository = usersRepository;
        this.jobSeekerProfileService = jobSeekerProfileService;
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
}
