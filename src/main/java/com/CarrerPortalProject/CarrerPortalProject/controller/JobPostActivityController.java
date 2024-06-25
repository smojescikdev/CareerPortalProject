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
import org.springframework.web.bind.annotation.*;

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
    private final RecruiterProfileService recruiterProfileService;


    @Autowired
    public JobPostActivityController(UsersService usersService, JobPostActivityService jobPostActivityService, IndustryService industryService, IndustryRepository industryRepository, IndustryFormRepository industryFormRepository, IndustryFormService industryFormService, QualificationQuestionService qualificationQuestionService, RecruiterProfileService recruiterProfileService) {
        this.usersService = usersService;
        this.jobPostActivityService = jobPostActivityService;
        this.industryService = industryService;
        this.industryRepository = industryRepository;
        this.industryFormRepository = industryFormRepository;
        this.industryFormService = industryFormService;
        this.qualificationQuestionService = qualificationQuestionService;
        this.recruiterProfileService = recruiterProfileService;
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


    //edit job offer

//    @PostMapping("/dashboard/edit/{id}")
//    public String editJob(@PathVariable("id") int id, Model model) {
//
//
//        JobPostActivity jobPostActivity = jobPostActivityService.getOne(id);
//        JobPostActivity jobDetails = jobPostActivityService.getOne(id);
//
//        System.out.println("Job Description: " + jobPostActivity.getDescriptionOfJob());
//
//
//        model.addAttribute("jobPostActivity", jobPostActivity);
//        model.addAttribute("jobDetails", jobDetails);
//        model.addAttribute("user", usersService.getCurrentUserProfile());
//
//        return "recruiter/add-jobs";
//
//    }

    @GetMapping("/dashboard/edit/{id}")
    public String editJobForm(@PathVariable("id") int id, Model model) {
        JobPostActivity jobPostActivity = jobPostActivityService.getOne(id);
        model.addAttribute("jobPostActivity", jobPostActivity);
        model.addAttribute("user", usersService.getCurrentUserProfile());

        // Pobierz listę branż z industry form
        List<IndustryForm> industries = industryFormService.getAllIndustries();
        model.addAttribute("industries", industries);

        return "recruiter/add-jobs"; // Przekierowanie do formularza edycji
    }

    @PostMapping("/dashboard/edit/{id}")
    public String editJob(@PathVariable("id") int id, @ModelAttribute("jobPostActivity") JobPostActivity jobPostActivity, @RequestParam("industryFormId") int industryFormId) {
        // Pobierz oryginalny obiekt z bazy danych
        JobPostActivity existingJobPostActivity = jobPostActivityService.getOne(id);

        // Zachowaj niezmienione pola
        jobPostActivity.setPostedDate(existingJobPostActivity.getPostedDate());
        jobPostActivity.setPostedById(existingJobPostActivity.getPostedById());

        // Pobierz wybraną branżę i ustaw ją w JobPostActivity
        IndustryForm selectedIndustry = industryFormService.getIndustryFormById(industryFormId);
        jobPostActivity.setIndustryForm(selectedIndustry);

        // Ustaw pozostałe pola i zapisz lub aktualizuj
        jobPostActivityService.saveOrUpdate(jobPostActivity);

        return "redirect:/dashboard/"; // Przekierowanie po zapisaniu
    }



    //delete job:

    // Metoda do wyświetlenia potwierdzenia usunięcia oferty pracy
    @GetMapping("/dashboard/delete/confirm/{id}")
    public String deleteJobConfirmation(@PathVariable("id") int id, Model model) {
        JobPostActivity jobPostActivity = jobPostActivityService.getOne(id);
        model.addAttribute("jobPostActivity", jobPostActivity);
        model.addAttribute("user", usersService.getCurrentUserProfile());

        return "recruiter/delete-job-confirmation"; // Widok potwierdzenia usunięcia
    }

    // Metoda do usunięcia oferty pracy
    @PostMapping("/dashboard/delete/{id}")
    public String deleteJob(@PathVariable("id") int id) {
        jobPostActivityService.deleteJob(id);

        return "redirect:/dashboard/"; // Przekierowanie po usunięciu
    }


}