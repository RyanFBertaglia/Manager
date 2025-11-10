package com.manager.controller;

import com.manager.dto.OrderDetailsRequestDTO;
import com.manager.dto.OrderRequest;
import com.manager.model.OrderDetail;
import com.manager.service.EstoqueService;
import com.manager.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VendasController {

    private final EstoqueService estoqueService;
    private final OrderService orderService;

    @Autowired
    public VendasController(EstoqueService estoqueService, OrderService orderService) {
        this.estoqueService = estoqueService;
        this.orderService = orderService;
    }

    @GetMapping("/produto/{idProduto}/disponivel/{quantidade}")
    public ResponseEntity<String> produtoDisponivel(@PathVariable int idProduto, @PathVariable int quantidade) {
        if (estoqueService.disponibilidade(idProduto, quantidade)) {
            return ResponseEntity.ok("Produto disponivel");
        } else {
            return ResponseEntity.status(400).body("Produto não disponivel");
        }
    }

    @PostMapping("/order")
    public ResponseEntity<String> createOrder(@RequestBody OrderRequest orderRequest) {
        List<OrderDetailsRequestDTO> orderDetails = orderRequest.orderDetails();
        orderDetails.forEach(orderDetail ->
                estoqueService.disponibilidade(orderDetail.productId(), orderDetail.quantity()));

        // Divide os pedidos marcando-os com o id da nota
        int id = orderService.createOrder(orderRequest);
        orderService.createOrderDetail(orderDetails, id);
        return ResponseEntity.ok("Pedido criado");
    }

    @GetMapping("/order")
    public ResponseEntity<List<OrderDetail>> findAll() {
        return ResponseEntity.ok(orderService.findAll());
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<List<OrderDetail>> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(orderService.findAllOrderDetailsById(id));
    }

    @PutMapping("/order{id}")
    public ResponseEntity<String> updateOrderDetail(@PathVariable Integer id, @RequestBody OrderDetail orderDetailsRequestDTO) {
        orderService.updateOrderDetail(orderDetailsRequestDTO);
        return ResponseEntity.ok("Pedido atualizado");
    }

    @DeleteMapping("/order{id}")
    public ResponseEntity<String> deleteOrderDetail(@PathVariable Integer id) {
        orderService.deleteOrderDetail(id);
        return ResponseEntity.ok("Pedido deletado");
    }
}
