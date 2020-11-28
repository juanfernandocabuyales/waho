package co.com.woaho.request;

import java.util.List;

public class SolicitarPedidoRequest {

	private List<PedidoDto> listPedidos;
	
	private String idioma;
	
	public List<PedidoDto> getListPedidos() {
		return listPedidos;
	}

	public void setListPedidos(List<PedidoDto> listPedidos) {
		this.listPedidos = listPedidos;
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	public static class PedidoDto{
		
		private String id;
		
		private String idPerson;
		
		private String preferenceService;
		
		private Adress address;
		
		private Service service;
		
		private String state;
		
		private String date;
		
		private String hour;
		
		private String professional;
		
		private String paymentMethod;
		
		private String codPromocional;
		
		private String lat;
		
		private String lon;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getIdPerson() {
			return idPerson;
		}

		public void setIdPerson(String idPerson) {
			this.idPerson = idPerson;
		}

		public String getPreferenceService() {
			return preferenceService;
		}

		public void setPreferenceService(String preferenceService) {
			this.preferenceService = preferenceService;
		}

		public Adress getAddress() {
			return address;
		}

		public void setAddress(Adress address) {
			this.address = address;
		}	

		public Service getService() {
			return service;
		}

		public void setService(Service service) {
			this.service = service;
		}

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

		public String getHour() {
			return hour;
		}

		public void setHour(String hour) {
			this.hour = hour;
		}

		public String getProfessional() {
			return professional;
		}

		public void setProfessional(String professional) {
			this.professional = professional;
		}

		public String getPaymentMethod() {
			return paymentMethod;
		}

		public void setPaymentMethod(String paymentMethod) {
			this.paymentMethod = paymentMethod;
		}

		public String getCodPromocional() {
			return codPromocional;
		}

		public void setCodPromocional(String codPromocional) {
			this.codPromocional = codPromocional;
		}

		public String getLat() {
			return lat;
		}

		public void setLat(String lat) {
			this.lat = lat;
		}

		public String getLon() {
			return lon;
		}

		public void setLon(String lon) {
			this.lon = lon;
		}
		
		public static class Adress{
			
			private String lat;
			
			private String lng;

			public String getLat() {
				return lat;
			}

			public void setLat(String lat) {
				this.lat = lat;
			}

			public String getLng() {
				return lng;
			}

			public void setLng(String lng) {
				this.lng = lng;
			}
		}
		
		public static class Service{
			
			private String idService;
			
			private Long number;

			public String getIdService() {
				return idService;
			}

			public void setIdService(String idService) {
				this.idService = idService;
			}

			public Long getNumber() {
				return number;
			}

			public void setNumber(Long number) {
				this.number = number;
			} 
		}
	}
}
