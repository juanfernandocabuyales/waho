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

import co.com.woaho.interfaces.IDireccionService;
import co.com.woaho.request.ConsultarDireccionRequest;
import co.com.woaho.request.GeneralRequest;
import co.com.woaho.response.ConsultarDireccionResponse;
import co.com.woaho.response.GeneralResponse;
import co.com.woaho.utilidades.RegistrarLog;

@RestController
@RequestMapping("/direccion")
public class DireccionController {
	
	private RegistrarLog logs = new RegistrarLog(DireccionController.class);
	
	@Autowired
	private IDireccionService direccionService;
	
	@PostMapping(value = "/consultarDirecciones", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> consultarDirecciones(@RequestBody GeneralRequest request) {
		
		logs.registrarLogInfoEjecutaServicio("consultarDirecciones");
		
		Gson gson = new Gson();
		
		ConsultarDireccionRequest consultarDireccionRequest = gson.fromJson(request.getStrMensaje(), ConsultarDireccionRequest.class);
		
		ConsultarDireccionResponse consultarDireccionResponse = direccionService.obtenerDireccionesUsuario(consultarDireccionRequest);
		
		GeneralResponse resp = new GeneralResponse();
		resp.setMensaje(gson.toJson(consultarDireccionResponse));
		
		return new ResponseEntity<GeneralResponse>(resp, HttpStatus.OK);
	}

}
