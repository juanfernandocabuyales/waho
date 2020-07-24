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
@Table(name = "ubicacion")
@NamedQueries({ @NamedQuery(name="Ubicacion.findAll", query="SELECT ub FROM Ubicacion ub"),
				@NamedQuery(name="Ubicacion.findProfesional", query="SELECT ub FROM Ubicacion ub WHERE ub.profesional = :pProfesional")})
public class Ubicacion implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "ESTADOID_GENERATOR", sequenceName = "SEC_UBICACION", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ESTADOID_GENERATOR")
	@Column(name = "ubicacion_id", unique = true, nullable = false, precision = 12)
	private Long ubicacionId;
	
	@Column(name ="ubicacion_latitud",length = 100)
	private String strLatitud;
	
	@Column(name ="ubicacion_longitud",length = 100)
	private String strLongitud;
	
	@Column(name ="ubicacion_lugar_id",length = 100)
	private String strLugarId;
	
	@ManyToOne
	@JoinColumn(name = "ubicacion_profesional")
	private Profesional profesional;

	public Long getUbicacionId() {
		return ubicacionId;
	}

	public void setUbicacionId(Long ubicacionId) {
		this.ubicacionId = ubicacionId;
	}

	public String getStrLatitud() {
		return strLatitud;
	}

	public void setStrLatitud(String strLatitud) {
		this.strLatitud = strLatitud;
	}

	public String getStrLongitud() {
		return strLongitud;
	}

	public void setStrLongitud(String strLongitud) {
		this.strLongitud = strLongitud;
	}

	public Profesional getProfesional() {
		return profesional;
	}

	public void setProfesional(Profesional profesional) {
		this.profesional = profesional;
	}

	public String getStrLugarId() {
		return strLugarId;
	}

	public void setStrLugarId(String strLugarId) {
		this.strLugarId = strLugarId;
	}
}
