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

    //zwraca listę kwalifikacji Job Seeker na podstawie userId
    public List<JobSeekerQualificationList> findByJobSeekerProfileUserId(int userId) {
        return qualificationListRepository.findByJobSeekerProfile_UserAccountId(userId);
    }

    //zapisuje listę kwalifikacji poszukującego pracy
    public void saveJobSeekerQualifications(List<JobSeekerQualificationList> qualifications) {
        qualificationListRepository.saveAll(qualifications);
    }

    //usuwa wszystkie kwalifikacje dla danego użytkownika
    public void removeAllQualificationsForUser(int userId) {
        List<JobSeekerQualificationList> qualifications = jobSeekerQualificationListRepository.findByJobSeekerProfile_UserAccountId(userId);
        jobSeekerQualificationListRepository.deleteAll(qualifications);
    }

}