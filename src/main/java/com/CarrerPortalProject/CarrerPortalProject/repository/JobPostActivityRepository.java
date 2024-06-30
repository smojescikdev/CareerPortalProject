package com.CarrerPortalProject.CarrerPortalProject.repository;

import com.CarrerPortalProject.CarrerPortalProject.model.JobPostActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobPostActivityRepository extends JpaRepository<JobPostActivity, Integer> {

    List<JobPostActivity> findByPostedById_UserId(int postedById);


    // Metoda zwracająca listę ofert pracy posortowaną od najnowszych do najstarszych
    //List<JobPostActivity> findAllByOrderByPostedDateDesc();


    //lista ofert pracy -- DESC -- JPQL
    @Query("SELECT j FROM JobPostActivity j ORDER BY j.postedDate DESC")
    List<JobPostActivity> findAllByOrderByPostedDateDesc();
}
