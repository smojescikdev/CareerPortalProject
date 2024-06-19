package com.CarrerPortalProject.CarrerPortalProject.services;

import com.CarrerPortalProject.CarrerPortalProject.model.JobSeekerQualificationList;
import com.CarrerPortalProject.CarrerPortalProject.repository.JobSeekerQualificationListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobSeekerQualificationListService {

    private final JobSeekerQualificationListRepository qualificationListRepository;

    @Autowired
    public JobSeekerQualificationListService(JobSeekerQualificationListRepository qualificationListRepository) {
        this.qualificationListRepository = qualificationListRepository;
    }

    public List<JobSeekerQualificationList> findByJobSeekerProfileUserId(int userId) {
        return qualificationListRepository.findByJobSeekerProfile_UserAccountId(userId);
    }

    public void saveJobSeekerQualifications(List<JobSeekerQualificationList> qualifications) {
        qualificationListRepository.saveAll(qualifications);
    }
}