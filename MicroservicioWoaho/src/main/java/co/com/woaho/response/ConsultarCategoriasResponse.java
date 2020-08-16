package co.com.woaho.response;

import java.util.List;

public class ConsultarCategoriasResponse {
	
	private List<Categoria> listCategorias;
	
	private String codigoRespuesta;
	
	private String mensajeRespuesta;

	public List<Categoria> getListCategorias() {
		return listCategorias;
	}

	public void setListCategorias(List<Categoria> listCategorias) {
		this.listCategorias = listCategorias;
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

	public static class Categoria{
		
		private String categoriaId;
		
		private String nombreCategoria;
		
		private Imagen imagenCategoria;		
		
		public String getCategoriaId() {
			return categoriaId;
		}

		public void setCategoriaId(String categoriaId) {
			this.categoriaId = categoriaId;
		}

		public String getNombreCategoria() {
			return nombreCategoria;
		}

		public void setNombreCategoria(String nombreCategoria) {
			this.nombreCategoria = nombreCategoria;
		}

		public Imagen getImagenCategoria() {
			return imagenCategoria;
		}

		public void setImagenCategoria(Imagen imagenCategoria) {
			this.imagenCategoria = imagenCategoria;
		}

		public static class Imagen{
			
			private String imagenId;
			
			private String imagenRuta;

			public Imagen(String imagenId, String imagenRuta) {
				super();
				this.imagenId = imagenId;
				this.imagenRuta = imagenRuta;
			}

			public String getImagenId() {
				return imagenId;
			}

			public void setImagenId(String imagenId) {
				this.imagenId = imagenId;
			}

			public String getImagenRuta() {
				return imagenRuta;
			}

			public void setImagenRuta(String imagenRuta) {
				this.imagenRuta = imagenRuta;
			}
			
		}
	}
}
