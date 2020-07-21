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
import co.com.woaho.enumeraciones.EnumGeneral;
import co.com.woaho.enumeraciones.EnumMensajes;
import co.com.woaho.interfaces.IProfesionalDao;
import co.com.woaho.interfaces.IProfesionalService;
import co.com.woaho.modelo.Profesional;
import co.com.woaho.utilidades.RegistrarLog;
import co.com.woaho.utilidades.Utilidades;

@Service
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ProfesionalService implements IProfesionalService {

	@Autowired
	private IProfesionalDao profesionalDao;
	
	private RegistrarLog logs = new RegistrarLog(ProfesionalService.class);	
	
	@Override
	public String obtenerProfesionales(String pIdServicios) {
		ObjectMapper mapper = new ObjectMapper();
		String resultado = null;
		RespuestaNegativa respuestaNegativa = new RespuestaNegativa();
		respuestaNegativa.setCodigoServicio(EnumGeneral.SERVICIO_CONSULTAR_PROFESIONALES.getValorInt());
		logs.registrarLogInfoEjecutaMetodoConParam("obtenerProfesionales","pIdServicios: "+pIdServicios);
		try {
			List<Profesional> listProfesionales = profesionalDao.obtenerProfesionales(pIdServicios);
			
			if(listProfesionales != null && !listProfesionales.isEmpty()) {
				JsonGenerico<Profesional> objetoJson = new JsonGenerico<>();
				
				for(Profesional profesional: listProfesionales) {
					objetoJson.add(profesional);
				}
				
				RespuestaPositiva respuestaPositiva = new RespuestaPositiva(
						EnumGeneral.SERVICIO_CONSULTAR_PROFESIONALES.getValorInt(), objetoJson);
				resultado = mapper.writeValueAsString(respuestaPositiva);
				
			}else {
				resultado = Utilidades.getInstance().procesarException(EnumGeneral.SERVICIO_CONSULTAR_PROFESIONALES.getValorInt(), EnumMensajes.NO_PROFESIONALES.getMensaje());
			}
		}catch (Exception e) {
			logs.registrarLogError("obtenerProfesionales", "No se ha podido procesar la peticion", e);
			resultado = Utilidades.getInstance().procesarException(EnumGeneral.SERVICIO_CONSULTAR_PROFESIONALES.getValorInt(), EnumMensajes.NO_PROFESIONALES.getMensaje());
		}
		return resultado;
	}

}
