package co.com.woaho.response;

import java.util.List;

import co.com.woaho.dto.UnidadDto;

public class ConsultarUnidadesResponse extends BaseResponse {

	private List<UnidadDto> listUnidades;

	public List<UnidadDto> getListUnidades() {
		return listUnidades;
	}

	public void setListUnidades(List<UnidadDto> listUnidades) {
		this.listUnidades = listUnidades;
	}
}
