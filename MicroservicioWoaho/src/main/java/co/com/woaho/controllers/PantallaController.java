package co.com.woaho.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import co.com.woaho.interfaces.IPantallaService;
import co.com.woaho.request.ConsultarPantallasRequest;
import co.com.woaho.request.GeneralRequest;
import co.com.woaho.response.GeneralResponse;
import co.com.woaho.response.ConsultarPantallasResponse;
import co.com.woaho.utilidades.RegistrarLog;

@RestController
@RequestMapping("/pantalla")
public class PantallaController {

	@Autowired
	private IPantallaService pantallaService;

	private RegistrarLog logs = new RegistrarLog(PantallaController.class);


	@PostMapping(value = "/consultarSlides", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> consultarSlides(@RequestBody GeneralRequest request) {
		logs.registrarLogInfoEjecutaServicio("consultarSlides");

		Gson gson = new Gson();
		ConsultarPantallasRequest consultarPantallasRequest = gson.fromJson(request.getStrMensaje(), ConsultarPantallasRequest.class);
		ConsultarPantallasResponse mensajePantallaResponse = pantallaService.obtenerMensajesPantalla(consultarPantallasRequest);

		GeneralResponse resp = new GeneralResponse();
		resp.setMensaje(gson.toJson(mensajePantallaResponse));

		return new ResponseEntity<GeneralResponse>(resp, HttpStatus.OK);
	}
}
