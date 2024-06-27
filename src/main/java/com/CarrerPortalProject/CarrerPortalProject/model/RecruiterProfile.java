package com.CarrerPortalProject.CarrerPortalProject.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "recruiter_profile")
public class RecruiterProfile {


    //Recruiter details
    @Id
    private int userAccountId;

    @OneToOne
    @JoinColumn(name = "user_account_id")
    @MapsId
    private Users userId;


    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;


    //Company details
    private String companyName;
    private String companyCountry;
    private String companyStreet;
    private String companyZipCode;
    private String companyCity;
    private String companyWebsite;
    private String companyEmail;
    private String companyTaxId;
    private String companyDescription;


    public RecruiterProfile() {
    }

    public RecruiterProfile(int userAccountId, Users userId, String firstName, String lastName, String phoneNumber, String email, String companyName, String companyCountry, String companyStreet, String companyZipCode, String companyCity, String companyWebsite, String companyEmail, String companyTaxId, String companyDescription) {
        this.userAccountId = userAccountId;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.companyName = companyName;
        this.companyCountry = companyCountry;
        this.companyStreet = companyStreet;
        this.companyZipCode = companyZipCode;
        this.companyCity = companyCity;
        this.companyWebsite = companyWebsite;
        this.companyEmail = companyEmail;
        this.companyTaxId = companyTaxId;
        this.companyDescription = companyDescription;
    }

    public RecruiterProfile(Users users) {
        this.userId = users;
    }

    @Override
    public String toString() {
        return "RecruiterProfile{" +
                "userAccountId=" + userAccountId +
                ", userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", companyName='" + companyName + '\'' +
                ", companyCountry='" + companyCountry + '\'' +
                ", companyStreet='" + companyStreet + '\'' +
                ", companyZipCode='" + companyZipCode + '\'' +
                ", companyCity='" + companyCity + '\'' +
                ", companyWebsite='" + companyWebsite + '\'' +
                ", companyEmail='" + companyEmail + '\'' +
                ", companyTaxId='" + companyTaxId + '\'' +
                ", companyDescription='" + companyDescription + '\'' +
                '}';
    }
}

