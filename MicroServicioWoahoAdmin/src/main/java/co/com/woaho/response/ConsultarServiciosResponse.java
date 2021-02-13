package co.com.woaho.response;

import java.util.List;

public class ConsultarServiciosResponse {
	
	private List<Servicio> listServicios;
	
	private String codigoRespuesta;
	
	private String mensajeRespuesta;	
	
	public List<Servicio> getListServicios() {
		return listServicios;
	}

	public void setListServicios(List<Servicio> listServicios) {
		this.listServicios = listServicios;
	}

	public String getCodigoRespuesta() {
		return codigoRespuesta;
	}

	public void setCodigoRespuesta(String codigoRespuesta) {
		this.codigoRespuesta = codigoRespuesta;
	}

	public String getMensajeRespuesta() {
		return mensajeRespuesta;
	}

	public void setMensajeRespuesta(String mensajeRespuesta) {
		this.mensajeRespuesta = mensajeRespuesta;
	}

	public static class Servicio{
	
		private String codigo;
		
		private String image;
		
		private String name;
		
		private double price;
		
		private Long category;
		
		private Long clicks;
		
		private String description;		

		public String getCodigo() {
			return codigo;
		}

		public void setCodigo(String codigo) {
			this.codigo = codigo;
		}

		public String getImage() {
			return image;
		}

		public void setImage(String image) {
			this.image = image;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public double getPrice() {
			return price;
		}

		public void setPrice(double price) {
			this.price = price;
		}

		public Long getCategory() {
			return category;
		}

		public void setCategory(Long category) {
			this.category = category;
		}

		public Long getClicks() {
			return clicks;
		}

		public void setClicks(Long clicks) {
			this.clicks = clicks;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}		
	}
}
