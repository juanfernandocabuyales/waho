package co.com.woaho.request;

import co.com.woaho.dto.TerritorioDto;

public class CrearTerritoriosRequest extends BaseRequest {

	private TerritorioDto territorioDto;

	public TerritorioDto getTerritorioDto() {
		return territorioDto;
	}

	public void setTerritorioDto(TerritorioDto territorioDto) {
		this.territorioDto = territorioDto;
	}
}
