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
@Table(name = "equivalencia_idioma")
@NamedQueries({ @NamedQuery(name="EquivalenciaIdioma.findAll", query="SELECT ei FROM EquivalenciaIdioma ei"),
	            @NamedQuery(name="EquivalenciaIdioma.findEquivalencia", query="SELECT ei FROM EquivalenciaIdioma ei WHERE LOWER(ei.equivalenciaIdiomaOriginal) LIKE :pTexto")})
public class EquivalenciaIdioma implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "EQUI_IDIOMA_ID_GENERATOR", sequenceName = "SEC_EQUIVALENCIA_IDIOMA", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EQUI_IDIOMA_ID_GENERATOR")
	@Column(name = "equivalencia_idioma_id", unique = true, nullable = false, precision = 12)
	private Long equivalenciaIdiomaId;
	
	@Column(name ="equivalencia_idioma_original",length = 1000)
	private String equivalenciaIdiomaOriginal;
	
	@Column(name ="equivalencia_idioma_ingles",length = 1000)
	private String equivalenciaIdiomaIngles;

	public Long getEquivalenciaIdiomaId() {
		return equivalenciaIdiomaId;
	}

	public void setEquivalenciaIdiomaId(Long equivalenciaIdiomaId) {
		this.equivalenciaIdiomaId = equivalenciaIdiomaId;
	}

	public String getEquivalenciaIdiomaOriginal() {
		return equivalenciaIdiomaOriginal;
	}

	public void setEquivalenciaIdiomaOriginal(String equivalenciaIdiomaOriginal) {
		this.equivalenciaIdiomaOriginal = equivalenciaIdiomaOriginal;
	}

	public String getEquivalenciaIdiomaIngles() {
		return equivalenciaIdiomaIngles;
	}

	public void setEquivalenciaIdiomaIngles(String equivalenciaIdiomaIngles) {
		this.equivalenciaIdiomaIngles = equivalenciaIdiomaIngles;
	}
}
