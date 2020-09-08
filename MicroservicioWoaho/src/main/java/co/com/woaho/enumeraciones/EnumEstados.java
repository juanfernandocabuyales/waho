package co.com.woaho.enumeraciones;

public enum EnumEstados {

	ESTADO_ACTIVO("1","ACTIVO"),
	ESTADO_INACTIVO("2","INACTIVO"),
	ESTADO_PENDIENTE("3","PENDIENTE"),
	ESTADO_REGISTRADO("4","REGISTRADO"),
	ESTADO_FINALIZADO("5","FINALIZADO"),
	ESTADO_CANCELADO("6","CANCELADO")
	
	;
	
	private final String strIdEstado;
	
	private final String strEstado;
	
	private EnumEstados (String pStrId,String pStrEstado ) {
		this.strIdEstado = pStrId;
		this.strEstado = pStrEstado;
	}

	public String getStrIdEstado() {
		return strIdEstado;
	}

	public String getStrEstado() {
		return strEstado;
	}
	
	public long getIdEstado() {
		return Long.parseLong(this.strIdEstado);
	}
}
