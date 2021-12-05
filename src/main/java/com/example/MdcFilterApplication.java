package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Locale;
import java.util.UUID;

@SpringBootApplication
public class MdcFilterApplication {

	public static void main(String[] args) {
		SpringApplication.run(MdcFilterApplication.class, args);
		String token=UUID.randomUUID().toString();
		System.out.println(token);
		System.out.println(token.toUpperCase().replace("-",""));
		System.out.println(token.substring(5,8));
	}

}
