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

import co.com.woaho.interfaces.IPedidoService;
import co.com.woaho.request.CancelarPedidoRequest;
import co.com.woaho.request.ConsultarPedidoProfesionalRequest;
import co.com.woaho.request.ConsultarPedidoUsuarioRequest;
import co.com.woaho.request.GeneralRequest;
import co.com.woaho.request.SolicitarPedidoRequest;
import co.com.woaho.response.CancelarPedidoResponse;
import co.com.woaho.response.ConsultarPedidoProfesionalResponse;
import co.com.woaho.response.ConsultarPedidoUsuarioResponse;
import co.com.woaho.response.GeneralResponse;
import co.com.woaho.response.SolicitarPedidoResponse;
import co.com.woaho.utilidades.RegistrarLog;

@RestController
@RequestMapping("/pedido")
public class PedidoController {

	private RegistrarLog logs = new RegistrarLog(PedidoController.class);
	
	@Autowired
	private IPedidoService pedidoService;
	
	@PostMapping(value = "/solicitarPedido", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> solicitarPedido(@RequestBody GeneralRequest request) {
		
		logs.registrarLogInfoEjecutaServicio("solicitarPedido");		
		Gson gson = new Gson();
		
		SolicitarPedidoRequest solicitarPedidoRequest = gson.fromJson(request.getStrMensaje(), SolicitarPedidoRequest.class);
		SolicitarPedidoResponse solicitarPedidoResponse = pedidoService.soliciarPedido(solicitarPedidoRequest);
		
		GeneralResponse resp = new GeneralResponse();
		resp.setMensaje(gson.toJson(solicitarPedidoResponse));
		
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
	
	@PostMapping(value = "/actualizarPedido", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> actualizarPedido(@RequestBody GeneralRequest request) {
		
		logs.registrarLogInfoEjecutaServicio("actualizarPedido");		
		Gson gson = new Gson();
		
		SolicitarPedidoRequest solicitarPedidoRequest = gson.fromJson(request.getStrMensaje(), SolicitarPedidoRequest.class);
		SolicitarPedidoResponse solicitarPedidoResponse = pedidoService.actualizarPedido(solicitarPedidoRequest);
		
		GeneralResponse resp = new GeneralResponse();
		resp.setMensaje(gson.toJson(solicitarPedidoResponse));
		
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
	
	@PostMapping(value = "/consultarPedidosUsuario", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> consultarPedidosUsuario(@RequestBody GeneralRequest request) {
		
		logs.registrarLogInfoEjecutaServicio("consultarPedidosUsuario");		
		Gson gson = new Gson();
		
		ConsultarPedidoUsuarioRequest consultarPedidoUsuarioRequest = gson.fromJson(request.getStrMensaje(), ConsultarPedidoUsuarioRequest.class);
		ConsultarPedidoUsuarioResponse consultarPedidoUsuarioResponse = pedidoService.consultarPedidosUsuario(consultarPedidoUsuarioRequest);
		
		GeneralResponse resp = new GeneralResponse();
		resp.setMensaje(gson.toJson(consultarPedidoUsuarioResponse));
		
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
	
	@PostMapping(value = "/consultarPedidosProfesional", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> consultarPedidosProfesional(@RequestBody GeneralRequest request) {
		
		logs.registrarLogInfoEjecutaServicio("consultarPedidosProfesional");		
		Gson gson = new Gson();
		
		ConsultarPedidoProfesionalRequest consultarPedidoProfesionalRequest = gson.fromJson(request.getStrMensaje(), ConsultarPedidoProfesionalRequest.class);
		ConsultarPedidoProfesionalResponse consultarPedidoProfesionalResponse = pedidoService.consultarPedidosProfesional(consultarPedidoProfesionalRequest);
		
		GeneralResponse resp = new GeneralResponse();
		resp.setMensaje(gson.toJson(consultarPedidoProfesionalResponse));
		
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
	
	@PostMapping(value = "/cancelarPedido", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> cancelarPedido(@RequestBody GeneralRequest request) {
		
		logs.registrarLogInfoEjecutaServicio("cancelarPedido");		
		Gson gson = new Gson();
		
		CancelarPedidoRequest cancelarPedidoRequest = gson.fromJson(request.getStrMensaje(), CancelarPedidoRequest.class);
		CancelarPedidoResponse cancelarPedidoResponse = pedidoService.cancelarPedido(cancelarPedidoRequest);
		
		GeneralResponse resp = new GeneralResponse();
		resp.setMensaje(gson.toJson(cancelarPedidoResponse));
		
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
}
