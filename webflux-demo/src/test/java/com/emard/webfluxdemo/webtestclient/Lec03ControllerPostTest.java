package com.emard.webfluxdemo.webtestclient;

import com.emard.webfluxdemo.controller.ReactiveMathController;
import com.emard.webfluxdemo.dto.MultiplyRequestDto;
import com.emard.webfluxdemo.dto.Response;
import com.emard.webfluxdemo.service.ReactiveMathService;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@WebFluxTest(ReactiveMathController.class)
public class Lec03ControllerPostTest {
    @Autowired
    private WebTestClient client;
    @MockBean
    private ReactiveMathService service;
    @Test
    void postTest(){
        when(service.multiply(any())).thenReturn(Mono.just(new Response(1)));
        client.post().uri("/reactive-math/multiply")
                .accept(APPLICATION_JSON)
                .headers(h-> h.setBasicAuth("username", "pwd"))
                .headers(h-> h.set("somekey", "somevalue"))
                .bodyValue(new MultiplyRequestDto())
                .exchange()
                .expectStatus().is2xxSuccessful();
    }
}
