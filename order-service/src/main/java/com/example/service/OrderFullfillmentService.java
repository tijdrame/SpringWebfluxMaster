package com.example.service;

import com.example.client.ProductClient;
import com.example.client.UserClient;
import com.example.dto.PurchaseOrderRequestDto;
import com.example.dto.PurchaseOrderResponseDto;
import com.example.dto.RequestContext;
import com.example.repo.PurchaseOrderRepository;
import com.example.util.EntityDtoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class OrderFullfillmentService {
    private final ProductClient productClient;
    private final PurchaseOrderRepository orderRepository;
    private final UserClient userClient;
    public Mono<PurchaseOrderResponseDto> processOrder(
            Mono<PurchaseOrderRequestDto> dto){
        return dto.map(RequestContext::new)
                .flatMap(this::productRequestResponse)
                .doOnNext(EntityDtoUtil::setTransactionRequestDto)
                .flatMap(this::userRequestResponse)
                .map(EntityDtoUtil::getPurchaseOrder)
                .map(orderRepository::save)//blocking
                .map(EntityDtoUtil::getPurchaseOrderResponseDto)
                //pour ne pas attendre le save qui est bloquant
                .subscribeOn(Schedulers.boundedElastic());
    }
    private Mono<RequestContext> productRequestResponse(RequestContext rc){
        return productClient.getProductById(
                rc.getPurchaseOrderRequestDto().getProductId())
                .doOnNext(rc::setProductDto)
                //.retry(5)essaye 5 autre fois basique
                .retryWhen(Retry.fixedDelay(5, Duration.ofSeconds(1)))
                .thenReturn(rc);
    }
    private Mono<RequestContext> userRequestResponse(RequestContext rc){
        return userClient.authorizeTransaction(
                        rc.getTransactionRequestDto())
                .doOnNext(rc::setTransactionResponseDto)
                .thenReturn(rc);
    }

}
