package com.emard.webfluxdemo.service;

import com.emard.webfluxdemo.dto.MultiplyRequestDto;
import com.emard.webfluxdemo.dto.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
public class ReactiveMathService {
    public Mono<Response> findSquare(int input){
        return Mono.fromSupplier(()-> new Response(input * input));
        //return Mono.fromSupplier(()-> input * input).map(Response::new);
    }
    public Flux<Response> multiplicationTable(int input){
        //not reactive code
        /*List<Response> list = IntStream.rangeClosed(1, 10)
                .peek(i-> SleepUtil.sleepSeconds(1))
                .peek(i-> log.info("math-service processing [{}]", i))
                .mapToObj(i -> new Response(i * input))
                .collect(Collectors.toList());
        return Flux.fromIterable(list);*/
        return Flux.range(1, 10)
                //.doOnNext(i-> SleepUtil.sleepSeconds(1))
                .delayElements(Duration.ofSeconds(1))
                .doOnNext(i-> log.info("math-service processing [{}]", i))
                .map(i -> new Response(i * input));
    }
    public Mono<Response>multiply(Mono<MultiplyRequestDto> dtoMono){
        return dtoMono.map(dto-> dto.getFirst() * dto.getSecond())
                .map(Response::new);
    }
    public Mono<Response>calculator(Mono<MultiplyRequestDto> dtoMono, String op){
        switch (op){
            case "+": return dtoMono.map(dto-> dto.getFirst() + dto.getSecond()).map(Response::new);
            case "-": return dtoMono.map(dto-> dto.getFirst() - dto.getSecond()).map(Response::new);
            case "*": return dtoMono.map(dto-> dto.getFirst() * dto.getSecond()).map(Response::new);
            case "/": return dtoMono.map(dto-> dto.getFirst() / dto.getSecond()).map(Response::new);
            default: return Mono.empty();
        }
    }
}
