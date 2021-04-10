package co.com.woaho.principal;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.util.unit.DataSize;

import co.com.woaho.utilidades.RegistrarLog;

@SpringBootApplication
@ComponentScan(basePackages = { "co.com.woaho"})
@EntityScan("co.com.woaho")
public class MicroServicioWoahoAdminApplication {
	
	private static RegistrarLog logs = new RegistrarLog(MicroServicioWoahoAdminApplication.class);

	public static void main(String[] args) {
		logs.registrarInfo("INICIA SERVICIO ADMIN 10/04/2021 17:11 pm");
		SpringApplication.run(MicroServicioWoahoAdminApplication.class, args);
	}
	
	@Bean
	public MultipartConfigElement multipartConfigElement() {
	    MultipartConfigFactory factory = new MultipartConfigFactory();
	    factory.setMaxFileSize(DataSize.ofMegabytes(100L));
	    factory.setMaxRequestSize(DataSize.ofMegabytes(100L));
	    return factory.createMultipartConfig();
	}
}
