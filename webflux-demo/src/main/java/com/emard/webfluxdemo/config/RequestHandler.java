package com.emard.webfluxdemo.config;

import com.emard.webfluxdemo.dto.InputFailedValidationResponse;
import com.emard.webfluxdemo.dto.MultiplyRequestDto;
import com.emard.webfluxdemo.dto.Response;
import com.emard.webfluxdemo.exception.InputValidationException;
import com.emard.webfluxdemo.service.ReactiveMathService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class RequestHandler {
    private final ReactiveMathService mathService;
    public Mono<ServerResponse> squareHandler(ServerRequest request){
        int input = Integer.parseInt(request.pathVariable("input"));
        Mono<Response> responseMono = this.mathService.findSquare(input);
        // bodyValue pour les pojos simples
        return ServerResponse.ok().body(responseMono, Response.class);
    }
    public Mono<ServerResponse> tableHandler(ServerRequest request){
        int input = Integer.parseInt(request.pathVariable("input"));
        Flux<Response> responseFlux = this.mathService.multiplicationTable(input);
        // bodyValue pour les pojos simples
        return ServerResponse.ok().body(responseFlux, Response.class);
    }
    public Mono<ServerResponse> tableStreamHandler(ServerRequest request){
        int input = Integer.parseInt(request.pathVariable("input"));
        Flux<Response> responseFlux = this.mathService.multiplicationTable(input);
        // bodyValue pour les pojos simples
        return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM)
                .body(responseFlux, Response.class);
    }

    public Mono<ServerResponse> multiplyHandler(ServerRequest request){
        Mono<MultiplyRequestDto> requestDtoMono = request.bodyToMono(MultiplyRequestDto.class);
        Mono<Response> responseMono = this.mathService.multiply(requestDtoMono);
        // bodyValue pour les pojos simples
        return ServerResponse.ok()
                .body(responseMono, Response.class);
    }
    public Mono<ServerResponse> squareHandlerWithValidation(ServerRequest request){
        int input = Integer.parseInt(request.pathVariable("input"));
        if(input < 10 || input > 20){
            return Mono.error(new InputValidationException(input));
        }
        Mono<Response> responseMono = this.mathService.findSquare(input);
        // bodyValue pour les pojos simples
        return ServerResponse.ok().body(responseMono, Response.class);
    }

    public Mono<ServerResponse> calculatorHandler(ServerRequest request){
        int first = Integer.parseInt(request.pathVariable("first"));
        int second = Integer.parseInt(request.pathVariable("second"));
        MultiplyRequestDto dto = new MultiplyRequestDto();
        dto.setSecond(second);dto.setFirst(first);
        String op = request.headers().firstHeader("OP");
        if(op.equals("/") && second==0 ){
            return ServerResponse.badRequest().bodyValue("On ne peut pas diviser par 0.");
        }
        if(!op.equals("+")&&!op.equals("-")&&!op.equals("*")&&!op.equals("/")){
            return ServerResponse.badRequest().bodyValue("Cette opération n'est pas supporté");
        }
        Mono<MultiplyRequestDto> requestDtoMono = Mono.just(dto);
        Mono<Response> responseMono = this.mathService.calculator(requestDtoMono, op);
        return ServerResponse.ok().body(responseMono, Response.class);
    }
}
