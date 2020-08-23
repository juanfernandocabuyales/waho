package co.com.woaho.response;

import java.util.List;

public class ConsultarEtiquetasResponse {

	private List<Etiqueta> listEtiquetas;
	
	private String mensajeRespuesta;
	
	private String codRespuesta;	
	
	public List<Etiqueta> getListEtiquetas() {
		return listEtiquetas;
	}

	public void setListEtiquetas(List<Etiqueta> listEtiquetas) {
		this.listEtiquetas = listEtiquetas;
	}

	public String getMensajeRespuesta() {
		return mensajeRespuesta;
	}

	public void setMensajeRespuesta(String mensajeRespuesta) {
		this.mensajeRespuesta = mensajeRespuesta;
	}

	public String getCodRespuesta() {
		return codRespuesta;
	}

	public void setCodRespuesta(String codRespuesta) {
		this.codRespuesta = codRespuesta;
	}

	public static class Etiqueta{
		
		private String etiqueta;
		
		private String codEtiqueta;
		
		private String idEtiqueta;

		public String getEtiqueta() {
			return etiqueta;
		}

		public void setEtiqueta(String etiqueta) {
			this.etiqueta = etiqueta;
		}

		public String getCodEtiqueta() {
			return codEtiqueta;
		}

		public void setCodEtiqueta(String codEtiqueta) {
			this.codEtiqueta = codEtiqueta;
		}

		public String getIdEtiqueta() {
			return idEtiqueta;
		}

		public void setIdEtiqueta(String idEtiqueta) {
			this.idEtiqueta = idEtiqueta;
		}
	}
}
