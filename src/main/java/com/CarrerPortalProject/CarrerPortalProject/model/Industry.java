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
}
