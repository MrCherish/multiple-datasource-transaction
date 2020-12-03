package com.xtt.transaction;

import org.slf4j.MDC;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.UUID;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@EnableTransactionManagement
public class TransactionApplication {

    public static void main(String[] args) {
        MDC.put("requestId", UUID.randomUUID().toString());
        SpringApplication.run(TransactionApplication.class);
    }
}
