package com.CarrerPortalProject.CarrerPortalProject.controller;

import com.CarrerPortalProject.CarrerPortalProject.model.*;
import com.CarrerPortalProject.CarrerPortalProject.repository.IndustryRepository;
import com.CarrerPortalProject.CarrerPortalProject.repository.JobSeekerProfileRepository;
import com.CarrerPortalProject.CarrerPortalProject.repository.UsersRepository;
import com.CarrerPortalProject.CarrerPortalProject.services.IndustryFormService;
import com.CarrerPortalProject.CarrerPortalProject.services.IndustryService;
import com.CarrerPortalProject.CarrerPortalProject.services.JobSeekerQualificationListService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;


@Controller
public class JobSeekerQualificationController {

    private final IndustryService industryService;
    private final IndustryFormService industryFormService;
    private final JobSeekerQualificationListService jobSeekerQualificationListService;
    private final UsersRepository usersRepository;
    private final JobSeekerProfileRepository jobSeekerProfileRepository;
    private final IndustryRepository industryRepository;

    public JobSeekerQualificationController(IndustryService industryService, IndustryFormService industryFormService, JobSeekerQualificationListService jobSeekerQualificationListService, UsersRepository usersRepository, JobSeekerProfileRepository jobSeekerProfileRepository, IndustryRepository industryRepository) {
        this.industryService = industryService;
        this.industryFormService = industryFormService;
        this.jobSeekerQualificationListService = jobSeekerQualificationListService;
        this.usersRepository = usersRepository;
        this.jobSeekerProfileRepository = jobSeekerProfileRepository;
        this.industryRepository = industryRepository;
    }


    @GetMapping("/job-seeker/select-industry")
    public String selectIndustry(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {

            List<Industry> industries = industryService.getAllIndustries();
            model.addAttribute("industries", industries);
        }
        return "job-seeker/select-industry";
    }

    @PostMapping("/job-seeker/select-industry")
    public String handleIndustrySelection(@RequestParam("industryId") int industryId, Model model, @RequestParam(required = false) String name) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUsername = authentication.getName();
            Users user = usersRepository.findByEmail(currentUsername).orElseThrow(() -> new UsernameNotFoundException("Could not find user"));


            IndustryForm industryForm = industryFormService.getIndustryFormById(industryId);
            List<QualificationQuestion> questions = industryFormService.getQuestionsByIndustryFormId(industryForm.getId());
            model.addAttribute("questions", questions);


            //adding selected industry form to database
            JobSeekerProfile jobSeekerProfile = jobSeekerProfileRepository.findById(user.getUserId()).orElseThrow(() -> new RuntimeException("Job seeker profile not found"));

            Industry selectedIndustry = industryRepository.findById(industryId).orElseThrow(() -> new RuntimeException("Industry not found"));

            jobSeekerProfile.setDesiredIndustry(selectedIndustry.getName());

            jobSeekerProfileRepository.save(jobSeekerProfile);


            //  lista odpowiedzi
            List<String> answers = new ArrayList<>(questions.size());
            for (int i = 0; i < questions.size(); i++) {
                answers.add("false");
            }
            model.addAttribute("answers", answers);
        }
        return "job-seeker/qualification-form";
    }

    @PostMapping("/job-seeker/submit-qualification")
    public String submitQualification(@RequestParam("answers") List<String> answers, @RequestParam("qualificationNames") List<String> qualificationNames) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUsername = authentication.getName();
            Users user = usersRepository.findByEmail(currentUsername).orElseThrow(() -> new UsernameNotFoundException("Could not find user"));

            int userAccountId = user.getUserId();  // Pobieranie identyfikatora użytkownika

            //pobierz istnieją kwalifikacje
            List<JobSeekerQualificationList> existingQualifications = jobSeekerQualificationListService.findByJobSeekerProfileUserId(userAccountId);

            //lista do przechowywania nowych kwalifikacji do zapisania
            List<JobSeekerQualificationList> qualificationsToSave = new ArrayList<>();

            //pętla po wszystkich kwalifikacjach (na podstawie przesłanych nazw)
            for (int i = 0; i < qualificationNames.size(); i++) {
                String qualificationName = qualificationNames.get(i);
                boolean qualificationStatus = false;

                if (answers.contains(qualificationName)) {
                    qualificationStatus = true;
                }

                //check czy istnieje w bazie
                JobSeekerQualificationList existingQualification = findExistingQualification(existingQualifications, qualificationName);
                if (existingQualification != null) {

                    existingQualification.setQualificationStatus(qualificationStatus);
                    qualificationsToSave.add(existingQualification);
                } else {

                    JobSeekerProfile profile = new JobSeekerProfile();
                    profile.setUserAccountId(userAccountId);

                    JobSeekerQualificationList newQualification = new JobSeekerQualificationList();
                    newQualification.setQualificationName(qualificationName);
                    newQualification.setQualificationStatus(qualificationStatus);
                    newQualification.setJobSeekerProfile(profile);
                    qualificationsToSave.add(newQualification);
                }
            }

            //zapis wszystkich kwalifikacji do bazy
            jobSeekerQualificationListService.saveJobSeekerQualifications(qualificationsToSave);
        }
        return "job-seeker/qualification-submitted";
    }


    //metoda do znalezienia istniejącej kwalifikacji w liście
    private JobSeekerQualificationList findExistingQualification(List<JobSeekerQualificationList> existingQualifications, String qualificationName) {
        for (JobSeekerQualificationList qualification : existingQualifications) {
            if (qualification.getQualificationName().equals(qualificationName)) {
                return qualification;
            }
        }
        return null;
    }


    @PostMapping("/job-seeker/remove-qualifications")
    public String removeQualifications(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUsername = authentication.getName();
            Users user = usersRepository.findByEmail(currentUsername).orElseThrow(() -> new UsernameNotFoundException("Could not find user"));


            jobSeekerQualificationListService.removeAllQualificationsForUser(user.getUserId());
        }
        return "redirect:/job-seeker/select-industry";
    }


}