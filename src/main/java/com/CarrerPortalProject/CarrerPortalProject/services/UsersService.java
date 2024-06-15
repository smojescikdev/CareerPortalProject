package com.CarrerPortalProject.CarrerPortalProject.services;


import com.CarrerPortalProject.CarrerPortalProject.model.JobSeekerBasicInformation;
import com.CarrerPortalProject.CarrerPortalProject.model.JobSeekerProfile;
import com.CarrerPortalProject.CarrerPortalProject.model.RecruiterProfile;
import com.CarrerPortalProject.CarrerPortalProject.model.Users;
import com.CarrerPortalProject.CarrerPortalProject.repository.JobSeekerProfileRepository;
import com.CarrerPortalProject.CarrerPortalProject.repository.RecruiterProfileRepository;
import com.CarrerPortalProject.CarrerPortalProject.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
public class UsersService {

    private final UsersRepository usersRepository;
    private final JobSeekerProfileRepository profileRepository;
    private final RecruiterProfileRepository recruiterProfileRepository;
    private final JobSeekerProfileRepository jobSeekerProfileRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsersService(UsersRepository usersRepository, JobSeekerProfileRepository profileRepository, RecruiterProfileRepository recruiterProfileRepository, JobSeekerProfileRepository jobSeekerProfileRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.profileRepository = profileRepository;
        this.recruiterProfileRepository = recruiterProfileRepository;
        this.jobSeekerProfileRepository = jobSeekerProfileRepository;
        this.passwordEncoder = passwordEncoder;
    }


    //zapis usera do bazy + utworzenie profilu
    public Users addNew(Users users) {
        users.setActive(true);
        users.setRegistrationDate(new Date(System.currentTimeMillis()));
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        Users savedUser = usersRepository.save(users);
        int userTypeId = users.getUserTypeId().getUserTypeId();
        if (userTypeId == 1) {
            recruiterProfileRepository.save(new RecruiterProfile(savedUser));




        } else {

            // Create JobSeekerProfile
            JobSeekerProfile jobSeekerProfile = new JobSeekerProfile(savedUser);

            // Create JobSeekerBasicInformation
            JobSeekerBasicInformation jobSeekerBasicInformation = new JobSeekerBasicInformation();
            jobSeekerBasicInformation.setJobSeekerProfile(jobSeekerProfile);

            jobSeekerProfile.setJobSeekerBasicInformation(jobSeekerBasicInformation);

            jobSeekerProfileRepository.save(jobSeekerProfile);


        }
        return savedUser;

    }


}
