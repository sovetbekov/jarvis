package com.example.application.data.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.example.application.data.AbstractEntity;

@Entity
public class Tasks extends AbstractEntity {

    @NotEmpty
    private String task = "";

    @NotEmpty
    private String details = "";

    @NotEmpty
    private String project = "";

    @NotEmpty
    private String priority = "";

    @ManyToOne
    @JoinColumn(name = "company_id")

    @JsonIgnoreProperties({"employees"})
    private Company company;

    @NotNull
    @ManyToOne
    private Status status;

    @Override
    public String toString() {
        return task + " " + details;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String firstName) {
        this.task = firstName;
    }

    public String getDetails() { return details; }

    public void setDetails(String description) { this.details = description; }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}
