package com.manager;

import com.manager.repository.OrderDetailRepository;
import com.manager.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;


@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class TestAcessDB {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderService orderService;

    @Test
    public void testAccesOrderDetails() {
        orderDetailRepository.findAll().forEach(System.out::println);
    }

    @Test
    public void testAccesOrderService() {
        orderService.findAll().forEach(System.out::println);
    }
}
