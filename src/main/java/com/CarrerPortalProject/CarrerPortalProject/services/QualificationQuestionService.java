package com.CarrerPortalProject.CarrerPortalProject.services;

import com.CarrerPortalProject.CarrerPortalProject.model.QualificationQuestion;
import com.CarrerPortalProject.CarrerPortalProject.repository.QualificationQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QualificationQuestionService {

    private final QualificationQuestionRepository qualificationQuestionRepository;

    @Autowired
    public QualificationQuestionService(QualificationQuestionRepository qualificationQuestionRepository) {
        this.qualificationQuestionRepository = qualificationQuestionRepository;
    }

    public List<QualificationQuestion> getQuestionsByIndustryFormId(int industryFormId) {
        return qualificationQuestionRepository.findByIndustryFormId(industryFormId);
    }
}