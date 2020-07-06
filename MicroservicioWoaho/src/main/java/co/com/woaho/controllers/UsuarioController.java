package co.com.woaho.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.com.woaho.interfaces.IUsuarioService;
import co.com.woaho.utilidades.RegistrarLog;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

	private RegistrarLog logs = new RegistrarLog(UsuarioController.class);

	@Autowired
	private IUsuarioService usuarioService;

	@PostMapping(value = "/registrarUsuario", produces = MediaType.APPLICATION_JSON_VALUE)
	public String registrarUsuario(@RequestBody String pUsuarioDto) {
		String strResultado = null;
		try {
			logs.registrarLogInfoEjecutaServicio("registrarUsuario");
			strResultado = usuarioService.registrarUsuario(pUsuarioDto);
		}catch (Exception e) {
			logs.registrarLogError("registrarUsuario", e.getMessage(),e);
		}
		logs.registrarLogInfoResultado(strResultado);
		return strResultado;
	}
	
	@PostMapping(value = "/actualizarUsuario", produces = MediaType.APPLICATION_JSON_VALUE)
	public String actualizarUsuario(@RequestBody String pUsuarioDto) {
		String strResultado = null;
		try {
			logs.registrarLogInfoEjecutaServicio("actualizarUsuario");
			strResultado = usuarioService.actualizarUsuario(pUsuarioDto);
		}catch (Exception e) {
			logs.registrarLogError("actualizarUsuario", e.getMessage(),e);
		}
		logs.registrarLogInfoResultado(strResultado);
		return strResultado;
	}
	
	@PostMapping(value = "/consultarUsuario", produces = MediaType.APPLICATION_JSON_VALUE)
	public String consultarUsuario(@RequestParam(name = "pStrCelular") String pStrCelular ) {
		String strResultado = null;
		try {
			logs.registrarLogInfoEjecutaServicio("consultarUsuario");
			strResultado = usuarioService.consultarUsuario(pStrCelular);
		}catch (Exception e) {
			logs.registrarLogError("consultarUsuario", e.getMessage(),e);
		}
		logs.registrarLogInfoResultado(strResultado);
		return strResultado;
	}
}
