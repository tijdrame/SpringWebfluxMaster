package com.emard.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.nio.charset.StandardCharsets;

import static java.nio.charset.StandardCharsets.UTF_8;

@RequiredArgsConstructor
//@Service
@Slf4j
public class DataSetupService implements
        CommandLineRunner {
    private final R2dbcEntityTemplate entityTemplate;
    @Value("classpath:h2/init.sql")
    private Resource initSql;

    @Override
    public void run(String... args) throws Exception {
        String query = StreamUtils.copyToString(
                initSql.getInputStream(), UTF_8);
        log.info(query);
        entityTemplate.getDatabaseClient()
                .sql(query)
                .then()
                .subscribe();
    }
}
