package com.co.woaho.enumeraciones;

public enum EnumGeneral {
	
	RESPUESTA_NEGATIVA("1"),
	RESPUESTA_POSITIVA("0"),
	
	
	SERVICIO_CONSULTA_PANTALLAS("1")
	
	;
	
	private final String strValor;
	
	private EnumGeneral (String pStrValor) {
		this.strValor = pStrValor;
	}
	
	public String getValor() {
		return strValor;
	}
	
	public int getValorInt() {
		return Integer.parseInt(strValor);
	}

}
