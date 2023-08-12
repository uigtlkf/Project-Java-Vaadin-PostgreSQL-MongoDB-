package com.example.application.data.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="warehouses")
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int warehouseId;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "warehouse_employee",
            joinColumns = @JoinColumn(name = "warehouse_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    private List<Employee> employees;

    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL)
    private List<Department> departments;

    public Warehouse(String location, String name) {
        this.location = location;
        this.name = name;
    }
}
