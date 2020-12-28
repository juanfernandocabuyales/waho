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
import co.com.woaho.interfaces.IEquivalenciaIdiomaDao;
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
import co.com.woaho.utilidades.Utilidades;

@Service
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UsuarioService implements IUsuarioService{

	private RegistrarLog logs = new RegistrarLog(UsuarioService.class);

	@Autowired
	private IUsuarioDao usuarioDao;
	
	@Autowired
	private EnviarNotificacion envioNotificacion;
	
	@Autowired
	private IEquivalenciaIdiomaDao equivalenciaIdiomaDao;

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
				usuario.setReferralCode(pUsuarioDTO.getReferralCode());

				usuarioDao.registarUsuario(usuario);
				
				response.setCodigoRespuesta(EnumGeneral.RESPUESTA_POSITIVA.getValor());
				response.setMensajeRespuesta(EnumGeneral.OK.getValor());
				response.setExisteNumero(EnumGeneral.NO.getValor());
			}else {
				generarCodigoIngreso(usuarioval, request.getIdioma());
				response.setCodigoRespuesta(EnumGeneral.RESPUESTA_POSITIVA.getValor());
				response.setMensajeRespuesta(EnumGeneral.OK.getValor());
				response.setExisteNumero(EnumGeneral.SI.getValor());
			}			
		}catch (Exception e) {
			logs.registrarLogError("registrarUsuario", "No se ha podido procesar la peticion", e);
			response.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
			response.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.INCONVENIENTE_EN_OPERACION.getMensaje(), request.getIdioma(), equivalenciaIdiomaDao));
		}
		return response;
	}
	
	@Override
	public RegistrarUsuarioResponse actualizarUsuario(RegistrarUsuarioRequest request) {
		logs.registrarLogInfoEjecutaMetodo("actualizarUsuario");
		RegistrarUsuarioResponse response = new RegistrarUsuarioResponse();
		try {
			
			RegistrarUsuarioRequest.UsuarioDTO pUsuarioDTO = request.getUsuarioDto();
			
			Usuario usuarioval = usuarioDao.obtenerUsuarioId(Long.parseLong(pUsuarioDTO.getId()));
			
			if(usuarioval == null) {
				response.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
				String equivalencia = Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.NO_USUARIO.getMensaje(), request.getIdioma(), equivalenciaIdiomaDao);
				response.setMensajeRespuesta(ProcesarCadenas.getInstance().obtenerMensajeFormat(equivalencia, "cel",pUsuarioDTO.getCell()));
			}else {
				Usuario usuario = new Usuario();
				usuario.setUsuarioId(Long.parseLong(pUsuarioDTO.getId()));
				usuario.setStrNombre(pUsuarioDTO.getName());
				usuario.setStrApellido(pUsuarioDTO.getLastName());
				usuario.setStrCelular(pUsuarioDTO.getCell());				
				usuario.setStrCorreo(pUsuarioDTO.getEmail());
				usuario.setReferralCode(pUsuarioDTO.getReferralCode());

				usuarioDao.actualizarUsuario(usuarioval);
				
				response.setCodigoRespuesta(EnumGeneral.RESPUESTA_POSITIVA.getValor());
				response.setMensajeRespuesta(EnumGeneral.OK.getValor());
			}		
		}catch (Exception e) {
			logs.registrarLogError("actualizarUsuario", "No se ha podido procesar la peticion", e);
			response.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
			response.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.INCONVENIENTE_EN_OPERACION.getMensaje(), request.getIdioma(), equivalenciaIdiomaDao));
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
				String equivalencia = Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.NO_USUARIO.getMensaje(), request.getIdioma(), equivalenciaIdiomaDao);
				response.setMensajeRespuesta(ProcesarCadenas.getInstance().obtenerMensajeFormat(equivalencia, "cel",request.getNumeroCelular()));			
			}else {
				ConsultarUsuarioResponse.UsuarioDTO usuarioDto = new ConsultarUsuarioResponse.UsuarioDTO(String.valueOf(usuario.getUsuarioId()), 
						usuario.getStrNombre(),
						usuario.getStrApellido(),
						usuario.getStrCelular(),
						usuario.getStrCorreo(),
						usuario.getStrAceptaTerminos(),
						usuario.getStrClave(),
						usuario.getReferralCode());
				response.setUsuarioDto(usuarioDto);
				response.setCodigoRespuesta(EnumGeneral.RESPUESTA_POSITIVA.getValor());
				response.setMensajeRespuesta(EnumGeneral.OK.getValor());							
			}			
		}catch (Exception e) {
			logs.registrarLogError("consultarUsuario", "No se ha podido procesar la peticion", e);
			response.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
			response.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.INCONVENIENTE_EN_OPERACION.getMensaje(), request.getIdioma(), equivalenciaIdiomaDao));
		}
		return response;
	}

	@Override
	public GenerarCodigoResponse generarCodigoRegistro(GenerarCodigoRequest request) {
		logs.registrarLogInfoEjecutaMetodo("generarCodigoRegistro");
		GenerarCodigoResponse response = new GenerarCodigoResponse();
		try {
			String [] strRespuesta = usuarioDao.generarCodigoRegistro(request.getCelular(),request.getIdioma()).split("\\,");

			if(strRespuesta[0].equalsIgnoreCase(EnumGeneral.RESPUESTA_NEGATIVA.getValor())) {
				response.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
				response.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.INCONVENIENTE_EN_OPERACION.getMensaje(), request.getIdioma(), equivalenciaIdiomaDao));
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
			response.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.INCONVENIENTE_EN_OPERACION.getMensaje(), request.getIdioma(), equivalenciaIdiomaDao));
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
				String equivalencia = Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.NO_USUARIO.getMensaje(), request.getIdioma(), equivalenciaIdiomaDao);
				response.setMensajeRespuesta(ProcesarCadenas.getInstance().obtenerMensajeFormat(equivalencia, "email",request.getCorreo()));
			}else {				
				String [] strRespuesta = usuarioDao.generarCodigoRegistro(usuario.getStrCelular(),request.getIdioma()).split("\\,");
				
				if(strRespuesta[0].equalsIgnoreCase(EnumGeneral.RESPUESTA_NEGATIVA.getValor())) {
					response.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
					response.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.INCONVENIENTE_EN_OPERACION.getMensaje(), request.getIdioma(), equivalenciaIdiomaDao));
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
			response.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.INCONVENIENTE_EN_OPERACION.getMensaje(), request.getIdioma(), equivalenciaIdiomaDao));
		}
		return response;
	}

	@Override
	public ValidarCodigoResponse validarCodigoRegistro(ValidarCodigoRequest request) {
		logs.registrarLogInfoEjecutaMetodo("validarCodigoRegistro");
		ValidarCodigoResponse response = new ValidarCodigoResponse();
		try {

			String [] respuesta = usuarioDao.validarCodigoRegistro(request.getCelular(), request.getCodigo(),request.getIdioma()).split("\\,");
			
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
			response.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.INCONVENIENTE_EN_OPERACION.getMensaje(), request.getIdioma(), equivalenciaIdiomaDao));
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
				String equivalencia = Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.NO_USUARIO.getMensaje(), request.getIdioma(), equivalenciaIdiomaDao);
				response.setMensajeRespuesta(ProcesarCadenas.getInstance().obtenerMensajeFormat(equivalencia, "email",request.getCorreo()));
			}else {
				String [] respuesta = usuarioDao.validarCodigoRegistro(usuario.getStrCelular(), request.getCodigo(),request.getIdioma()).split("\\,");
				
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
			response.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.INCONVENIENTE_EN_OPERACION.getMensaje(), request.getIdioma(), equivalenciaIdiomaDao));
		}
		return response;
	}

	@Override
	public void generarCodigoIngreso(Usuario pUsuario,String pIdioma) throws Exception {
		try {
			String [] strRespuesta = usuarioDao.generarCodigoRegistro(pUsuario.getStrCelular(),pIdioma).split("\\,");
			
			if(strRespuesta[0].equalsIgnoreCase(EnumGeneral.RESPUESTA_NEGATIVA.getValor())) {
				throw new Exception(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.INCONVENIENTE_EN_OPERACION.getMensaje(), pIdioma, equivalenciaIdiomaDao));
			}else {					
				HashMap<String, String> pParametros = new HashMap<>();
				pParametros.put(Constantes.CONTENIDO, 
						ProcesarCadenas.getInstance().obtenerMensajeFormat(Constantes.CONTENIDO_PUSH_REGISTRO,strRespuesta[1]));
				pParametros.put(Constantes.CABECERA, Constantes.CONTENIDO_PUSH_CABECERA);
				pParametros.put(Constantes.ID_DEVICE,pUsuario.getIdSuscriptor());
				
				envioNotificacion.notificarCodigo(pParametros);
			}	
		}catch(Exception e) {
			logs.registrarLogError("generarCodigoIngreso", "No se ha podido procesar la peticion", e);
			throw e;
		}
	}
}
