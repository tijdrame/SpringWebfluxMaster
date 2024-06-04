package com.emard.webfluxdemo.webtestclient;

import com.emard.webfluxdemo.controller.ReactiveMathController;
import com.emard.webfluxdemo.dto.Response;
import com.emard.webfluxdemo.service.ReactiveMathService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyInt;

@WebFluxTest(ReactiveMathController.class)
public class Lec04ErrorHandlingTest {
    @Autowired
    private WebTestClient client;
    @MockBean
    private ReactiveMathService service;
    @Test
    void errorHandlingTest(){
        when(service.findSquare(anyInt())).thenReturn(Mono.just(new Response(1)));
        client.get().uri("/reactive-math/square/{input}", 3)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                //$ represente le json entier nous on prend juste le msg)
                .jsonPath("$.message").isEqualTo("allowed range is 10 - 20")
                .jsonPath("$.errorCode").isEqualTo(100)
                .jsonPath("$.input").isEqualTo(3);
    }
}
