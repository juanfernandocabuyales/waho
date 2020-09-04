package co.com.woaho.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;


import co.com.woaho.dto.MensajeDTO;
import co.com.woaho.enumeraciones.EnumGeneral;
import co.com.woaho.enumeraciones.EnumMensajes;
import co.com.woaho.interfaces.IPantallaDao;
import co.com.woaho.interfaces.IPantallaService;
import co.com.woaho.request.MensajesPantallaRequest;
import co.com.woaho.response.MensajePantallaResponse;
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
	public MensajePantallaResponse obtenerMensajesPantalla(MensajesPantallaRequest request) {
		logs.registrarLogInfoEjecutaMetodo("obtenerMensajesPantalla");
		MensajePantallaResponse response = new MensajePantallaResponse();
		try {

			String strCadena = pantallaDao.consultarPantallas(Integer.parseInt(request.getIdPantalla()));

			logs.registrarLogInfo(strCadena);

			List<MensajeDTO> mensajesList = ProcesarCadenas.getInstance().obtenerMensajesCadena(strCadena);

			if(mensajesList != null && !mensajesList.isEmpty()) {
				response.setCodigoRespuesta(EnumGeneral.RESPUESTA_POSITIVA.getValor());
				response.setMensajeRespuesta(EnumGeneral.OK.getValor());
				response.setListMensajesDto(mensajesList);
			}else {
				response.setCodigoRespuesta(EnumGeneral.RESPUESTA_POSITIVA.getValor());
				response.setMensajeRespuesta(EnumMensajes.NO_MENSAJES_PANTALLA.getMensaje());
			}
		}catch (Exception e) {
			logs.registrarLogError("obtenerMensajesPantalla", "No se ha podido procesar la peticion", e);
			response.setCodigoRespuesta(EnumGeneral.RESPUESTA_POSITIVA.getValor());
			response.setMensajeRespuesta(EnumMensajes.INCONVENIENTE_EN_OPERACION.getMensaje());
		}
		return response;
	}
}
