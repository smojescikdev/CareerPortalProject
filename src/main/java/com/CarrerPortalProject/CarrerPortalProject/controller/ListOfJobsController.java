package com.CarrerPortalProject.CarrerPortalProject.controller;

import com.CarrerPortalProject.CarrerPortalProject.model.JobPostActivity;
import com.CarrerPortalProject.CarrerPortalProject.model.Users;
import com.CarrerPortalProject.CarrerPortalProject.services.JobPostActivityService;
import com.CarrerPortalProject.CarrerPortalProject.services.UsersService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ListOfJobsController {

    private final UsersService usersService;
    private final JobPostActivityService jobPostActivityService;


    public ListOfJobsController(UsersService usersService, JobPostActivityService jobPostActivityService) {
        this.usersService = usersService;
        this.jobPostActivityService = jobPostActivityService;
    }


    @GetMapping("/job-listing")
    public String globalJobList(Model model, Users users) {
        Object currentUserProfile = usersService.getCurrentUserProfile();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            model.addAttribute("username", currentUserName);
        }
        model.addAttribute("user", currentUserProfile);
        System.out.println("Dashboard viewed with current logged user: " + authentication.getName());

        Users currentUser = usersService.getCurrentUser();

        if (currentUser != null) {
            List<JobPostActivity> jobPosts = jobPostActivityService.getAllJobPostsSortedByDateDesc();
            model.addAttribute("jobPosts", jobPosts);
            model.addAttribute("username", currentUser.getEmail());
        }
        return "job-listing";
    }
}
