package com.example;

import com.example.client.ProductClient;
import com.example.client.UserClient;
import com.example.dto.ProductDto;
import com.example.dto.PurchaseOrderRequestDto;
import com.example.dto.UserDto;
import com.example.service.OrderFullfillmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
class OrderServiceApplicationTests {
	@Autowired
	private UserClient userClient;
	@Autowired
	private ProductClient productClient;
	@Autowired
	private OrderFullfillmentService service;
	@Test
	void contextLoads() {
		//zip prend un element de chaque liste (ici 2 mais ça peut etre 3..)
		var dtoFlux = Flux.zip(userClient.getAllUsers(), productClient.getAllProducts())
				.map(t-> buildDto(t.getT1(), t.getT2()))
				.map(dto-> service.processOrder(Mono.just(dto)))
				.doOnNext(System.out::println);
		StepVerifier.create(dtoFlux)
				.expectNextCount(3)
				.verifyComplete();
	}
	private PurchaseOrderRequestDto buildDto(
			UserDto userDto, ProductDto productDto){
		return PurchaseOrderRequestDto.builder()
				.userId(userDto.getId())
				.productId(productDto.getId())
				.build();
	}

}
