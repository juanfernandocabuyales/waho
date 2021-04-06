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
@Table(name = "categoria")
@NamedQueries({ @NamedQuery(name="Categoria.findAll", query="SELECT cat FROM Categoria cat"),
				@NamedQuery(name="Categoria.findId", query="SELECT cat FROM Categoria cat WHERE cat.categoriaId = :pId")})
public class Categoria implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "CATEGORIAID_GENERATOR", sequenceName = "SEC_CATEGORIA", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CATEGORIAID_GENERATOR")
	@Column(name = "categoria_id", unique = true, nullable = false, precision = 12)
	private Long categoriaId;
	
	@Column(name ="categoria_descripcion",length = 100)
	private String strDescripcion;
	
	@ManyToOne
	@JoinColumn(name = "categoria_imagen")
	private Imagen imagen;

	public Long getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(Long categoriaId) {
		this.categoriaId = categoriaId;
	}

	public String getStrDescripcion() {
		return strDescripcion;
	}

	public void setStrDescripcion(String strDescripcion) {
		this.strDescripcion = strDescripcion;
	}

	public Imagen getImagen() {
		return imagen;
	}

	public void setImagen(Imagen imagen) {
		this.imagen = imagen;
	}
}
