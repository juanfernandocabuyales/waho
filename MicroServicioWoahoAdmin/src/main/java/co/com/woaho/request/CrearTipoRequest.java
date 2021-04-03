package co.com.woaho.request;

import co.com.woaho.dto.TipoDto;

public class CrearTipoRequest extends BaseRequest {

	private TipoDto tipoDto;

	public TipoDto getTipoDto() {
		return tipoDto;
	}

	public void setTipoDto(TipoDto tipoDto) {
		this.tipoDto = tipoDto;
	}
}
