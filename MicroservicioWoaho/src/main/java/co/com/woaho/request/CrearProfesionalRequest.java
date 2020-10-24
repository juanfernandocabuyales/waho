package co.com.woaho.request;

public class CrearProfesionalRequest {

	private String nombre;
	
	private String apellido;
	
	private String nacionalidad;
	
	private String servicios;
	
	private String lenguaje;
	
	private String descripcion;
	
	private String idIcono;
	
	private String profesiones;
	
	private String idioma;
	
	private Ubicacion ubicacion;
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getNacionalidad() {
		return nacionalidad;
	}
	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}
	public String getServicios() {
		return servicios;
	}
	public void setServicios(String servicios) {
		this.servicios = servicios;
	}
	public String getLenguaje() {
		return lenguaje;
	}
	public void setLenguaje(String lenguaje) {
		this.lenguaje = lenguaje;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getIdIcono() {
		return idIcono;
	}
	public void setIdIcono(String idIcono) {
		this.idIcono = idIcono;
	}
	public String getProfesiones() {
		return profesiones;
	}
	public void setProfesiones(String profesiones) {
		this.profesiones = profesiones;
	}
	
	public Ubicacion getUbicacion() {
		return ubicacion;
	}
	public void setUbicacion(Ubicacion ubicacion) {
		this.ubicacion = ubicacion;
	}

	public String getIdioma() {
		return idioma;
	}
	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	public static class Ubicacion{
		
		private String latitud;
		
		private String longitud;
		
		private String idLugar;

		public String getLatitud() {
			return latitud;
		}

		public void setLatitud(String latitud) {
			this.latitud = latitud;
		}

		public String getLongitud() {
			return longitud;
		}

		public void setLongitud(String longitud) {
			this.longitud = longitud;
		}

		public String getIdLugar() {
			return idLugar;
		}

		public void setIdLugar(String idLugar) {
			this.idLugar = idLugar;
		}
	}
}
