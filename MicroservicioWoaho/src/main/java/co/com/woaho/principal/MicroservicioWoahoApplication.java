package co.com.woaho.principal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

import co.com.woaho.utilidades.RegistrarLog;

@SpringBootApplication
@ComponentScan(basePackages = { "co.com.woaho"})
@EntityScan("co.com.woaho")
public class MicroservicioWoahoApplication {
	
	private static RegistrarLog logs = new RegistrarLog(MicroservicioWoahoApplication.class);	
	
	public static void main(String[] args) {
		logs.registrarLogInfo("Se inicia servicio");
		SpringApplication.run(MicroservicioWoahoApplication.class, args);
	}

}
