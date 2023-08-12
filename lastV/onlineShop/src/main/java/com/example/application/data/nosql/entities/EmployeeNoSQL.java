package com.example.application.data.nosql.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Document(collection = "employees")
public class EmployeeNoSQL {
    @Id
    private String id;
    private String location;
    private String name;
    private List<String> warehouses;
}
