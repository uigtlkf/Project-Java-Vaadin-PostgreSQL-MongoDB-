package com.example.application.data.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int personalId;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "employee")
    private List<Order> order;

    @ManyToMany(mappedBy = "employees", fetch = FetchType.EAGER)
    private List<Warehouse> warehouses;

    public Employee(String location, String name) {
        this.location = location;
        this.name = name;
    }
}
