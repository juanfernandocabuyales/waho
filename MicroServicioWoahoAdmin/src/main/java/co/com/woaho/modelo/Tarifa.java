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
@Table(name = "tarifa")
@NamedQueries({ @NamedQuery(name="Tarifa.findAll", query="SELECT ta FROM Tarifa ta"),
				@NamedQuery(name="Tarifa.findId", query="SELECT ta FROM Tarifa ta WHERE ta.tarifaId = :pId"),
			    @NamedQuery(name="Tarifa.findServicio", query="SELECT ta FROM Tarifa ta WHERE ta.servicio.servicioId = :pIdServicio")
             })
public class Tarifa implements Serializable {

private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "TARIFAID_GENERATOR", sequenceName = "SEC_TARIFA", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TARIFAID_GENERATOR")
	@Column(name = "tarifa_id", unique = true, nullable = false, precision = 12)
	private Long tarifaId;
	
	@Column(name = "tarifa_valor", precision = 12)
	private double valor;
	
	@ManyToOne
	@JoinColumn(name = "tarifa_moneda")
	private Moneda moneda;
	
	@ManyToOne
	@JoinColumn(name = "tarifa_territorio")
	private Territorio pais;
	
	@ManyToOne
	@JoinColumn(name = "tarifa_servicio")
	private Servicio servicio;
	
	@ManyToOne
	@JoinColumn(name = "tarifa_unidad")
	private UnidadTarifa unidadTarifa;

	public Long getTarifaId() {
		return tarifaId;
	}

	public void setTarifaId(Long tarifaId) {
		this.tarifaId = tarifaId;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public Moneda getMoneda() {
		return moneda;
	}

	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}

	public Territorio getPais() {
		return pais;
	}

	public void setPais(Territorio pais) {
		this.pais = pais;
	}

	public Servicio getServicio() {
		return servicio;
	}

	public void setServicio(Servicio servicio) {
		this.servicio = servicio;
	}

	public UnidadTarifa getUnidadTarifa() {
		return unidadTarifa;
	}

	public void setUnidadTarifa(UnidadTarifa unidadTarifa) {
		this.unidadTarifa = unidadTarifa;
	}
}
