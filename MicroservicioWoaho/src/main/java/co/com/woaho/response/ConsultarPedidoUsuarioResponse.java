package co.com.woaho.response;

import java.util.List;

public class ConsultarPedidoUsuarioResponse extends BaseResponse {

	private List<PedidoUsuarioDto> listPedidos;
	
	public List<PedidoUsuarioDto> getListPedidos() {
		return listPedidos;
	}

	public void setListPedidos(List<PedidoUsuarioDto> listPedidos) {
		this.listPedidos = listPedidos;
	}

	public static class PedidoUsuarioDto{
		
		private String servicio;
		
		private String descripcion;
		
		private String estado;
		
		private String direccion;
		
		private String fechaHoraInicio;
		
		private String profesional;
		
		private String medioPago;
		
		private String fechaHoraFin;

		public String getServicio() {
			return servicio;
		}

		public void setServicio(String servicio) {
			this.servicio = servicio;
		}

		public String getDescripcion() {
			return descripcion;
		}

		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}

		public String getEstado() {
			return estado;
		}

		public void setEstado(String estado) {
			this.estado = estado;
		}

		public String getDireccion() {
			return direccion;
		}

		public void setDireccion(String direccion) {
			this.direccion = direccion;
		}

		public String getFechaHora() {
			return fechaHoraInicio;
		}

		public void setFechaHora(String fechaHora) {
			this.fechaHoraInicio = fechaHora;
		}

		public String getProfesional() {
			return profesional;
		}

		public void setProfesional(String profesional) {
			this.profesional = profesional;
		}

		public String getMedioPago() {
			return medioPago;
		}

		public void setMedioPago(String medioPago) {
			this.medioPago = medioPago;
		}

		public String getFechaHoraFin() {
			return fechaHoraFin;
		}

		public void setFechaHoraFin(String fechaHoraFin) {
			this.fechaHoraFin = fechaHoraFin;
		}
	}
}
