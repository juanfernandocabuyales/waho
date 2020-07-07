package co.com.woaho.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import co.com.respuestas.JsonGenerico;
import co.com.respuestas.RespuestaNegativa;
import co.com.respuestas.RespuestaPositiva;
import co.com.respuestas.RespuestaPositivaCadena;
import co.com.woaho.dto.UsuarioDTO;
import co.com.woaho.enumeraciones.EnumGeneral;
import co.com.woaho.enumeraciones.EnumMensajes;
import co.com.woaho.interfaces.IUsuarioDao;
import co.com.woaho.interfaces.IUsuarioService;
import co.com.woaho.modelo.Usuario;
import co.com.woaho.utilidades.RegistrarLog;
import co.com.woaho.utilidades.Utilidades;

@Service
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UsuarioService implements IUsuarioService{

	private RegistrarLog logs = new RegistrarLog(UsuarioService.class);

	@Autowired
	private IUsuarioDao usuarioDao;

	@Override
	public String registrarUsuario(String pCadenaUsuarioDTO) {
		ObjectMapper mapper = new ObjectMapper();
		String resultado = null;
		RespuestaNegativa respuestaNegativa = new RespuestaNegativa();
		respuestaNegativa.setCodigoServicio(EnumGeneral.SERVICIO_CREAR_USUARIO.getValorInt());
		logs.registrarLogInfoEjecutaMetodoConParam("registrarUsuario",pCadenaUsuarioDTO);
		try {

			UsuarioDTO pUsuarioDTO = new Gson().fromJson(pCadenaUsuarioDTO, UsuarioDTO.class);

			Usuario usuario = new Usuario();
			usuario.setStrNombre(pUsuarioDTO.getName());
			usuario.setStrApellido(pUsuarioDTO.getLastName());
			usuario.setStrCelular(pUsuarioDTO.getCell());
			usuario.setStrAceptaTerminos(pUsuarioDTO.getCheckTerminos());
			usuario.setFechaHoraAceptaTerminos(new Date());

			usuarioDao.registarUsuario(usuario);

			RespuestaPositivaCadena respuestaPositiva = new RespuestaPositivaCadena(EnumGeneral.SERVICIO_CREAR_USUARIO.getValorInt(),EnumMensajes.REGISTRO_EXITOSO.getMensaje("del usuario "+pUsuarioDTO.getNombreApellido()));
			resultado = mapper.writeValueAsString(respuestaPositiva);

		}catch (Exception e) {
			logs.registrarLogError("registrarUsuario", "No se ha podido procesar la peticion", e);
			resultado = Utilidades.getInstance().procesarException(EnumGeneral.SERVICIO_CREAR_USUARIO.getValorInt(), EnumMensajes.INCONVENIENTE_EN_OPERACION.getMensaje());
		}
		return resultado;
	}

	@Override
	public String actualizarUsuario(String pCadenaUsuarioDTO) {
		ObjectMapper mapper = new ObjectMapper();
		String resultado = null;
		RespuestaNegativa respuestaNegativa = new RespuestaNegativa();
		respuestaNegativa.setCodigoServicio(EnumGeneral.SERVICIO_ACTUALIZAR_USUARIO.getValorInt());
		logs.registrarLogInfoEjecutaMetodoConParam("actualizarUsuario",pCadenaUsuarioDTO);
		try {

			UsuarioDTO pUsuarioDTO = new Gson().fromJson(pCadenaUsuarioDTO, UsuarioDTO.class);

			Usuario usuario = usuarioDao.obtenerUsuarioCelular(pUsuarioDTO.getCell());

			if(usuario == null) {
				respuestaNegativa.setRespuesta(EnumMensajes.NO_USUARIO.getMensaje(pUsuarioDTO.getCell()));
				resultado = mapper.writeValueAsString(respuestaNegativa);
			}else {

				usuario.setStrClave(Utilidades.getInstance().encriptarTexto(pUsuarioDTO.getPassword()));
				usuario.setStrCorreo(pUsuarioDTO.getEmail());

				usuarioDao.actualizarUsuario(usuario);

				RespuestaPositivaCadena respuestaPositiva = new RespuestaPositivaCadena(EnumGeneral.SERVICIO_ACTUALIZAR_USUARIO.getValorInt(),EnumMensajes.REGISTRO_EXITOSO.getMensaje(usuario.getStrNombre()));
				resultado = mapper.writeValueAsString(respuestaPositiva);
			}			
		}catch (Exception e) {
			logs.registrarLogError("actualizarUsuario", "No se ha podido procesar la peticion", e);
			resultado = Utilidades.getInstance().procesarException(EnumGeneral.SERVICIO_ACTUALIZAR_USUARIO.getValorInt(), EnumMensajes.INCONVENIENTE_EN_OPERACION.getMensaje());
		}
		return resultado;
	}

	@Override
	public String consultarUsuario(String pCelular) {
		ObjectMapper mapper = new ObjectMapper();
		String resultado = null;
		RespuestaNegativa respuestaNegativa = new RespuestaNegativa();
		respuestaNegativa.setCodigoServicio(EnumGeneral.SERVICIO_CONSULTAR_USUARIO.getValorInt());
		logs.registrarLogInfoEjecutaMetodoConParam("consultarUsuario",pCelular);
		try {
			Usuario usuario = usuarioDao.obtenerUsuarioCelular(pCelular);

			if(usuario == null) {
				respuestaNegativa.setRespuesta(EnumMensajes.NO_USUARIO.getMensaje(pCelular));
				resultado = mapper.writeValueAsString(respuestaNegativa);
			}else {
				JsonGenerico<UsuarioDTO> jsonGenerico = new JsonGenerico<>();
				UsuarioDTO usuarioDto = new UsuarioDTO(String.valueOf(usuario.getUsuarioId()), 
													   usuario.getStrNombre(),
													   usuario.getStrApellido(),
													   usuario.getStrCelular(),
													   usuario.getStrCorreo(),
													   usuario.getStrAceptaTerminos(),
													   usuario.getStrClave());
				jsonGenerico.add(usuarioDto);
				RespuestaPositiva respuestaPositiva = new RespuestaPositiva(
						EnumGeneral.SERVICIO_CONSULTAR_USUARIO.getValorInt(), jsonGenerico);
				resultado = mapper.writeValueAsString(respuestaPositiva);				
			}			
		}catch (Exception e) {
			logs.registrarLogError("consultarUsuario", "No se ha podido procesar la peticion", e);
			resultado = Utilidades.getInstance().procesarException(EnumGeneral.SERVICIO_CONSULTAR_USUARIO.getValorInt(), EnumMensajes.INCONVENIENTE_EN_OPERACION.getMensaje());
		}
		return resultado;
	}

}