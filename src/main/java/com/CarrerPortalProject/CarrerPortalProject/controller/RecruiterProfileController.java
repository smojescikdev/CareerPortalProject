package com.CarrerPortalProject.CarrerPortalProject.controller;

import com.CarrerPortalProject.CarrerPortalProject.model.RecruiterProfile;
import com.CarrerPortalProject.CarrerPortalProject.model.Users;
import com.CarrerPortalProject.CarrerPortalProject.repository.RecruiterProfileRepository;
import com.CarrerPortalProject.CarrerPortalProject.repository.UsersRepository;
import com.CarrerPortalProject.CarrerPortalProject.services.RecruiterProfileService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class RecruiterProfileController {

    private final UsersRepository usersRepository;
    private final RecruiterProfileService recruiterProfileService;
    private final RecruiterProfileRepository recruiterProfileRepository;

    public RecruiterProfileController(UsersRepository usersRepository, RecruiterProfileService recruiterProfileService, RecruiterProfileRepository recruiterProfileRepository) {
        this.usersRepository = usersRepository;
        this.recruiterProfileService = recruiterProfileService;
        this.recruiterProfileRepository = recruiterProfileRepository;
    }

    @GetMapping("/recruiter/recruiter-profile/")
    public String recruiterProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUsername = authentication.getName();
            Users user = usersRepository.findByEmail(currentUsername)
                    .orElseThrow(() -> new UsernameNotFoundException("Could not find user"));

            Optional<RecruiterProfile> recruiterProfile = recruiterProfileService.getOne(user);
            recruiterProfile.ifPresent(profile -> model.addAttribute("profile", profile));
        }
        return "recruiter/recruiter-profile";
    }

    }



