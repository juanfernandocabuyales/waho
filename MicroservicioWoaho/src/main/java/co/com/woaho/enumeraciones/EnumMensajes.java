package co.com.woaho.enumeraciones;

import java.text.MessageFormat;

public enum EnumMensajes {

	NO_MENSAJES_PANTALLA("No se han encontrado mensajes para la pantalla"),
	NO_PAISES("No se han encontrado paises para la busqueda"),
	INCONVENIENTE_EN_OPERACION("Se ha presentado un inconveniente realizado la operación, favor intentar nuevamente"),
	REGISTRO_EXITOSO("Se ha realizado el registro {0}"),
	NO_USUARIO("No se ha encontrado un usuario para el {0} {1}"),
	NO_SOLICITUD("No se ha podido procesar la solicitud "),
	CLAVE_INVALIDA("La clave ingresada no corresponde al usuario. Intente nuevamente.")
	;
	
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
