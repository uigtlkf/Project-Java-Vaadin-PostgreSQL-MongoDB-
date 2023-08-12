package com.example.application.data.nosql.repositories;

import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseNoSqlRepository extends org.springframework.data.mongodb.repository.MongoRepository<com.example.application.data.nosql.entities.WarehouseNoSQL, String>{
}
