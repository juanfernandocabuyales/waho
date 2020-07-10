package co.com.woaho.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.com.woaho.interfaces.IDireccionService;
import co.com.woaho.utilidades.RegistrarLog;

@RestController
@RequestMapping("/direccion")
public class DireccionController {
	
	private RegistrarLog logs = new RegistrarLog(DireccionController.class);
	
	@Autowired
	private IDireccionService direccionService;
	
	@PostMapping(value = "/consultarDirecciones", produces = MediaType.APPLICATION_JSON_VALUE)
	public String consultarDirecciones(@RequestParam(name = "pStrId") String pStrId) {
		String strResultado = null;
		try {
			logs.registrarLogInfoEjecutaServicio("consultarDirecciones");
			strResultado = direccionService.obtenerDireccionesUsuario(pStrId);
		}catch (Exception e) {
			logs.registrarLogError("consultarDirecciones", e.getMessage(),e);
		}
		logs.registrarLogInfoResultado(strResultado);
		return strResultado;
	}

}
