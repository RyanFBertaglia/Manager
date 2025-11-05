package com.manager.service;

import com.manager.dto.OrderDetailsRequestDTO;
import com.manager.dto.OrderRequest;
import com.manager.exceptions.NotFound;
import com.manager.model.Order;
import com.manager.model.OrderDetail;
import com.manager.repository.OrderDetailRepository;
import com.manager.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderRepository orderRepository;

    public List<OrderDetail> findAll() {
        return orderDetailRepository.findAll();
    }

    public int createOrder(OrderRequest orderRequest) {
        Order order = Order.toOrder(orderRequest);
        orderRepository.save(order);
        return order.getOrderId();
    }

    public Order getOrderById(long id) {
        return orderRepository.findById(id).orElseThrow(() -> new NotFound("Order not found"));
    }

    public void createOrderDetail(List<OrderDetailsRequestDTO> ordersDTO, int orderId) {
        checkIfOrderDetailExistsOrThrow(orderId);

        ordersDTO.forEach(orderDTO -> {
            OrderDetail order = OrderDetail.toOrderDetail(orderDTO, orderId);
            orderDetailRepository.save(order);
        });
    }

    public void updateOrderDetail(OrderDetail orderDetail) {
        checkIfOrderDetailExistsOrThrow(orderDetail.getOrderId());
        orderDetailRepository.save(orderDetail);
    }

    public void updateOrder(Order order) {
        checkIfOrderExistsOrThrow(order.getOrderId());
        orderRepository.save(order);
    }

    public void deleteOrderDetail(int id) {
        checkIfOrderDetailExistsOrThrow(id);
        orderDetailRepository.deleteById(id);
    }

    public void checkIfOrderDetailExistsOrThrow(int id) {
        if(!orderDetailRepository.existsById(id)) {
            throw new NotFound("OrderDetail not found");
        }
    }

    public void checkIfOrderExistsOrThrow(int id) {
        if(!orderRepository.existsById((long) id)) {
            throw new NotFound("Order not found");
        }
    }

    public Order findOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new NotFound("Order not found"));
    }

    public List<OrderDetail> findAllOrderDetailsById(Integer id) {
        return orderDetailRepository.findByOrderId(id);
    }

}
