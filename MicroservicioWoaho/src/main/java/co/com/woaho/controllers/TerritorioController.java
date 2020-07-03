package co.com.woaho.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.com.woaho.interfaces.ITerritorioService;
import co.com.woaho.utilidades.RegistrarLog;

@RestController
@RequestMapping("/territorio")
public class TerritorioController {

	@Autowired
	private ITerritorioService territorioService;
	
	private RegistrarLog logs = new RegistrarLog(TerritorioController.class);
	
	@PostMapping(value = "/consultarPaises", produces = MediaType.APPLICATION_JSON_VALUE)
	public String consultarPaises(@RequestParam("tipoTerritorio") String tipoTerritorio) {
		String strResultado = null;
		try {
			logs.registrarLogInfoEjecutaServicio("consultarPaises");
			strResultado = territorioService.obtenerTerritorios(tipoTerritorio);
		}catch (Exception e) {
			logs.registrarLogError("consultarPaises", e.getMessage(),e);
		}
		logs.registrarLogInfoResultado(strResultado);
		return strResultado;
	}
}
