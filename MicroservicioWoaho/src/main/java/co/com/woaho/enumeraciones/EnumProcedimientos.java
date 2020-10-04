package co.com.woaho.enumeraciones;

public enum EnumProcedimientos {
	
	FNDB_CONULTAR_PANTALLAS("woaho.fndb_consultar_pantallas"),
	FNDB_GENERAR_CODIGO_REGISTRO("woaho.fndb_generar_codigo_registro"),
	FNDB_VALIDAR_CODIGO_REGISTRO("woaho.fndb_validar_codigo_registro")
	
	
	
	;
	
	
	private final String strProcedimiento;

	private EnumProcedimientos(String strProcedimiento) {
		this.strProcedimiento = strProcedimiento;
	}


	public String getProcedimiento() {
		return strProcedimiento;
	}

}
