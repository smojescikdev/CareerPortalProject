package com.CarrerPortalProject.CarrerPortalProject.controller;

import com.CarrerPortalProject.CarrerPortalProject.model.JobPostActivity;
import com.CarrerPortalProject.CarrerPortalProject.model.JobSeekerProfile;
import com.CarrerPortalProject.CarrerPortalProject.model.RecruiterProfile;
import com.CarrerPortalProject.CarrerPortalProject.model.Users;
import com.CarrerPortalProject.CarrerPortalProject.repository.UsersRepository;
import com.CarrerPortalProject.CarrerPortalProject.services.JobPostActivityService;
import com.CarrerPortalProject.CarrerPortalProject.services.JobSeekerProfileService;
import com.CarrerPortalProject.CarrerPortalProject.services.RecruiterProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class JobDetailsController {

    private final JobPostActivityService jobPostActivityService;
    private final UsersRepository usersRepository;
    private final JobSeekerProfileService jobSeekerProfileService;
    private final RecruiterProfileService recruiterProfileService;

    @Autowired
    public JobDetailsController(JobPostActivityService jobPostActivityService, UsersRepository usersRepository, JobSeekerProfileService jobSeekerProfileService, RecruiterProfileService recruiterProfileService) {
        this.jobPostActivityService = jobPostActivityService;
        this.usersRepository = usersRepository;
        this.jobSeekerProfileService = jobSeekerProfileService;
        this.recruiterProfileService = recruiterProfileService;
    }


    @GetMapping("/job-details-apply/{id}")
    public String jobDetails(@PathVariable("id") int id, Model model) {
        JobPostActivity jobPostActivity = jobPostActivityService.getOne(id);
        model.addAttribute("jobDetails", jobPostActivity);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUsername = authentication.getName();
            Users user = usersRepository.findByEmail(currentUsername).orElseThrow(() -> new UsernameNotFoundException("Could not find user"));

            Optional<JobSeekerProfile> jobSeekerProfile = jobSeekerProfileService.getOne(user);
            jobSeekerProfile.ifPresent(profile -> {
                model.addAttribute("jobSeekerProfile", profile);
                model.addAttribute("profile", profile);

                // Prepare qualifications for display in the email body
                List<String> qualifications = profile.getJobSeekerQualificationList().stream().map(q -> q.getQualificationName() + ": " + (q.isQualificationStatus() ? "TAK" : "NIE")).collect(Collectors.toList());
                model.addAttribute("jobSeekerQualifications", String.join("%0D%0A", qualifications));
            });

            // Pobierz profil rekrutera na podstawie autora ogłoszenia o pracę
            Optional<RecruiterProfile> recruiterProfile = recruiterProfileService.getOne(jobPostActivity.getPostedById());
            recruiterProfile.ifPresent(profile -> {
                model.addAttribute("recruiterProfile", profile);
                model.addAttribute("profile1", profile);
                System.out.println("Recruiter Profile: " + profile.toString());
            });

            return "job-details";
        }

        return "redirect:/login";
    }
}

