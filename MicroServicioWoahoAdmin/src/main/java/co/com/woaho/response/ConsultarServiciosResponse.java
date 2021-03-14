package co.com.woaho.response;

import java.util.List;

public class ConsultarServiciosResponse extends BaseResponse {
	
	private List<Servicio> listServicios;
	
	public List<Servicio> getListServicios() {
		return listServicios;
	}

	public void setListServicios(List<Servicio> listServicios) {
		this.listServicios = listServicios;
	}
	
	public static class Servicio{
	
		private String codigo;
		
		private String image;
		
		private String name;
		
		private List<TarifaServicio> listTarifas;
		
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

		public List<TarifaServicio> getListTarifas() {
			return listTarifas;
		}

		public void setListTarifas(List<TarifaServicio> listTarifas) {
			this.listTarifas = listTarifas;
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
		
		public static class TarifaServicio {
			
			private String pais;
			
			private Double valor;
			
			private String moneda;
			
			private String unidad;

			public String getPais() {
				return pais;
			}

			public void setPais(String pais) {
				this.pais = pais;
			}

			public Double getValor() {
				return valor;
			}

			public void setValor(Double valor) {
				this.valor = valor;
			}

			public String getMoneda() {
				return moneda;
			}

			public void setMoneda(String moneda) {
				this.moneda = moneda;
			}

			public String getUnidad() {
				return unidad;
			}

			public void setUnidad(String unidad) {
				this.unidad = unidad;
			}
		}
	}
}
