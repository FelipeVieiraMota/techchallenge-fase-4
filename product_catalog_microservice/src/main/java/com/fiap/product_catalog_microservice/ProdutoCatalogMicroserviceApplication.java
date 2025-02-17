package com.fiap.product_catalog_microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ProdutoCatalogMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProdutoCatalogMicroserviceApplication.class, args);
	}

}
