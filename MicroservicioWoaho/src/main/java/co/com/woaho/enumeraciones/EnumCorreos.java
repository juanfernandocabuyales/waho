package co.com.woaho.enumeraciones;

public enum EnumCorreos {

	CORREO_CODIGO_ACCESO(1L);
	
	private Long codigo;
	
	private EnumCorreos (Long codigo) {
		this.codigo = codigo;
	}

	public Long getCodigo() {
		return codigo;
	}
}
