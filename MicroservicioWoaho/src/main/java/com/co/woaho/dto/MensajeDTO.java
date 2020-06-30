package com.co.woaho.dto;

public class MensajeDTO {

	private String strMensaje;
	
	private String strTipo;	

	public MensajeDTO(String strMensaje, String strTipo) {
		super();
		this.strMensaje = strMensaje;
		this.strTipo = strTipo;
	}

	public String getStrMensaje() {
		return strMensaje;
	}

	public void setStrMensaje(String strMensaje) {
		this.strMensaje = strMensaje;
	}

	public String getStrTipo() {
		return strTipo;
	}

	public void setStrTipo(String strTipo) {
		this.strTipo = strTipo;
	}
	
}
