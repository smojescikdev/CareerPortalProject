package com.CarrerPortalProject.CarrerPortalProject.services;

import com.CarrerPortalProject.CarrerPortalProject.model.Industry;
import com.CarrerPortalProject.CarrerPortalProject.repository.IndustryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndustryService {

    @Autowired
    private IndustryRepository industryRepository;

    public List<Industry> getAllIndustries() {
        return industryRepository.findAll();
    }
}
