package com.CarrerPortalProject.CarrerPortalProject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "industry_form")
public class IndustryForm {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "industry_id")
    private Industry industry;

    private String formName;

    // Relacja z QualificationQuestion - ignor zagniezdzenia
    @JsonIgnore
    @OneToMany(mappedBy = "industryForm")
    private List<QualificationQuestion> questions;
}