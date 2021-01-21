package co.com.woaho.enumeraciones;

public enum EnumUsuarios {
	
	USUARIO_ADMIN(1L),
	USUARIO_NORMAL(2L);

	private final Long tipoUsuario;
	
	private EnumUsuarios(Long pValor) {
		this.tipoUsuario = pValor;
	}
	
	public Long getValor() {
		return tipoUsuario;
	}
}
