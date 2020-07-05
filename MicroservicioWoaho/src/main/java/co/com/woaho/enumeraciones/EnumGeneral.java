package co.com.woaho.enumeraciones;

public enum EnumGeneral {
	
	RESPUESTA_NEGATIVA("1"),
	RESPUESTA_POSITIVA("0"),
	
	
	SERVICIO_CONSULTA_PANTALLAS("1"),
	SERVICIO_CONSULTAR_PAISES("2"),
	SERVICIO_CREAR_USUARIO("3"),
	SERVICIO_ACTUALIZAR_USUARIO("4"),
	
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
