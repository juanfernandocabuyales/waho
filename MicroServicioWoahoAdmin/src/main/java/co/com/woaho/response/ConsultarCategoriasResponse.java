package co.com.woaho.response;

import java.util.List;

public class ConsultarCategoriasResponse extends BaseResponse {
	
	private List<Categoria> listCategorias;

	public List<Categoria> getListCategorias() {
		return listCategorias;
	}

	public void setListCategorias(List<Categoria> listCategorias) {
		this.listCategorias = listCategorias;
	}

	public static class Categoria{
		
		private String idCategoria;
		
		private String nombreCategoria;

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
}
