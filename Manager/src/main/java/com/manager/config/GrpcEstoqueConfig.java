package com.manager.config;

import com.manager.estoque.EstoqueServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcEstoqueConfig {

    @Bean
    public EstoqueServiceGrpc.EstoqueServiceBlockingStub estoqueServiceBlockingStub() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();
        return EstoqueServiceGrpc.newBlockingStub(channel);
    }
}
