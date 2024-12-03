package com.ada.santander.coders.locadora.service;

import com.ada.santander.coders.locadora.response.CepResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ClienteWeb {

    private final WebClient webClient;

    public ClienteWeb(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<CepResponse> consultaCep(String cep) {
        return webClient
                .get()
                .uri("/{cep}/json", cep)
                .retrieve()
                .bodyToMono(CepResponse.class);
    }

}
