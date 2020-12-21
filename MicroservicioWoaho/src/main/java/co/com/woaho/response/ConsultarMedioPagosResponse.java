package co.com.woaho.response;

import java.util.List;

public class ConsultarMedioPagosResponse extends BaseResponse {

	private List<MedioPago> listMediosPagos;
	
	public List<MedioPago> getListMediosPagos() {
		return listMediosPagos;
	}

	public void setListMediosPagos(List<MedioPago> listMediosPagos) {
		this.listMediosPagos = listMediosPagos;
	}

	public static class MedioPago{
		
		private String id;
		
		private String nombre;
		
		private String etiqueta;
		
		private String territorio;

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

		public String getEtiqueta() {
			return etiqueta;
		}

		public void setEtiqueta(String etiqueta) {
			this.etiqueta = etiqueta;
		}

		public String getTerritorio() {
			return territorio;
		}

		public void setTerritorio(String territorio) {
			this.territorio = territorio;
		}
	}
}
