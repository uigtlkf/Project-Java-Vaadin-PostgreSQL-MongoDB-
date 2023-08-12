package com.example.application.data.nosql.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Document(collection = "customers")
public class CustomerNoSQL {
    @Id
    private String id;
    private String address;
    private String email;
    private String lastname;
    private String password;
    private String surname;
    private String vendorName;
    private List<OrderNoSQL> orders;
}
