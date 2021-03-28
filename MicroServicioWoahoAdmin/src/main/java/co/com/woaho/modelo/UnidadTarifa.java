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
@Table(name = "unidad_tarifa")
@NamedQueries({ @NamedQuery(name="UnidadTarifa.findAll", query="SELECT ut FROM UnidadTarifa ut"),
				@NamedQuery(name="UnidadTarifa.findId", query="SELECT ut FROM UnidadTarifa ut WHERE ut.unidadTarifaId = :pId")})
public class UnidadTarifa implements Serializable {

private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "UNIDADTARIFAID_GENERATOR", sequenceName = "SEC_ESTADO", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UNIDADTARIFAID_GENERATOR")
	@Column(name = "unidad_tarifa_id", unique = true, nullable = false, precision = 12)
	private Long unidadTarifaId;
	
	@Column(name ="unidad_tarifa_nombre",length = 100)
	private String strNombre;

	public Long getUnidadTarifaId() {
		return unidadTarifaId;
	}

	public void setUnidadTarifaId(Long unidadTarifaId) {
		this.unidadTarifaId = unidadTarifaId;
	}

	public String getStrNombre() {
		return strNombre;
	}

	public void setStrNombre(String strNombre) {
		this.strNombre = strNombre;
	}
}
