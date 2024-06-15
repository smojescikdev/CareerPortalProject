package com.CarrerPortalProject.CarrerPortalProject.services;


import com.CarrerPortalProject.CarrerPortalProject.model.Users;
import com.CarrerPortalProject.CarrerPortalProject.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
public class UsersService {

    private final UsersRepository usersRepository;

    @Autowired
    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

public Users addNew(Users users) {
        users.setActive(true);
        users.setRegistrationDate(new Date(System.currentTimeMillis()));
        return usersRepository.save(users);

}


}
