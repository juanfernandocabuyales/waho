package com.co.respuestas;

import com.co.woaho.enumeraciones.GeneralEnum;

/**
* ****************************************************************.
* @autor: Juan Cabuyales
* @fecha: 30/06/2020
* @descripcion: Clase que permite definir una respuesta positiva
* ****************************************************************
*/	
@SuppressWarnings("rawtypes")
public class RespuestaPositiva {

	private int codigoServicio;
	private final int tipoRespuesta = GeneralEnum.RESPUESTA_POSITIVA.getValorInt();
	private JsonGenerico respuesta;

	public RespuestaPositiva(int pCodigoServicio, JsonGenerico pRespuesta) {
		super();
		this.codigoServicio = pCodigoServicio;
		this.respuesta = pRespuesta;
		
	}

	public int getCodigoServicio() {
		return codigoServicio;
	}

	public void setCodigoServicio(int pCodigoServicio) {
		this.codigoServicio = pCodigoServicio;
	}

	public int getTipoRespuesta() {
		return tipoRespuesta;
	}

	public JsonGenerico getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(JsonGenerico pRespuestaJson) {
		this.respuesta = pRespuestaJson;
	}

}
