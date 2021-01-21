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
import co.com.woaho.request.LoginAdminRequest;
import co.com.woaho.request.LoginRequest;
import co.com.woaho.request.RegistrarUsuarioRequest;
import co.com.woaho.request.ValidarCodigoRequest;
import co.com.woaho.response.ConsultarUsuarioResponse;
import co.com.woaho.response.GeneralResponse;
import co.com.woaho.response.GenerarCodigoResponse;
import co.com.woaho.response.LoginAdminResponse;
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
	public ResponseEntity<GeneralResponse> registrarUsuario(@RequestBody GeneralRequest request) {
		
		logs.registrarLogInfoEjecutaServicio("registrarUsuario",request.getStrMensaje());
		
		Gson gson = new Gson();
		
		RegistrarUsuarioRequest registrarUsuarioRequest = gson.fromJson(request.getStrMensaje(), RegistrarUsuarioRequest.class);
		RegistrarUsuarioResponse registrarUsuarioResponse = usuarioService.registrarUsuario(registrarUsuarioRequest);

		GeneralResponse resp = new GeneralResponse();
		resp.setMensaje(gson.toJson(registrarUsuarioResponse));
		
		logs.registrarLogInfoRespuestaServicio("registrarUsuario",resp.getMensaje());

		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
	
	@PostMapping(value = "/actualizarUsuario", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GeneralResponse> actualizarUsuario(@RequestBody GeneralRequest request) {
		
		logs.registrarLogInfoEjecutaServicio("actualizarUsuario",request.getStrMensaje());
		
		Gson gson = new Gson();
		
		RegistrarUsuarioRequest registrarUsuarioRequest = gson.fromJson(request.getStrMensaje(), RegistrarUsuarioRequest.class);
		RegistrarUsuarioResponse registrarUsuarioResponse = usuarioService.actualizarUsuario(registrarUsuarioRequest);

		GeneralResponse resp = new GeneralResponse();
		resp.setMensaje(gson.toJson(registrarUsuarioResponse));
		
		logs.registrarLogInfoRespuestaServicio("actualizarUsuario",resp.getMensaje());

		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping(value = "/consultarUsuario", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GeneralResponse> consultarUsuario(@RequestBody GeneralRequest request) {
		
		logs.registrarLogInfoEjecutaServicio("consultarUsuario",request.getStrMensaje());
		
		Gson gson = new Gson();
		
		ConsultarUsuarioRequest consultarUsuarioRequest = gson.fromJson(request.getStrMensaje(), ConsultarUsuarioRequest.class);
		ConsultarUsuarioResponse consultarUsuarioResponse = usuarioService.consultarUsuario(consultarUsuarioRequest);

		GeneralResponse resp = new GeneralResponse();
		resp.setMensaje(gson.toJson(consultarUsuarioResponse));
		
		logs.registrarLogInfoRespuestaServicio("consultarUsuario",resp.getMensaje());

		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@PostMapping(value = "/generarCodigoRegistro", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GeneralResponse> generarCodigoRegistro(@RequestBody GeneralRequest request) {
		
		logs.registrarLogInfoEjecutaServicio("generarCodigoRegistro",request.getStrMensaje());
		
		Gson gson = new Gson();
		
		GenerarCodigoRequest generarCodigoRequest = gson.fromJson(request.getStrMensaje(), GenerarCodigoRequest.class);
		GenerarCodigoResponse generarCodigoResponse = usuarioService.generarCodigoRegistro(generarCodigoRequest);

		GeneralResponse resp = new GeneralResponse();
		resp.setMensaje(gson.toJson(generarCodigoResponse));

		logs.registrarLogInfoRespuestaServicio("generarCodigoRegistro",resp.getMensaje());
		
		return new ResponseEntity<>(resp, HttpStatus.OK);		
	}
	
	@PostMapping(value = "/validarLogin", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GeneralResponse> validarLogin(@RequestBody GeneralRequest request) {
		
		logs.registrarLogInfoEjecutaServicio("validarLogin",request.getStrMensaje());
		
		Gson gson = new Gson();
		
		LoginRequest loginRequest = gson.fromJson(request.getStrMensaje(), LoginRequest.class);
		LoginResponse loginResponse = usuarioService.loginUsuario(loginRequest);

		GeneralResponse resp = new GeneralResponse();
		resp.setMensaje(gson.toJson(loginResponse));
		
		logs.registrarLogInfoRespuestaServicio("validarLogin",resp.getMensaje());

		return new ResponseEntity<>(resp, HttpStatus.OK);	
	}
	
	@PostMapping(value = "/validarCodigoRegistro", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GeneralResponse> validarCodigoRegistro(@RequestBody GeneralRequest request) {
		
		logs.registrarLogInfoEjecutaServicio("validarCodigoRegistro",request.getStrMensaje());
		
		Gson gson = new Gson();
		
		ValidarCodigoRequest validarCodigoRequest = gson.fromJson(request.getStrMensaje(), ValidarCodigoRequest.class);
		ValidarCodigoResponse validarCodigoResponse = usuarioService.validarCodigoRegistro(validarCodigoRequest);

		GeneralResponse resp = new GeneralResponse();
		resp.setMensaje(gson.toJson(validarCodigoResponse));
		
		logs.registrarLogInfoRespuestaServicio("validarCodigoRegistro",resp.getMensaje());

		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
	
	@PostMapping(value = "/validarCodigoLogin", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GeneralResponse> validarCodigoLogin(@RequestBody GeneralRequest request) {
		
		logs.registrarLogInfoEjecutaServicio("validarCodigoLogin",request.getStrMensaje());
		
		Gson gson = new Gson();
		
		ValidarCodigoRequest validarCodigoRequest = gson.fromJson(request.getStrMensaje(), ValidarCodigoRequest.class);
		ValidarCodigoResponse validarCodigoResponse = usuarioService.validarCodigoLogin(validarCodigoRequest);

		GeneralResponse resp = new GeneralResponse();
		resp.setMensaje(gson.toJson(validarCodigoResponse));
		
		logs.registrarLogInfoRespuestaServicio("validarCodigoLogin",resp.getMensaje());

		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
	
	@PostMapping(value = "/validarLoginAdmin", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GeneralResponse> validarLoginAdmin(@RequestBody GeneralRequest request) {
		
		logs.registrarLogInfoEjecutaServicio("validarLoginAdmin",request.getStrMensaje());
		
		Gson gson = new Gson();
		
		LoginAdminRequest loginAdminRequest = gson.fromJson(request.getStrMensaje(), LoginAdminRequest.class);
		LoginAdminResponse loginAdminResponse = usuarioService.validarLoginAdmin(loginAdminRequest);

		GeneralResponse resp = new GeneralResponse();
		resp.setMensaje(gson.toJson(loginAdminResponse));
		
		logs.registrarLogInfoRespuestaServicio("validarLoginAdmin",resp.getMensaje());

		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
}
