package com.example.application.data.services;


import com.example.application.data.nosql.entities.OutOfStockReportNoSQL;
import com.example.application.data.nosql.repositories.OrderNoSqlRepository;
import com.example.application.data.repositories.CountryOrderCount;
import com.example.application.data.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

    private final OrderNoSqlRepository orderRepository;

    @Autowired
    public ReportService(OrderNoSqlRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<OutOfStockReportNoSQL> getOutOfStockReport() {
        return this.orderRepository.report();
    }
}

