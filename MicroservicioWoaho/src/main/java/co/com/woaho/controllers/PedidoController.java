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
import co.com.woaho.request.GeneralRequest;
import co.com.woaho.request.SolicitarPedidoRequest;
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
}
