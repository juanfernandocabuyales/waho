package co.com.woaho.response;

import java.util.List;

import co.com.woaho.dto.TerritorioDto;

public class ConsultarTerritoriosResponse extends BaseResponse {

	List<TerritorioDto> listTerritorios;

	public List<TerritorioDto> getListTerritorios() {
		return listTerritorios;
	}

	public void setListTerritorios(List<TerritorioDto> listTerritorios) {
		this.listTerritorios = listTerritorios;
	}
}
