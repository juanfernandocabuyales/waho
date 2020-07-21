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
@Table(name = "calificacion")
@NamedQueries({ @NamedQuery(name="Calificacion.findAll", query="SELECT cal FROM Calificacion cal")})
public class Calificacion implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "CALIFICACIONID_GENERATOR", sequenceName = "SEC_ESTADO", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CALIFICACIONID_GENERATOR")
	@Column(name = "calificacion_id", unique = true, nullable = false, precision = 12)
	private Long calificacionId;
	
	@ManyToOne
	@JoinColumn(name = "calificacion_usuario")
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name = "calificacion_profesional")
	private Profesional profesional;
	
	@Column(name ="calificacion_descripcion",length = 100)
	private String strDescripcion;
	
	@Column(name = "calificacion_calificacion", precision = 12)
	private Long calificacion;

	public Long getCalificacionId() {
		return calificacionId;
	}

	public void setCalificacionId(Long calificacionId) {
		this.calificacionId = calificacionId;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Profesional getProfesional() {
		return profesional;
	}

	public void setProfesional(Profesional profesional) {
		this.profesional = profesional;
	}

	public String getStrDescripcion() {
		return strDescripcion;
	}

	public void setStrDescripcion(String strDescripcion) {
		this.strDescripcion = strDescripcion;
	}

	public Long getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(Long calificacion) {
		this.calificacion = calificacion;
	}
}
