package co.com.woaho.response;

import java.util.List;

import co.com.woaho.dto.ServicioDto;

public class ConsultarServiciosResponse extends BaseResponse {
	
	private List<ServicioDto> listServicios;
	
	public List<ServicioDto> getListServicios() {
		return listServicios;
	}

	public void setListServicios(List<ServicioDto> listServicios) {
		this.listServicios = listServicios;
	}
}
