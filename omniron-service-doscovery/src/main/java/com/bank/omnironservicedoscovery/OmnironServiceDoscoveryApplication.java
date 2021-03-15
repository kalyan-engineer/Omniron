package com.bank.omnironservicedoscovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class OmnironServiceDoscoveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(OmnironServiceDoscoveryApplication.class, args);
	}

}
