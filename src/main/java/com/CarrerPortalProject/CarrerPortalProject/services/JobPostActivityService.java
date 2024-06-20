package com.CarrerPortalProject.CarrerPortalProject.services;

import com.CarrerPortalProject.CarrerPortalProject.model.JobPostActivity;
import com.CarrerPortalProject.CarrerPortalProject.repository.JobPostActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobPostActivityService {


    //zapis do bazy
    private final JobPostActivityRepository jobPostActivityRepository;

    @Autowired
    public JobPostActivityService(JobPostActivityRepository jobPostActivityRepository) {
        this.jobPostActivityRepository = jobPostActivityRepository;
    }

    public JobPostActivity addNew(JobPostActivity jobPostActivity) {
        return jobPostActivityRepository.save(jobPostActivity);

    }
}
