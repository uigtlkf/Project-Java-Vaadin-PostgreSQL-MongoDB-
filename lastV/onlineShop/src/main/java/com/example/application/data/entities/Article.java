package com.example.application.data.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "articles")
public class Article {

    @Id
    @Column(name = "article_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "article_name", nullable = false)
    private String articleName;

    @Column(name = "article_price", nullable = false)
    private double articlePrice;

    @Column(name = "article_quantity", nullable = false)
    private int articleQuantity;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public Article(String name, double price, int quantity) {
        this.articleName = name;
        this.articlePrice = price;
        this.articleQuantity = quantity;
    }
}