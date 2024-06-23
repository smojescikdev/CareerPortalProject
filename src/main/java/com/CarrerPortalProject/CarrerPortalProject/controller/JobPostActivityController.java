package com.CarrerPortalProject.CarrerPortalProject.controller;

import com.CarrerPortalProject.CarrerPortalProject.model.*;
import com.CarrerPortalProject.CarrerPortalProject.repository.IndustryFormRepository;
import com.CarrerPortalProject.CarrerPortalProject.repository.IndustryRepository;
import com.CarrerPortalProject.CarrerPortalProject.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@Controller
public class JobPostActivityController {

    private final UsersService usersService;
    private final JobPostActivityService jobPostActivityService;
    private final IndustryService industryService;
    private final IndustryRepository industryRepository;
    private final IndustryFormRepository industryFormRepository;
    private final IndustryFormService industryFormService;
    private final QualificationQuestionService qualificationQuestionService;


    @Autowired
    public JobPostActivityController(UsersService usersService, JobPostActivityService jobPostActivityService, IndustryService industryService, IndustryRepository industryRepository, IndustryFormRepository industryFormRepository, IndustryFormService industryFormService, QualificationQuestionService qualificationQuestionService) {
        this.usersService = usersService;
        this.jobPostActivityService = jobPostActivityService;
        this.industryService = industryService;
        this.industryRepository = industryRepository;
        this.industryFormRepository = industryFormRepository;
        this.industryFormService = industryFormService;
        this.qualificationQuestionService = qualificationQuestionService;
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

        Users currentUser = usersService.getCurrentUser();



////list of jobs
        if (currentUser != null) {
            List<JobPostActivity> jobPosts = jobPostActivityService.getJobsByRecruiterId(currentUser.getUserId());
            model.addAttribute("jobPosts", jobPosts);
            model.addAttribute("username", currentUser.getEmail());
        }

////        ended here




        return "dashboard";
    }


    //dodawanie metody w kontrolerze pokazujacej nowy formularz
    @GetMapping("/dashboard/add")
    public String addJobs(Model model) {
        model.addAttribute("jobPostActivity", new JobPostActivity());
        model.addAttribute("user", usersService.getCurrentUserProfile());

        // Pobierz listę branż z industry form
        List<IndustryForm> industries = industryFormService.getAllIndustries();
        model.addAttribute("industries", industries);

        return "recruiter/add-jobs";
    }


    @PostMapping("/dashboard/addNew")
    public String addNew(@RequestParam("industryFormId") int industryFormId, Model model, JobPostActivity jobPostActivity) {
        Users user = usersService.getCurrentUser();
        if (user != null) {
            jobPostActivity.setPostedById(user);
        }
        jobPostActivity.setPostedDate(new Date());

        // Pobierz wybraną branżę i ustaw ją w JobPostActivity
        IndustryForm selectedIndustry = industryFormService.getIndustryFormById(industryFormId);
        jobPostActivity.setIndustryForm(selectedIndustry);


        JobPostActivity saved = jobPostActivityService.addNew(jobPostActivity);


        return "redirect:/dashboard/";
    }

    @GetMapping("/dashboard/getQuestions")
    @ResponseBody
    public List<QualificationQuestion> getQuestions(@RequestParam("industryFormId") int industryFormId) {
        return qualificationQuestionService.getQuestionsByIndustryFormId(industryFormId);
    }

}