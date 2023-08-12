package com.example.application.data.nosql.entities;

public class OutOfStockReportNoSQL {
    private String id; // der Artikelname
    private Integer totalOutOfStock;

    public OutOfStockReportNoSQL(String id, Integer totalOutOfStock) {
        this.id = id;
        this.totalOutOfStock = totalOutOfStock;
    }

    // Getter und Setter...
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getTotalOutOfStock() {
        return totalOutOfStock;
    }

    public void setTotalOutOfStock(Integer totalOutOfStock) {
        this.totalOutOfStock = totalOutOfStock;
    }
}
