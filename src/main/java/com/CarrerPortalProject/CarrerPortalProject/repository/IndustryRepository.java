package com.CarrerPortalProject.CarrerPortalProject.repository;

import com.CarrerPortalProject.CarrerPortalProject.model.Industry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndustryRepository extends JpaRepository<Industry, Integer> {
}
