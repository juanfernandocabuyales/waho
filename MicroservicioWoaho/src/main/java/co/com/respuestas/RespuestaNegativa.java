package co.com.respuestas;

import co.com.woaho.enumeraciones.EnumGeneral;

/**
* ****************************************************************.
* @autor: Juan Cabuyales
* @fecha: 30/06/2020
* @descripcion: Clase para definir una respuesta negativa.
* ****************************************************************
*/	
public class RespuestaNegativa {

	private int codigoServicio;
	private final int tipoRespuesta = EnumGeneral.RESPUESTA_NEGATIVA.getValorInt();
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
