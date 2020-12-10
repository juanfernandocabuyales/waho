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
@Table(name = "servicio_favorito")
@NamedQueries({ @NamedQuery(name="ServicioFavorito.findAll", query="SELECT sf FROM ServicioFavorito sf"),
	            @NamedQuery(name="ServicioFavorito.findByUsuario", query="SELECT sf FROM ServicioFavorito sf WHERE sf.servicioUsuario.usuarioId = :pIdUsuario"),
	            @NamedQuery(name="ServicioFavorito.findByUsuarioAndService", query="SELECT sf FROM ServicioFavorito sf WHERE sf.servicioUsuario.usuarioId = :pIdUsuario AND sf.servicioServicio.servicioId = :pIdServicio")
})
public class ServicioFavorito implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "SERVICIO_FAVORITO_ID_GENERATOR", sequenceName = "SEC_SERVICIO_FAVORITO", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SERVICIO_FAVORITO_ID_GENERATOR")
	@Column(name = "servicio_favorito_id", unique = true, nullable = false, precision = 12)
	private Long servicioFavoritoId;
	
	@ManyToOne
	@JoinColumn(name = "servicio_favorito_usuario")
	private Usuario servicioUsuario;
	
	@ManyToOne
	@JoinColumn(name = "servicio_favorito_servicio")
	private Servicio servicioServicio;

	public Long getServicioFavoritoId() {
		return servicioFavoritoId;
	}

	public void setServicioFavoritoId(Long servicioFavoritoId) {
		this.servicioFavoritoId = servicioFavoritoId;
	}

	public Usuario getServicioUsuario() {
		return servicioUsuario;
	}

	public void setServicioUsuario(Usuario servicioUsuario) {
		this.servicioUsuario = servicioUsuario;
	}

	public Servicio getServicioServicio() {
		return servicioServicio;
	}

	public void setServicioServicio(Servicio servicioServicio) {
		this.servicioServicio = servicioServicio;
	}
}
