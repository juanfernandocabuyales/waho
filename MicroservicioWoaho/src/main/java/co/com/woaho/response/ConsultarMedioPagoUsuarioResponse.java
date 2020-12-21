package co.com.woaho.response;

import java.util.List;

public class ConsultarMedioPagoUsuarioResponse extends BaseResponse {
	
	private List<MedioPagoUsuario> listMediosPagos;

	
	public List<MedioPagoUsuario> getListMediosPagos() {
		return listMediosPagos;
	}

	public void setListMediosPagos(List<MedioPagoUsuario> listMediosPagos) {
		this.listMediosPagos = listMediosPagos;
	}

	public static class MedioPagoUsuario{
		
		private String id;
		
		private String nombre;
		
		private String fecha;
		
		private String cvc;
		
		private String codigo;
		
		private String estado;
		
		private String medioPago;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

		public String getFecha() {
			return fecha;
		}

		public void setFecha(String fecha) {
			this.fecha = fecha;
		}

		public String getCvc() {
			return cvc;
		}

		public void setCvc(String cvc) {
			this.cvc = cvc;
		}

		public String getCodigo() {
			return codigo;
		}

		public void setCodigo(String codigo) {
			this.codigo = codigo;
		}

		public String getEstado() {
			return estado;
		}

		public void setEstado(String estado) {
			this.estado = estado;
		}

		public String getMedioPago() {
			return medioPago;
		}

		public void setMedioPago(String medioPago) {
			this.medioPago = medioPago;
		}
	}
}
