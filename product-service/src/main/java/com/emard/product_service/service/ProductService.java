package com.emard.product_service.service;

import com.emard.product_service.dto.ProductDto;
import com.emard.product_service.repository.ProductRepository;
import com.emard.product_service.util.EntityDtoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository repository;
    private final Sinks.Many<ProductDto> sink;

    public Flux<ProductDto> getAll() {
        return this.repository.findAll()
                .map(EntityDtoUtil::toDto);
    }
    public Flux<ProductDto> getProductByPriceRange(
            int min, int max) {
        return repository.findByPriceBetween(Range.closed(min, max))
                .map(EntityDtoUtil::toDto);
    }

    public Mono<ProductDto> getProductById(String id) {
        return repository.findById(id).map(EntityDtoUtil::toDto);
    }

    public Mono<ProductDto> insertProduct(Mono<ProductDto> dto) {
        return dto.map(EntityDtoUtil::toEntity)
                .flatMap(repository::insert)
                .map(EntityDtoUtil::toDto)
                .doOnNext(sink::tryEmitNext);
    }
    public Mono<ProductDto> updateProduct(String id, Mono<ProductDto> dto) {
        return repository.findById(id)
                .flatMap(p -> dto.map(EntityDtoUtil::toEntity)
                        .doOnNext(e -> e.setId(id)))
                .flatMap(repository::save)
                .map(EntityDtoUtil::toDto);

    }
    public Mono<Void> deleteProduct(String id){
        return repository.deleteById(id);
    }
}
