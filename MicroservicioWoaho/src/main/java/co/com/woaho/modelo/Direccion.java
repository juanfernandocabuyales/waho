package co.com.woaho.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "direccion")
@NamedQueries({ @NamedQuery(name="Direccion.findAll", query="SELECT d FROM Direccion d"),
		 @NamedQuery(name="Direccion.buscarUsario", query="SELECT d FROM Direccion d where d.usuarioDireccion.usuarioId = :pIdUsuario")})
public class Direccion implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "DIRECCIONID_GENERATOR", sequenceName = "SEC_DIRECCION", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIRECCIONID_GENERATOR")
	@Column(name = "direccion_id", unique = true, nullable = false, precision = 12)
	private Long direccionId;
	
	@Column(name ="direccion_nombre",length = 100)
	private String strNombreDireccion;
	
	@Column(name ="direccion_descripcion",length = 100)
	private String strDireccion;	

	@ManyToOne
	@JoinColumn(name = "direccion_territorio_id")
	private Territorio territorioDireccion;
	
	@Column(name ="direccion_edificacion",length = 100)
	private String strEdificacion;
	
	@ManyToOne
	@JoinColumn(name = "direccion_estado")
	private Estado estadoDireccion;
	
	@ManyToOne
	@JoinColumn(name = "direccion_usuario")
	private Usuario usuarioDireccion;
	
	@Column(name ="direccion_latitud",length = 100)
	private String strDireccionLatitud;
	
	@Column(name ="direccion_longitud",length = 100)
	private String strDireccionLongitud;
	
	@Column(name ="direccion_lugar_id",length = 100)
	private String strLugarId;	

	public Long getDireccionId() {
		return direccionId;
	}

	public void setDireccionId(Long direccionId) {
		this.direccionId = direccionId;
	}

	public String getStrNombreDireccion() {
		return strNombreDireccion;
	}

	public void setStrNombreDireccion(String strNombreDireccion) {
		this.strNombreDireccion = strNombreDireccion;
	}

	public String getStrDireccion() {
		return strDireccion;
	}

	public void setStrDireccion(String strDireccion) {
		this.strDireccion = strDireccion;
	}

	public Territorio getTerritorioDireccion() {
		return territorioDireccion;
	}

	public void setTerritorioDireccion(Territorio territorioDireccion) {
		this.territorioDireccion = territorioDireccion;
	}

	public String getStrEdificacion() {
		return strEdificacion;
	}

	public void setStrEdificacion(String strEdificacion) {
		this.strEdificacion = strEdificacion;
	}

	public Estado getEstadoDireccion() {
		return estadoDireccion;
	}

	public void setEstadoDireccion(Estado estadoDireccion) {
		this.estadoDireccion = estadoDireccion;
	}

	public Usuario getUsuarioDireccion() {
		return usuarioDireccion;
	}

	public void setUsuarioDireccion(Usuario usuarioDireccion) {
		this.usuarioDireccion = usuarioDireccion;
	}

	public String getStrDireccionLatitud() {
		return strDireccionLatitud;
	}

	public void setStrDireccionLatitud(String strDireccionLatitud) {
		this.strDireccionLatitud = strDireccionLatitud;
	}

	public String getStrDireccionLongitud() {
		return strDireccionLongitud;
	}

	public void setStrDireccionLongitud(String strDireccionLongitud) {
		this.strDireccionLongitud = strDireccionLongitud;
	}

	public String getStrLugarId() {
		return strLugarId;
	}

	public void setStrLugarId(String strLugarId) {
		this.strLugarId = strLugarId;
	}	
}
