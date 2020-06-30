package com.co.woaho.principal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "com.co.woaho"})
@EntityScan("com.co.woaho")
public class MicroservicioWoahoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicioWoahoApplication.class, args);
	}

}
