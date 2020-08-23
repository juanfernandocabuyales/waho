package co.com.woaho.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import co.com.woaho.interfaces.IServicioServices;
import co.com.woaho.response.ConsultarServiciosResponse;
import co.com.woaho.response.GeneralResponse;
import co.com.woaho.utilidades.RegistrarLog;

@RestController
@RequestMapping("/servicio")
public class ServicioController {

	private RegistrarLog logs = new RegistrarLog(ServicioController.class);

	@Autowired
	private IServicioServices servicioService;
	
	@PostMapping(value = "/consultarServicios", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> consultarServicios() {
		
		logs.registrarLogInfoEjecutaServicio("consultarServicios");
		
		Gson gson = new Gson();
		ConsultarServiciosResponse consultarServiciosResponse = servicioService.consultarServicios();
		
		GeneralResponse resp = new GeneralResponse();
		resp.setMensaje(gson.toJson(consultarServiciosResponse));
		
		return new ResponseEntity<GeneralResponse>(resp, HttpStatus.OK);
	}
}