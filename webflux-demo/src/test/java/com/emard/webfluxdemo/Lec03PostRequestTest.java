package com.emard.webfluxdemo;

import com.emard.webfluxdemo.dto.MultiplyRequestDto;
import com.emard.webfluxdemo.dto.Response;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

public class Lec03PostRequestTest extends BaseTest{
    @Test
    void postTest(){
        var response= webClient.post()
                .uri("reactive-math/multiply")
                .bodyValue(buildRequestDto(5,2))
                .retrieve()
                .bodyToMono(Response.class)
                .doOnNext(System.out::println);
        StepVerifier.create(response)
                //.expectNextCount(1)
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
