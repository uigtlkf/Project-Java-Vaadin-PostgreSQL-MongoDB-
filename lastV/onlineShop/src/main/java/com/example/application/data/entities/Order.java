package com.example.application.data.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @Column(name = "order_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

    @Column(name = "total_amount", nullable = false)
    private double totalAmount;

    @Column(name = "order_date", nullable = false)
    private String orderDate;

    @Column(name = "fulfilled", nullable = false)
    private boolean fulfilled;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "order",fetch = FetchType.EAGER)
    private List<Article> articles;

    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;

    @OneToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "delivery_provider", nullable = true)
    private String deliveryProvider;

    public String getOrderSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append("Order summary: ");

        for (Article article : articles) {
            summary.append("Article:");
            summary.append(article.getArticleName());
            summary.append(" Price: ");
            summary.append(article.getArticlePrice());
            summary.append(" Quantity: ");
            summary.append(article.getArticleQuantity());
            summary.append("\n");
        }

        return summary.toString();
    }

    public void addArticle(Article article) {
        if (articles == null) {
            articles = new ArrayList<>();
        }
        articles.add(article);
        article.setOrder(this);
    }
}
