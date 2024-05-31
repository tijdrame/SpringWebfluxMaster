package com.emard.webfluxdemo;

import com.emard.webfluxdemo.dto.InputFailedValidationResponse;
import com.emard.webfluxdemo.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.net.URI;
import java.util.Map;

public class Lec06QueryParamsTest extends BaseTest {
    @Test
    void queryParamsTest(){
        String url = "http://localhost:8080/jobs/search?count={count}&page={page}";
        //URI uri =  UriComponentsBuilder.fromUriString(url)
          //      .build(10,20);
        var map = Map.of(
                "count", 10,
                "page", 20);
        var response = webClient.get()
                //.uri(uri)
                .uri(b-> b.path("jobs/search")
                        .query("count={count}&page={page}").build(map))
                .retrieve()
                .bodyToFlux(Integer.class)
                .doOnNext(System.out::println);
        StepVerifier.create(response)
                .expectNextCount(2)
                .verifyComplete();
    }

}
