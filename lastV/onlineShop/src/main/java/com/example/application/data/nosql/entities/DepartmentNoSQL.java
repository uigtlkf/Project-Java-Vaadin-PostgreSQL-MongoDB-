package com.example.application.data.nosql.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "departments")
public class DepartmentNoSQL {

    @Id
    private String id;
    private int etage;
    private String sector;
    private String warehouseId;

}
