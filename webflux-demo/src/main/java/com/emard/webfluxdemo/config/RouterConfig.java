package com.emard.webfluxdemo.config;

import com.emard.webfluxdemo.dto.InputFailedValidationResponse;
import com.emard.webfluxdemo.exception.InputValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@RequiredArgsConstructor
@Configuration
public class RouterConfig {
    private final RequestHandler requestHandler;
    @Bean
    public RouterFunction<ServerResponse> highLevelRouter(){
        return RouterFunctions.route()
                .path("router", this::serverResponseRouterFunction)
                .build();
    }
    //@Bean
    public RouterFunction<ServerResponse> serverResponseRouterFunction(){
        return RouterFunctions.route()
                //.GET("square/{input}", requestHandler::squareHandler)
                .GET("square/{input}", RequestPredicates.path("*/1?")
                        .or(RequestPredicates.path("*/2?")),requestHandler::squareHandler)
                .GET("square/{input}", req -> ServerResponse.badRequest().bodyValue("Only 10 - 29 allowed"))
                .GET("table/{input}", requestHandler::tableHandler)
                .GET("table/{input}/stream", requestHandler::tableStreamHandler)
                .POST("multiply", requestHandler::multiplyHandler)
                //.GET("square/{input}/validation", requestHandler::squareHandlerWithValidation)
                .onError(InputValidationException.class, exceptionHanler())
                .build();
    }
    @Bean
    public RouterFunction<ServerResponse> calculatorFunction(){
        return RouterFunctions.route()
                .GET("calculator/{first}/{second}", requestHandler::calculatorHandler)
                .build();
    }
    private BiFunction<Throwable, ServerRequest, Mono<ServerResponse>> exceptionHanler(){
        return (err, req) -> {
            InputValidationException ex = (InputValidationException) err;
            InputFailedValidationResponse response = new InputFailedValidationResponse();
            response.setInput(ex.getInput());
            response.setMessage(ex.getMessage());
            response.setErrorCode(InputValidationException.ERROR_CODE);
            return ServerResponse.badRequest().bodyValue(response);
        };
    }
}
