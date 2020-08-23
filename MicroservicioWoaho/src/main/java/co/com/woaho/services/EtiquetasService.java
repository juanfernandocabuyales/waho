package co.com.woaho.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import co.com.woaho.enumeraciones.EnumGeneral;
import co.com.woaho.enumeraciones.EnumMensajes;
import co.com.woaho.interfaces.IEtiquetaDao;
import co.com.woaho.interfaces.IEtiquetasService;
import co.com.woaho.modelo.Etiqueta;
import co.com.woaho.request.ConsultarEtiquetasRequest;
import co.com.woaho.response.ConsultarEtiquetasResponse;
import co.com.woaho.utilidades.RegistrarLog;

@Service
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class EtiquetasService implements IEtiquetasService {

	@Autowired
	private IEtiquetaDao etiquetasDao;

	private RegistrarLog logs = new RegistrarLog(EtiquetasService.class);

	@Override
	public ConsultarEtiquetasResponse consultarEtiquetasIdioma(ConsultarEtiquetasRequest request) {
		ConsultarEtiquetasResponse response = new ConsultarEtiquetasResponse();
		logs.registrarLogInfoEjecutaMetodoConParam("consultarEtiquetasIdioma",request.toString());
		try {
			List<Etiqueta> listEtiquetas = etiquetasDao.obtenerEtiquetasIdioma(Long.parseLong(request.getIdiomaEtiqueta()));
			if(listEtiquetas != null && !listEtiquetas.isEmpty()) {
				List<ConsultarEtiquetasResponse.Etiqueta> lisEtiquetasDTO = new ArrayList<>();
				for(Etiqueta etiqueta : listEtiquetas) {
					ConsultarEtiquetasResponse.Etiqueta etiquetaDTO = new ConsultarEtiquetasResponse.Etiqueta();
					etiquetaDTO.setCodEtiqueta(etiqueta.getStrCodigoEtiqueta());
					etiquetaDTO.setEtiqueta(etiqueta.getStrValorEtiqueta());
					etiquetaDTO.setIdEtiqueta(String.valueOf(etiqueta.getEtiquetaId()));
					lisEtiquetasDTO.add(etiquetaDTO);
				}
				response.setListEtiquetas(lisEtiquetasDTO);
				response.setCodRespuesta(EnumGeneral.RESPUESTA_POSITIVA.getValor());
				response.setMensajeRespuesta(EnumGeneral.OK.getValor());
			}else {
				response.setCodRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
				response.setMensajeRespuesta(EnumMensajes.INCONVENIENTE_EN_OPERACION.getMensaje());
			}
		}catch(Exception e) {
			logs.registrarLogError("consultarEtiquetasIdioma", "No se ha podido procesar la peticion", e);
			response.setCodRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
			response.setMensajeRespuesta(EnumMensajes.INCONVENIENTE_EN_OPERACION.getMensaje());
		}
		return response;
	}

	@Override
	public ConsultarEtiquetasResponse consultarEtiquetaCodigoIdioma(ConsultarEtiquetasRequest request) {
		ConsultarEtiquetasResponse response = new ConsultarEtiquetasResponse();
		logs.registrarLogInfoEjecutaMetodoConParam("consultarEtiquetaCodigoIdioma",request.toString());
		try {
			Etiqueta etiqueta = etiquetasDao.obtenerEtiquetaCodigoIdioma(request.getCodigoEtiqueta(), Long.parseLong(request.getIdiomaEtiqueta()));
			if(etiqueta != null ) {
				List<ConsultarEtiquetasResponse.Etiqueta> lisEtiquetasDTO = new ArrayList<>();
				ConsultarEtiquetasResponse.Etiqueta etiquetaDTO = new ConsultarEtiquetasResponse.Etiqueta();
				etiquetaDTO.setCodEtiqueta(etiqueta.getStrCodigoEtiqueta());
				etiquetaDTO.setEtiqueta(etiqueta.getStrValorEtiqueta());
				etiquetaDTO.setIdEtiqueta(String.valueOf(etiqueta.getEtiquetaId()));
				lisEtiquetasDTO.add(etiquetaDTO);
				response.setListEtiquetas(lisEtiquetasDTO);
				response.setCodRespuesta(EnumGeneral.RESPUESTA_POSITIVA.getValor());
				response.setMensajeRespuesta(EnumGeneral.OK.getValor());
			}else {
				response.setCodRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
				response.setMensajeRespuesta(EnumMensajes.INCONVENIENTE_EN_OPERACION.getMensaje());
			}
		}catch(Exception e) {
			logs.registrarLogError("consultarEtiquetaCodigoIdioma", "No se ha podido procesar la peticion", e);
			response.setCodRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
			response.setMensajeRespuesta(EnumMensajes.INCONVENIENTE_EN_OPERACION.getMensaje());
		}
		return response;
	}
}
