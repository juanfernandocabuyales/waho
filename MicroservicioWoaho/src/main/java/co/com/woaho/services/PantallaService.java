package co.com.woaho.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;


import co.com.woaho.enumeraciones.EnumGeneral;
import co.com.woaho.enumeraciones.EnumMensajes;
import co.com.woaho.interfaces.IPantallaDao;
import co.com.woaho.interfaces.IPantallaService;
import co.com.woaho.request.ConsultarPantallasRequest;
import co.com.woaho.response.ConsultarPantallasResponse;
import co.com.woaho.utilidades.Constantes;
import co.com.woaho.utilidades.ProcesarCadenas;
import co.com.woaho.utilidades.RegistrarLog;

import org.springframework.context.annotation.ScopedProxyMode;

@Service
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PantallaService implements IPantallaService {

	@Autowired
	IPantallaDao pantallaDao;

	private RegistrarLog logs = new RegistrarLog(PantallaService.class);	


	@Override
	public ConsultarPantallasResponse obtenerMensajesPantalla(ConsultarPantallasRequest request) {
		logs.registrarLogInfoEjecutaMetodo("obtenerMensajesPantalla");
		ConsultarPantallasResponse response = new ConsultarPantallasResponse();
		try {

			String strCadena = pantallaDao.consultarPantallas(Integer.parseInt(Constantes.TIPO_SLIDES),
					                                          request.getIdioma().toUpperCase());

			logs.registrarLogInfo(strCadena);

			if(strCadena.isEmpty()) {
				response.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
				response.setMensajeRespuesta(EnumMensajes.NO_MENSAJES_PANTALLA.getMensaje());
			}else {
				List<ConsultarPantallasResponse.Slide> listSlides = ProcesarCadenas.getInstance().obtenerSlides(strCadena);
				response.setCodigoRespuesta(EnumGeneral.RESPUESTA_POSITIVA.getValor());
				response.setMensajeRespuesta(EnumMensajes.OK.getMensaje());
				response.setListSlides(listSlides);
			}
		}catch (Exception e) {
			logs.registrarLogError("obtenerMensajesPantalla", "No se ha podido procesar la peticion", e);
			response.setCodigoRespuesta(EnumGeneral.RESPUESTA_POSITIVA.getValor());
			response.setMensajeRespuesta(EnumMensajes.INCONVENIENTE_EN_OPERACION.getMensaje());
		}
		return response;
	}
}
