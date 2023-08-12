package com.example.application.data.controller.order.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private int customerId;
    private String creditCardNr;
    private String creditCardName;
    private String orderDate;
    private List<Integer> articleIds;
}