package com.example.postof;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class PostofApplication {
	
	private static ConfigurableApplicationContext cac;

	public static void main(String[] args) {
		cac = SpringApplication.run(PostofApplication.class, args);
	}

	public static void close(int code) {
		SpringApplication.exit(cac, () -> code);
	}



}
