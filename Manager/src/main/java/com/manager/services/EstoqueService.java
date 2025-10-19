package com.manager.services;

import com.manager.estoque.EstoqueProto;
import com.manager.estoque.EstoqueServiceGrpc;
import org.springframework.stereotype.Service;

@Service
public class EstoqueService {

    private final EstoqueServiceGrpc.EstoqueServiceBlockingStub estoqueStub;

    public EstoqueService(EstoqueServiceGrpc.EstoqueServiceBlockingStub estoqueStub) {
        this.estoqueStub = estoqueStub;
    }

    public String consultarProduto() {
        var response = estoqueStub.consultarEstoque(
                EstoqueProto.ConsultaEstoqueRequest.newBuilder()
                        .setProdutoId("1")
                        .build()
        );
        return response.toString();
    }
}
