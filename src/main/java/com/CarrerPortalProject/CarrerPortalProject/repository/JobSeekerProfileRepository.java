package com.CarrerPortalProject.CarrerPortalProject.repository;

import com.CarrerPortalProject.CarrerPortalProject.model.JobSeekerProfile;
import com.CarrerPortalProject.CarrerPortalProject.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobSeekerProfileRepository extends JpaRepository<JobSeekerProfile, Integer> {


    Optional<JobSeekerProfile> findByUserId(Users user);
}