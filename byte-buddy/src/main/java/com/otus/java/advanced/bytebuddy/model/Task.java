package com.otus.java.advanced.bytebuddy.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class Task {
    @Id
    @Column(name = "ID")
    private long id;

    @Basic
    @Column(name = "NAME")
    private String name;

    @Basic
    @Column(name = "EMPLOYEE_ID")
    private Long employeeId;

    @Basic
    @Column(name = "TASK_CATEGORY_ID")
    private Long taskCategoryId;

    @Version
    @Column(name = "VERSION")
    private Long version;

    @Enumerated(EnumType.STRING)
    private TaskType taskType;
}
