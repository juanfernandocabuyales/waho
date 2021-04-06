package co.com.woaho.request;

import co.com.woaho.dto.CategoriaDto;

public class CrearCategoriaRequest extends BaseRequest {

	private CategoriaDto categoriaDto;

	public CategoriaDto getCategoriaDto() {
		return categoriaDto;
	}

	public void setCategoriaDto(CategoriaDto categoriaDto) {
		this.categoriaDto = categoriaDto;
	}
}
