package com.CarrerPortalProject.CarrerPortalProject.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "job_seeker_qualification_list")
public class JobSeekerQualificationList {


    //lista odpowiedzi
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String qualificationName;
    private boolean qualificationStatus;

    @ManyToOne
    @JoinColumn(name = "job_seeker_profile_id")
    private JobSeekerProfile jobSeekerProfile;

    public JobSeekerQualificationList() {
    }

    @Override
    public String toString() {
        return "JobSeekerQualificationList{" +
                "id=" + id +
                ", qualificationName='" + qualificationName + '\'' +
                ", qualificationStatus=" + qualificationStatus +
                ", jobSeekerProfile=" + jobSeekerProfile +
                '}';
    }
}
