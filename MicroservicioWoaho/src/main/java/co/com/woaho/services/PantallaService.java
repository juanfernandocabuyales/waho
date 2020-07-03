package co.com.woaho.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.com.respuestas.JsonGenerico;
import co.com.respuestas.RespuestaNegativa;
import co.com.respuestas.RespuestaPositiva;
import co.com.woaho.dto.MensajeDTO;
import co.com.woaho.enumeraciones.EnumGeneral;
import co.com.woaho.enumeraciones.EnumMensajes;
import co.com.woaho.interfaces.IPantallaDao;
import co.com.woaho.interfaces.IPantallaService;
import co.com.woaho.utilidades.ProcesarCadenas;
import co.com.woaho.utilidades.RegistrarLog;
import co.com.woaho.utilidades.Utilidades;

import org.springframework.context.annotation.ScopedProxyMode;

@Service
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PantallaService implements IPantallaService {

	@Autowired
	IPantallaDao pantallaDao;

	private RegistrarLog logs = new RegistrarLog(PantallaService.class);	


	@Override
	public String obtenerMensajesPantalla(int pIntPantallaId) {
		ObjectMapper mapper = new ObjectMapper();
		String resultado = null;
		RespuestaNegativa respuestaNegativa = new RespuestaNegativa();
		respuestaNegativa.setCodigoServicio(EnumGeneral.SERVICIO_CONSULTA_PANTALLAS.getValorInt());
		logs.registrarLogInfoEjecutaMetodoConParam("obtenerMensajesPantalla", ""+pIntPantallaId);
		try {

			String strCadena = pantallaDao.consultarPantallas(pIntPantallaId);

			logs.registrarLogInfo(strCadena);

			List<MensajeDTO> mensajesList = ProcesarCadenas.getInstance().obtenerMensajesCadena(strCadena);

			if(mensajesList != null && !mensajesList.isEmpty()) {
				JsonGenerico<MensajeDTO> objetoJson = new JsonGenerico<>();

				for(MensajeDTO mensaje: mensajesList) {
					objetoJson.add(mensaje);
				}

				RespuestaPositiva respuestaPositiva = new RespuestaPositiva(
						EnumGeneral.SERVICIO_CONSULTA_PANTALLAS.getValorInt(), objetoJson);
				resultado = mapper.writeValueAsString(respuestaPositiva);

			}else {
				respuestaNegativa.setRespuesta(EnumMensajes.NO_MENSAJES_PANTALLA.getMensaje());
				resultado = mapper.writeValueAsString(respuestaNegativa);
			}


		}catch (Exception e) {
			logs.registrarLogError("obtenerMensajesPantalla", "No se ha podido procesar la peticion", e);
			resultado = Utilidades.getInstance().procesarException(EnumGeneral.SERVICIO_CONSULTA_PANTALLAS.getValorInt(), EnumMensajes.NO_MENSAJES_PANTALLA.getMensaje());
		}
		logs.registrarLogInfo(resultado);
		return resultado;
	}



}
