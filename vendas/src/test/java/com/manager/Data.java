package com.manager;

import com.manager.dto.OrderDetailsRequestDTO;
import com.manager.dto.OrderRequest;
import com.manager.model.Order;
import com.manager.model.OrderDetail;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Data {
    Random random = new Random();

    public OrderDetailsRequestDTO generateOrderDetail() {
            return new OrderDetailsRequestDTO(
                    1,
                    1,
                    2,
                    0
            );
    }

    public Date generateDate() {
        return new Date(System.currentTimeMillis());
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
                "Country",
                generateOrderDetail()
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

    public Map<String, Object> generateOrderResponse() {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("orderId", 1);
        response.put("message", "Order created successfully");
        response.put("status", "COMPLETED");
        return response;
    }

    public Map<String, Object> generateGetOrderResponse() {
        Order order = generateOrder(); // Reuse your existing method
        Map<String, Object> response = new HashMap<>();
        response.put("orderId", order.getOrderId());
        response.put("customerId", order.getCustomerId());
        response.put("employeeId", order.getEmployeeId());
        response.put("orderDate", order.getOrderDate());
        response.put("requiredDate", order.getRequiredDate());
        response.put("shippedDate", order.getShippedDate());
        response.put("shipVia", order.getShipVia());
        response.put("freight", order.getFreight());
        response.put("shipName", order.getShipName());
        response.put("shipAddress", order.getShipAddress());
        response.put("shipCity", order.getShipCity());
        response.put("shipRegion", order.getShipRegion());
        response.put("shipPostalCode", order.getShipPostalCode());
        response.put("shipCountry", order.getShipCountry());
        response.put("orderDetails", generateOrderDetail());
        return response;
    }

    public Map<String, Object> generateUpdateOrderResponse() {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("orderId", 1);
        response.put("message", "Order updated successfully");
        response.put("status", "UPDATED");
        return response;
    }

    public Map<String, Object> generateDeleteOrderResponse() {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Order deleted successfully");
        response.put("deletedOrderId", 1);
        return response;
    }

    public Map<String, Object> generateErrorResponse() {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("error", "Operation failed");
        response.put("message", "An error occurred while processing your request");
        return response;
    }

    public List<Order> generateOrderList() {
        List<Order> orders = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Order order = generateOrder();
            order.setOrderId(i + 1);
            orders.add(order);
        }
        return orders;
    }

    public Map<String, Object> generateOrderListResponse() {
        List<Order> orders = generateOrderList();
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("orders", orders);
        response.put("totalCount", orders.size());
        return response;
    }

    public OrderRequest generateInvalidOrderRequest() {
        return new OrderRequest(
                null,
                -1,
                null,
                null,
                null,
                -1,
                -50.0,
                null,
                null,
                null,
                null,
                "",
                "",
                new OrderDetailsRequestDTO(0, 0, 0, 0)
        );
    }

    public List<OrderDetailsRequestDTO> generateOrderDetailsWithDiscount() {
        List<OrderDetailsRequestDTO> orderDetails = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            OrderDetailsRequestDTO orderDetail = new OrderDetailsRequestDTO(
                    i + 1,
                    i + 1,
                    (i + 1) * 5,
                    10.0 + i // Different discount for each item
            );
            orderDetails.add(orderDetail);
        }
        return orderDetails;
    }

    public OrderDetail generateOrderDetailObject() {
        return OrderDetail.builder()
                .orderId(1)
                .productId(1)
                .unitPrice(10.0)
                .quantity(2)
                .discount(0.0)
                .build();
    }
}