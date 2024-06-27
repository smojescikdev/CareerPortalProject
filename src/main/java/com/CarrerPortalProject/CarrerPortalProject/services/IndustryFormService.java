package com.CarrerPortalProject.CarrerPortalProject.services;

import com.CarrerPortalProject.CarrerPortalProject.model.IndustryForm;
import com.CarrerPortalProject.CarrerPortalProject.model.QualificationQuestion;
import com.CarrerPortalProject.CarrerPortalProject.repository.IndustryFormRepository;
import com.CarrerPortalProject.CarrerPortalProject.repository.QualificationQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndustryFormService {

    @Autowired
    private IndustryFormRepository industryFormRepository;

    @Autowired
    private QualificationQuestionRepository qualificationQuestionRepository;

    public IndustryForm getIndustryFormById(int id) {
        return industryFormRepository.findById(id).orElse(null);
    }

    public List<QualificationQuestion> getQuestionsByIndustryFormId(int industryFormId) {
        return qualificationQuestionRepository.findByIndustryFormId(industryFormId);
    }

    public List<IndustryForm> getAllIndustries() {
        return industryFormRepository.findAll();
    }

}
