package com.example.messaging_stomp_websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Configuration
public class AppConfig {

    @Bean
    public BlockingQueue<HelloMessage> messageQueue() {
        return new LinkedBlockingQueue<>();
    }
}