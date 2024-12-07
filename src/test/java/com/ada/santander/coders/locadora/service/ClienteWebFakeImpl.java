package com.ada.santander.coders.locadora.service;

import com.ada.santander.coders.locadora.response.CepResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class ClienteWebFakeImpl extends ClienteWeb {
    public CepResponse expectedCepResponse;
    public ClienteWebFakeImpl(WebClient webClient,CepResponse cepResponse) {
        super(webClient);
        expectedCepResponse=cepResponse;
    }

    @Override
    public Mono<CepResponse> consultaCep(String cep) {
        return Mono.just(expectedCepResponse);
    }
}
