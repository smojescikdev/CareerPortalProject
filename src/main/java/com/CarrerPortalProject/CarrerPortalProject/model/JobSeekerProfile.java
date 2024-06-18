package com.CarrerPortalProject.CarrerPortalProject.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "job_seeker_profile")
public class JobSeekerProfile {

    @Id
    private int userAccountId;

    @OneToOne
    @JoinColumn(name = "user_account_id")
    @MapsId
    private Users userId;

    @OneToOne(mappedBy = "jobSeekerProfile", cascade = CascadeType.ALL)
    private JobSeekerBasicInformation jobSeekerBasicInformation;

    // Job Seeker details
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Date dateOfBirth;
    private String resume;

    private String desiredIndustry;
    private String desiredOccupation;

//    @Column(nullable = true, length = 64)
//    private String profilePhoto;


    public JobSeekerProfile() {
    }

    public JobSeekerProfile(Users user) {
        this.userId = user;
    }

//TODO: resume delete or implement


    public JobSeekerProfile(int userAccountId, Users userId, JobSeekerBasicInformation jobSeekerBasicInformation, String firstName, String lastName, String email, String phone, Date dateOfBirth, String desiredIndustry, String desiredOccupation) {
        this.userAccountId = userAccountId;
        this.userId = userId;
        this.jobSeekerBasicInformation = jobSeekerBasicInformation;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
        //     this.resume = resume;
        this.desiredIndustry = desiredIndustry;
        this.desiredOccupation = desiredOccupation;
    }

    @Override
    public String toString() {
        return "JobSeekerProfile{" +
                "userAccountId=" + userAccountId +
                ", userId=" + userId +
                ", jobSeekerBasicInformation=" + jobSeekerBasicInformation +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", dateOfBirth=" + dateOfBirth +
//                ", resume='" + resume + '\'' +
                ", desiredIndustry='" + desiredIndustry + '\'' +
                ", desiredOccupation='" + desiredOccupation + '\'' +
                '}';
    }
}