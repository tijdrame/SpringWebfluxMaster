package com.emard.webfluxdemo.controller;

import com.emard.webfluxdemo.dto.MultiplyRequestDto;
import com.emard.webfluxdemo.dto.Response;
import com.emard.webfluxdemo.exception.InputValidationException;
import com.emard.webfluxdemo.service.ReactiveMathService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("reactive-math")
public class ReactiveMathController {
    private final ReactiveMathService mathService;

    @GetMapping("square/{input}")
    public Mono<Response> findSquare(@PathVariable int input){
        /*if(input <10 || input > 20){
            throw new InputValidationException(input);
        }
        return this.mathService.findSquare(input);*/
        return Mono.just(input)
                .handle((i, sink)->{
                    if(i >=10 && i <= 20)
                        sink.next(i);
                    else
                        sink.error(new InputValidationException(i));
                })
                .cast(Integer.class)
                .flatMap(i -> this.mathService.findSquare(i));
    }

    @GetMapping("table/{input}")
    public Flux<Response> multiplicationTable(@PathVariable int input){
        return this.mathService.multiplicationTable(input);
    }

    @GetMapping(value = "table/{input}/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Response> multiplicationTableStream(@PathVariable int input){
        return this.mathService.multiplicationTable(input);
    }

    @PostMapping("multiply")
    public Mono<Response> multiply(@RequestBody Mono<MultiplyRequestDto> dto,
                                   @RequestHeader Map<String, String> headers){
        log.info(String.format("The headers %s", headers));
        return mathService.multiply(dto);
    }

    @GetMapping("assignement/{input}")
    public Mono<ResponseEntity<Response>> assignement(@PathVariable int input){
        return Mono.just(input)
                .filter(integer -> integer >= 10 && integer<=20)
                .flatMap(integer -> this.mathService.findSquare(integer))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }
}
