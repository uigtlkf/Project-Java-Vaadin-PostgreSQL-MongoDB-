package com.example.application.data.nosql.repositories;

import com.example.application.data.nosql.entities.OutOfStockReportNoSQL;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderNoSqlRepository extends org.springframework.data.mongodb.repository.MongoRepository<com.example.application.data.nosql.entities.OrderNoSQL, String>{
    @Aggregation(pipeline = {
            "{ $match: { _class: { $eq: \"com.example.application.data.nosql.entities.OrderNoSQL\" }, } }",
            "{ $unwind: \"$articles\"}",
            "{ $match: { \"articles.articleQuantity\": { $eq: 0 } } }",
            "{ $group: { _id: \"$articles.articleName\", totalOutOfStock: { $sum: 1 } } }",
            "{ $sort: {\"totalOutOfStock\": -1}}",
            "{ $limit: 5 }"
    })
    List<OutOfStockReportNoSQL> report();
}
