package com.emard.webfluxdemo.webclient;

import com.emard.webfluxdemo.dto.MultiplyRequestDto;
import com.emard.webfluxdemo.dto.Response;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

public class Lec08AttributesTest extends BaseTest {
    @Test
    void headerTest(){
        var response= webClient.post()
                .uri("reactive-math/multiply")
                .bodyValue(buildRequestDto(5,2))
                .headers(h -> h.set("someKey", "someValue"))
                .attribute("auth", "oAuth")
                .retrieve()
                .bodyToMono(Response.class)
                .doOnNext(System.out::println);
        StepVerifier.create(response)
                .expectNextMatches(response1 -> response1.getOutput() == 10)
                .verifyComplete();
    }
    private MultiplyRequestDto buildRequestDto(int a , int b){
        MultiplyRequestDto dto = new MultiplyRequestDto();
        dto.setFirst(a);
        dto.setSecond(b);
        return dto;
    }
}
