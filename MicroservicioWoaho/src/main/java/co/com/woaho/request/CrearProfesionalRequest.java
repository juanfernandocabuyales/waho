package co.com.woaho.request;

public class CrearProfesionalRequest {

	private String nombre;
	private String apellido;
	private String nacionalidad;
	private String servicios;
	private String lenguaje;
	private String descripcion;
	private String idIcono;
	private int cantEstrellas;
	private int cantServicios;
	private String comentarios;
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
	public int getCantEstrellas() {
		return cantEstrellas;
	}
	public void setCantEstrellas(int cantEstrellas) {
		this.cantEstrellas = cantEstrellas;
	}
	public int getCantServicios() {
		return cantServicios;
	}
	public void setCantServicios(int cantServicios) {
		this.cantServicios = cantServicios;
	}
	public String getComentarios() {
		return comentarios;
	}
	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}
}
