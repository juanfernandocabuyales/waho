package co.com.woaho.modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "territorio")
@NamedQueries({ @NamedQuery(name="Territorio.findAll", query="SELECT t FROM Territorio t"),
				@NamedQuery(name="Territorio.findId", query="SELECT t FROM Territorio t WHERE t.idTerritorio = :pId"),
				@NamedQuery(name="Territorio.buscarTipo", query="SELECT t FROM Territorio t WHERE t.tipoTerritorio.idTipoTerritorio = :pTipoTerritorio")})
public class Territorio implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "territorio_id", unique = true, nullable = false)
	private Long idTerritorio;

	@Column(name ="territorio_nombre",length = 100)
	private String strNombreTerritorio;

	@ManyToOne
	@JoinColumn(name = "territorio_padre")
	private Territorio territorioPadre;

	@ManyToOne
	@JoinColumn(name = "territorio_tipo")
	private TipoTerritorio tipoTerritorio;

	@Column(name ="territorio_codigo",length = 100)
	private String strCodigoTerritorio;
	
	@ManyToOne(optional = true)
	@JoinColumn(name = "territorio_imagen")
	private Imagen territorioImagen;

	public Long getIdTerritorio() {
		return idTerritorio;
	}

	public void setIdTerritorio(Long idTerritorio) {
		this.idTerritorio = idTerritorio;
	}

	public String getStrNombreTerritorio() {
		return strNombreTerritorio;
	}

	public void setStrNombreTerritorio(String strNombreTerritorio) {
		this.strNombreTerritorio = strNombreTerritorio;
	}

	public Territorio getTerritorioPadre() {
		return territorioPadre;
	}

	public void setTerritorioPadre(Territorio territorioPadre) {
		this.territorioPadre = territorioPadre;
	}

	public TipoTerritorio getTipoTerritorio() {
		return tipoTerritorio;
	}

	public void setTipoTerritorio(TipoTerritorio tipoTerritorio) {
		this.tipoTerritorio = tipoTerritorio;
	}

	public String getStrCodigoTerritorio() {
		return strCodigoTerritorio;
	}

	public void setStrCodigoTerritorio(String strCodigoTerritorio) {
		this.strCodigoTerritorio = strCodigoTerritorio;
	}

	public Imagen getTerritorioImagen() {
		return territorioImagen;
	}

	public void setTerritorioImagen(Imagen territorioImagen) {
		this.territorioImagen = territorioImagen;
	}
}
