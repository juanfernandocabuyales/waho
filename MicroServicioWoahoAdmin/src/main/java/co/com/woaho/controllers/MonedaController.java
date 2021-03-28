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

import co.com.woaho.interfaces.IMonedaServices;
import co.com.woaho.request.ConsultarMonedasRequest;
import co.com.woaho.request.CrearMonedaRequest;
import co.com.woaho.request.EliminarMonedaRequest;
import co.com.woaho.request.GeneralRequest;
import co.com.woaho.response.ConsultarMonedasResponse;
import co.com.woaho.response.CrearMonedaResponse;
import co.com.woaho.response.EliminarMonedaResponse;
import co.com.woaho.response.GeneralResponse;
import co.com.woaho.utilidades.RegistrarLog;

@RestController
@RequestMapping("/moneda")
public class MonedaController {

	private RegistrarLog logs = new RegistrarLog(MonedaController.class);
	
	@Autowired
	private IMonedaServices monedaServices;
	
	@PostMapping(value = "/consultarMonedas", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GeneralResponse> consultarMonedas(@RequestBody GeneralRequest request) {
		
		logs.registrarLogInfoEjecutaServicio("consultarMonedas",request.getStrMensaje());
		
		Gson gson = new Gson();
		ConsultarMonedasRequest consultarMonedasRequest = gson.fromJson(request.getStrMensaje(), ConsultarMonedasRequest.class);
		ConsultarMonedasResponse consultarMonedasResponse = monedaServices.consultarMonedas(consultarMonedasRequest);
		
		GeneralResponse resp = new GeneralResponse();
		resp.setMensaje(gson.toJson(consultarMonedasResponse));
		
		logs.registrarLogInfoRespuestaServicio("consultarMonedas",resp.getMensaje());
		
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
	
	@PostMapping(value = "/crearMonedas", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GeneralResponse> crearMonedas(@RequestBody GeneralRequest request) {
		
		logs.registrarLogInfoEjecutaServicio("crearMonedas",request.getStrMensaje());
		
		Gson gson = new Gson();
		CrearMonedaRequest crearMonedaRequest = gson.fromJson(request.getStrMensaje(), CrearMonedaRequest.class);
		CrearMonedaResponse crearMonedaResponse = monedaServices.crearMoneda(crearMonedaRequest);
		
		GeneralResponse resp = new GeneralResponse();
		resp.setMensaje(gson.toJson(crearMonedaResponse));
		
		logs.registrarLogInfoRespuestaServicio("crearMonedas",resp.getMensaje());
		
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
	
	@PostMapping(value = "/actualizarMonedas", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GeneralResponse> actualizarMonedas(@RequestBody GeneralRequest request) {
		
		logs.registrarLogInfoEjecutaServicio("actualizarMonedas",request.getStrMensaje());
		
		Gson gson = new Gson();
		CrearMonedaRequest crearMonedaRequest = gson.fromJson(request.getStrMensaje(), CrearMonedaRequest.class);
		CrearMonedaResponse crearMonedaResponse = monedaServices.actualizarMoneda(crearMonedaRequest);
		
		GeneralResponse resp = new GeneralResponse();
		resp.setMensaje(gson.toJson(crearMonedaResponse));
		
		logs.registrarLogInfoRespuestaServicio("actualizarMonedas",resp.getMensaje());
		
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
	
	@PostMapping(value = "/eliminarMonedas", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GeneralResponse> eliminarMonedas(@RequestBody GeneralRequest request) {
		
		logs.registrarLogInfoEjecutaServicio("eliminarMonedas",request.getStrMensaje());
		
		Gson gson = new Gson();
		EliminarMonedaRequest eliminarMonedaRequest = gson.fromJson(request.getStrMensaje(), EliminarMonedaRequest.class);
		EliminarMonedaResponse eliminarMonedaResponse = monedaServices.eliminarMoneda(eliminarMonedaRequest);
		
		GeneralResponse resp = new GeneralResponse();
		resp.setMensaje(gson.toJson(eliminarMonedaResponse));
		
		logs.registrarLogInfoRespuestaServicio("eliminarMonedas",resp.getMensaje());
		
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
}
