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
@Table(name = "parametro")
@NamedQueries({ @NamedQuery(name="Parametro.findAll", query="SELECT pa FROM Parametro pa")})
public class Parametro implements Serializable {

private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "PARAMETROID_GENERATOR", sequenceName = "SEC_PARAMETRO", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PARAMETROID_GENERATOR")
	@Column(name = "parametro_id", unique = true, nullable = false, precision = 12)
	private Long parametroId;
	
	@Column(name ="parametro_nombre",length = 4000)
	private String parametroNombre;
	
	@Column(name ="parametro_valor",length = 4000)
	private String parametroValor;
	
	@Column(name ="parametro_descripcion",length = 4000)
	private String parametroDescripcion;

	public Long getParametroId() {
		return parametroId;
	}

	public void setParametroId(Long parametroId) {
		this.parametroId = parametroId;
	}

	public String getParametroNombre() {
		return parametroNombre;
	}

	public void setParametroNombre(String parametroNombre) {
		this.parametroNombre = parametroNombre;
	}

	public String getParametroValor() {
		return parametroValor;
	}

	public void setParametroValor(String parametroValor) {
		this.parametroValor = parametroValor;
	}

	public String getParametroDescripcion() {
		return parametroDescripcion;
	}

	public void setParametroDescripcion(String parametroDescripcion) {
		this.parametroDescripcion = parametroDescripcion;
	}
}
