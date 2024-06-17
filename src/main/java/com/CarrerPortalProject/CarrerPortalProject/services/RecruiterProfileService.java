package com.CarrerPortalProject.CarrerPortalProject.services;

import com.CarrerPortalProject.CarrerPortalProject.model.RecruiterProfile;
import com.CarrerPortalProject.CarrerPortalProject.model.Users;
import com.CarrerPortalProject.CarrerPortalProject.repository.RecruiterProfileRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RecruiterProfileService {

    private final RecruiterProfileRepository recruiterProfileRepository;


    public RecruiterProfileService(RecruiterProfileRepository recruiterProfileRepository) {
        this.recruiterProfileRepository = recruiterProfileRepository;
    }


    public Optional<RecruiterProfile> getOne(Users user) {
        return recruiterProfileRepository.findByUserId(user);
    }

    public Optional<RecruiterProfile> findById(int accountId) {
        return recruiterProfileRepository.findById(accountId);
    }
}
