package com.example.application.data.nosql.repositories;

import com.example.application.data.nosql.entities.VendorNoSQL;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VendorNoSQLRepository extends MongoRepository<VendorNoSQL,String> {
}
