package co.com.woaho.dto.cliente;

import java.util.List;

public class ProfesionalDTO {

	private String id;

	private Properties properties;

	private Geometry geometry;


	public static class Properties{

		private String name;
		
		private String image;
		
		private String profession;
		
		private String nationality;
		
		private String services;
		
		private String languages;
		
		private String aboutme;
		
		private IconSize iconSize;
		
		private double numberStars;
		
		private int numberServices;
		
		private List<Comments> comments;
		
		public static class IconSize{
			
			private Long alto;
			
			private Long ancho;

			public IconSize(Long alto, Long ancho) {
				super();
				this.alto = alto;
				this.ancho = ancho;
			}

			public Long getAlto() {
				return alto;
			}

			public void setAlto(Long alto) {
				this.alto = alto;
			}

			public Long getAncho() {
				return ancho;
			}

			public void setAncho(Long ancho) {
				this.ancho = ancho;
			}
		}
		
		public static class Comments{
			
			private String message;

			public Comments(String message) {
				super();
				this.message = message;
			}

			public String getMessage() {
				return message;
			}

			public void setMessage(String message) {
				this.message = message;
			}	
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getImage() {
			return image;
		}

		public void setImage(String image) {
			this.image = image;
		}

		public String getProfession() {
			return profession;
		}

		public void setProfession(String profession) {
			this.profession = profession;
		}

		public String getNationality() {
			return nationality;
		}

		public void setNationality(String nationality) {
			this.nationality = nationality;
		}

		public String getServices() {
			return services;
		}

		public void setServices(String services) {
			this.services = services;
		}

		public String getLanguages() {
			return languages;
		}

		public void setLanguages(String languages) {
			this.languages = languages;
		}

		public String getAboutme() {
			return aboutme;
		}

		public void setAboutme(String aboutme) {
			this.aboutme = aboutme;
		}

		public IconSize getIconSize() {
			return iconSize;
		}

		public void setIconSize(IconSize iconSize) {
			this.iconSize = iconSize;
		}

		public double getNumberStars() {
			return numberStars;
		}

		public void setNumberStars(double numberStars) {
			this.numberStars = numberStars;
		}

		public int getNumberServices() {
			return numberServices;
		}

		public void setNumberServices(int numberServices) {
			this.numberServices = numberServices;
		}

		public List<Comments> getComments() {
			return comments;
		}

		public void setComments(List<Comments> comments) {
			this.comments = comments;
		}
	}

	public static class Geometry{

		private String placeId;

		private Location location;


		public static class Location{

			private String lat;

			private String lng;

			public Location(String lat, String lng) {
				super();
				this.lat = lat;
				this.lng = lng;
			}

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
		
		public String getPlaceId() {
			return placeId;
		}

		public void setPlaceId(String placeId) {
			this.placeId = placeId;
		}

		public Location getLocation() {
			return location;
		}

		public void setLocation(Location location) {
			this.location = location;
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public Geometry getGeometry() {
		return geometry;
	}

	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}
}
