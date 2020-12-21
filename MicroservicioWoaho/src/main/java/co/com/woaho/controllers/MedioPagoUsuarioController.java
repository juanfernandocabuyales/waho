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

import co.com.woaho.interfaces.IMedioPagoUsuarioService;
import co.com.woaho.request.BaseRequest;
import co.com.woaho.request.ConsultarMedioPagoUsuarioRequest;
import co.com.woaho.request.CrearMedioPagoUsuarioRequest;
import co.com.woaho.request.GeneralRequest;
import co.com.woaho.response.ConsultarMedioPagoUsuarioResponse;
import co.com.woaho.response.ConsultarMedioPagosResponse;
import co.com.woaho.response.CrearMedioPagoUsuarioResponse;
import co.com.woaho.response.GeneralResponse;
import co.com.woaho.utilidades.RegistrarLog;

@RestController
@RequestMapping("/medioPago")
public class MedioPagoUsuarioController {

	private RegistrarLog logs = new RegistrarLog(MedioPagoUsuarioController.class);

	@Autowired
	private IMedioPagoUsuarioService medioPagoUsuarioService;

	@PostMapping(value = "/obtenerMedios", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GeneralResponse> obtenerMedios(@RequestBody GeneralRequest request) {
		logs.registrarLogInfoEjecutaServicio("obtenerMedios",request.getStrMensaje());

		Gson gson = new Gson();
		BaseRequest baseRequest = gson.fromJson(request.getStrMensaje(), BaseRequest.class);
		ConsultarMedioPagosResponse consultarMedioPagosResponse = medioPagoUsuarioService.consultarMediosPagos(baseRequest);

		GeneralResponse resp = new GeneralResponse();
		resp.setMensaje(gson.toJson(consultarMedioPagosResponse));

		logs.registrarLogInfoRespuestaServicio("obtenerMedios",resp.getMensaje());

		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
	
	@PostMapping(value = "/crearMedioPagoUsuario", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GeneralResponse> crearMedioPagoUsuario(@RequestBody GeneralRequest request) {
		logs.registrarLogInfoEjecutaServicio("crearMedioPagoUsuario",request.getStrMensaje());

		Gson gson = new Gson();
		CrearMedioPagoUsuarioRequest crearMedioPagoUsuarioRequest = gson.fromJson(request.getStrMensaje(), CrearMedioPagoUsuarioRequest.class);
		CrearMedioPagoUsuarioResponse crearMedioPagoUsuarioResponse = medioPagoUsuarioService.crearMedioPagoUsuario(crearMedioPagoUsuarioRequest);

		GeneralResponse resp = new GeneralResponse();
		resp.setMensaje(gson.toJson(crearMedioPagoUsuarioResponse));

		logs.registrarLogInfoRespuestaServicio("crearMedioPagoUsuario",resp.getMensaje());

		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
	
	@PostMapping(value = "/actualizarMedioPagoUsuario", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GeneralResponse> actualizarMedioPagoUsuario(@RequestBody GeneralRequest request) {
		logs.registrarLogInfoEjecutaServicio("actualizarMedioPagoUsuario",request.getStrMensaje());

		Gson gson = new Gson();
		CrearMedioPagoUsuarioRequest crearMedioPagoUsuarioRequest = gson.fromJson(request.getStrMensaje(), CrearMedioPagoUsuarioRequest.class);
		CrearMedioPagoUsuarioResponse crearMedioPagoUsuarioResponse = medioPagoUsuarioService.actualizarMedioPagoUsuario(crearMedioPagoUsuarioRequest);

		GeneralResponse resp = new GeneralResponse();
		resp.setMensaje(gson.toJson(crearMedioPagoUsuarioResponse));

		logs.registrarLogInfoRespuestaServicio("actualizarMedioPagoUsuario",resp.getMensaje());

		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
	
	@PostMapping(value = "/consultarMedioPagoUsuario", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GeneralResponse> consultarMedioPagoUsuario(@RequestBody GeneralRequest request) {
		logs.registrarLogInfoEjecutaServicio("consultarMedioPagoUsuario",request.getStrMensaje());

		Gson gson = new Gson();
		ConsultarMedioPagoUsuarioRequest consultarMedioPagoUsuarioRequest = gson.fromJson(request.getStrMensaje(), ConsultarMedioPagoUsuarioRequest.class);
		ConsultarMedioPagoUsuarioResponse consultarMedioPagoUsuarioResponse = medioPagoUsuarioService.consultarMedioPagoUsuario(consultarMedioPagoUsuarioRequest);

		GeneralResponse resp = new GeneralResponse();
		resp.setMensaje(gson.toJson(consultarMedioPagoUsuarioResponse));

		logs.registrarLogInfoRespuestaServicio("consultarMedioPagoUsuario",resp.getMensaje());

		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
	
	@PostMapping(value = "/consultarMedioPagoUsuarioEstado", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GeneralResponse> consultarMedioPagoUsuarioEstado(@RequestBody GeneralRequest request) {
		logs.registrarLogInfoEjecutaServicio("consultarMedioPagoUsuarioEstado",request.getStrMensaje());

		Gson gson = new Gson();
		ConsultarMedioPagoUsuarioRequest consultarMedioPagoUsuarioRequest = gson.fromJson(request.getStrMensaje(), ConsultarMedioPagoUsuarioRequest.class);
		ConsultarMedioPagoUsuarioResponse consultarMedioPagoUsuarioResponse = medioPagoUsuarioService.consultarMedioPagoUsuarioEstado(consultarMedioPagoUsuarioRequest);

		GeneralResponse resp = new GeneralResponse();
		resp.setMensaje(gson.toJson(consultarMedioPagoUsuarioResponse));

		logs.registrarLogInfoRespuestaServicio("consultarMedioPagoUsuarioEstado",resp.getMensaje());

		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
}
