package com.manager;

import com.manager.dto.OrderDetailsRequestDTO;
import com.manager.dto.OrderRequest;
import com.manager.model.Order;
import com.manager.service.EstoqueService;
import com.manager.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class OrderTest {

    private final OrderService orderService;
    private final EstoqueService estoqueService;
    Data data = new Data();

    @Autowired
    public OrderTest(OrderService orderService, EstoqueService estoqueService) {
        this.orderService = orderService;
        this.estoqueService = estoqueService;
    }

    @Test
    public void getOrderById() {
        // Cria um pedido primeiro para ter um ID válido
        OrderRequest orderRequest = data.generateOrderRequest();
        Integer orderId = orderService.createOrder(orderRequest);
        Order order = orderService.getOrderById(orderId);
        assertNotEquals(null, order);
    }

    @Test
    public void updateOrder() {
        // Cria um pedido primeiro
        OrderRequest orderRequest = data.generateOrderRequest();
        Integer orderId = orderService.createOrder(orderRequest);

        // Busca o pedido criado
        Order order = orderService.getOrderById(orderId);
        order.setFreight(Math.random() * 100);
        Order antes = orderService.getOrderById(orderId);
        assertNotEquals(null, antes);

        orderService.updateOrder(order);
        Order depois = orderService.getOrderById(orderId);
        assertNotEquals(antes, depois);
    }

    @Test
    public void testCreateOrder() {
        OrderRequest orderRequest = data.generateOrderRequest();
        OrderDetailsRequestDTO orderDetails = data.generateOrderDetail();
        estoqueService.disponibilidade(orderDetails.productId(), orderDetails.quantity());

        // Divide os pedidos marcando-os com o id da nota
        int id = orderService.createOrder(orderRequest);
        orderService.createOrderDetail(orderDetails, id);

        assertNotNull(id);
        assertNotEquals(0, id);
    }
}