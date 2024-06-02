package com.example.client;

import com.example.dto.ProductDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ProductClient {
    private final WebClient webClient;
    public ProductClient(@Value("${product.service.url}") String url){
        webClient = WebClient.builder().baseUrl(url).build();
    }
    public Mono<ProductDto> getProductById(final String productId){
        return webClient.get().uri("{id}", productId)
                .retrieve()
                .bodyToMono(ProductDto.class);
    }
}
