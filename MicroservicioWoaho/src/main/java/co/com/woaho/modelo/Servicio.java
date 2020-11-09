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
@Table(name = "servicio")
@NamedQueries({ @NamedQuery(name="Servicio.findAll", query="SELECT se FROM Servicio se"),
				@NamedQuery(name="Servicio.servicioCategoria", query="SELECT se FROM Servicio se WHERE se.categoria.categoriaId = :pIdCategoria"),
				@NamedQuery(name="Servicio.findId", query="SELECT se FROM Servicio se WHERE se.servicioId IN ( :pId )")})
public class Servicio implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "SERVICIOID_GENERATOR", sequenceName = "SEC_SERVICIO", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SERVICIOID_GENERATOR")
	@Column(name = "servicio_id", unique = true, nullable = false, precision = 12)
	private Long servicioId;
	
	@Column(name ="servicio_nombre",length = 100)
	private String strNombre;
	
	@ManyToOne
	@JoinColumn(name = "servicio_imagen")
	private Imagen imagen;
	
	@ManyToOne
	@JoinColumn(name = "servicio_categoria")
	private Categoria categoria;
	
	@Column(name ="servicio_descripcion",length = 100)
	private String strDescripcion;

	public Long getServicioId() {
		return servicioId;
	}

	public void setServicioId(Long servicioId) {
		this.servicioId = servicioId;
	}

	public String getStrNombre() {
		return strNombre;
	}

	public void setStrNombre(String strNombre) {
		this.strNombre = strNombre;
	}

	public Imagen getImagen() {
		return imagen;
	}

	public void setImagen(Imagen imagen) {
		this.imagen = imagen;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public String getStrDescripcion() {
		return strDescripcion;
	}

	public void setStrDescripcion(String strDescripcion) {
		this.strDescripcion = strDescripcion;
	}
}
