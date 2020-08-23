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
@Table(name = "etiqueta")
@NamedQueries({ @NamedQuery(name="Etiqueta.findAll", query="SELECT et FROM Etiqueta et"),
		 		@NamedQuery(name="Etiqueta.etiquetaCodigoIdioma", query="SELECT et FROM Etiqueta et WHERE et.idiomaEtiqueta = :pIdioma AND et.strCodigoEtiqueta = :pCodigo"),
		 		@NamedQuery(name="Etiqueta.etiquetaIdioma", query="SELECT et FROM Etiqueta et WHERE et.idiomaEtiqueta = :pIdioma")})
public class Etiqueta implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "ETIQUETAID_GENERATOR", sequenceName = "SEC_ETIQUETA", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ETIQUETAID_GENERATOR")
	@Column(name = "etiqueta_id", unique = true, nullable = false, precision = 12)
	private Long etiquetaId;	
	
	@Column(name ="etiqueta_valor",length = 100)
	private String strValorEtiqueta;
	
	@ManyToOne
	@JoinColumn(name = "etiqueta_idioma")
	private Idioma idiomaEtiqueta;
	
	@Column(name ="etiqueta_codigo",length = 100)
	private String strCodigoEtiqueta;

	public Long getEtiquetaId() {
		return etiquetaId;
	}

	public void setEtiquetaId(Long etiquetaId) {
		this.etiquetaId = etiquetaId;
	}

	public String getStrValorEtiqueta() {
		return strValorEtiqueta;
	}

	public void setStrValorEtiqueta(String strValorEtiqueta) {
		this.strValorEtiqueta = strValorEtiqueta;
	}

	public Idioma getIdiomaEtiqueta() {
		return idiomaEtiqueta;
	}

	public void setIdiomaEtiqueta(Idioma idiomaEtiqueta) {
		this.idiomaEtiqueta = idiomaEtiqueta;
	}

	public String getStrCodigoEtiqueta() {
		return strCodigoEtiqueta;
	}

	public void setStrCodigoEtiqueta(String strCodigoEtiqueta) {
		this.strCodigoEtiqueta = strCodigoEtiqueta;
	}
}
