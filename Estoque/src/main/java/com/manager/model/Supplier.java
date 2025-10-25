package com.manager.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "suppliers")
@Data
public class Supplier {

    @Id
    @Column(name = "supplier_id")
    private Short id;

    @Column(name = "company_name", length = 40, nullable = false)
    private String companyName;

    private String contactName;
    private String contactTitle;
    private String address;
    private String city;
    private String region;
    private String postalCode;
    private String country;
    private String phone;
    private String fax;

    @Column(columnDefinition = "text")
    private String homepage;
}

