package co.com.woaho.response;

import java.util.List;

import co.com.woaho.dto.PaisDTO;

public class ConsultarTerritorioResponse extends BaseResponse {

	private List<PaisDTO> lisPaisesDto;

	public List<PaisDTO> getLisPaisesDto() {
		return lisPaisesDto;
	}

	public void setLisPaisesDto(List<PaisDTO> lisPaisesDto) {
		this.lisPaisesDto = lisPaisesDto;
	}
}
