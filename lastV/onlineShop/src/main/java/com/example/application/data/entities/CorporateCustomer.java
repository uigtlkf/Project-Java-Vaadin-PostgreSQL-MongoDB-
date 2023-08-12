package com.example.application.data.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "corporate_customers")
public class CorporateCustomer extends Customer {

    @Column(name = "tax_number", nullable = true)
    private String taxNumber;

    public CorporateCustomer(String email, String lastname, String surname, String address, String password, String taxNumber) {
        super(email, lastname, surname, address, password);
        this.taxNumber = taxNumber;
    }
}
