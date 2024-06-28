package com.CarrerPortalProject.CarrerPortalProject.repository;

import com.CarrerPortalProject.CarrerPortalProject.model.JobSeekerQualificationList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobSeekerQualificationListRepository extends JpaRepository<JobSeekerQualificationList, Integer> {

    List<JobSeekerQualificationList> findByJobSeekerProfile_UserAccountId(Integer userAccountId);


}