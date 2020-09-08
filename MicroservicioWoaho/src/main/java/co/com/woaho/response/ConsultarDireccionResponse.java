package co.com.woaho.response;

import java.util.List;

public class ConsultarDireccionResponse extends BaseResponse {

	private List<Direccion> listDireccion;	

	public List<Direccion> getListDireccion() {
		return listDireccion;
	}

	public void setListDireccion(List<Direccion> listDireccion) {
		this.listDireccion = listDireccion;
	}

	public static class Direccion{
		
		private String id;
		
		private String placeId;
		
		private String mainAddress;
		
		private String name;
		
		private String secondaryAddress;
		
		private String home;
		
		private Location location;
		
		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getPlaceId() {
			return placeId;
		}

		public void setPlaceId(String placeId) {
			this.placeId = placeId;
		}

		public String getMainAddress() {
			return mainAddress;
		}

		public void setMainAddress(String mainAddress) {
			this.mainAddress = mainAddress;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getSecondaryAddress() {
			return secondaryAddress;
		}

		public void setSecondaryAddress(String secondaryAddress) {
			this.secondaryAddress = secondaryAddress;
		}

		public String getHome() {
			return home;
		}

		public void setHome(String home) {
			this.home = home;
		}

		public Location getLocation() {
			return location;
		}

		public void setLocation(Location location) {
			this.location = location;
		}

		public static class Location{
			
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
	}
	
}
