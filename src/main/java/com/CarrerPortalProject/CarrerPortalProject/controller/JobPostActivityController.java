package com.CarrerPortalProject.CarrerPortalProject.controller;

import com.CarrerPortalProject.CarrerPortalProject.model.Users;
import com.CarrerPortalProject.CarrerPortalProject.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;

@Controller
public class JobPostActivityController {

    private final UsersService usersService;
//    private final JobPostActivityService jobPostActivityService;


    @Autowired
    public JobPostActivityController(UsersService usersService) {
        this.usersService = usersService;
  //      this.jobPostActivityService = jobPostActivityService;
    }

    @GetMapping("/dashboard/")
    public String searchJobs(Model model, Users users) {

        Object currentUserProfile = usersService.getCurrentUserProfile();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            model.addAttribute("username", currentUserName);
        }
        model.addAttribute("user", currentUserProfile);
        System.out.println("Dashboard viewed with current logged user: " + authentication.getName());
        return "dashboard";
    }
}
//
//    //dodawanie metody w kontrolerze pokazujacej nowy formularz
//    @GetMapping("/dashboard/add")
//    public String addJobs(Model model) {
//        model.addAttribute("jobPostActivity", new JobPostActivity());
//        model.addAttribute("user", usersService.getCurrentUserProfile());
//
//
//        return "recruiter/add-jobs";
//
//    }
//
//
//    @PostMapping("/dashboard/addNew")
//    public String addNew(Model model, JobPostActivity jobPostActivity) {
//        Users user = usersService.getCurrentUser();
//        if (user != null) {
//            jobPostActivity.setPostedById(user);
//        }
//        jobPostActivity.setPostedDate(new Date());
//        model.addAttribute("jobPostActivity", jobPostActivity);
//        JobPostActivity saved = jobPostActivityService.addNew(jobPostActivity);
//
//
//        return "redirect:/dashboard/";
//    }
//}
//
