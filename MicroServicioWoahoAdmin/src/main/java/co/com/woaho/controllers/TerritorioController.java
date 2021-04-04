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

import co.com.woaho.interfaces.ITerritorioService;
import co.com.woaho.request.ConsultarTerritorioRequest;
import co.com.woaho.request.ConsultarTerritoriosRequest;
import co.com.woaho.request.CrearTerritoriosRequest;
import co.com.woaho.request.EliminarRequest;
import co.com.woaho.request.GeneralRequest;
import co.com.woaho.response.ConsultarTerritorioResponse;
import co.com.woaho.response.ConsultarTerritoriosResponse;
import co.com.woaho.response.CrearResponse;
import co.com.woaho.response.EliminarResponse;
import co.com.woaho.response.GeneralResponse;
import co.com.woaho.utilidades.RegistrarLog;

@RestController
@RequestMapping("/territorio")
public class TerritorioController {

	@Autowired
	private ITerritorioService territorioService;
	
	private RegistrarLog logs = new RegistrarLog(TerritorioController.class);
	
	@PostMapping(value = "/consultarPaises", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GeneralResponse> consultarPaises(@RequestBody GeneralRequest request) {
		logs.registrarLogInfoEjecutaServicio("consultarPaises");
		Gson gson = new Gson();
		ConsultarTerritorioRequest consultarTerritorioRequest = gson.fromJson(request.getStrMensaje(), ConsultarTerritorioRequest.class);
		ConsultarTerritorioResponse consultarTerritorioResponse = territorioService.obtenerTerritorios(consultarTerritorioRequest);

		GeneralResponse resp = new GeneralResponse();
		resp.setMensaje(gson.toJson(consultarTerritorioResponse));

		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
	
	@PostMapping(value = "/consultarTerritorios", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GeneralResponse> consultarTerritorios(@RequestBody GeneralRequest request) {
		
		logs.registrarLogInfoEjecutaServicio("consultarTerritorios", request.getStrMensaje());
		
		Gson gson = new Gson();
		
		ConsultarTerritoriosRequest consultarTerritoriosRequest = gson.fromJson(request.getStrMensaje(), ConsultarTerritoriosRequest.class);
		ConsultarTerritoriosResponse consultarTerritoriosResponse = territorioService.consultarTerritorios(consultarTerritoriosRequest);
		
		GeneralResponse resp = new GeneralResponse();
		resp.setMensaje(gson.toJson(consultarTerritoriosResponse));
		
		logs.registrarLogInfoRespuestaServicio("consultarTerritorios", resp.getMensaje());
		
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
	
	@PostMapping(value = "/crearTerritorios", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GeneralResponse> crearTerritorios(@RequestBody GeneralRequest request) {
		
		logs.registrarLogInfoEjecutaServicio("crearTerritorios", request.getStrMensaje());
		
		Gson gson = new Gson();
		
		CrearTerritoriosRequest crearTerritoriosRequest = gson.fromJson(request.getStrMensaje(), CrearTerritoriosRequest.class);
		CrearResponse crearResponse = territorioService.crearTerritorios(crearTerritoriosRequest);
		
		GeneralResponse resp = new GeneralResponse();
		resp.setMensaje(gson.toJson(crearResponse));
		
		logs.registrarLogInfoRespuestaServicio("crearTerritorios", resp.getMensaje());
		
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
	
	@PostMapping(value = "/actualizarTerritorios", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GeneralResponse> actualizarTerritorios(@RequestBody GeneralRequest request) {
		
		logs.registrarLogInfoEjecutaServicio("actualizarTerritorios", request.getStrMensaje());
		
		Gson gson = new Gson();
		
		CrearTerritoriosRequest crearTerritoriosRequest = gson.fromJson(request.getStrMensaje(), CrearTerritoriosRequest.class);
		CrearResponse crearResponse = territorioService.actualizarTerritorios(crearTerritoriosRequest);
		
		GeneralResponse resp = new GeneralResponse();
		resp.setMensaje(gson.toJson(crearResponse));
		
		logs.registrarLogInfoRespuestaServicio("actualizarTerritorios", resp.getMensaje());
		
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
	
	@PostMapping(value = "/eliminarTerritorio", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GeneralResponse> eliminarTerritorio(@RequestBody GeneralRequest request) {
		
		logs.registrarLogInfoEjecutaServicio("eliminarTerritorio", request.getStrMensaje());
		
		Gson gson = new Gson();
		
		EliminarRequest eliminarRequest = gson.fromJson(request.getStrMensaje(), EliminarRequest.class);
		EliminarResponse eliminarResponse = territorioService.eliminarTerritorios(eliminarRequest);
		
		GeneralResponse resp = new GeneralResponse();
		resp.setMensaje(gson.toJson(eliminarResponse));
		
		logs.registrarLogInfoRespuestaServicio("eliminarTerritorio", resp.getMensaje());
		
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
}
