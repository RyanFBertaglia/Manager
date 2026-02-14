package com.manager.service;

import com.manager.model.Category;
import com.manager.model.Product;
import com.manager.repository.CategoryRepository;
import com.manager.repository.ProductRepository;
import com.manager.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstoqueService {

    @Autowired
    private CategoryRepository categoriesRepository;

    @Autowired
    private ProductRepository productsRepository;

    @Autowired
    private SupplierRepository suppliersRepository;


    public List<Product> findAllProducts() {
        return productsRepository.findAll();
    }

    public Product findById(Long id) {
        return productsRepository.findById(id).orElse(null);
    }

    public Product save(Product product) {
        return productsRepository.save(product);
    }

    public void updateEstoque(Long id, Double diferenca) {
        Product product = productsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        short newStock = (short) (product.getUnitsInStock() + diferenca.shortValue());
        product.setUnitsInStock(newStock);

        productsRepository.save(product);
    }

}
