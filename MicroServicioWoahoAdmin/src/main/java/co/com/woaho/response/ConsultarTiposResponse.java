package co.com.woaho.response;

import java.util.List;

import co.com.woaho.dto.TipoDto;

public class ConsultarTiposResponse extends BaseResponse {

	private List<TipoDto> listTipos;

	public List<TipoDto> getListTipos() {
		return listTipos;
	}

	public void setListTipos(List<TipoDto> listTipos) {
		this.listTipos = listTipos;
	}
}
