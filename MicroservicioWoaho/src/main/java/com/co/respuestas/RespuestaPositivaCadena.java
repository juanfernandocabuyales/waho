package com.co.respuestas;

import com.co.woaho.enumeraciones.GeneralEnum;

/**
* ****************************************************************.
* @autor: Juan Cabuyales
* @fecha: 30/06/2020
* @descripcion: Clase que permite definir una respuesta positiva en cadena
* ****************************************************************
*/	
public class RespuestaPositivaCadena {

	private int codigoServicio;
	private final int tipoRespuesta = GeneralEnum.RESPUESTA_POSITIVA.getValorInt();
	private String respuesta;
	
	
	public int getCodigoServicio() {
		return codigoServicio;
	}

	public void setCodigoServicio(int pCodigoServicio) {
		this.codigoServicio = pCodigoServicio;
	}

	public int getTipoRespuesta() {
		return tipoRespuesta;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String pError) {
		this.respuesta = pError;
	}

}
