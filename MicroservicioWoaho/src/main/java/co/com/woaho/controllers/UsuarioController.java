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

	@PostMapping(value = "/generarCodigoRegistro", produces = MediaType.APPLICATION_JSON_VALUE)
	public String generarCodigoRegistro(@RequestParam(name = "pStrCelular") String pStrCelular ) {
		String strResultado = null;
		try {
			logs.registrarLogInfoEjecutaServicio("generarCodigoRegistro");
			strResultado = usuarioService.generarCodigoRegistro(pStrCelular);
		}catch (Exception e) {
			logs.registrarLogError("generarCodigoRegistro", e.getMessage(),e);
		}
		logs.registrarLogInfoResultado(strResultado);
		return strResultado;
	}
	
	@PostMapping(value = "/validarLogin", produces = MediaType.APPLICATION_JSON_VALUE)
	public String validarLogin(@RequestParam(name = "pStrCorreo") String pStrCorreo,@RequestParam(name = "pStrClave") String pStrClave ) {
		String strResultado = null;
		try {
			logs.registrarLogInfoEjecutaServicio("validarLogin");
			strResultado = usuarioService.validarLogin(pStrCorreo, pStrClave);
		}catch (Exception e) {
			logs.registrarLogError("validarLogin", e.getMessage(),e);
		}
		logs.registrarLogInfoResultado(strResultado);
		return strResultado;
	}
	
	@PostMapping(value = "/validarCodigoRegistro", produces = MediaType.APPLICATION_JSON_VALUE)
	public String validarCodigoRegistro(@RequestParam(name = "pStrCelular") String pStrCelular,@RequestParam(name = "pStrCodigo") String pStrCodigo ) {
		String strResultado = null;
		try {
			logs.registrarLogInfoEjecutaServicio("validarCodigoRegistro");
			strResultado = usuarioService.validarCodigoRegistro(pStrCelular, pStrCodigo);
		}catch (Exception e) {
			logs.registrarLogError("validarCodigoRegistro", e.getMessage(),e);
		}
		logs.registrarLogInfoResultado(strResultado);
		return strResultado;
	}
}
