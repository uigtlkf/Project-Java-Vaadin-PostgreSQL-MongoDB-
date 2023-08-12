package com.example.application.data.nosql.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "articles")
public class ArticleNoSQL {
    @Id
    private String id;
    private String articleName;
    private double articlePrice;
    private int articleQuantity;
}
