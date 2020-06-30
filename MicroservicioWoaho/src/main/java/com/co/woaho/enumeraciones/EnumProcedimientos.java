package com.co.woaho.enumeraciones;

public enum EnumProcedimientos {
	
	FNDB_CONULTAR_PANTALLA("woaho.consultar_mensajes_pantalla");
	
	
	private final String strProcedimiento;

	private EnumProcedimientos(String strProcedimiento) {
		this.strProcedimiento = strProcedimiento;
	}


	public String getProcedimiento() {
		return strProcedimiento;
	}

}
