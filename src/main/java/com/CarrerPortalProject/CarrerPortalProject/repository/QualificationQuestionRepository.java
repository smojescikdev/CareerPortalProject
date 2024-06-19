package com.CarrerPortalProject.CarrerPortalProject.repository;

import com.CarrerPortalProject.CarrerPortalProject.model.QualificationQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QualificationQuestionRepository extends JpaRepository<QualificationQuestion, Integer> {
    List<QualificationQuestion> findByIndustryFormId(int industryFormId);
}
