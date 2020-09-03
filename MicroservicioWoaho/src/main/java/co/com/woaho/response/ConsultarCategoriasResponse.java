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
		
		private String id;
		
		private String name;
		
		private String icon;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getIcon() {
			return icon;
		}

		public void setIcon(String icon) {
			this.icon = icon;
		}
	}
}
