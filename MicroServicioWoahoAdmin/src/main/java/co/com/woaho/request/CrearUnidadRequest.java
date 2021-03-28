package co.com.woaho.request;

import co.com.woaho.dto.UnidadDto;

public class CrearUnidadRequest extends BaseRequest {

	private UnidadDto unidad;

	public UnidadDto getUnidad() {
		return unidad;
	}

	public void setUnidad(UnidadDto unidad) {
		this.unidad = unidad;
	}
}
