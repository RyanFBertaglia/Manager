package com.manager.model;

import com.manager.estoque.EstoqueProto;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "products")
@Data
public class Product {

    @Id
    @Column(name = "product_id")
    private Short id;

    @Column(name = "product_name", length = 40, nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private String quantityPerUnit;
    private Float unitPrice;
    private Short unitsInStock;
    private Short unitsOnOrder;
    private Short reorderLevel;
    private Integer discontinued;

    public EstoqueProto.EstoqueResponse toEstoqueProto(String status, boolean sucesso, String mensagem) {
        return EstoqueProto.EstoqueResponse.newBuilder()
                .setProdutoId(id)
                .setNome(name)
                .setQuantidade(unitsInStock)
                .setPreco(unitPrice)
                .setStatus(status)
                .setSucesso(sucesso)
                .setMensagem(mensagem)
                .build();
    }

}

