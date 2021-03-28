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
@Table(name = "moneda")
@NamedQueries({ @NamedQuery(name="Moneda.findAll", query="SELECT mon FROM Moneda mon"),
				@NamedQuery(name="Moneda.findId", query="SELECT mon FROM Moneda mon WHERE mon.monedaId = :pId")})
public class Moneda implements Serializable {

private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "MONEDAID_GENERATOR", sequenceName = "SEC_MONEDA", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MONEDAID_GENERATOR")
	@Column(name = "moneda_id", unique = true, nullable = false, precision = 12)
	private Long monedaId;
	
	@Column(name ="moneda_nombre",length = 100)
	private String strNombre;
	
	@ManyToOne
	@JoinColumn(name = "moneda_territorio")
	private Territorio pais;

	public Long getMonedaId() {
		return monedaId;
	}

	public void setMonedaId(Long monedaId) {
		this.monedaId = monedaId;
	}

	public String getStrNombre() {
		return strNombre;
	}

	public void setStrNombre(String strNombre) {
		this.strNombre = strNombre;
	}

	public Territorio getPais() {
		return pais;
	}

	public void setPais(Territorio pais) {
		this.pais = pais;
	}
}
