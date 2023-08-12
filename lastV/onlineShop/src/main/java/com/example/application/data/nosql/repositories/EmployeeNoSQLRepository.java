package com.example.application.data.nosql.repositories;

import com.example.application.data.nosql.entities.EmployeeNoSQL;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeNoSQLRepository extends MongoRepository<EmployeeNoSQL,String> {
}
