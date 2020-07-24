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
@Table(name = "profesion")
@NamedQueries({ @NamedQuery(name="Profesion.findAll", query="SELECT pr FROM Profesion pr"),
				@NamedQuery(name="Profesion.findId", query="SELECT pr FROM Profesion pr WHERE pr.profesionId IN ( :pId )")})
public class Profesion implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "PROFESIONID_GENERATOR", sequenceName = "SEC_PROFESION", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROFESIONID_GENERATOR")
	@Column(name = "profesion_id", unique = true, nullable = false, precision = 12)
	private Long profesionId;
	
	@Column(name ="profesion_nombre",length = 100)
	private String strNombre;

	public Long getProfesionId() {
		return profesionId;
	}

	public void setProfesionId(Long profesionId) {
		this.profesionId = profesionId;
	}

	public String getStrNombre() {
		return strNombre;
	}

	public void setStrNombre(String strNombre) {
		this.strNombre = strNombre;
	}
}
