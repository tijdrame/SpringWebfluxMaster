package com.emard.webfluxdemo.webtestclient;

import com.emard.webfluxdemo.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest
@AutoConfigureWebTestClient
public class Lec01SimpleWebTestClientTest {
    @Autowired
    private WebTestClient client;

    @Test
    void stepVerifierTest() {
        Flux<Response> response = client
                .get()
                .uri("/reactive-math/square/{number}",
                        10)
                .exchange()
                .expectStatus().is2xxSuccessful()
                //.expectStatus().isEqualTo(HttpStatus.OK)
                .expectHeader().contentType(APPLICATION_JSON)
                .returnResult(Response.class)
                .getResponseBody();
        StepVerifier.create(response)
                .expectNextMatches(r -> r.getOutput() == 100)
                .verifyComplete();
    }

    @Test
    void fluentAssertionTest() {
        client
            .get()
            .uri("/reactive-math/square/{number}", 10)
            .exchange()
            .expectStatus().is2xxSuccessful()
            .expectHeader().contentType(APPLICATION_JSON)
            .expectBody(Response.class)
            .value(r -> assertThat(r.getOutput()).isEqualTo(100));
    }
}
