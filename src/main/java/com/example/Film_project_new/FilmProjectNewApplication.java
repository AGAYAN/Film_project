package com.example.Film_project_new;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.example.Film_project_new.model")
public class FilmProjectNewApplication {
	public static void main(String[] args) {
		SpringApplication.run(FilmProjectNewApplication.class, args);
	}
}