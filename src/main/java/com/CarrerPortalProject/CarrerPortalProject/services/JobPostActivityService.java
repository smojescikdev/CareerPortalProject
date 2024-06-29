package com.CarrerPortalProject.CarrerPortalProject.services;

import com.CarrerPortalProject.CarrerPortalProject.model.JobPostActivity;
import com.CarrerPortalProject.CarrerPortalProject.repository.JobPostActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    //zwraca listę ofert pracy dla danego rekrutera
    public List<JobPostActivity> getJobsByRecruiterId(int recruiterId) {
        return jobPostActivityRepository.findByPostedById_UserId(recruiterId);
    }

    // zwraca oferty na podstawie id - wszystkie
    public JobPostActivity getOne(int id) {
        return jobPostActivityRepository.findById(id).orElseThrow(() -> new RuntimeException("Job Not Found"));
    }

    //pobiera wszystkie ofert pracy Desc
    public List<JobPostActivity> getAllJobPostsSortedByDateDesc() {
        return jobPostActivityRepository.findAllByOrderByPostedDateDesc();
    }

    //zapisuje/update oferty pracy
    public void saveOrUpdate(JobPostActivity jobPostActivity) {
        jobPostActivityRepository.save(jobPostActivity);
    }

    //usuwanie oferty pracy po id
    public void deleteJob(int id) {
        jobPostActivityRepository.deleteById(id);
    }
}
