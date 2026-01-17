package com.organization.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String role; // Employer, Department Head, Sub-Department Head, Team Leader, Employee

    @ManyToOne
    private Department department;

    @ManyToOne
    private SubDepartment subDepartment;

    @ManyToOne
    private Team team;

    @ManyToOne
    private Position position;
}
