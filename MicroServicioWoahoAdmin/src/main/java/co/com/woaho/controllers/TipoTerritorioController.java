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

import co.com.woaho.interfaces.ITipoTerritorioServices;
import co.com.woaho.request.ConsultarTiposRequest;
import co.com.woaho.request.CrearTipoRequest;
import co.com.woaho.request.EliminarRequest;
import co.com.woaho.request.GeneralRequest;
import co.com.woaho.response.ConsultarTiposResponse;
import co.com.woaho.response.CrearResponse;
import co.com.woaho.response.EliminarResponse;
import co.com.woaho.response.GeneralResponse;
import co.com.woaho.utilidades.RegistrarLog;

@RestController
@RequestMapping("/tipoTerritorio")
public class TipoTerritorioController {

	private RegistrarLog logs = new RegistrarLog(TipoTerritorioController.class);
	
	@Autowired
	private ITipoTerritorioServices tipoTerritorioService;
	
	@PostMapping(value = "/consultarTipos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GeneralResponse> consultarTipos(@RequestBody GeneralRequest request) {
		
		logs.registrarLogInfoEjecutaServicio("consultarTipos",request.getStrMensaje());
		
		Gson gson = new Gson();
		ConsultarTiposRequest consultarTiposRequest = gson.fromJson(request.getStrMensaje(), ConsultarTiposRequest.class);
		ConsultarTiposResponse consultarTiposResponse = tipoTerritorioService.consultarTipos(consultarTiposRequest);
		
		GeneralResponse resp = new GeneralResponse();
		resp.setMensaje(gson.toJson(consultarTiposResponse));
		
		logs.registrarLogInfoRespuestaServicio("consultarTipos",resp.getMensaje());
		
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
	
	@PostMapping(value = "/crearTipos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GeneralResponse> crearTipos(@RequestBody GeneralRequest request) {
		
		logs.registrarLogInfoEjecutaServicio("crearTipos",request.getStrMensaje());
		
		Gson gson = new Gson();
		CrearTipoRequest crearTipoRequest = gson.fromJson(request.getStrMensaje(), CrearTipoRequest.class);
		CrearResponse crearResponse = tipoTerritorioService.crearTipoTerritorio(crearTipoRequest);
		
		GeneralResponse resp = new GeneralResponse();
		resp.setMensaje(gson.toJson(crearResponse));
		
		logs.registrarLogInfoRespuestaServicio("crearTipos",resp.getMensaje());
		
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
	
	@PostMapping(value = "/actualizarTipos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GeneralResponse> actualizarTipos(@RequestBody GeneralRequest request) {
		
		logs.registrarLogInfoEjecutaServicio("actualizarTipos",request.getStrMensaje());
		
		Gson gson = new Gson();
		CrearTipoRequest crearTipoRequest = gson.fromJson(request.getStrMensaje(), CrearTipoRequest.class);
		CrearResponse crearResponse = tipoTerritorioService.actualizarTipoTerritorio(crearTipoRequest);
		
		GeneralResponse resp = new GeneralResponse();
		resp.setMensaje(gson.toJson(crearResponse));
		
		logs.registrarLogInfoRespuestaServicio("actualizarTipos",resp.getMensaje());
		
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
	
	@PostMapping(value = "/eliminarTipos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GeneralResponse> eliminarTipos(@RequestBody GeneralRequest request) {
		
		logs.registrarLogInfoEjecutaServicio("eliminarTipos",request.getStrMensaje());
		
		Gson gson = new Gson();
		EliminarRequest eliminarRequest = gson.fromJson(request.getStrMensaje(), EliminarRequest.class);
		EliminarResponse eliminarResponse = tipoTerritorioService.eliminarTipo(eliminarRequest);
		
		GeneralResponse resp = new GeneralResponse();
		resp.setMensaje(gson.toJson(eliminarResponse));
		
		logs.registrarLogInfoRespuestaServicio("eliminarTipos",resp.getMensaje());
		
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
}
