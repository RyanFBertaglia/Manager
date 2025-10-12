package com.manager.grpc.server;

import io.grpc.stub.StreamObserver;
import lombok.Getter;
import lombok.Setter;
import net.devh.boot.grpc.server.service.GrpcService;
import com.manager.estoque.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.Map;

@GrpcService
public class EstoqueGrpcService extends EstoqueServiceGrpc.EstoqueServiceImplBase {

    // Dados padrão para teste
    private final Map<String, ProdutoEstoque> estoquePadrao = new HashMap<>();

    public EstoqueGrpcService() {
        // Inicializa com dados de exemplo
        estoquePadrao.put("PROD001", new ProdutoEstoque("PROD001", "Notebook Dell", 50, 2500.00));
        estoquePadrao.put("PROD002", new ProdutoEstoque("PROD002", "Mouse Logitech", 100, 89.90));
        estoquePadrao.put("PROD003", new ProdutoEstoque("PROD003", "Teclado Mecânico", 30, 350.00));
        estoquePadrao.put("PROD004", new ProdutoEstoque("PROD004", "Monitor 24\"", 15, 1200.00));
        estoquePadrao.put("PROD005", new ProdutoEstoque("PROD005", "Headphone Sony", 25, 450.00));
    }

    @Override
    public void consultarEstoque(EstoqueProto.ConsultaEstoqueRequest request,
                                 StreamObserver<EstoqueProto.EstoqueResponse> responseObserver) {

        String produtoId = request.getProdutoId();
        ProdutoEstoque produto = estoquePadrao.get(produtoId);

        EstoqueProto.EstoqueResponse response;

        if (produto != null) {
            response = EstoqueProto.EstoqueResponse.newBuilder()
                    .setProdutoId(produto.getId())
                    .setNome(produto.getNome())
                    .setQuantidade(produto.getQuantidade())
                    .setPreco(produto.getPreco())
                    .setStatus("DISPONIVEL")
                    .setSucesso(true)
                    .setMensagem("Produto encontrado em estoque")
                    .build();
        } else {
            response = EstoqueProto.EstoqueResponse.newBuilder()
                    .setProdutoId(produtoId)
                    .setNome("Produto não cadastrado")
                    .setQuantidade(0)
                    .setPreco(0.0)
                    .setStatus("NAO_ENCONTRADO")
                    .setSucesso(false)
                    .setMensagem("Produto não encontrado no estoque")
                    .build();
        }

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void listarTodosEstoques(EstoqueProto.ListaEstoquesRequest request,
                                    StreamObserver<EstoqueProto.ListaEstoquesResponse> responseObserver) {

        List<EstoqueProto.EstoqueResponse> produtos = estoquePadrao.values().stream()
                .map(produto -> EstoqueProto.EstoqueResponse.newBuilder()
                        .setProdutoId(produto.getId())
                        .setNome(produto.getNome())
                        .setQuantidade(produto.getQuantidade())
                        .setPreco(produto.getPreco())
                        .setStatus("DISPONIVEL")
                        .setSucesso(true)
                        .setMensagem("")
                        .build())
                .collect(Collectors.toList());

        EstoqueProto.ListaEstoquesResponse response = EstoqueProto.ListaEstoquesResponse.newBuilder()
                .addAllProdutos(produtos)
                .setTotalProdutos(produtos.size())
                .setSucesso(true)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void atualizarEstoque(EstoqueProto.AtualizaEstoqueRequest request,
                                 StreamObserver<EstoqueProto.EstoqueResponse> responseObserver) {

        String produtoId = request.getProdutoId();
        int quantidade = request.getQuantidade();
        String operacao = request.getOperacao();

        ProdutoEstoque produto = estoquePadrao.get(produtoId);

        EstoqueProto.EstoqueResponse response;

        if (produto != null) {
            int novaQuantidade = produto.getQuantidade();
            String mensagem = "";
            boolean sucesso = true;

            if ("ENTRADA".equals(operacao)) {
                novaQuantidade += quantidade;
                mensagem = "Entrada de " + quantidade + " unidades realizada";
            } else if ("SAIDA".equals(operacao)) {
                if (produto.getQuantidade() >= quantidade) {
                    novaQuantidade -= quantidade;
                    mensagem = "Saída de " + quantidade + " unidades realizada";
                } else {
                    sucesso = false;
                    mensagem = "Estoque insuficiente. Disponível: " + produto.getQuantidade();
                }
            }

            if (sucesso) {
                // Atualiza a quantidade (em memória)
                produto.setQuantidade(novaQuantidade);
            }

            response = EstoqueProto.EstoqueResponse.newBuilder()
                    .setProdutoId(produto.getId())
                    .setNome(produto.getNome())
                    .setQuantidade(produto.getQuantidade())
                    .setPreco(produto.getPreco())
                    .setStatus(sucesso ? "ATUALIZADO" : "ERRO")
                    .setSucesso(sucesso)
                    .setMensagem(mensagem)
                    .build();
        } else {
            response = EstoqueProto.EstoqueResponse.newBuilder()
                    .setProdutoId(produtoId)
                    .setNome("Produto não cadastrado")
                    .setQuantidade(0)
                    .setPreco(0.0)
                    .setStatus("NAO_ENCONTRADO")
                    .setSucesso(false)
                    .setMensagem("Produto não encontrado para atualização")
                    .build();
        }

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    // Classe interna para representar o produto - VERSÃO CORRIGIDA
    @Getter
    @Setter
    private static class ProdutoEstoque {
        private String id;
        private String nome;
        private int quantidade;
        private double preco;

        public ProdutoEstoque(String id, String nome, int quantidade, double preco) {
            this.id = id;
            this.nome = nome;
            this.quantidade = quantidade;
            this.preco = preco;
        }
    }
}