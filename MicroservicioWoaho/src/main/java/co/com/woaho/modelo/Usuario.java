package co.com.woaho.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "usuario")
@NamedQueries({ @NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u"),
	@NamedQuery(name="Usuario.buscarCelular", query="SELECT u FROM Usuario u WHERE u.strCelular = :pCelular"),
	@NamedQuery(name="Usuario.buscarEmail", query="SELECT u FROM Usuario u WHERE u.strCorreo = :pCorreo")})
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "USUARIOID_GENERATOR", sequenceName = "SEC_USUARIO", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USUARIOID_GENERATOR")
	@Column(name = "usuario_id", unique = true, nullable = false, precision = 12)
	private Long usuarioId;
	
	@Column(name = "usuario_nombre")
	private String strNombre;
	
	@Column(name = "usuario_apellido")
	private String strApellido;
	
	@Column(name = "usuario_celular")
	private String strCelular;
	
	@Column(name = "usuario_correo")
	private String strCorreo;
	
	@Column(name = "usuario_acepta_terminos")
	private String strAceptaTerminos;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "usuario_fecha_hora_acepta_terminos", nullable = false)
	private Date fechaHoraAceptaTerminos;
	
	@Column(name = "usuario_clave")
	private String strClave;
	
	@Column(name = "usuario_id_suscriptor")
	private String idSuscriptor;
	

	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getStrNombre() {
		return strNombre;
	}

	public void setStrNombre(String strNombre) {
		this.strNombre = strNombre;
	}

	public String getStrApellido() {
		return strApellido;
	}

	public void setStrApellido(String strApellido) {
		this.strApellido = strApellido;
	}

	public String getStrCelular() {
		return strCelular;
	}

	public void setStrCelular(String strCelular) {
		this.strCelular = strCelular;
	}

	public String getStrCorreo() {
		return strCorreo;
	}

	public void setStrCorreo(String strCorreo) {
		this.strCorreo = strCorreo;
	}

	public String getStrAceptaTerminos() {
		return strAceptaTerminos;
	}

	public void setStrAceptaTerminos(String strAceptaTerminos) {
		this.strAceptaTerminos = strAceptaTerminos;
	}

	public Date getFechaHoraAceptaTerminos() {
		return fechaHoraAceptaTerminos;
	}

	public void setFechaHoraAceptaTerminos(Date fechaHoraAceptaTerminos) {
		this.fechaHoraAceptaTerminos = fechaHoraAceptaTerminos;
	}

	public String getStrClave() {
		return strClave;
	}

	public void setStrClave(String strClave) {
		this.strClave = strClave;
	}

	public String getIdSuscriptor() {
		return idSuscriptor;
	}

	public void setIdSuscriptor(String idSuscriptor) {
		this.idSuscriptor = idSuscriptor;
	}
	
	public String getNombreCompleto() {
		return this.strNombre + " " + this.strApellido;
	}
}
