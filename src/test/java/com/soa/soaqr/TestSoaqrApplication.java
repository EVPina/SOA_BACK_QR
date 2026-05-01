package com.soa.soaqr;

import org.springframework.boot.SpringApplication;

public class TestSoaqrApplication {

	public static void main(String[] args) {
		SpringApplication.from(SoaqrApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
