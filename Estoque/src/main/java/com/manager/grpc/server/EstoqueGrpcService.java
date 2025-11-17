package com.manager.grpc.server;

import com.manager.model.Product;
import com.manager.service.EstoqueService;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import com.manager.estoque.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;

@GrpcService
public class EstoqueGrpcService extends EstoqueServiceGrpc.EstoqueServiceImplBase {

    private final EstoqueService estoqueService;

    @Autowired
    public EstoqueGrpcService(EstoqueService estoqueService) {
        this.estoqueService = estoqueService;
    }

    @Override
    public void consultarEstoque(EstoqueProto.ConsultaEstoqueRequest request,
                                 StreamObserver<EstoqueProto.EstoqueResponse> responseObserver) {

        long produtoId = request.getProdutoId();
        Product produto = estoqueService.findById(produtoId);

        EstoqueProto.EstoqueResponse response;

        if (produto != null) {
            response = produto.toEstoqueProto("Disponivel", true,
                    "Produto encontrado em estoque");
        } else {
            response = createErrorResponse(produtoId, "Produto não encontrado no estoque");
        }

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void listarTodosEstoques(EstoqueProto.ListaEstoquesRequest request,
                                    StreamObserver<EstoqueProto.ListaEstoquesResponse> responseObserver) {

        List<EstoqueProto.EstoqueResponse> produtos = estoqueService.findAllProducts().stream()
                .map(produto -> EstoqueProto.EstoqueResponse.newBuilder()
                        .setProdutoId(produto.getId())
                        .setNome(produto.getName())
                        .setQuantidade(produto.getUnitsInStock())
                        .setPreco(produto.getUnitPrice() != null ? produto.getUnitPrice() : 0.0f)
                        .setStatus("DISPONIVEL")
                        .setSucesso(true)
                        .setMensagem("")
                        .build())
                .toList();

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

        int produtoId = request.getProdutoId();
        int quantidade = request.getQuantidade();
        String operacao = request.getOperacao();

        Product produto = estoqueService.findById((long) produtoId);

        EstoqueProto.EstoqueResponse response;

        if (produto != null) {
            int novaQuantidade = produto.getUnitsInStock();
            int qtdOriginal = quantidade;

            if ("SAIDA".equalsIgnoreCase(operacao)) {
                if (produto.getUnitsInStock() < quantidade) {
                    response = createErrorResponse(produtoId,
                            "Estoque insuficiente. Disponível: " + produto.getUnitsInStock());
                    sendResponse(response, responseObserver);
                    return;
                }
                quantidade = -quantidade;
            }

            novaQuantidade += quantidade;
            produto.setUnitsInStock((short) novaQuantidade);
            estoqueService.save(produto);

            String mensagem = MessageFormat.format("{0} de {1} unidades realizada",
                    operacao.toLowerCase(), qtdOriginal);

            response = produto.toEstoqueProto("Disponível", true, mensagem);
        } else {
            response = createErrorResponse(produtoId, "Produto não encontrado para atualização");
        }

        sendResponse(response, responseObserver);
    }


    private void sendResponse(EstoqueProto.EstoqueResponse response, StreamObserver<EstoqueProto.EstoqueResponse> responseObserver) {
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    private EstoqueProto.EstoqueResponse createErrorResponse(long produtoId, String mensagem) {
        return EstoqueProto.EstoqueResponse.newBuilder()
                .setProdutoId((int) produtoId)
                .setNome("")
                .setQuantidade(0)
                .setPreco(0.0f)
                .setStatus("NAO_ENCONTRADO")
                .setSucesso(false)
                .setMensagem(mensagem)
                .build();
    }
}