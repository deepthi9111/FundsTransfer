package com.deepthi.fundstransfer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class FundsTransferApplication {

	public static void main(String[] args) {
		SpringApplication.run(FundsTransferApplication.class, args);
	}

}
