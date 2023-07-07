package com.example.customerorder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class CustomerOrderFileApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerOrderFileApplication.class, args);
	}

}
