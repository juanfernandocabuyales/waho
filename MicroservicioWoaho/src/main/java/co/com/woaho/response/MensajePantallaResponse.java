package co.com.woaho.response;

import java.util.List;

import co.com.woaho.dto.MensajeDTO;

public class MensajePantallaResponse extends BaseResponse{

	private List<MensajeDTO> listMensajesDto;

	public List<MensajeDTO> getListMensajesDto() {
		return listMensajesDto;
	}

	public void setListMensajesDto(List<MensajeDTO> listMensajesDto) {
		this.listMensajesDto = listMensajesDto;
	}
}
