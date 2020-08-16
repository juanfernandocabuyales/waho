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
	
		private String idServicio;
		
		private String nombreServicio;
		
		private Categoria categoria;
		
		private Imagen imagen;
		
		public String getIdServicio() {
			return idServicio;
		}

		public void setIdServicio(String idServicio) {
			this.idServicio = idServicio;
		}

		public String getNombreServicio() {
			return nombreServicio;
		}

		public void setNombreServicio(String nombreServicio) {
			this.nombreServicio = nombreServicio;
		}

		public Categoria getCategoria() {
			return categoria;
		}

		public void setCategoria(Categoria categoria) {
			this.categoria = categoria;
		}

		public Imagen getImagen() {
			return imagen;
		}

		public void setImagen(Imagen imagen) {
			this.imagen = imagen;
		}

		public static class Categoria{
			
			private String idCategoria;
			
			private String nombreCategoria;

			public Categoria(String idCategoria, String nombreCategoria) {
				super();
				this.idCategoria = idCategoria;
				this.nombreCategoria = nombreCategoria;
			}

			public String getIdCategoria() {
				return idCategoria;
			}

			public void setIdCategoria(String idCategoria) {
				this.idCategoria = idCategoria;
			}

			public String getNombreCategoria() {
				return nombreCategoria;
			}

			public void setNombreCategoria(String nombreCategoria) {
				this.nombreCategoria = nombreCategoria;
			}		
		}
		
		public static class Imagen{
			
			private String idImagen;
			
			private String rutaImagen;

			public Imagen(String idImagen, String rutaImagen) {
				super();
				this.idImagen = idImagen;
				this.rutaImagen = rutaImagen;
			}

			public String getIdImagen() {
				return idImagen;
			}

			public void setIdImagen(String idImagen) {
				this.idImagen = idImagen;
			}

			public String getRutaImagen() {
				return rutaImagen;
			}

			public void setRutaImagen(String rutaImagen) {
				this.rutaImagen = rutaImagen;
			}
		}
	}
}
