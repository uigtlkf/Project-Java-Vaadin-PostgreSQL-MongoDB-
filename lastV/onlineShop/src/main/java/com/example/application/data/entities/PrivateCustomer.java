package com.example.application.data.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Table(name = "private_customers")
public class PrivateCustomer extends Customer {

    public PrivateCustomer(String email, String lastname, String surname, String address, String password){
        super(email, lastname, surname, address, password);
    }

}
