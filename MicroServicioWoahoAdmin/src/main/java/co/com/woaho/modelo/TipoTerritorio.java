package co.com.woaho.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name = "tipo_territorio")
@NamedQueries({ @NamedQuery(name="TipoTerritorio.findAll", query="SELECT tp FROM TipoTerritorio tp"),
				@NamedQuery(name="TipoTerritorio.findId", query="SELECT tp FROM TipoTerritorio tp WHERE tp.idTipoTerritorio = :pId")})
public class TipoTerritorio implements Serializable {
	
private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "tipo_territorio_id", unique = true, nullable = false)
	private Long idTipoTerritorio;
	
	@Column(name ="tipo_territorio_nombre",length = 100)
	private String strNombreTipo;

	public Long getIdTipoTerritorio() {
		return idTipoTerritorio;
	}

	public void setIdTipoTerritorio(Long idTipoTerritorio) {
		this.idTipoTerritorio = idTipoTerritorio;
	}

	public String getStrNombreTipo() {
		return strNombreTipo;
	}

	public void setStrNombreTipo(String strNombreTipo) {
		this.strNombreTipo = strNombreTipo;
	}

}
