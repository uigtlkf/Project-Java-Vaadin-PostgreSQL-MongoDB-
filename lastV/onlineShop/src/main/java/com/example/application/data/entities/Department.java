package com.example.application.data.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int departmentId;

    @Column(name = "sector", nullable = false)
    private String sector;

    @Column(name = "etage", nullable = false)
    private int etage;

    @ManyToOne
    private Warehouse warehouse;
}
