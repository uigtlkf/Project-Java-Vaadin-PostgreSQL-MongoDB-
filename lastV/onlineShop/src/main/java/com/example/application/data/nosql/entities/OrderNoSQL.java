package com.example.application.data.nosql.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Document(collection = "orders")
public class OrderNoSQL {
    @Id
    private String id;
    private boolean fulfilled;
    private String orderDate;
    private double totalAmount;
    private String employeeId;
    private String paymentId;
    private String vendorId;
    private String status;
    private List<ArticleNoSQL> articles;

}
