package com.example.application.data.nosql.repositories;

import com.example.application.data.nosql.entities.ArticleNoSQL;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleNoSQLRepository extends MongoRepository<ArticleNoSQL, String> {
}
