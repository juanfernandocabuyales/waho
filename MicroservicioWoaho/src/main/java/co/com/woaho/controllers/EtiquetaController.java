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

import co.com.woaho.interfaces.IEtiquetasService;
import co.com.woaho.request.ConsultarEtiquetasRequest;
import co.com.woaho.request.GeneralRequest;
import co.com.woaho.response.ConsultarEtiquetasResponse;
import co.com.woaho.response.GeneralResponse;
import co.com.woaho.utilidades.RegistrarLog;

@RestController
@RequestMapping("/etiquetas")
public class EtiquetaController {

	@Autowired
	private IEtiquetasService etiquetasService;

	private RegistrarLog logs = new RegistrarLog(EtiquetaController.class);

	@PostMapping(value = "/obtenerEtiquetasIdioma", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> obtenerEtiquetasIdioma(@RequestBody GeneralRequest request) {
		logs.registrarLogInfoEjecutaServicio("obtenerEtiquetasIdioma");

		Gson gson = new Gson();
		ConsultarEtiquetasRequest consultarEtiquetasRequest = gson.fromJson(request.getStrMensaje(), ConsultarEtiquetasRequest.class);
		ConsultarEtiquetasResponse consultarEtiquetasResponse = etiquetasService.consultarEtiquetasIdioma(consultarEtiquetasRequest);

		GeneralResponse resp = new GeneralResponse();
		resp.setMensaje(gson.toJson(consultarEtiquetasResponse));

		return new ResponseEntity<GeneralResponse>(resp, HttpStatus.OK);
	}
	
	@PostMapping(value = "/obtenerEtiquetasCodigoIdioma", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> obtenerEtiquetasCodigoIdioma(@RequestBody GeneralRequest request) {
		logs.registrarLogInfoEjecutaServicio("obtenerEtiquetasCodigoIdioma");

		Gson gson = new Gson();
		ConsultarEtiquetasRequest consultarEtiquetasRequest = gson.fromJson(request.getStrMensaje(), ConsultarEtiquetasRequest.class);
		ConsultarEtiquetasResponse consultarEtiquetasResponse = etiquetasService.consultarEtiquetaCodigoIdioma(consultarEtiquetasRequest);

		GeneralResponse resp = new GeneralResponse();
		resp.setMensaje(gson.toJson(consultarEtiquetasResponse));

		return new ResponseEntity<GeneralResponse>(resp, HttpStatus.OK);
	}
}
