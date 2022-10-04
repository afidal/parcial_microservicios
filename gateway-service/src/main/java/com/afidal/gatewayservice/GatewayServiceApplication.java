package com.afidal.gatewayservice;

import com.afidal.gatewayservice.config.LoadBalancerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;

@SpringBootApplication
// Indicamos que vamos a personalizar el LB propio del Gateway | Microservicio: movie-service
@LoadBalancerClient(name = "movie-service", configuration = LoadBalancerConfig.class)
public class GatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayServiceApplication.class, args);
	}

}
