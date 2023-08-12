package com.example.application.data.nosql.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "payments")
public class PaymentNoSQL {
    @Id
    private String id;
    private String paymentDate;
    private double price;
    private String time;
}
