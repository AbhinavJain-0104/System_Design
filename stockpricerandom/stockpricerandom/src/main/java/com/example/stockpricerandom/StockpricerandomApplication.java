package com.example.stockpricerandom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class StockpricerandomApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockpricerandomApplication.class, args);
	}

}
