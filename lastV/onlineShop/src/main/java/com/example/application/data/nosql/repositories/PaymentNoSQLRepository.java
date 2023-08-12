package com.example.application.data.nosql.repositories;

import com.example.application.data.nosql.entities.PaymentNoSQL;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PaymentNoSQLRepository extends MongoRepository<PaymentNoSQL,String> {
}
