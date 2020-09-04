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
import co.com.woaho.request.GeneralRequest;
import co.com.woaho.response.ConsultarTerritorioResponse;
import co.com.woaho.response.GeneralResponse;
import co.com.woaho.utilidades.RegistrarLog;

@RestController
@RequestMapping("/territorio")
public class TerritorioController {

	@Autowired
	private ITerritorioService territorioService;
	
	private RegistrarLog logs = new RegistrarLog(TerritorioController.class);
	
	@PostMapping(value = "/consultarPaises", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> consultarPaises(@RequestBody GeneralRequest request) {
		logs.registrarLogInfoEjecutaServicio("consultarPaises");
		Gson gson = new Gson();
		ConsultarTerritorioRequest consultarTerritorioRequest = gson.fromJson(request.getStrMensaje(), ConsultarTerritorioRequest.class);
		ConsultarTerritorioResponse consultarTerritorioResponse = territorioService.obtenerTerritorios(consultarTerritorioRequest);

		GeneralResponse resp = new GeneralResponse();
		resp.setMensaje(gson.toJson(consultarTerritorioResponse));

		return new ResponseEntity<GeneralResponse>(resp, HttpStatus.OK);
	}
}
