package com.emard.webfluxdemo.webtestclient;

import com.emard.webfluxdemo.controller.ParamsController;
import com.emard.webfluxdemo.controller.ReactiveMathController;
import com.emard.webfluxdemo.dto.Response;
import com.emard.webfluxdemo.service.ReactiveMathService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;

@WebFluxTest(controllers = {ReactiveMathController.class, ParamsController.class})
public class Lec02ControllerGetTest {
    @Autowired
    private WebTestClient client;
    @MockBean
    private ReactiveMathService service;
    @Test
    void fluentAssertionTest() {
        when(service.findSquare(anyInt())).thenReturn(Mono.just(new Response(100)));
        client
            .get()
            .uri("/reactive-math/square/{number}", 10)
            .exchange()
            .expectStatus().is2xxSuccessful()
            .expectHeader().contentType(APPLICATION_JSON)
            .expectBody(Response.class)
            .value(r -> assertThat(r.getOutput()).isEqualTo(100));
    }
    @Test
    void listResponseTest() {
        var flux = Flux.range(1,3).map(Response::new);
        when(service.multiplicationTable(anyInt())).thenReturn(flux);
        //when(service.multiplicationTable(anyInt())).thenReturn(Flux.error(new IllegalArgumentException()));
        client
            .get()
            .uri("/reactive-math/table/{number}", 5)
            .exchange()
            .expectStatus().is2xxSuccessful()
            .expectHeader().contentType(APPLICATION_JSON)
            .expectBodyList(Response.class)
            .hasSize(3);
    }
    @Test
    void streamingResponseTest() {
        var flux = Flux.range(1,3).map(Response::new)
                .delayElements(Duration.ofMillis(100));
        when(service.multiplicationTable(anyInt())).thenReturn(flux);
        client
                .get()
                .uri("/reactive-math/table/{number}/stream", 5)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentTypeCompatibleWith(TEXT_EVENT_STREAM)
                .expectBodyList(Response.class)
                .hasSize(3);
    }
    @Test
    void paramTest(){
        var map = Map.of(
                "count", 10,
                "page", 20);
        client.get()
            .uri(b-> b.path("/jobs/search")
            .query("count={count}&page={page}").build(map))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(Integer.class)
                .hasSize(2)
                .contains(10,20);
    }
}
