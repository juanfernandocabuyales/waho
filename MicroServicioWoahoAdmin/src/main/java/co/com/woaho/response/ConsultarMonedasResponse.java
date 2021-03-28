package co.com.woaho.response;

import java.util.List;

import co.com.woaho.dto.MonedaDto;

public class ConsultarMonedasResponse extends BaseResponse {

	private List<MonedaDto> listMonedas;
	
	public List<MonedaDto> getListMonedas() {
		return listMonedas;
	}

	public void setListMonedas(List<MonedaDto> listMonedas) {
		this.listMonedas = listMonedas;
	}
}
