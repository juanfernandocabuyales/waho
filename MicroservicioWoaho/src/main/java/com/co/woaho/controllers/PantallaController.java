package com.co.woaho.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestParam;

import com.co.woaho.interfaces.IPantallaService;
import com.co.woaho.utilidades.RegistrarLog;

@RestController
@RequestMapping("/pantalla")
public class PantallaController {

	@Autowired
	private IPantallaService pantallaService;

	private RegistrarLog logs = new RegistrarLog(PantallaController.class);


	@PostMapping(value = "/consultarPantalla", produces = MediaType.APPLICATION_JSON_VALUE)
	public String consultarPantalla(@RequestParam("idPantalla") int pIdPantalla) {
		String strResultado = null;
		try {
			logs.registrarLogInfoEjecutaServicio("consultarPantalla");
			strResultado = pantallaService.obtenerMensajesPantalla(pIdPantalla);
		}catch (Exception e) {
			logs.registrarLogError("consultarPantalla", e.getMessage(),e);
		}
		logs.registrarLogInfoResultado(strResultado);
		return strResultado;
	}
}
