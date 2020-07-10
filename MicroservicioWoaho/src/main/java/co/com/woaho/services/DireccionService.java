package co.com.woaho.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.com.respuestas.JsonGenerico;
import co.com.respuestas.RespuestaNegativa;
import co.com.respuestas.RespuestaPositiva;
import co.com.woaho.dto.DireccionDTO;
import co.com.woaho.enumeraciones.EnumGeneral;
import co.com.woaho.enumeraciones.EnumMensajes;
import co.com.woaho.interfaces.IDireccionDao;
import co.com.woaho.interfaces.IDireccionService;
import co.com.woaho.modelo.Direccion;
import co.com.woaho.utilidades.ProcesarCadenas;
import co.com.woaho.utilidades.RegistrarLog;
import co.com.woaho.utilidades.Utilidades;

@Service
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class DireccionService implements IDireccionService {

	private RegistrarLog logs = new RegistrarLog(DireccionService.class);

	@Autowired
	private IDireccionDao direccionDao;

	@Override
	public String obtenerDireccionesUsuario(String pIdUsuario) {
		ObjectMapper mapper = new ObjectMapper();
		String resultado = null;
		RespuestaNegativa respuestaNegativa = new RespuestaNegativa();
		respuestaNegativa.setCodigoServicio(EnumGeneral.SERVICIO_CONSULTAR_DIRECCIONES.getValorInt());
		logs.registrarLogInfoEjecutaMetodoConParam("obtenerDireccionesUsuario","pIdUsuario: " + pIdUsuario);
		try {
			List<Direccion> listDirecciones = direccionDao.obtenerDireccionesUsuario(Long.parseLong(pIdUsuario));
			if(listDirecciones == null || listDirecciones.isEmpty()) {
				respuestaNegativa.setRespuesta(EnumMensajes.NO_DIRECCIONES.getMensaje());
				resultado = mapper.writeValueAsString(respuestaNegativa);
			}else {
				JsonGenerico<DireccionDTO> objetoJson = ProcesarCadenas.getInstance().obtenerDirecciones(listDirecciones);
				RespuestaPositiva respuestaPositiva = new RespuestaPositiva(
						EnumGeneral.SERVICIO_CONSULTAR_DIRECCIONES.getValorInt(), objetoJson);
				resultado = mapper.writeValueAsString(respuestaPositiva);
			}
		}catch (Exception e) {
			logs.registrarLogError("obtenerDireccionesUsuario", "No se ha podido procesar la peticion", e);
			resultado = Utilidades.getInstance().procesarException(EnumGeneral.SERVICIO_CONSULTAR_DIRECCIONES.getValorInt(), EnumMensajes.INCONVENIENTE_EN_OPERACION.getMensaje());
		}
		return resultado;
	}

}
