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
import co.com.woaho.request.ConsultarUsuariosRequest;
import co.com.woaho.request.CrearUsuarioRequest;
import co.com.woaho.request.GeneralRequest;
import co.com.woaho.request.LoginAdminRequest;
import co.com.woaho.response.ConsultarUsuariosResponse;
import co.com.woaho.response.CrearResponse;
import co.com.woaho.response.GeneralResponse;
import co.com.woaho.response.LoginAdminResponse;
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
		
		CrearUsuarioRequest crearUsuarioRequest = gson.fromJson(request.getStrMensaje(), CrearUsuarioRequest.class);
		CrearResponse crearResponse = usuarioService.registrarUsuario(crearUsuarioRequest);

		GeneralResponse resp = new GeneralResponse();
		resp.setMensaje(gson.toJson(crearResponse));
		
		logs.registrarLogInfoRespuestaServicio("registrarUsuario",resp.getMensaje());

		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
	
	@PostMapping(value = "/actualizarUsuario", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GeneralResponse> actualizarUsuario(@RequestBody GeneralRequest request) {
		
		logs.registrarLogInfoEjecutaServicio("actualizarUsuario",request.getStrMensaje());
		
		Gson gson = new Gson();
		
		CrearUsuarioRequest crearUsuarioRequest = gson.fromJson(request.getStrMensaje(), CrearUsuarioRequest.class);
		CrearResponse crearResponse = usuarioService.actualizarUsuario(crearUsuarioRequest);

		GeneralResponse resp = new GeneralResponse();
		resp.setMensaje(gson.toJson(crearResponse));
		
		logs.registrarLogInfoRespuestaServicio("actualizarUsuario",resp.getMensaje());

		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
	
	@PostMapping(value = "/consultarUsuarios", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GeneralResponse> consultarUsuarios(@RequestBody GeneralRequest request) {
		
		logs.registrarLogInfoEjecutaServicio("consultarUsuarios",request.getStrMensaje());
		
		Gson gson = new Gson();
		
		ConsultarUsuariosRequest consultarUsuariosRequest = gson.fromJson(request.getStrMensaje(), ConsultarUsuariosRequest.class);
		ConsultarUsuariosResponse consultarUsuariosResponse = usuarioService.consultarUsuarios(consultarUsuariosRequest);

		GeneralResponse resp = new GeneralResponse();
		resp.setMensaje(gson.toJson(consultarUsuariosResponse));
		
		logs.registrarLogInfoRespuestaServicio("consultarUsuarios",resp.getMensaje());

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
