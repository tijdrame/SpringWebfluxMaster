package com.example.service;

import com.example.dto.PurchaseOrderResponseDto;
import com.example.entity.PurchaseOrder;
import com.example.repo.PurchaseOrderRepository;
import com.example.util.EntityDtoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderQueryService {
    private final PurchaseOrderRepository repository;
    public Flux<PurchaseOrderResponseDto> getProductsByUserId(int userId){
        //bad usage blocking way
        /*List<PurchaseOrder> purchaseOrders= repository.findByUserId(userId);
        return Flux.fromIterable(purchaseOrders);*/
        return Flux.fromStream(()-> repository.findByUserId(userId).stream())
                .map(EntityDtoUtil::getPurchaseOrderResponseDto)
                .subscribeOn(Schedulers.boundedElastic());
    }
}
