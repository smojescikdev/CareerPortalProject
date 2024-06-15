package com.CarrerPortalProject.CarrerPortalProject.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "job_seeker_basic_information")
public class JobSeekerBasicInformation {

    // Enum dla poziomów języka
    public enum LanguageLevel {
        A1, A2, B1, B2, C1, C2
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private boolean driverLicense;
    private boolean carOwner;
    private LanguageLevel polishLanguageLevel;
    private LanguageLevel germanLanguageLevel;
    private LanguageLevel englishLanguageLevel;

    @ManyToOne
    @JoinColumn(name = "job_seeker_profile_id")
    private JobSeekerProfile jobSeekerProfile;


    @Override
    public String toString() {
        return "JobSeekerBasicInformation{" +
                "id=" + id +
                ", driverLicense=" + driverLicense +
                ", carOwner=" + carOwner +
                ", polishLanguageLevel=" + polishLanguageLevel +
                ", germanLanguageLevel=" + germanLanguageLevel +
                ", englishLanguageLevel=" + englishLanguageLevel +
                ", jobSeekerProfile=" + jobSeekerProfile +
                '}';
    }
}

