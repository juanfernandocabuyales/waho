package co.com.respuestas;

import co.com.woaho.enumeraciones.EnumGeneral;

/**
* ****************************************************************.
* @autor: Juan Cabuyales
* @fecha: 30/06/2020
* @descripcion: Clase que permite definir una respuesta positiva en cadena
* ****************************************************************
*/	
public class RespuestaPositivaCadena {

	private int codigoServicio;
	private final int tipoRespuesta = EnumGeneral.RESPUESTA_POSITIVA.getValorInt();
	private String respuesta;	
	
	public RespuestaPositivaCadena(int codigoServicio, String respuesta) {
		super();
		this.codigoServicio = codigoServicio;
		this.respuesta = respuesta;
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

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String pError) {
		this.respuesta = pError;
	}

}
