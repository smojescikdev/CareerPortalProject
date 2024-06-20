package com.CarrerPortalProject.CarrerPortalProject.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "industry")
public class Industry {





    //branze
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    private String name;

    // Relacja z IndustryForm
    @OneToOne(mappedBy = "industry")
    private IndustryForm industryForm;




    //test

    @ManyToOne
    @JoinColumn(name = "job_seeker_profile_id")
    private JobSeekerProfile jobSeekerProfile;


//test ended here


    @Override
    public String toString() {
        return "Industry{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", industryForm=" + industryForm +
                ", jobSeekerProfile=" + jobSeekerProfile +
                '}';
    }
}
