package com.example.application.data.nosql.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Document(collection = "vendors")
public class VendorNoSQL {
    @Id
    private String id;
    private String vendorName;
    private String vendorAddress;
    private String vendorSvnr;
    private List<String> relatedVendors;
}