package co.com.woaho.enumeraciones;

import java.text.MessageFormat;

public enum EnumMensajes {

	NO_MENSAJES_PANTALLA("No se han encontrado mensajes para la pantalla"),
	NO_PAISES("No se han encontrado paises para la busqueda");
	
	private final String strValor;
	
	private EnumMensajes (String pStrValor) {
		this.strValor = pStrValor;
	}
	
	public String getMensaje() {
		return strValor;
	}
	
	public String getMensaje(Object ... pStrDatos) {
		return MessageFormat.format(strValor, pStrDatos);
	}
}
