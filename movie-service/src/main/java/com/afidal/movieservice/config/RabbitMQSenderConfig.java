package com.afidal.movieservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Queue;

@Configuration
public class RabbitMQSenderConfig {

    @Value("${queue.movie.name}")
    private String movieQueue;

    @Bean
    public Queue queue() {
        return new Queue(this.movieQueue, true);
    }
}