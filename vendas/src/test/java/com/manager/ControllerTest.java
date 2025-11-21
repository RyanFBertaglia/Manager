package com.manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.manager.controller.VendasController;
import com.manager.dto.OrderRequest;
import com.manager.model.Order;
import com.manager.model.OrderDetail;
import com.manager.service.EstoqueService;
import com.manager.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VendasController.class)
@TestPropertySource("classpath:application-test.properties")
public class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EstoqueService estoqueService;

    @MockBean
    private OrderService orderService;

    private final Data data = new Data();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testCreateOrder() throws Exception {
        when(orderService.createOrder(any(OrderRequest.class)))
                .thenReturn(1);

        mockMvc.perform(post("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(data.generateOrderRequest())))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateOrderWithInvalidData() throws Exception {

        String invalidJson = """
                {
                    "invalid": "data"
                }
                """;

        mockMvc.perform(post("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }


    @Test
    void testGetOrder() throws Exception {
        Order mockOrder = data.generateOrder();

        when(orderService.getOrderById(anyLong())).thenReturn(mockOrder);

        mockMvc.perform(get("/order/{id}", 1L))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateOrder() throws Exception {
        OrderDetail orderDetail = data.generateOrderDetailObject();

        mockMvc.perform(put("/order/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDetail)))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteOrder() throws Exception {
        mockMvc.perform(delete("/order/{id}", 1L))
                .andExpect(status().isOk());
    }
}
