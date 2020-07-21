package co.com.woaho.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.com.woaho.interfaces.IProfesionalService;
import co.com.woaho.utilidades.RegistrarLog;

@RestController
@RequestMapping("/profesional")
public class ProfesionalController {

	@Autowired
	private IProfesionalService profesionalService;
	
	private RegistrarLog logs = new RegistrarLog(ProfesionalController.class);
	
	@PostMapping(value = "/consultarProfesionales", produces = MediaType.APPLICATION_JSON_VALUE)
	public String consultarProfesionales(@RequestParam("idServicio") String pServicioId) {
		String strResultado = null;
		try {
			logs.registrarLogInfoEjecutaServicio("consultarProfesionales");
			strResultado = profesionalService.obtenerProfesionales(pServicioId);
		}catch (Exception e) {
			logs.registrarLogError("consultarProfesionales", e.getMessage(),e);
		}
		logs.registrarLogInfoResultado(strResultado);
		return strResultado;
	}
}
