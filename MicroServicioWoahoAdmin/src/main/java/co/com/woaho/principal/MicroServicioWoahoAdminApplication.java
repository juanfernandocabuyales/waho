package co.com.woaho.principal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

import co.com.woaho.utilidades.RegistrarLog;

@SpringBootApplication
@ComponentScan(basePackages = { "co.com.woaho"})
@EntityScan("co.com.woaho")
public class MicroServicioWoahoAdminApplication {
	
	private static RegistrarLog logs = new RegistrarLog(MicroServicioWoahoAdminApplication.class);

	public static void main(String[] args) {
		logs.registrarInfo("INICIA SERVICIO ADMIN 30/03/2021 12:14 pm");
		SpringApplication.run(MicroServicioWoahoAdminApplication.class, args);
	}

}
