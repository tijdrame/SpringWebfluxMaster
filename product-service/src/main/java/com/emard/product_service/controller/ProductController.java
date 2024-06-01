package com.emard.product_service.controller;

import com.emard.product_service.dto.ProductDto;
import com.emard.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("product")
public class ProductController {
    private final ProductService service;
    @GetMapping("price-range")
    public Flux<ProductDto> getProducPriceRange(
            @RequestParam int min,
            @RequestParam int max
    ){
        return service.getProductByPriceRange(min, max);
    }
    @GetMapping("all")
    public Flux<ProductDto> all(){
        return service.getAll();
    }
    @GetMapping("{id}")
    public Mono<ResponseEntity<ProductDto>> getProductById(
            @PathVariable String id){
        return service.getProductById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    @PostMapping
    public Mono<ProductDto> insertProduct(
            @RequestBody Mono<ProductDto> dto){
        return service.insertProduct(dto);
    }
    @PutMapping("{id}")
    public Mono<ResponseEntity<ProductDto>> updateProduct(
            @PathVariable String id,
            @RequestBody Mono<ProductDto> dto){
        return service.updateProduct(id, dto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    @DeleteMapping("{id}")
    public Mono<Void> deleteProduct(@PathVariable String id){
        return service.deleteProduct(id);
    }
}
