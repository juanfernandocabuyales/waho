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
@Table(name = "imagen")
@NamedQueries({ @NamedQuery(name="Imagen.findAll", query="SELECT im FROM Imagen im")})
public class Imagen implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "IMAGENID_GENERATOR", sequenceName = "SEC_IMAGEN", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IMAGENID_GENERATOR")
	@Column(name = "imagen_id", unique = true, nullable = false, precision = 12)
	private Long imagenId;
	
	@Column(name ="imagen_nombre",length = 100)
	private String strNombre;
	
	@Column(name ="imagen_ruta",length = 100)
	private String strRuta;
	
	@Column(name ="imagen_alto",length = 100)
	private String strAlto;
	
	@Column(name ="imagen_ancho",length = 100)
	private String strAncho;

	public Long getImagenId() {
		return imagenId;
	}

	public void setImagenId(Long imagenId) {
		this.imagenId = imagenId;
	}

	public String getStrNombre() {
		return strNombre;
	}

	public void setStrNombre(String strNombre) {
		this.strNombre = strNombre;
	}

	public String getStrRuta() {
		return strRuta;
	}

	public void setStrRuta(String strRuta) {
		this.strRuta = strRuta;
	}

	public String getStrAlto() {
		return strAlto;
	}

	public void setStrAlto(String strAlto) {
		this.strAlto = strAlto;
	}

	public String getStrAncho() {
		return strAncho;
	}

	public void setStrAncho(String strAncho) {
		this.strAncho = strAncho;
	}
}
