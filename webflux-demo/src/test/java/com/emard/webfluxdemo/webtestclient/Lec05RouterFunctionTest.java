package com.emard.webfluxdemo.webtestclient;

import com.emard.webfluxdemo.config.RequestHandler;
import com.emard.webfluxdemo.config.RouterConfig;
import com.emard.webfluxdemo.dto.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.ServerResponse;

@WebFluxTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = RouterConfig.class)
public class Lec05RouterFunctionTest {
    private WebTestClient client;
    @Autowired
    private ApplicationContext ctx;
    @MockBean
    private RequestHandler handler;
    @BeforeAll
    public void setClient(){
        client = WebTestClient.bindToApplicationContext(ctx).build();
        //WebTestClient.bindToServer().baseUrl("http://localhost:8080/someUrl").build();
    }
    @Test
    void test(){
        when(handler.squareHandler(any())).thenReturn
                (ServerResponse.ok().bodyValue(new Response(225)));
        client.get().uri("/router/square/{input}", 15)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Response.class)
                .value(r-> assertThat(r.getOutput()).isEqualTo(225));
    }
}
