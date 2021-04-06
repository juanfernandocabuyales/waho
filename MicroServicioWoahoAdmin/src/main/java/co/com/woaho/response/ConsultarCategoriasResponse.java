package co.com.woaho.response;

import java.util.List;

import co.com.woaho.dto.CategoriaDto;

public class ConsultarCategoriasResponse extends BaseResponse {
	
	private List<CategoriaDto> listCategorias;

	public List<CategoriaDto> getListCategorias() {
		return listCategorias;
	}

	public void setListCategorias(List<CategoriaDto> listCategorias) {
		this.listCategorias = listCategorias;
	}
}
