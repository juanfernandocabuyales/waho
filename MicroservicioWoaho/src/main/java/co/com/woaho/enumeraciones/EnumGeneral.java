package co.com.woaho.enumeraciones;

public enum EnumGeneral {
	
	RESPUESTA_NEGATIVA("1"),
	RESPUESTA_POSITIVA("0"),
	OK("ok"),
	
	
	SERVICIO_CONSULTA_PANTALLAS("1"),
	SERVICIO_CONSULTAR_PAISES("2"),
	SERVICIO_CREAR_USUARIO("3"),
	SERVICIO_ACTUALIZAR_USUARIO("4"),
	SERVICIO_CONSULTAR_USUARIO("5"),
	SERVICIO_GENERAR_CODIGO_REGISTRO("6"),
	SERVICIO_VALIDAR_LOGIN("7"),
	
	LLAVE_CIFRADO("W0ah0;")
	
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
