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

import co.com.woaho.interfaces.IServicioFavoritoService;
import co.com.woaho.request.ConsultarServiciosFavoritosRequest;
import co.com.woaho.request.CrearServicioFavoritoRequest;
import co.com.woaho.request.EliminarServicioFavoritoRequest;
import co.com.woaho.request.GeneralRequest;
import co.com.woaho.response.ConsultarServiciosResponse;
import co.com.woaho.response.CrearServicioFavoritoResponse;
import co.com.woaho.response.EliminarServicioFavoritoResponse;
import co.com.woaho.response.GeneralResponse;
import co.com.woaho.utilidades.RegistrarLog;

@RestController
@RequestMapping("/servicioFavorito")
public class ServicioFavoritoController {

	private RegistrarLog logs = new RegistrarLog(ServicioFavoritoController.class);
	
	@Autowired
	private IServicioFavoritoService servicioFavoritoService;
	
	@PostMapping(value = "/consultarFavoritos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GeneralResponse> consultarFavoritos(@RequestBody GeneralRequest request) {
		
		logs.registrarLogInfoEjecutaServicio("consultarFavoritos",request.getStrMensaje());
		
		Gson gson = new Gson();
		ConsultarServiciosFavoritosRequest consultarServiciosFavoritosRequest = gson.fromJson(request.getStrMensaje(), ConsultarServiciosFavoritosRequest.class);
		ConsultarServiciosResponse consultarServiciosResponse = servicioFavoritoService.consultarFavoritos(consultarServiciosFavoritosRequest);
		
		GeneralResponse resp = new GeneralResponse();
		resp.setMensaje(gson.toJson(consultarServiciosResponse));
		
		logs.registrarLogInfoRespuestaServicio("consultarFavoritos",resp.getMensaje());
		
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
	
	@PostMapping(value = "/crearFavoritos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GeneralResponse> crearFavoritos(@RequestBody GeneralRequest request) {
		
		logs.registrarLogInfoEjecutaServicio("crearFavoritos",request.getStrMensaje());
		
		Gson gson = new Gson();
		CrearServicioFavoritoRequest crearServicioFavoritoRequest = gson.fromJson(request.getStrMensaje(), CrearServicioFavoritoRequest.class);
		CrearServicioFavoritoResponse crearServicioFavoritoResponse = servicioFavoritoService.crearServicioFavorito(crearServicioFavoritoRequest);
		
		GeneralResponse resp = new GeneralResponse();
		resp.setMensaje(gson.toJson(crearServicioFavoritoResponse));
		
		logs.registrarLogInfoRespuestaServicio("crearFavoritos",resp.getMensaje());
		
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
	
	@PostMapping(value = "/eliminarFavoritos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GeneralResponse> eliminarFavoritos(@RequestBody GeneralRequest request) {
		
		logs.registrarLogInfoEjecutaServicio("eliminarFavoritos",request.getStrMensaje());
		
		Gson gson = new Gson();
		EliminarServicioFavoritoRequest eliminarServicioFavoritoRequest = gson.fromJson(request.getStrMensaje(), EliminarServicioFavoritoRequest.class);
		EliminarServicioFavoritoResponse eliminarServicioFavoritoResponse = servicioFavoritoService.eliminarServicioFavorito(eliminarServicioFavoritoRequest);
		
		GeneralResponse resp = new GeneralResponse();
		resp.setMensaje(gson.toJson(eliminarServicioFavoritoResponse));
		
		logs.registrarLogInfoRespuestaServicio("eliminarFavoritos",resp.getMensaje());
		
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
}
