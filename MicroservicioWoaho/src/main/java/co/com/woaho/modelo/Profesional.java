package co.com.woaho.modelo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "profesional")
@NamedQueries({ @NamedQuery(name="Profesional.findAll", query="SELECT pr FROM Profesional pr"),
				@NamedQuery(name="Profesional.findServicio", query="SELECT pr FROM Profesional pr WHERE pr.strServicios LIKE CONCAT('%',:pServicios,'%')")})
public class Profesional implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "PROFESIONALID_GENERATOR", sequenceName = "SEC_PROFESIONAL", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROFESIONALID_GENERATOR")
	@Column(name = "profesional_id", unique = true, nullable = false, precision = 12)
	private Long profesionalId;
	
	@Column(name ="profesional_nombre",length = 100)
	private String strNombre;
	
	@Column(name ="profesional_apellido",length = 100)
	private String strApellido;
	
	@Column(name ="profesional_profesiones",length = 100)
	private String strProfesiones;
	
	@ManyToOne
	@JoinColumn(name = "profesional_nacionalidad")
	private Territorio nacionalidad;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "profesional")
	private List<Ubicacion> ubicacion;
	
	@Column(name ="profesional_servicios",length = 100)
	private String strServicios;
	
	@Column(name ="profesional_lenguajes",length = 100)
	private String strLenguajes;
	
	@Column(name ="profesional_descripcion",length = 100)
	private String strDescripcion;
	
	@ManyToOne
	@JoinColumn(name ="profesional_imagen_icono")
	private Imagen icono;
	
	@Column(name = "profesional_cant_estrellas", precision = 12)
	private double cantEstrellas;
	
	@Column(name = "profesional_cant_servicios", precision = 12)
	private Long cantServicios;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "profesional")
	private List<Calificacion> calificaciones;
	
	@Column(name = "profesional_distancia", precision = 12)
	private double distanciaUsuario;

	public Long getProfesionalId() {
		return profesionalId;
	}

	public void setProfesionalId(Long profesionalId) {
		this.profesionalId = profesionalId;
	}

	public String getStrNombre() {
		return strNombre;
	}

	public void setStrNombre(String strNombre) {
		this.strNombre = strNombre;
	}

	public String getStrApellido() {
		return strApellido;
	}

	public void setStrApellido(String strApellido) {
		this.strApellido = strApellido;
	}

	public String getStrProfesiones() {
		return strProfesiones;
	}

	public void setStrProfesiones(String strProfesiones) {
		this.strProfesiones = strProfesiones;
	}

	public Territorio getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(Territorio nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public String getStrServicios() {
		return strServicios;
	}

	public void setStrServicios(String strServicios) {
		this.strServicios = strServicios;
	}

	public String getStrLenguajes() {
		return strLenguajes;
	}

	public void setStrLenguajes(String strLenguajes) {
		this.strLenguajes = strLenguajes;
	}

	public String getStrDescripcion() {
		return strDescripcion;
	}

	public void setStrDescripcion(String strDescripcion) {
		this.strDescripcion = strDescripcion;
	}

	public Imagen getIcono() {
		return icono;
	}

	public void setIcono(Imagen icono) {
		this.icono = icono;
	}

	public double getCantEstrellas() {
		return cantEstrellas;
	}

	public void setCantEstrellas(double cantEstrellas) {
		this.cantEstrellas = cantEstrellas;
	}

	public Long getCantServicios() {
		return cantServicios;
	}

	public void setCantServicios(Long cantServicios) {
		this.cantServicios = cantServicios;
	}

	public List<Calificacion> getCalificaciones() {
		return calificaciones;
	}

	public void setCalificaciones(List<Calificacion> calificaciones) {
		this.calificaciones = calificaciones;
	}

	public List<Ubicacion> getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(List<Ubicacion> ubicacion) {
		this.ubicacion = ubicacion;
	}
	
	public String getNombreCompleto() {
		return this.strNombre + " " + this.strApellido;
	}

	public double getDistanciaUsuario() {
		return distanciaUsuario;
	}

	public void setDistanciaUsuario(double distanciaUsuario) {
		this.distanciaUsuario = distanciaUsuario;
	}
}
