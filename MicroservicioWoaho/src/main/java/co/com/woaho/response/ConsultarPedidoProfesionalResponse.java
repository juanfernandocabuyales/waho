package co.com.woaho.response;

import java.util.List;


public class ConsultarPedidoProfesionalResponse extends BaseResponse{

	private List<PedidoProfesionalDto> listPedidos;

	public List<PedidoProfesionalDto> getListPedidos() {
		return listPedidos;
	}

	public void setListPedidos(List<PedidoProfesionalDto> listPedidos) {
		this.listPedidos = listPedidos;
	}

	public static class PedidoProfesionalDto{
		
		private String idPedido;

		private String servicio;

		private String descripcion;

		private String estado;

		private String direccion;

		private String fechaHora;

		private String usuario;

		private String medioPago;
		
		private String fechaHoraFinal;

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
			return fechaHora;
		}

		public void setFechaHora(String fechaHora) {
			this.fechaHora = fechaHora;
		}

		public String getUsuario() {
			return usuario;
		}

		public void setUsuario(String usuario) {
			this.usuario = usuario;
		}

		public String getMedioPago() {
			return medioPago;
		}

		public void setMedioPago(String medioPago) {
			this.medioPago = medioPago;
		}

		public String getFechaHoraFinal() {
			return fechaHoraFinal;
		}

		public void setFechaHoraFinal(String fechaHoraFinal) {
			this.fechaHoraFinal = fechaHoraFinal;
		}

		public String getIdPedido() {
			return idPedido;
		}

		public void setIdPedido(String idPedido) {
			this.idPedido = idPedido;
		}
	}
}
