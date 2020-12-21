package co.com.woaho.request;

public class CrearMedioPagoUsuarioRequest {
	
	private String idMedioPagoUsuario;

	private String idUsuario;
	
	private String medioPago;
	
	private String nombre;
	
	private String fecha;
	
	private String cvc;
	
	private String codigo;
	
	private String estado;
	
	private String idioma;

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}	

	public String getMedioPago() {
		return medioPago;
	}

	public void setMedioPago(String medioPago) {
		this.medioPago = medioPago;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getCvc() {
		return cvc;
	}

	public void setCvc(String cvc) {
		this.cvc = cvc;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getIdMedioPagoUsuario() {
		return idMedioPagoUsuario;
	}

	public void setIdMedioPagoUsuario(String idMedioPagoUsuario) {
		this.idMedioPagoUsuario = idMedioPagoUsuario;
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}
}
