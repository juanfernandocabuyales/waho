package co.com.woaho.enumeraciones;

import java.text.MessageFormat;

public enum EnumMensajes {

	NO_MENSAJES_PANTALLA("No se han encontrado mensajes para la pantalla"),
	NO_PAISES("No se han encontrado paises para la busqueda"),
	NO_PROFESIONALES("No se han encontrado profesionales para la busqueda"),
	NO_SERVICIOS("No se han encontrado servicios disponibles"),
	NO_CATEGORIAS("No se han encontrado categorias disponibles"),
	INCONVENIENTE_EN_OPERACION("Se ha presentado un inconveniente realizado la operación, favor intentar nuevamente"),
	INCONVENIENTE_EN_NOTIFICACION("Se ha presentado un inconveniente al solicitar la notificacion"),
	REGISTRO_EXITOSO("Se ha realizado el registro {0}"),
	NO_USUARIO("No se ha encontrado un usuario para el {0} {1}"),
	NO_SOLICITUD("No se ha podido procesar la solicitud "),
	CLAVE_INVALIDA("La clave ingresada no corresponde al usuario. Intente nuevamente."),
	NO_DIRECCIONES("No se han encontrado direcciones registradas para el usuario"),
	NO_PEDIDOS("Listado de pedidos vacio y/o invalido"),
	NO_PEDIDOS_PARA("No se han encontrado pedidos para el {0} ."),
	NO_PEDIDO("No se ha encontrado un pedido para cancelar")
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
