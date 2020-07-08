package co.com.woaho.enumeraciones;

public enum EnumProcedimientos {
	
	FNDB_CONULTAR_PANTALLA("woaho.consultar_mensajes_pantalla"),
	FNDB_GENERAR_CODIGO_REGISTRO("woaho.fndb_generar_codigo_registro");
	
	
	private final String strProcedimiento;

	private EnumProcedimientos(String strProcedimiento) {
		this.strProcedimiento = strProcedimiento;
	}


	public String getProcedimiento() {
		return strProcedimiento;
	}

}
