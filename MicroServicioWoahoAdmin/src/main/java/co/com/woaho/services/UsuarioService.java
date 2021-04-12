package co.com.woaho.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import co.com.woaho.dto.UsuarioDto;
import co.com.woaho.enumeraciones.EnumGeneral;
import co.com.woaho.enumeraciones.EnumMensajes;
import co.com.woaho.enumeraciones.EnumUsuarios;
import co.com.woaho.interfaces.IEquivalenciaIdiomaDao;
import co.com.woaho.interfaces.IUsuarioDao;
import co.com.woaho.interfaces.IUsuarioService;
import co.com.woaho.modelo.Usuario;
import co.com.woaho.request.ConsultarUsuariosRequest;
import co.com.woaho.request.CrearUsuarioRequest;
import co.com.woaho.request.LoginAdminRequest;
import co.com.woaho.response.ConsultarUsuariosResponse;
import co.com.woaho.response.CrearResponse;
import co.com.woaho.response.LoginAdminResponse;
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
	private IEquivalenciaIdiomaDao equivalenciaIdiomaDao;

	@Override
	public CrearResponse registrarUsuario(CrearUsuarioRequest request) {
		CrearResponse response = new CrearResponse();
		try {			
			UsuarioDto pUsuarioDTO = request.getUsuarioDto();			
			Usuario usuarioval = usuarioDao.obtenerUsuarioCorreo(pUsuarioDTO.getCorreo());			
			if(usuarioval == null) {
				Usuario usuario = new Usuario();
				usuario.setStrNombre(pUsuarioDTO.getNombres());
				usuario.setStrApellido(pUsuarioDTO.getApellidos());
				usuario.setStrCelular(pUsuarioDTO.getCelular());
				usuario.setStrAceptaTerminos(EnumGeneral.SI.getValor());
				usuario.setFechaHoraAceptaTerminos(new Date());
				usuario.setIdSuscriptor(pUsuarioDTO.getIdSuscriptor());
				usuario.setStrCorreo(pUsuarioDTO.getCorreo());
				usuario.setReferralCode(pUsuarioDTO.getReferrealCode());
				usuario.setStrClave( (pUsuarioDTO.getClave() == null || pUsuarioDTO.getClave().isEmpty() ? null : Utilidades.getInstance().encriptarTexto(pUsuarioDTO.getClave())) );
				usuario.setTipoUsuario(EnumGeneral.USUARIO_ADMIN.getValorLong());
				usuarioDao.crearActualizarUsuario(usuario);				
				response.setCodigoRespuesta(EnumGeneral.RESPUESTA_POSITIVA.getValor());
				response.setMensajeRespuesta(EnumGeneral.OK.getValor());
			}else {
				response.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
				response.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.CORREO_REGISTRADO.getMensaje(), request.getIdioma(), equivalenciaIdiomaDao));
			}			
		}catch (Exception e) {
			logs.registrarLogError("registrarUsuario", "No se ha podido procesar la peticion", e);
			response.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
			response.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.INCONVENIENTE_EN_OPERACION.getMensaje(), request.getIdioma(), equivalenciaIdiomaDao));
		}
		return response;
	}
	
	@Override
	public CrearResponse actualizarUsuario(CrearUsuarioRequest request) {
		CrearResponse response = new CrearResponse();
		try {			
			UsuarioDto pUsuarioDTO = request.getUsuarioDto();			
			Usuario usuario = usuarioDao.obtenerUsuarioCorreo(pUsuarioDTO.getCorreo());			
			if(usuario == null) {
				response.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
				response.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.NO_REGISTROS.getMensaje(), request.getIdioma(), equivalenciaIdiomaDao));
			}else {				
				usuario.setStrNombre(pUsuarioDTO.getNombres());
				usuario.setStrApellido(pUsuarioDTO.getApellidos());
				usuario.setStrCelular(pUsuarioDTO.getCelular());
				usuario.setStrAceptaTerminos(EnumGeneral.SI.getValor());
				usuario.setFechaHoraAceptaTerminos(new Date());
				usuario.setIdSuscriptor(pUsuarioDTO.getIdSuscriptor());
				usuario.setStrCorreo(pUsuarioDTO.getCorreo());
				usuario.setStrClave( (pUsuarioDTO.getClave() == null || pUsuarioDTO.getClave().isEmpty() ? null : Utilidades.getInstance().encriptarTexto(pUsuarioDTO.getClave())) );
				usuario.setReferralCode(pUsuarioDTO.getReferrealCode());
				usuario.setTipoUsuario(EnumGeneral.USUARIO_ADMIN.getValorLong());
				usuarioDao.crearActualizarUsuario(usuario);			
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
	public ConsultarUsuariosResponse consultarUsuarios(ConsultarUsuariosRequest request) {	
		ConsultarUsuariosResponse response = new ConsultarUsuariosResponse();
		try {
			List<Usuario> listUsuarios = usuarioDao.obtenerUsuarios(Long.parseLong(request.getTipoUsuario()));			
			if(null == listUsuarios || listUsuarios.isEmpty()) {
				response.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
				response.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.NO_REGISTROS.getMensaje(), request.getIdioma(), equivalenciaIdiomaDao));
			}else {
				List<UsuarioDto> listUsuarioDto = new ArrayList<>();
				listUsuarios.forEach(item ->{
					UsuarioDto usuarioDto = new UsuarioDto();
					usuarioDto.setId(String.valueOf(item.getUsuarioId()));
					usuarioDto.setNombres(item.getStrNombre());
					usuarioDto.setApellidos(item.getStrApellido());
					usuarioDto.setCelular(item.getStrCelular());
					usuarioDto.setCorreo(item.getStrCorreo());
					usuarioDto.setClave(Utilidades.getInstance().desencriptarTexto(item.getStrClave()));
					usuarioDto.setIdSuscriptor(item.getIdSuscriptor());
					usuarioDto.setReferrealCode(item.getReferralCode());
					usuarioDto.setTipoUsuario(String.valueOf(item.getTipoUsuario()));
					usuarioDto.setTerminos(item.getStrAceptaTerminos().equalsIgnoreCase("S"));
					listUsuarioDto.add(usuarioDto);
				});
				response.setCodigoRespuesta(EnumGeneral.RESPUESTA_POSITIVA.getValor());
				response.setMensajeRespuesta(EnumGeneral.OK.getValor());
				response.setListUsuarios(listUsuarioDto);
			}
		}catch (Exception e) {
			logs.registrarLogError("consultarUsuario", "No se ha podido procesar la peticion", e);
			response.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
			response.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.INCONVENIENTE_EN_OPERACION.getMensaje(), request.getIdioma(), equivalenciaIdiomaDao));
		}
		return response;
	}

	@Override
	public LoginAdminResponse validarLoginAdmin(LoginAdminRequest request) {
		LoginAdminResponse loginAdminResponse = new LoginAdminResponse();
		try {
			Usuario usuarioAdmin = usuarioDao.obtenerUsuarioAdmin(request.getUsuario(), EnumUsuarios.USUARIO_ADMIN.getValor());			
			if( null == usuarioAdmin) {
				loginAdminResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
				String equivalencia = Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.NO_USUARIO.getMensaje(), request.getIdioma(), equivalenciaIdiomaDao);
				loginAdminResponse.setMensajeRespuesta(ProcesarCadenas.getInstance().obtenerMensajeFormat(equivalencia, "email",request.getUsuario()));
			}else {
				String cifrado = Utilidades.getInstance().encriptarTexto(request.getLlave());				
				if(cifrado.equals(usuarioAdmin.getStrClave())) {
					loginAdminResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_POSITIVA.getValor());
					loginAdminResponse.setMensajeRespuesta(EnumGeneral.OK.getValor());
					loginAdminResponse.setIdUsuario(usuarioAdmin.getUsuarioId() + "");
				}else {
					loginAdminResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
					String equivalencia = Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.CLAVE_INVALIDA.getMensaje(), request.getIdioma(), equivalenciaIdiomaDao);
					loginAdminResponse.setMensajeRespuesta(equivalencia);
				}
			}
		}catch(Exception e) {
			logs.registrarLogError("validarLoginAdmin", "No se ha podido procesar la peticion", e);
			loginAdminResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
			loginAdminResponse.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.INCONVENIENTE_EN_OPERACION.getMensaje(), request.getIdioma(), equivalenciaIdiomaDao));
		}
		return loginAdminResponse;
	}
}
