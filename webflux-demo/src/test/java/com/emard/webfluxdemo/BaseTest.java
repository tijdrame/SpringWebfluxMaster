package com.emard.webfluxdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
class BaseTest {@Autowired
protected WebClient webClient;


}
