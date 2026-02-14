package com.manager.model;

import com.manager.dto.OrderRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "employee_id")
    private int employeeId;

    @Column(name = "order_date")
    private Date orderDate;

    @Column(name = "required_date")
    private Date requiredDate;

    @Column(name = "shipped_date")
    private Date shippedDate;

    @Column(name = "ship_via")
    private int shipVia;

    @Column(name = "freight")
    private double freight;

    @Column(name = "ship_name")
    private String shipName;

    @Column(name = "ship_address")
    private String shipAddress;

    @Column(name = "ship_city")
    private String shipCity;

    @Column(name = "ship_region")
    private String shipRegion;

    @Column(name = "ship_postal_code")
    private String shipPostalCode;

    @Column(name = "ship_country")
    private String shipCountry;

    public static Order toOrder(OrderRequest orderRequest) {
        return Order.builder()
                .customerId(orderRequest.customerId())
                .employeeId(orderRequest.employeeId())
                .orderDate(orderRequest.orderDate())
                .requiredDate(orderRequest.requiredDate())
                .shippedDate(orderRequest.shippedDate())
                .shipVia(orderRequest.shipVia())
                .freight(orderRequest.freight())
                .shipName(orderRequest.shipName())
                .shipAddress(orderRequest.shipAddress())
                .shipCity(orderRequest.shipCity())
                .shipRegion(orderRequest.shipRegion())
                .shipPostalCode(orderRequest.shipPostalCode())
                .shipCountry(orderRequest.shipCountry())
                .build();
    }
}
