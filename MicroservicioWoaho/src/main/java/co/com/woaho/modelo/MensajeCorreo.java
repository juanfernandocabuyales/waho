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
@Table(name = "mensaje_correo")
@NamedQueries({ @NamedQuery(name="MensajeCorreo.findAll", query="SELECT mc FROM MensajeCorreo mc"),
				@NamedQuery(name="MensajeCorreo.findCodLeng", query="SELECT mc FROM MensajeCorreo mc WHERE mc.mensajeCorreoCodigo = :pCodigo AND mc.mensajeCorreoIdioma = :pIdioma")})
public class MensajeCorreo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "MENSAJECORREOID_GENERATOR", sequenceName = "SEC_MENSAJE_CORREO", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MENSAJECORREOID_GENERATOR")
	@Column(name = "mensaje_correo_id", unique = true, nullable = false, precision = 12)
	private Long mensajeCorreoId;
	
	@Column(name = "mensaje_correo_codigo", precision = 12)
	private Long mensajeCorreoCodigo;
	
	@Column(name ="mensaje_correo_mensaje",length = 4000)
	private String mensajeCorreoMensaje;
	
	@Column(name ="mensaje_correo_idioma",length = 4000)
	private String mensajeCorreoIdioma;

	public Long getMensajeCorreoId() {
		return mensajeCorreoId;
	}

	public void setMensajeCorreoId(Long mensajeCorreoId) {
		this.mensajeCorreoId = mensajeCorreoId;
	}

	public Long getMensajeCorreoCodigo() {
		return mensajeCorreoCodigo;
	}

	public void setMensajeCorreoCodigo(Long mensajeCorreoCodigo) {
		this.mensajeCorreoCodigo = mensajeCorreoCodigo;
	}

	public String getMensajeCorreoMensaje() {
		return mensajeCorreoMensaje;
	}

	public void setMensajeCorreoMensaje(String mensajeCorreoMensaje) {
		this.mensajeCorreoMensaje = mensajeCorreoMensaje;
	}

	public String getMensajeCorreoIdioma() {
		return mensajeCorreoIdioma;
	}

	public void setMensajeCorreoIdioma(String mensajeCorreoIdioma) {
		this.mensajeCorreoIdioma = mensajeCorreoIdioma;
	}
}
