package com.CarrerPortalProject.CarrerPortalProject.services;

import com.CarrerPortalProject.CarrerPortalProject.model.JobSeekerQualificationList;
import com.CarrerPortalProject.CarrerPortalProject.repository.JobSeekerQualificationListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobSeekerQualificationListService {

    private final JobSeekerQualificationListRepository qualificationListRepository;
    private final JobSeekerQualificationListRepository jobSeekerQualificationListRepository;

    @Autowired
    public JobSeekerQualificationListService(JobSeekerQualificationListRepository qualificationListRepository, JobSeekerQualificationListRepository jobSeekerQualificationListRepository) {
        this.qualificationListRepository = qualificationListRepository;
        this.jobSeekerQualificationListRepository = jobSeekerQualificationListRepository;
    }

    public List<JobSeekerQualificationList> findByJobSeekerProfileUserId(int userId) {
        return qualificationListRepository.findByJobSeekerProfile_UserAccountId(userId);
    }

    public void saveJobSeekerQualifications(List<JobSeekerQualificationList> qualifications) {
        qualificationListRepository.saveAll(qualifications);
    }

    public void removeAllQualificationsForUser(int userId) {
        List<JobSeekerQualificationList> qualifications = jobSeekerQualificationListRepository.findByJobSeekerProfile_UserAccountId(userId);
        jobSeekerQualificationListRepository.deleteAll(qualifications);
    }

}