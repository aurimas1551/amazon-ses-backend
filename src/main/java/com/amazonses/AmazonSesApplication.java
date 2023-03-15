package com.amazonses;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "com.amazonses"} )
public class AmazonSesApplication {

	public static void main(String[] args) {
		SpringApplication.run(AmazonSesApplication.class, args);
	}
	
}
