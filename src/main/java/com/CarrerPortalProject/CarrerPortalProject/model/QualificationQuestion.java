package com.CarrerPortalProject.CarrerPortalProject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "qualification_question")
public class QualificationQuestion {

    //pytania kwalifikacyjne
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String question;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "industry_form_id")
    private IndustryForm industryForm;


    public QualificationQuestion() {
    }

    @Override
    public String toString() {
        return "QualificationQuestion{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", industryForm=" + industryForm +
                '}';
    }
}
