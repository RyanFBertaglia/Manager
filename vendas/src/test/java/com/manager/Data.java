package com.manager;

import com.manager.dto.OrderDetailsRequestDTO;
import com.manager.dto.OrderRequest;
import com.manager.model.Order;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Data {
    Random random = new Random();

    public List<OrderDetailsRequestDTO> generateOrderDetail() {
        List<OrderDetailsRequestDTO> orderDetailsRequestDTOList = new ArrayList<>();
        for(int i=0; i<4; i++) {
            OrderDetailsRequestDTO orderDetailsRequestDTO = new OrderDetailsRequestDTO(
                    i+1,
                    i+1,
                    i*2,
                    0
            );
            orderDetailsRequestDTOList.add(orderDetailsRequestDTO);
        }
        return orderDetailsRequestDTOList;
    }

    public Date generateDate() {
        return new Date(new java.util.Date().getTime());
    }

    public OrderRequest generateOrderRequest() {
        return new OrderRequest(
                "ALFKI",
                1,
                generateDate(),
                generateDate(),
                generateDate(),
                1,
                10.0,
                "ALFKI",
                "123 Main St",
                "Anytown",
                "Region",
                "12345",
                "Country"
        );
    }

    public Order generateOrder() {
        return Order.builder()
                .orderId(1)
                .customerId("ALFKI " + 3 * Math.random())
                .employeeId(random.nextInt())
                .orderDate(generateDate())
                .requiredDate(generateDate())
                .shippedDate(generateDate())
                .shipVia(random.nextInt(10))
                .freight(10.0)
                .shipName("ALFKI")
                .shipAddress("123 Main St")
                .shipCity("Anytown")
                .shipRegion("Region")
                .shipPostalCode(String.valueOf(random.nextInt(99999 - 10000 + 1)))
                .shipCountry("Country")
                .build();
    }
}
