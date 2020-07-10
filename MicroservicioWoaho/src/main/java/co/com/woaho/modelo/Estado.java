package co.com.woaho.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "estado")
@NamedQueries({ @NamedQuery(name="Estado.findAll", query="SELECT e FROM Estado e")})
public class Estado implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "ESTADOID_GENERATOR", sequenceName = "SEC_ESTADO", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ESTADOID_GENERATOR")
	@Column(name = "estado_id", unique = true, nullable = false, precision = 12)
	private Long estadoId;
	
	@Column(name ="estado_codigo",length = 100)
	private String strCodigoEstado;

	public Long getEstadoId() {
		return estadoId;
	}

	public void setEstadoId(Long estadoId) {
		this.estadoId = estadoId;
	}

	public String getStrCodigoEstado() {
		return strCodigoEstado;
	}

	public void setStrCodigoEstado(String strCodigoEstado) {
		this.strCodigoEstado = strCodigoEstado;
	}
}
