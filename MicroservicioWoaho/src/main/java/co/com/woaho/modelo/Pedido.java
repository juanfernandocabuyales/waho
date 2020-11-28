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
@Table(name = "pedido")
@NamedQueries({ @NamedQuery(name="Pedido.findAll", query="SELECT pe.pedidoId FROM Pedido pe"),
				@NamedQuery(name="Pedido.findId", query="SELECT pe FROM Pedido pe WHERE pe.pedidoId = :pId"),
				@NamedQuery(name="Pedido.findUser", query="SELECT pe FROM Pedido pe WHERE pe.pedidoUsuario.usuarioId = :pIdUsario"),
				@NamedQuery(name="Pedido.findProfesional", query="SELECT pe FROM Pedido pe WHERE pe.pedidoProfesional.profesionalId = :pIdProfesional")})
public class Pedido implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "PEDIDOID_GENERATOR", sequenceName = "SEC_PEDIDO", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PEDIDOID_GENERATOR")
	@Column(name = "pedido_id", unique = true, nullable = false, precision = 12)
	private Long pedidoId;
	
	@ManyToOne
	@JoinColumn(name = "pedido_servicio")
	private Servicio pedidoServicio;
	
	@ManyToOne
	@JoinColumn(name = "pedido_usuario")
	private Usuario pedidoUsuario;
	
	@Column(name ="pedido_descripcion",length = 100)
	private String pedidoDescripcion;
	
	@ManyToOne
	@JoinColumn(name = "pedido_estado")
	private Estado pedidoEstado;
	
	@ManyToOne
	@JoinColumn(name = "pedido_direccion")
	private Direccion pedidoDireccion;
	
	@Column(name ="pedido_cod_promocional",length = 100)
	private String pedidoCodPromocional;
	
	@Column(name ="pedido_fecha",length = 100)
	private String pedidoFecha;
	
	@Column(name ="pedido_hora",length = 100)
	private String pedidoHora;
	
	@ManyToOne
	@JoinColumn(name = "pedido_profesional")
	private Profesional pedidoProfesional;
	
	@ManyToOne
	@JoinColumn(name = "pedido_medio_pago")
	private MedioPago pedidoMedioPago;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "pedido_fecha_final", nullable = false)
	private Date fechafinal;
	
	@Column(name ="pedido_latitud",length = 100)
	private String pedidoLatitud;
	
	@Column(name ="pedido_longitu",length = 100)
	private String pedidoLongitud;

	public Long getPedidoId() {
		return pedidoId;
	}

	public void setPedidoId(Long pedidoId) {
		this.pedidoId = pedidoId;
	}

	public Servicio getPedidoServicio() {
		return pedidoServicio;
	}

	public void setPedidoServicio(Servicio pedidoServicio) {
		this.pedidoServicio = pedidoServicio;
	}

	public Usuario getPedidoUsuario() {
		return pedidoUsuario;
	}

	public void setPedidoUsuario(Usuario pedidoUsuario) {
		this.pedidoUsuario = pedidoUsuario;
	}

	public String getPedidoDescripcion() {
		return pedidoDescripcion;
	}

	public void setPedidoDescripcion(String pedidoDescripcion) {
		this.pedidoDescripcion = pedidoDescripcion;
	}

	public Estado getPedidoEstado() {
		return pedidoEstado;
	}

	public void setPedidoEstado(Estado pedidoEstado) {
		this.pedidoEstado = pedidoEstado;
	}

	public Direccion getPedidoDireccion() {
		return pedidoDireccion;
	}

	public void setPedidoDireccion(Direccion pedidoDireccion) {
		this.pedidoDireccion = pedidoDireccion;
	}

	public String getPedidoCodPromocional() {
		return pedidoCodPromocional;
	}

	public void setPedidoCodPromocional(String pedidoCodPromocional) {
		this.pedidoCodPromocional = pedidoCodPromocional;
	}

	public String getPedidoFecha() {
		return pedidoFecha;
	}

	public void setPedidoFecha(String pedidoFecha) {
		this.pedidoFecha = pedidoFecha;
	}

	public String getPedidoHora() {
		return pedidoHora;
	}

	public void setPedidoHora(String pedidoHora) {
		this.pedidoHora = pedidoHora;
	}

	public Profesional getPedidoProfesional() {
		return pedidoProfesional;
	}

	public void setPedidoProfesional(Profesional pedidoProfesional) {
		this.pedidoProfesional = pedidoProfesional;
	}

	public MedioPago getPedidoMedioPago() {
		return pedidoMedioPago;
	}

	public void setPedidoMedioPago(MedioPago pedidoMedioPago) {
		this.pedidoMedioPago = pedidoMedioPago;
	}

	public Date getFechafinal() {
		return fechafinal;
	}

	public void setFechafinal(Date fechafinal) {
		this.fechafinal = fechafinal;
	}

	public String getPedidoLatitud() {
		return pedidoLatitud;
	}

	public void setPedidoLatitud(String pedidoLatitud) {
		this.pedidoLatitud = pedidoLatitud;
	}

	public String getPedidoLongitud() {
		return pedidoLongitud;
	}

	public void setPedidoLongitud(String pedidoLongitud) {
		this.pedidoLongitud = pedidoLongitud;
	}
}
