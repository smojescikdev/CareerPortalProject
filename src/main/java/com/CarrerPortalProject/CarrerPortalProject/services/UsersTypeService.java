package com.CarrerPortalProject.CarrerPortalProject.services;

import com.CarrerPortalProject.CarrerPortalProject.model.UsersType;
import com.CarrerPortalProject.CarrerPortalProject.repository.UsersTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersTypeService {

    private final UsersTypeRepository usersTypeRepository;

    public UsersTypeService(UsersTypeRepository usersTypeRepository) {
        this.usersTypeRepository = usersTypeRepository;
    }


    //lista wszystkich typów użytkowników
    public List<UsersType> getAll() {
        return usersTypeRepository.findAll();
    }

    //lista wszystkich typów użytkowników
    public List<UsersType> getAllUsersTypes() {
        return usersTypeRepository.findAll();
    }
}
