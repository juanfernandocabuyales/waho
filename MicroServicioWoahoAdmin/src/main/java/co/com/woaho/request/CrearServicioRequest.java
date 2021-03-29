package co.com.woaho.request;

import co.com.woaho.dto.ServicioDto;

public class CrearServicioRequest extends BaseRequest {
	
	private ServicioDto servicioDto;

	public ServicioDto getServicioDto() {
		return servicioDto;
	}

	public void setServicioDto(ServicioDto servicioDto) {
		this.servicioDto = servicioDto;
	}
}
