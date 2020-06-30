package com.co.woaho.utilidades;

import com.co.respuestas.RespuestaNegativa;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utilidades {

	private static Utilidades utilidades;
	
	private RegistrarLog logs = new RegistrarLog(Utilidades.class);

	private Utilidades() {

	}

	public static Utilidades getInstance() {
		if(utilidades == null) {
			utilidades = new Utilidades();
		}
		return utilidades;
	}

	public String procesarException(int pServicio,String pStrMensaje) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			RespuestaNegativa respuestaNegativa = new RespuestaNegativa();
			respuestaNegativa.setCodigoServicio(pServicio);
			respuestaNegativa.setRespuesta(pStrMensaje);
			return mapper.writeValueAsString(respuestaNegativa);
		}catch (Exception e) {
			logs.registrarLogError("procesarException", "No se ha podido procesar la peticion", e);
			return "";
		}
	}
}
