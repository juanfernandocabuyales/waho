package co.com.woaho.services;

import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import co.com.woaho.alertas.EnviarNotificacion;
import co.com.woaho.enumeraciones.EnumGeneral;
import co.com.woaho.enumeraciones.EnumMensajes;
import co.com.woaho.interfaces.IUsuarioDao;
import co.com.woaho.interfaces.IUsuarioService;
import co.com.woaho.modelo.Usuario;
import co.com.woaho.request.ConsultarUsuarioRequest;
import co.com.woaho.request.GenerarCodigoRequest;
import co.com.woaho.request.LoginRequest;
import co.com.woaho.request.RegistrarUsuarioRequest;
import co.com.woaho.request.ValidarCodigoRequest;
import co.com.woaho.response.ConsultarUsuarioResponse;
import co.com.woaho.response.GenerarCodigoResponse;
import co.com.woaho.response.LoginResponse;
import co.com.woaho.response.RegistrarUsuarioResponse;
import co.com.woaho.response.ValidarCodigoResponse;
import co.com.woaho.utilidades.Constantes;
import co.com.woaho.utilidades.ProcesarCadenas;
import co.com.woaho.utilidades.RegistrarLog;

@Service
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UsuarioService implements IUsuarioService{

	private RegistrarLog logs = new RegistrarLog(UsuarioService.class);

	@Autowired
	private IUsuarioDao usuarioDao;
	
	@Autowired
	private EnviarNotificacion envioNotificacion;

	@Override
	public RegistrarUsuarioResponse registrarUsuario(RegistrarUsuarioRequest request) {
		logs.registrarLogInfoEjecutaMetodo("registrarUsuario");
		RegistrarUsuarioResponse response = new RegistrarUsuarioResponse();
		try {
			
			RegistrarUsuarioRequest.UsuarioDTO pUsuarioDTO = request.getUsuarioDto();
			
			Usuario usuarioval = usuarioDao.obtenerUsuarioCelular(pUsuarioDTO.getCell());
			
			if(usuarioval == null) {
				Usuario usuario = new Usuario();
				usuario.setStrNombre(pUsuarioDTO.getName());
				usuario.setStrApellido(pUsuarioDTO.getLastName());
				usuario.setStrCelular(pUsuarioDTO.getCell());
				usuario.setStrAceptaTerminos(pUsuarioDTO.getCheckTerminos());
				usuario.setFechaHoraAceptaTerminos(new Date());
				usuario.setIdSuscriptor(pUsuarioDTO.getIdSuscriptor());
				usuario.setStrCorreo(pUsuarioDTO.getEmail());

				usuarioDao.registarUsuario(usuario);
				
				response.setCodigoRespuesta(EnumGeneral.RESPUESTA_POSITIVA.getValor());
				response.setMensajeRespuesta(EnumGeneral.OK.getValor());
			}else {
				response.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
				response.setMensajeRespuesta(EnumMensajes.USUARIO_REGISTRADO.getMensaje(pUsuarioDTO.getCell()));
			}			
		}catch (Exception e) {
			logs.registrarLogError("registrarUsuario", "No se ha podido procesar la peticion", e);
			response.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
			response.setMensajeRespuesta(EnumMensajes.INCONVENIENTE_EN_OPERACION.getMensaje());
		}
		return response;
	}

	@Override
	public ConsultarUsuarioResponse consultarUsuario(ConsultarUsuarioRequest request) {	
		logs.registrarLogInfoEjecutaMetodo("consultarUsuario");
		ConsultarUsuarioResponse response = new ConsultarUsuarioResponse();
		try {
			Usuario usuario = usuarioDao.obtenerUsuarioCelular(request.getNumeroCelular());

			if(usuario == null) {
				response.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
				response.setMensajeRespuesta(EnumMensajes.NO_USUARIO.getMensaje("número",request.getNumeroCelular()));
			}else {
				ConsultarUsuarioResponse.UsuarioDTO usuarioDto = new ConsultarUsuarioResponse.UsuarioDTO(String.valueOf(usuario.getUsuarioId()), 
						usuario.getStrNombre(),
						usuario.getStrApellido(),
						usuario.getStrCelular(),
						usuario.getStrCorreo(),
						usuario.getStrAceptaTerminos(),
						usuario.getStrClave());
				response.setUsuarioDto(usuarioDto);
				response.setCodigoRespuesta(EnumGeneral.RESPUESTA_POSITIVA.getValor());
				response.setMensajeRespuesta(EnumGeneral.OK.getValor());							
			}			
		}catch (Exception e) {
			logs.registrarLogError("consultarUsuario", "No se ha podido procesar la peticion", e);
			response.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
			response.setMensajeRespuesta(EnumMensajes.INCONVENIENTE_EN_OPERACION.getMensaje());
		}
		return response;
	}

	@Override
	public GenerarCodigoResponse generarCodigoRegistro(GenerarCodigoRequest request) {
		logs.registrarLogInfoEjecutaMetodo("generarCodigoRegistro");
		GenerarCodigoResponse response = new GenerarCodigoResponse();
		try {
			String [] strRespuesta = usuarioDao.generarCodigoRegistro(request.getCelular()).split("\\,");

			if(strRespuesta[0].equalsIgnoreCase(EnumGeneral.RESPUESTA_NEGATIVA.getValor())) {
				response.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
				response.setMensajeRespuesta(EnumMensajes.INCONVENIENTE_EN_OPERACION.getMensaje());
			}else {
				response.setCodigoRespuesta(EnumGeneral.RESPUESTA_POSITIVA.getValor());
				response.setMensajeRespuesta(EnumGeneral.OK.getValor());
				response.setCodigo(strRespuesta[1]);
				
				HashMap<String, String> pParametros = new HashMap<>();
				pParametros.put(Constantes.CONTENIDO, 
						ProcesarCadenas.getInstance().obtenerMensajeFormat(Constantes.CONTENIDO_PUSH_REGISTRO, response.getCodigo()));
				pParametros.put(Constantes.CABECERA, Constantes.CONTENIDO_PUSH_CABECERA);
				pParametros.put(Constantes.ID_DEVICE, request.getIdSuscriptor());
				
				envioNotificacion.notificarCodigo(pParametros);
			}			
		}catch (Exception e) {
			logs.registrarLogError("generarCodigoRegistro", "No se ha podido procesar la peticion", e);
			response.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
			response.setMensajeRespuesta(EnumMensajes.INCONVENIENTE_EN_OPERACION.getMensaje());
		}
		return response;
	}

	@Override
	public LoginResponse loginUsuario(LoginRequest request) {
		logs.registrarLogInfoEjecutaMetodo("validarLogin");
		LoginResponse response = new LoginResponse();
		try {

			Usuario usuario = usuarioDao.obtenerUsuarioCorreo(request.getCorreo());			
			if (usuario == null) {
				response.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
				response.setMensajeRespuesta(EnumMensajes.NO_USUARIO.getMensaje("correo",request.getCorreo()));
			}else {				
				String [] strRespuesta = usuarioDao.generarCodigoRegistro(usuario.getStrCelular()).split("\\,");
				
				if(strRespuesta[0].equalsIgnoreCase(EnumGeneral.RESPUESTA_NEGATIVA.getValor())) {
					response.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
					response.setMensajeRespuesta(EnumMensajes.INCONVENIENTE_EN_OPERACION.getMensaje());
				}else {					
					HashMap<String, String> pParametros = new HashMap<>();
					pParametros.put(Constantes.CONTENIDO, 
							ProcesarCadenas.getInstance().obtenerMensajeFormat(Constantes.CONTENIDO_PUSH_REGISTRO,strRespuesta[1]));
					pParametros.put(Constantes.CABECERA, Constantes.CONTENIDO_PUSH_CABECERA);
					pParametros.put(Constantes.ID_DEVICE,usuario.getIdSuscriptor());
					
					envioNotificacion.notificarCodigo(pParametros);
					
					response.setCodigoRespuesta(EnumGeneral.RESPUESTA_POSITIVA.getValor());
					response.setMensajeRespuesta(EnumGeneral.OK.getValor());
				}	
			}	
		}catch (Exception e) {
			logs.registrarLogError("generarCodigoRegistro", "No se ha podido procesar la peticion", e);
			response.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
			response.setMensajeRespuesta(EnumMensajes.INCONVENIENTE_EN_OPERACION.getMensaje());
		}
		return response;
	}

	@Override
	public ValidarCodigoResponse validarCodigoRegistro(ValidarCodigoRequest request) {
		logs.registrarLogInfoEjecutaMetodo("validarCodigoRegistro");
		ValidarCodigoResponse response = new ValidarCodigoResponse();
		try {

			String [] respuesta = usuarioDao.validarCodigoRegistro(request.getCelular(), request.getCodigo()).split("\\,");
			
			if(respuesta[0].equalsIgnoreCase(EnumGeneral.RESPUESTA_NEGATIVA.getValor())) {
				response.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
				response.setMensajeRespuesta(respuesta[1]);
			}else {
				response.setCodigoRespuesta(EnumGeneral.RESPUESTA_POSITIVA.getValor());
				response.setMensajeRespuesta(respuesta[1]);
			}
		}catch (Exception e) {
			logs.registrarLogError("validarCodigoRegistro", "No se ha podido procesar la peticion", e);
			response.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
			response.setMensajeRespuesta(EnumMensajes.INCONVENIENTE_EN_OPERACION.getMensaje());
		}
		return response;
	}

	@Override
	public ValidarCodigoResponse validarCodigoLogin(ValidarCodigoRequest request) {
		logs.registrarLogInfoEjecutaMetodo("validarCodigoLogin");
		ValidarCodigoResponse response = new ValidarCodigoResponse();
		try {
			
			Usuario usuario = usuarioDao.obtenerUsuarioCorreo(request.getCorreo());
			
			if(usuario == null) {
				response.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
				response.setMensajeRespuesta(EnumMensajes.NO_USUARIO.getMensaje("correo",request.getCorreo()));
			}else {
				String [] respuesta = usuarioDao.validarCodigoRegistro(usuario.getStrCelular(), request.getCodigo()).split("\\,");
				
				if(respuesta[0].equalsIgnoreCase(EnumGeneral.RESPUESTA_NEGATIVA.getValor())) {
					response.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
					response.setMensajeRespuesta(respuesta[1]);
				}else {
					response.setCodigoRespuesta(EnumGeneral.RESPUESTA_POSITIVA.getValor());
					response.setMensajeRespuesta(respuesta[1]);
				}
			}			
		}catch (Exception e) {
			logs.registrarLogError("validarCodigoLogin", "No se ha podido procesar la peticion", e);
			response.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
			response.setMensajeRespuesta(EnumMensajes.INCONVENIENTE_EN_OPERACION.getMensaje());
		}
		return response;
	}

}
