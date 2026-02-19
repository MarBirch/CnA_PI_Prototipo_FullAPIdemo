package com.example.FullAPIdemo;

import org.springframework.boot.SpringApplication;

public class TestFullApIdemoApplication {

	public static void main(String[] args) {
		SpringApplication.from(FullAPIdemoApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
