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
@Table(name = "idioma")
@NamedQueries({ @NamedQuery(name="Idioma.findAll", query="SELECT id FROM Idioma id"),
				@NamedQuery(name="Idioma.findId", query="SELECT id FROM Idioma id WHERE id.idiomaId IN ( :pId )")})
public class Idioma implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "IDIOMAID_GENERATOR", sequenceName = "SEC_IDIOMA", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IDIOMAID_GENERATOR")
	@Column(name = "idioma_id", unique = true, nullable = false, precision = 12)
	private Long idiomaId;
	
	@Column(name ="idioma_nombre",length = 100)
	private String strNombre;
	
	@Column(name ="idioma_codigo",length = 100)
	private String strCodigo;

	public Long getIdiomaId() {
		return idiomaId;
	}

	public void setIdiomaId(Long idiomaId) {
		this.idiomaId = idiomaId;
	}

	public String getStrNombre() {
		return strNombre;
	}

	public void setStrNombre(String strNombre) {
		this.strNombre = strNombre;
	}

	public String getStrCodigo() {
		return strCodigo;
	}

	public void setStrCodigo(String strCodigo) {
		this.strCodigo = strCodigo;
	}
}
