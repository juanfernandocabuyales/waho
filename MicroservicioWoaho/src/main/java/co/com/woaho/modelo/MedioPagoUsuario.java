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
@Table(name = "medio_pago_usuario")
@NamedQueries({ @NamedQuery(name="MedioPagoUsuario.findAll", query="SELECT mpu FROM MedioPagoUsuario mpu"),
	            @NamedQuery(name="MedioPagoUsuario.findUsuario", query="SELECT mpu FROM MedioPagoUsuario mpu WHERE mpu.medioPagoUsuarioUsuario.usuarioId = :pIdUsuario"),
	            @NamedQuery(name="MedioPagoUsuario.findUsuarioActivo", query="SELECT mpu FROM MedioPagoUsuario mpu WHERE mpu.medioPagoUsuarioUsuario.usuarioId = :pIdUsuario AND mpu.medioPagoUsuarioEstado.estadoId = :pEstadoId")})
public class MedioPagoUsuario implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "MEDIOPAGOUSUARIOID_GENERATOR", sequenceName = "SEC_MEDIO_PAGO_USUARIO", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEDIOPAGOUSUARIOID_GENERATOR")
	@Column(name = "medio_pago_usuario_id", unique = true, nullable = false, precision = 12)
	private Long medioPagoUsuarioId;
	
	@Column(name ="medio_pago_usuario_nombre",length = 4000)
	private String medioPagoUsuarioNombre;
	
	@Column(name ="medio_pago_usuario_fecha_vencimiento",length = 4000)
	private String medioPagoUsuarioFechaVen;
	
	@Column(name ="medio_pago_usuario_cvc",length = 4000)
	private String medioPagoUsuarioCvc;
	
	@Column(name ="medio_pago_usuario_codigo",length = 4000)
	private String medioPagoUsuarioCodigo;
	
	@ManyToOne
	@JoinColumn(name = "medio_pago_usuario_estado")
	private Estado medioPagoUsuarioEstado;
	
	@ManyToOne
	@JoinColumn(name = "medio_pago_usuario_usuario")
	private Usuario medioPagoUsuarioUsuario;
	
	@ManyToOne
	@JoinColumn(name = "medio_pago_usuario_medio_pago")
	private MedioPago medioPagoUsuarioMedioPago;

	public Long getMedioPagoUsuarioId() {
		return medioPagoUsuarioId;
	}

	public void setMedioPagoUsuarioId(Long medioPagoUsuarioId) {
		this.medioPagoUsuarioId = medioPagoUsuarioId;
	}

	public String getMedioPagoUsuarioNombre() {
		return medioPagoUsuarioNombre;
	}

	public void setMedioPagoUsuarioNombre(String medioPagoUsuarioNombre) {
		this.medioPagoUsuarioNombre = medioPagoUsuarioNombre;
	}

	public String getMedioPagoUsuarioFechaVen() {
		return medioPagoUsuarioFechaVen;
	}

	public void setMedioPagoUsuarioFechaVen(String medioPagoUsuarioFechaVen) {
		this.medioPagoUsuarioFechaVen = medioPagoUsuarioFechaVen;
	}

	public String getMedioPagoUsuarioCvc() {
		return medioPagoUsuarioCvc;
	}

	public void setMedioPagoUsuarioCvc(String medioPagoUsuarioCvc) {
		this.medioPagoUsuarioCvc = medioPagoUsuarioCvc;
	}

	public String getMedioPagoUsuarioCodigo() {
		return medioPagoUsuarioCodigo;
	}

	public void setMedioPagoUsuarioCodigo(String medioPagoUsuarioCodigo) {
		this.medioPagoUsuarioCodigo = medioPagoUsuarioCodigo;
	}

	public Estado getMedioPagoUsuarioEstado() {
		return medioPagoUsuarioEstado;
	}

	public void setMedioPagoUsuarioEstado(Estado medioPagoUsuarioEstado) {
		this.medioPagoUsuarioEstado = medioPagoUsuarioEstado;
	}

	public Usuario getMedioPagoUsuarioUsuario() {
		return medioPagoUsuarioUsuario;
	}

	public void setMedioPagoUsuarioUsuario(Usuario medioPagoUsuarioUsuario) {
		this.medioPagoUsuarioUsuario = medioPagoUsuarioUsuario;
	}

	public MedioPago getMedioPagoUsuarioMedioPago() {
		return medioPagoUsuarioMedioPago;
	}

	public void setMedioPagoUsuarioMedioPago(MedioPago medioPagoUsuarioMedioPago) {
		this.medioPagoUsuarioMedioPago = medioPagoUsuarioMedioPago;
	}
}
