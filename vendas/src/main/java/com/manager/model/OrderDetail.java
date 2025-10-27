package com.manager.model;

import com.manager.dto.OrderDetailsRequestDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
@Table(name = "order_details")
public class OrderDetail {

    @Column(name = "order_id")
    private int orderId;
    @Column(name = "product_id")
    private int productId;
    @Column(name = "unit_price")
    private double unitPrice;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "discount")
    private double discount;

    public static OrderDetail toOrderDetail(OrderDetailsRequestDTO orderDetailsRequestDTO, int orderId) {
        return OrderDetail.builder()
                .orderId(orderId)
                .productId(orderDetailsRequestDTO.productId())
                .unitPrice(orderDetailsRequestDTO.unitPrice())
                .quantity(orderDetailsRequestDTO.quantity())
                .discount(orderDetailsRequestDTO.discount())
                .build();
    }

}
