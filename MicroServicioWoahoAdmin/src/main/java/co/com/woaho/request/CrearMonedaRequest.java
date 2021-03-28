package co.com.woaho.request;

import co.com.woaho.dto.MonedaDto;

public class CrearMonedaRequest extends BaseRequest {

	private MonedaDto moneda;

	public MonedaDto getMoneda() {
		return moneda;
	}

	public void setMoneda(MonedaDto moneda) {
		this.moneda = moneda;
	}
}
