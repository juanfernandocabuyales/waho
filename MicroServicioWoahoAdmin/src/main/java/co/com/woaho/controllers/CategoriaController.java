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

import co.com.woaho.interfaces.ICategoriaService;
import co.com.woaho.request.ConsultarCategoriaRequest;
import co.com.woaho.request.CrearCategoriaRequest;
import co.com.woaho.request.EliminarRequest;
import co.com.woaho.request.GeneralRequest;
import co.com.woaho.response.ConsultarCategoriasResponse;
import co.com.woaho.response.CrearResponse;
import co.com.woaho.response.EliminarResponse;
import co.com.woaho.response.GeneralResponse;
import co.com.woaho.utilidades.RegistrarLog;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {
	
	private RegistrarLog logs = new RegistrarLog(CategoriaController.class);
	
	@Autowired
	private ICategoriaService categoriaService;
	
	@PostMapping(value = "/consultarCategorias", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> consultarCategorias(@RequestBody GeneralRequest request) {
		
		logs.registrarLogInfoEjecutaServicio("consultarCategorias",request.getStrMensaje());
		
		Gson gson = new Gson();
		ConsultarCategoriaRequest consultarCategoriaRequest = gson.fromJson(request.getStrMensaje(), ConsultarCategoriaRequest.class);
		ConsultarCategoriasResponse consultarCategoriasResponse = categoriaService.consultarCategorias(consultarCategoriaRequest);
		
		GeneralResponse resp = new GeneralResponse();
		resp.setMensaje(gson.toJson(consultarCategoriasResponse));
		
		logs.registrarLogInfoRespuestaServicio("consultarCategorias",resp.getMensaje());
		
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
	
	@PostMapping(value = "/crearCategoria", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> crearCategorias(@RequestBody GeneralRequest request) {
		
		logs.registrarLogInfoEjecutaServicio("crearCategorias",request.getStrMensaje());
		
		Gson gson = new Gson();
		CrearCategoriaRequest crearCategoriaRequest = gson.fromJson(request.getStrMensaje(), CrearCategoriaRequest.class);
		CrearResponse crearResponse = categoriaService.crearCategoria(crearCategoriaRequest);
		
		GeneralResponse resp = new GeneralResponse();
		resp.setMensaje(gson.toJson(crearResponse));
		
		logs.registrarLogInfoRespuestaServicio("crearCategorias",resp.getMensaje());
		
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
	
	@PostMapping(value = "/actualizarCategorias", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> actualizarCategorias(@RequestBody GeneralRequest request) {
		
		logs.registrarLogInfoEjecutaServicio("actualizarCategorias",request.getStrMensaje());
		
		Gson gson = new Gson();
		CrearCategoriaRequest crearCategoriaRequest = gson.fromJson(request.getStrMensaje(), CrearCategoriaRequest.class);
		CrearResponse crearResponse = categoriaService.actualizarCategoria(crearCategoriaRequest);
		
		GeneralResponse resp = new GeneralResponse();
		resp.setMensaje(gson.toJson(crearResponse));
		
		logs.registrarLogInfoRespuestaServicio("actualizarCategorias",resp.getMensaje());
		
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
	
	@PostMapping(value = "/eliminarCategorias", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> eliminarCategorias(@RequestBody GeneralRequest request) {
		
		logs.registrarLogInfoEjecutaServicio("eliminarCategorias",request.getStrMensaje());
		
		Gson gson = new Gson();
		EliminarRequest eliminarRequest = gson.fromJson(request.getStrMensaje(), EliminarRequest.class);
		EliminarResponse eliminarResponse = categoriaService.eliminarCategoria(eliminarRequest);
		
		GeneralResponse resp = new GeneralResponse();
		resp.setMensaje(gson.toJson(eliminarResponse));
		
		logs.registrarLogInfoRespuestaServicio("eliminarCategorias",resp.getMensaje());
		
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

}
