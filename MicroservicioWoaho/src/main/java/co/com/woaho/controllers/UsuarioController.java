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

import co.com.woaho.interfaces.IUsuarioService;
import co.com.woaho.request.ConsultarUsuarioRequest;
import co.com.woaho.request.GeneralRequest;
import co.com.woaho.request.GenerarCodigoRequest;
import co.com.woaho.request.LoginRequest;
import co.com.woaho.request.RegistrarUsuarioRequest;
import co.com.woaho.request.ValidarCodigoRequest;
import co.com.woaho.response.ConsultarUsuarioResponse;
import co.com.woaho.response.GeneralResponse;
import co.com.woaho.response.GenerarCodigoResponse;
import co.com.woaho.response.LoginResponse;
import co.com.woaho.response.RegistrarUsuarioResponse;
import co.com.woaho.response.ValidarCodigoResponse;
import co.com.woaho.utilidades.RegistrarLog;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

	private RegistrarLog logs = new RegistrarLog(UsuarioController.class);

	@Autowired
	private IUsuarioService usuarioService;

	@PostMapping(value = "/registrarUsuario", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> registrarUsuario(@RequestBody GeneralRequest request) {
		logs.registrarLogInfoEjecutaServicio("registrarUsuario");
		Gson gson = new Gson();
		RegistrarUsuarioRequest registrarUsuarioRequest = gson.fromJson(request.getStrMensaje(), RegistrarUsuarioRequest.class);
		RegistrarUsuarioResponse registrarUsuarioResponse = usuarioService.registrarUsuario(registrarUsuarioRequest);

		GeneralResponse resp = new GeneralResponse();
		resp.setMensaje(gson.toJson(registrarUsuarioResponse));

		return new ResponseEntity<GeneralResponse>(resp, HttpStatus.OK);
	}

	@PostMapping(value = "/actualizarUsuario", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> actualizarUsuario(@RequestBody GeneralRequest request) {
		logs.registrarLogInfoEjecutaServicio("actualizarUsuario");
		Gson gson = new Gson();
		RegistrarUsuarioRequest registrarUsuarioRequest = gson.fromJson(request.getStrMensaje(), RegistrarUsuarioRequest.class);
		RegistrarUsuarioResponse registrarUsuarioResponse = usuarioService.actualizarUsuario(registrarUsuarioRequest);

		GeneralResponse resp = new GeneralResponse();
		resp.setMensaje(gson.toJson(registrarUsuarioResponse));

		return new ResponseEntity<GeneralResponse>(resp, HttpStatus.OK);
	}

	@PostMapping(value = "/consultarUsuario", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> consultarUsuario(@RequestBody GeneralRequest request) {
		logs.registrarLogInfoEjecutaServicio("consultarUsuario");
		Gson gson = new Gson();
		ConsultarUsuarioRequest consultarUsuarioRequest = gson.fromJson(request.getStrMensaje(), ConsultarUsuarioRequest.class);
		ConsultarUsuarioResponse consultarUsuarioResponse = usuarioService.consultarUsuario(consultarUsuarioRequest);

		GeneralResponse resp = new GeneralResponse();
		resp.setMensaje(gson.toJson(consultarUsuarioResponse));

		return new ResponseEntity<GeneralResponse>(resp, HttpStatus.OK);
	}

	@PostMapping(value = "/generarCodigoRegistro", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> generarCodigoRegistro(@RequestBody GeneralRequest request) {
		logs.registrarLogInfoEjecutaServicio("generarCodigoRegistro");
		Gson gson = new Gson();
		GenerarCodigoRequest generarCodigoRequest = gson.fromJson(request.getStrMensaje(), GenerarCodigoRequest.class);
		GenerarCodigoResponse generarCodigoResponse = usuarioService.generarCodigoRegistro(generarCodigoRequest);

		GeneralResponse resp = new GeneralResponse();
		resp.setMensaje(gson.toJson(generarCodigoResponse));

		return new ResponseEntity<GeneralResponse>(resp, HttpStatus.OK);		
	}
	
	@PostMapping(value = "/validarLogin", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> validarLogin(@RequestBody GeneralRequest request) {
		logs.registrarLogInfoEjecutaServicio("validarLogin");
		Gson gson = new Gson();
		LoginRequest loginRequest = gson.fromJson(request.getStrMensaje(), LoginRequest.class);
		LoginResponse loginResponse = usuarioService.validarLogin(loginRequest);

		GeneralResponse resp = new GeneralResponse();
		resp.setMensaje(gson.toJson(loginResponse));

		return new ResponseEntity<GeneralResponse>(resp, HttpStatus.OK);	
	}
	
	@PostMapping(value = "/validarCodigoRegistro", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> validarCodigoRegistro(@RequestBody GeneralRequest request) {
		logs.registrarLogInfoEjecutaServicio("validarCodigoRegistro");
		Gson gson = new Gson();
		ValidarCodigoRequest validarCodigoRequest = gson.fromJson(request.getStrMensaje(), ValidarCodigoRequest.class);
		ValidarCodigoResponse validarCodigoResponse = usuarioService.validarCodigoRegistro(validarCodigoRequest);

		GeneralResponse resp = new GeneralResponse();
		resp.setMensaje(gson.toJson(validarCodigoResponse));

		return new ResponseEntity<GeneralResponse>(resp, HttpStatus.OK);
	}
}
