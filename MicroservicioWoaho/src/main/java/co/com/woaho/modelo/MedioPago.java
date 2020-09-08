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
@Table(name = "medio_pago")
@NamedQueries({ @NamedQuery(name="MedioPago.findAll", query="SELECT mp FROM MedioPago mp"),
		 @NamedQuery(name="MedioPago.buscarMedioPago", query="SELECT mp FROM MedioPago mp WHERE mp.medioPagoId = :pIdMedio")})
public class MedioPago implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "MEDIOPAGOID_GENERATOR", sequenceName = "SEC_MEDIO_PAGO", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEDIOPAGOID_GENERATOR")
	@Column(name = "medio_pago_id", unique = true, nullable = false, precision = 12)
	private Long medioPagoId;
	
	@Column(name ="medio_pago_nombre",length = 100)
	private String medioPagoNombre;
	
	@Column(name ="medio_pago_etiqueta",length = 100)
	private String medioPagoEtiqueta;
	
	@ManyToOne
	@JoinColumn(name = "medio_pago_territorio")
	private Territorio medioPagoTerritorio;

	public Long getMedioPagoId() {
		return medioPagoId;
	}

	public void setMedioPagoId(Long medioPagoId) {
		this.medioPagoId = medioPagoId;
	}

	public String getMedioPagoNombre() {
		return medioPagoNombre;
	}

	public void setMedioPagoNombre(String medioPagoNombre) {
		this.medioPagoNombre = medioPagoNombre;
	}

	public String getMedioPagoEtiqueta() {
		return medioPagoEtiqueta;
	}

	public void setMedioPagoEtiqueta(String medioPagoEtiqueta) {
		this.medioPagoEtiqueta = medioPagoEtiqueta;
	}

	public Territorio getMedioPagoTerritorio() {
		return medioPagoTerritorio;
	}

	public void setMedioPagoTerritorio(Territorio medioPagoTerritorio) {
		this.medioPagoTerritorio = medioPagoTerritorio;
	}	
}
