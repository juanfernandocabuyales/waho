package com.co.woaho.principal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import com.co.woaho.utilidades.RegistrarLog;

@SpringBootApplication
@ComponentScan(basePackages = { "com.co.woaho"})
@EntityScan("com.co.woaho")
public class MicroservicioWoahoApplication {
	
	private static RegistrarLog logs = new RegistrarLog(MicroservicioWoahoApplication.class);	
	
	public static void main(String[] args) {
		logs.registrarLogInfo("Se inicia servicio");
		SpringApplication.run(MicroservicioWoahoApplication.class, args);
	}

}
