package com.CarrerPortalProject.CarrerPortalProject.repository;

import com.CarrerPortalProject.CarrerPortalProject.model.IndustryForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndustryFormRepository extends JpaRepository<IndustryForm, Integer> {
}
