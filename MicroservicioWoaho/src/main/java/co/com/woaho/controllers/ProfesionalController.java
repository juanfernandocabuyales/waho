package co.com.woaho.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import co.com.woaho.interfaces.IProfesionalService;
import co.com.woaho.request.CrearProfesionalRequest;
import co.com.woaho.request.GeneralRequest;
import co.com.woaho.response.CrearProfesionalResponse;
import co.com.woaho.response.GeneralResponse;
import co.com.woaho.utilidades.RegistrarLog;

@RestController
@RequestMapping("/profesional")
public class ProfesionalController {

	@Autowired
	private IProfesionalService profesionalService;

	private RegistrarLog logs = new RegistrarLog(ProfesionalController.class);

	@PostMapping(value = "/consultarProfesionales", produces = MediaType.APPLICATION_JSON_VALUE)
	public String consultarProfesionales(@RequestParam("idServicio") String pServicioId) {
		String strResultado = null;
		try {
			logs.registrarLogInfoEjecutaServicio("consultarProfesionales");
			strResultado = profesionalService.obtenerProfesionales(pServicioId);
		}catch (Exception e) {
			logs.registrarLogError("consultarProfesionales", e.getMessage(),e);
		}
		logs.registrarLogInfoResultado(strResultado);
		return strResultado;
	}

	@PostMapping(value = "/crearProfesional", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> crearProfesional(@RequestBody GeneralRequest request) {
		logs.registrarLogInfoEjecutaServicio("crearProfesional");

		Gson gson = new Gson();
		CrearProfesionalRequest requestProfesional = gson.fromJson(request.getStrMensaje(), CrearProfesionalRequest.class);
		CrearProfesionalResponse crearProfesionalResponse = profesionalService.crearProfesional(requestProfesional);

		GeneralResponse resp = new GeneralResponse();
		resp.setMensaje(gson.toJson(crearProfesionalResponse));

		return new ResponseEntity<GeneralResponse>(resp, HttpStatus.OK);
	}
}
