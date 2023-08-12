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
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int paymentId;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "payment_date", nullable = false)
    private String paymentDate;

    @Column(name = "time", nullable = false)
    private String time;

    @OneToOne(mappedBy = "payment")
    private Order order;

}
