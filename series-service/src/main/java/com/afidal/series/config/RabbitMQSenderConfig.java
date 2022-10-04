package com.afidal.series.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQSenderConfig {

    @Value("${queue.series.name}")
    private String seriesQueue;

    @Bean
    public Queue queue() {
        return new Queue(this.seriesQueue, true);
    }
}