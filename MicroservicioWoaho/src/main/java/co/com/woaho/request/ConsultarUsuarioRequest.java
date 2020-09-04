package co.com.woaho.request;

public class ConsultarUsuarioRequest {

	private String numeroCelular;
	
	private String correoPersona;

	public String getNumeroCelular() {
		return numeroCelular;
	}

	public void setNumeroCelular(String numeroCelular) {
		this.numeroCelular = numeroCelular;
	}

	public String getCorreoPersona() {
		return correoPersona;
	}

	public void setCorreoPersona(String correoPersona) {
		this.correoPersona = correoPersona;
	}	
}
