package co.com.woaho.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import co.com.woaho.interfaces.ICategoriaService;
import co.com.woaho.response.ConsultarCategoriasResponse;
import co.com.woaho.response.GeneralResponse;
import co.com.woaho.utilidades.RegistrarLog;

@RestController
@RequestMapping("/categoria")
public class CategoriaController extends BaseController {
	
	private RegistrarLog logs = new RegistrarLog(CategoriaController.class);
	
	@Autowired
	private ICategoriaService categoriaService;
	
	@PostMapping(value = "/consultarCategorias", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> consultarCategorias() {
		
		logs.registrarLogInfoEjecutaServicio("consultarCategorias");
		
		Gson gson = new Gson();
		ConsultarCategoriasResponse consultarCategoriasResponse = categoriaService.consultarCategorias();
		
		GeneralResponse resp = new GeneralResponse();
		resp.setMensaje(encrypt(gson.toJson(consultarCategoriasResponse)));
		
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

}
