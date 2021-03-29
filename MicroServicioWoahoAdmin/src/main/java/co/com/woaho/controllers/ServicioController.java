package co.com.woaho.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import co.com.woaho.interfaces.IServicioServices;
import co.com.woaho.request.ConsultarServiciosRequest;
import co.com.woaho.request.CrearServicioRequest;
import co.com.woaho.request.GeneralRequest;
import co.com.woaho.response.ConsultarServiciosResponse;
import co.com.woaho.response.CrearServicioResponse;
import co.com.woaho.response.GeneralResponse;
import co.com.woaho.utilidades.RegistrarLog;

@RestController
@RequestMapping("/servicio")
public class ServicioController {

	private RegistrarLog logs = new RegistrarLog(ServicioController.class);

	@Autowired
	private IServicioServices servicioService;
	
	@PostMapping(value = "/consultarServicios", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> consultarServicios(@RequestBody GeneralRequest request) {
		
		logs.registrarLogInfoEjecutaServicio("consultarServicios", request.getStrMensaje());
		
		Gson gson = new Gson();
		
		ConsultarServiciosRequest consultarServiciosRequest = gson.fromJson(request.getStrMensaje(), ConsultarServiciosRequest.class);
		ConsultarServiciosResponse consultarServiciosResponse = servicioService.consultarServicios(consultarServiciosRequest);
		
		GeneralResponse resp = new GeneralResponse();
		resp.setMensaje(gson.toJson(consultarServiciosResponse));
		
		logs.registrarLogInfoRespuestaServicio("consultarServicios", resp.getMensaje());
		
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
	
	@PostMapping(value = "/crearServicios", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> crearServicios(@RequestBody GeneralRequest request) {
		
		logs.registrarLogInfoEjecutaServicio("crearServicios", request.getStrMensaje());
		
		Gson gson = new Gson();
		
		CrearServicioRequest crearServicioRequest = gson.fromJson(request.getStrMensaje(), CrearServicioRequest.class);
		CrearServicioResponse crearServicioResponse = servicioService.crearServicio(crearServicioRequest);
		
		GeneralResponse resp = new GeneralResponse();
		resp.setMensaje(gson.toJson(crearServicioResponse));
		
		logs.registrarLogInfoRespuestaServicio("crearServicios", resp.getMensaje());
		
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
}
