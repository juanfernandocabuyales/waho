package co.com.woaho.modelo;

import java.io.Serializable;
import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "cancelacion")
@NamedQueries({ @NamedQuery(name="Cancelacion.findServicio", query="SELECT ca FROM Cancelacion ca")})
public class Cancelacion implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "CANCELACIONID_GENERATOR", sequenceName = "SEC_CANCELACION", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CANCELACIONID_GENERATOR")
	@Column(name = "cancelacion_id", unique = true, nullable = false, precision = 12)
	private Long cancelacionId;
	
	@ManyToOne
	@JoinColumn(name = "cancelacion_pedido")
	private Pedido cancelacionPedido;
	
	@Column(name ="cancelacion_motivo",length = 100)
	private String cancelacionMotivo;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "cancelacion_fecha", nullable = false)
	private Date cancelacionFecha;

	public Long getCancelacionId() {
		return cancelacionId;
	}

	public void setCancelacionId(Long cancelacionId) {
		this.cancelacionId = cancelacionId;
	}

	public Pedido getCancelacionPedido() {
		return cancelacionPedido;
	}

	public void setCancelacionPedido(Pedido cancelacionPedido) {
		this.cancelacionPedido = cancelacionPedido;
	}

	public String getCancelacionMotivo() {
		return cancelacionMotivo;
	}

	public void setCancelacionMotivo(String cancelacionMotivo) {
		this.cancelacionMotivo = cancelacionMotivo;
	}

	public Date getCancelacionFecha() {
		return cancelacionFecha;
	}

	public void setCancelacionFecha(Date cancelacionFecha) {
		this.cancelacionFecha = cancelacionFecha;
	}
}
