package com.otus.java.advanced.bytebuddy.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class Employee {

    @Id
    @Column(name = "ID")
    private long id;

    @Basic
    @Column(name = "NAME")
    private String name;

    @Version
    @Column(name = "VERSION")
    private Long version;
}
