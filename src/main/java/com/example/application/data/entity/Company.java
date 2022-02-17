package com.example.application.data.entity;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.example.application.data.AbstractEntity;
import org.hibernate.annotations.Formula;

@Entity
public class Company extends AbstractEntity {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
