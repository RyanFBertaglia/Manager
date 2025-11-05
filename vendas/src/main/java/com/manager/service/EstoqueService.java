package com.manager.service;

import com.manager.estoque.EstoqueProto;
import com.manager.estoque.EstoqueServiceGrpc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstoqueService {

    private final EstoqueServiceGrpc.EstoqueServiceBlockingStub estoqueStub;

    @Autowired
    public EstoqueService(EstoqueServiceGrpc.EstoqueServiceBlockingStub estoqueStub) {
        this.estoqueStub = estoqueStub;
    }

    public boolean disponibilidade(int idProduto, int quantidade) {
        var response = estoqueStub.consultarEstoque(
                EstoqueProto.ConsultaEstoqueRequest.newBuilder()
                        .setProdutoId(idProduto)
                        .build()
        );

        if (response.getQuantidade() < quantidade) {
            return false;
        }
        if (response.getSucesso()) {
            return true;
        }
        throw new RuntimeException(response.getMensagem());
    }
}
