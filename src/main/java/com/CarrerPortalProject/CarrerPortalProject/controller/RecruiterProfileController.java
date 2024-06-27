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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
            Users user = usersRepository.findByEmail(currentUsername).orElseThrow(() -> new UsernameNotFoundException("Could not find user"));

            Optional<RecruiterProfile> recruiterProfile = recruiterProfileService.getOne(user);
            recruiterProfile.ifPresent(profile -> model.addAttribute("profile", profile));
        }
        return "recruiter/recruiter-profile";
    }


    @PostMapping("/recruiter/recruiter-profile-success")
    public String recruiterProfileSuccess(

            //Recruiter details
            @RequestParam String userAccountId, @RequestParam(required = false) String firstName, @RequestParam(required = false) String lastName, @RequestParam(required = false) String phoneNumber, @RequestParam(required = false) String email,
            //Company details
            @RequestParam(required = false) String companyName, @RequestParam(required = false) String companyCountry, @RequestParam(required = false) String companyStreet, @RequestParam(required = false) String companyZipCode, @RequestParam(required = false) String companyCity, @RequestParam(required = false) String companyWebsite, @RequestParam(required = false) String companyEmail, @RequestParam(required = false) String companyTaxId, @RequestParam(required = false) String companyDescription) {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUsername = authentication.getName();
            Users user = usersRepository.findByEmail(currentUsername).orElseThrow(() -> new UsernameNotFoundException("Could not find user"));

            // Pobieramy ID zalogowanego użytkownika
            int accountId = user.getUserId();

            Optional<RecruiterProfile> existingProfileOptional = recruiterProfileService.findById(accountId);

            if (existingProfileOptional.isPresent()) {
                RecruiterProfile existingProfile = existingProfileOptional.get();

                // Aktualizujemy profil rekrutera tylko jeśli pola nie są puste
                if (firstName != null && !firstName.isEmpty()) {
                    existingProfile.setFirstName(firstName);
                }
                if (lastName != null && !lastName.isEmpty()) {
                    existingProfile.setLastName(lastName);
                }
                if (phoneNumber != null && !phoneNumber.isEmpty()) {
                    existingProfile.setPhoneNumber(phoneNumber);
                }
                if (email != null && !email.isEmpty()) {
                    existingProfile.setEmail(email);
                }
                if (companyName != null && !companyName.isEmpty()) {
                    existingProfile.setCompanyName(companyName);
                }
                if (companyCountry != null && !companyCountry.isEmpty()) {
                    existingProfile.setCompanyCountry(companyCountry);
                }
                if (companyStreet != null && !companyStreet.isEmpty()) {
                    existingProfile.setCompanyStreet(companyStreet);
                }
                if (companyZipCode != null && !companyZipCode.isEmpty()) {
                    existingProfile.setCompanyZipCode(companyZipCode);
                }
                if (companyCity != null && !companyCity.isEmpty()) {
                    existingProfile.setCompanyCity(companyCity);
                }
                if (companyWebsite != null && !companyWebsite.isEmpty()) {
                    existingProfile.setCompanyWebsite(companyWebsite);
                }
                if (companyEmail != null && !companyEmail.isEmpty()) {
                    existingProfile.setCompanyEmail(companyEmail);
                }
                if (companyTaxId != null && !companyTaxId.isEmpty()) {
                    existingProfile.setCompanyTaxId(companyTaxId);
                }
                if (companyDescription != null && !companyDescription.isEmpty()) {
                    existingProfile.setCompanyDescription(companyDescription);
                }

                recruiterProfileRepository.save(existingProfile);
                return "recruiter/recruiter-profile-success";
            } else {
                return "recruiter/recruiter-profile-not-found";
            }
        } else {
            return "recruiter/recruiter-profile-not-found";
        }
    }
}

