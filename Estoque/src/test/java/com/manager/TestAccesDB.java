package com.manager;

import com.manager.repository.CategoryRepository;
import com.manager.repository.ProductRepository;
import com.manager.repository.SupplierRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class TestAccesDB {

    @Autowired
    private CategoryRepository categoriesRepository;

    @Autowired
    private ProductRepository productsRepository;

    @Autowired
    private SupplierRepository suppliersRepository;

    @Test
    public void testAccesCategories() {
        categoriesRepository.findAll().forEach(System.out::println);
    }

    @Test
    public void testAccesProducts() {
        productsRepository.findAll().forEach(System.out::println);
    }

    @Test
    public void testAccesSuppliers() {
        suppliersRepository.findAll().forEach(System.out::println);
    }
}
