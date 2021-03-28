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

import co.com.woaho.interfaces.IUnidadServices;
import co.com.woaho.request.ConsultarUnidadesRequest;
import co.com.woaho.request.CrearUnidadRequest;
import co.com.woaho.request.EliminarRequest;
import co.com.woaho.request.GeneralRequest;
import co.com.woaho.response.ConsultarUnidadesResponse;
import co.com.woaho.response.CrearUnidadResponse;
import co.com.woaho.response.EliminarResponse;
import co.com.woaho.response.GeneralResponse;
import co.com.woaho.utilidades.RegistrarLog;

@RestController
@RequestMapping("/unidad")
public class UnidadController {
	
	@Autowired
	private IUnidadServices unidadServices;

	private RegistrarLog logs = new RegistrarLog(UnidadController.class);
	
	@PostMapping(value = "/consultarUnidades", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GeneralResponse> consultarUnidades(@RequestBody GeneralRequest request) {
		
		logs.registrarLogInfoEjecutaServicio("consultarUnidades",request.getStrMensaje());
		
		Gson gson = new Gson();
		ConsultarUnidadesRequest consultarUnidadesRequest = gson.fromJson(request.getStrMensaje(), ConsultarUnidadesRequest.class);
		ConsultarUnidadesResponse consultarUnidadesResponse = unidadServices.consultarUnidades(consultarUnidadesRequest);
		
		GeneralResponse resp = new GeneralResponse();
		resp.setMensaje(gson.toJson(consultarUnidadesResponse));
		
		logs.registrarLogInfoRespuestaServicio("consultarUnidades",resp.getMensaje());
		
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
	
	@PostMapping(value = "/crearUnidades", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GeneralResponse> crearUnidades(@RequestBody GeneralRequest request) {
		
		logs.registrarLogInfoEjecutaServicio("crearUnidades",request.getStrMensaje());
		
		Gson gson = new Gson();
		CrearUnidadRequest crearUnidadRequest = gson.fromJson(request.getStrMensaje(), CrearUnidadRequest.class);
		CrearUnidadResponse crearUnidadResponse = unidadServices.crearUnidad(crearUnidadRequest);
		
		GeneralResponse resp = new GeneralResponse();
		resp.setMensaje(gson.toJson(crearUnidadResponse));
		
		logs.registrarLogInfoRespuestaServicio("crearUnidades",resp.getMensaje());
		
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
	
	@PostMapping(value = "/actualizarUnidades", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GeneralResponse> actualizarUnidades(@RequestBody GeneralRequest request) {
		
		logs.registrarLogInfoEjecutaServicio("actualizarUnidades",request.getStrMensaje());
		
		Gson gson = new Gson();
		CrearUnidadRequest crearUnidadRequest = gson.fromJson(request.getStrMensaje(), CrearUnidadRequest.class);
		CrearUnidadResponse crearUnidadResponse = unidadServices.actualizarUnidad(crearUnidadRequest);
		
		GeneralResponse resp = new GeneralResponse();
		resp.setMensaje(gson.toJson(crearUnidadResponse));
		
		logs.registrarLogInfoRespuestaServicio("actualizarUnidades",resp.getMensaje());
		
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
	
	@PostMapping(value = "/eliminarUnidades", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GeneralResponse> eliminarUnidades(@RequestBody GeneralRequest request) {
		
		logs.registrarLogInfoEjecutaServicio("eliminarUnidades",request.getStrMensaje());
		
		Gson gson = new Gson();
		EliminarRequest eliminarRequest = gson.fromJson(request.getStrMensaje(), EliminarRequest.class);
		EliminarResponse eliminarResponse = unidadServices.eliminarUnidad(eliminarRequest);
		
		GeneralResponse resp = new GeneralResponse();
		resp.setMensaje(gson.toJson(eliminarResponse));
		
		logs.registrarLogInfoRespuestaServicio("eliminarUnidades",resp.getMensaje());
		
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
}
