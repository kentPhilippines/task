package com.task;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;
@RestController
@SpringBootApplication
@EnableTransactionManagement
public class application {
	public static void main(String[] args) {
		SpringApplication.run(application.class, args);
	}
}
