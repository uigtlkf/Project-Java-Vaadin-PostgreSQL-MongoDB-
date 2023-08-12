package com.example.application.data.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "vendors")
public class Vendor {

    @Id
    @Column(name = "vendor_name", nullable = false)
    private String name;

    @Column(name = "vendor_address", nullable = false)
    private String address;

    @Column(name = "vendor_svnr", nullable = false)
    private String svnr;

    @OneToOne(mappedBy = "vendor")
    private Customer customer;

    @OneToMany(mappedBy = "vendor",fetch = FetchType.EAGER)
    private List<Order> orders;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "vendor_relationship",
            joinColumns = @JoinColumn(name = "vendor_id"),
            inverseJoinColumns = @JoinColumn(name = "related_vendor_id")
    )
    private List<Vendor> relatedVendors;

}
