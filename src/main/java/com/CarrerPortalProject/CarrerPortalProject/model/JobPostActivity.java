package com.CarrerPortalProject.CarrerPortalProject.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;

@Entity
@Getter
@Setter
@Table(name = "job_post_activity")
public class JobPostActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer jobOfferPostId;

    @ManyToOne
    @JoinColumn(name = "postedById", referencedColumnName = "userId")
    private Users postedById;


    @Transient
    private Boolean isActive;
    @Transient
    private Boolean isSaved;


    //Job details
    private String jobTitle;
    @Length(max = 100000)
    private String descriptionOfJob;
    private String jobCategory;
    private String jobType;
    private String salary;


    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private java.util.Date postedDate;


    //Location
    private String city;
    private String postal;
    private String country;

    // Dodaj pole Industry
    @ManyToOne
    @JoinColumn(name = "industry_id")
    private Industry industry;

    public JobPostActivity() {
    }


    public JobPostActivity(Integer jobOfferPostId, Users postedById, Boolean isActive, Boolean isSaved, String jobTitle, String descriptionOfJob, String jobCategory, String jobType, String salary, Date postedDate, String city, String postal, String state, String country) {
        this.jobOfferPostId = jobOfferPostId;
        this.postedById = postedById;
        this.isActive = isActive;
        this.isSaved = isSaved;
        this.jobTitle = jobTitle;
        this.descriptionOfJob = descriptionOfJob;
        this.jobCategory = jobCategory;
        this.jobType = jobType;
        this.salary = salary;
        this.postedDate = postedDate;
        this.city = city;
        this.postal = postal;
        this.country = country;
    }


    @Override
    public String toString() {
        return "JobOfferPost{" +
                "jobOfferPostId=" + jobOfferPostId +
                ", postedById=" + postedById +
                ", isActive=" + isActive +
                ", isSaved=" + isSaved +
                ", jobTitle='" + jobTitle + '\'' +
                ", descriptionOfJob='" + descriptionOfJob + '\'' +
                ", jobCategory='" + jobCategory + '\'' +
                ", jobType='" + jobType + '\'' +
                ", salary='" + salary + '\'' +
                ", postedDate=" + postedDate +
                ", city='" + city + '\'' +
                ", postal='" + postal + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}

