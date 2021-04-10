package co.com.woaho.dto;

public class UsuarioDto {
	
	private String id;

	private String nombres;
	
	private String apellidos;
	
	private String celular;
	
	private String correo;
	
	private String clave;
	
	private String idSuscriptor;
	
	private String referrealCode;
	
	private String tipoUsuario;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getIdSuscriptor() {
		return idSuscriptor;
	}

	public void setIdSuscriptor(String idSuscriptor) {
		this.idSuscriptor = idSuscriptor;
	}

	public String getReferrealCode() {
		return referrealCode;
	}

	public void setReferrealCode(String referrealCode) {
		this.referrealCode = referrealCode;
	}

	public String getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}
}
