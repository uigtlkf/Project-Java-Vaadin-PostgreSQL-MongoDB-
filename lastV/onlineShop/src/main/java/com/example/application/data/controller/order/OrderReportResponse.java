package com.example.application.data.controller.order;

import com.example.application.data.entities.Order;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderReportResponse {
    private List<Order> rows;
}
