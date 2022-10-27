package com.lucas.consumerOne;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ConsumerOneApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsumerOneApplication.class, args);
	}

}
