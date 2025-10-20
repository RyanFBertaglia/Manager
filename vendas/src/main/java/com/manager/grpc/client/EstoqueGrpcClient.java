package com.manager.grpc.client;

import org.springframework.stereotype.Service;
import net.devh.boot.grpc.client.inject.GrpcClient;
import com.manager.pedido.grpc.*;

@Service
public class EstoqueGrpcClient {

    @GrpcClient("estoque-service")
    private EstoqueServiceGrpc.EstoqueServiceBlockingStub estoqueStub;

    public String consultarEstoque(String produtoId) {
        try {
            ConsultaEstoqueRequest request = ConsultaEstoqueRequest.newBuilder()
                    .setProdutoId(produtoId)
                    .build();

            EstoqueResponse response = estoqueStub.consultarEstoque(request);

            if (response.getSucesso()) {
                return String.format("Produto: %s | Nome: %s | Quantidade: %d | Preço: R$ %.2f | Status: %s",
                        response.getProdutoId(),
                        response.getNome(),
                        response.getQuantidade(),
                        response.getPreco(),
                        response.getStatus());
            } else {
                return "Erro: " + response.getMensagem();
            }
        } catch (Exception e) {
            return "Erro na comunicação: " + e.getMessage();
        }
    }

    public boolean verificarEstoque(String produtoId, int quantidade) {
        try {
            ConsultaEstoqueRequest request = ConsultaEstoqueRequest.newBuilder()
                    .setProdutoId(produtoId)
                    .build();

            EstoqueResponse response = estoqueStub.consultarEstoque(request);

            return response.getSucesso() && response.getQuantidade() >= quantidade;
        } catch (Exception e) {
            return false;
        }
    }

    public String reservarEstoque(String produtoId, int quantidade) {
        try {
            AtualizaEstoqueRequest request = AtualizaEstoqueRequest.newBuilder()
                    .setProdutoId(produtoId)
                    .setQuantidade(quantidade)
                    .setOperacao("SAIDA")
                    .build();

            EstoqueResponse response = estoqueStub.atualizarEstoque(request);
            return response.getMensagem();
        } catch (Exception e) {
            return "Erro: " + e.getMessage();
        }
    }

    // NOVO: Método para listar todos os produtos
    public String listarTodosEstoques() {
        try {
            ListaEstoquesRequest request = ListaEstoquesRequest.newBuilder().build();
            ListaEstoquesResponse response = estoqueStub.listarTodosEstoques(request);

            StringBuilder sb = new StringBuilder();
            sb.append("=== ESTOQUE COMPLETO ===\n");
            sb.append("Total de produtos: ").append(response.getTotalProdutos()).append("\n\n");

            for (EstoqueResponse produto : response.getProdutosList()) {
                sb.append(String.format("ID: %s | %s | Quant: %d | R$ %.2f | %s\n",
                        produto.getProdutoId(),
                        produto.getNome(),
                        produto.getQuantidade(),
                        produto.getPreco(),
                        produto.getStatus()));
            }

            return sb.toString();
        } catch (Exception e) {
            return "Erro ao listar estoques: " + e.getMessage();
        }
    }
}
