package com.emard.webfluxdemo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
@Slf4j
@Configuration
public class WebClientConfig {
    @Bean
    public WebClient webClient(){
        return WebClient.builder()
                .baseUrl("http://localhost:8080/")
                .filter(this::sessionToken)
                .build();
    }
    private Mono<ClientResponse> sessionToken(ClientRequest request, ExchangeFunction ex){
        //si attribut auth est present
            //et = basic call withBasicAuth
            //sinon call withOAuth
        //sinon (att absent) ne rien faire.
        var clientRequest = request.attribute("auth")
                .map(v-> v.equals("basic")?withBasicAuth(request): withOAuth(request))
                .orElse(request);

        return ex.exchange(clientRequest);
    }
    private ClientRequest withBasicAuth(ClientRequest request){
        return ClientRequest.from(request)
                .headers(h-> h.setBasicAuth("user", "pwd"))
                .build();
    }
    private ClientRequest withOAuth(ClientRequest request){
        return ClientRequest.from(request)
                .headers(h-> h.setBearerAuth("some-token"))
                .build();
    }
    /*private Mono<ClientResponse> sessionToken(ClientRequest request, ExchangeFunction ex){
        log.info("generating session token");
        ClientRequest clientRequest = ClientRequest.from(request)
                .headers(h-> h.setBearerAuth("some-lengthy-jwt")).build();
        return ex.exchange(clientRequest);
    }*/
}
