package com.mongo;

import com.spring4all.mongodb.EnableMongoPlus;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableMongoPlus
public class CsdnApplication {

	public static void main(String[] args) {
		SpringApplication.run(CsdnApplication.class, args);
	}
}
