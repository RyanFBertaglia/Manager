package com.manager;

import com.manager.model.Product;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import com.manager.service.EstoqueService;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class TestUpdateData {

    @Autowired
    private EstoqueService estoqueService;

    public Short seeProduct() {
        Product product = estoqueService.findById(1L);
        return product.getUnitsInStock();
    }

    @Test
    public void incrementEstoque() {
        System.out.println("Antes de incrementar: " + seeProduct());
        estoqueService.updateEstoque(1L, 12.0);
        System.out.println("Depois de incrementar: " + seeProduct());
    }

    @Test
    public void decrementEstoque() {
        System.out.println("Antes de reduzir: " + seeProduct());
        estoqueService.updateEstoque(1L, -10.0);
        System.out.println("Depois de reduzir: " + seeProduct());
    }
}
