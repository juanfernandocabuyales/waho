package co.com.woaho.interfaces;

import co.com.woaho.request.ConsultarServiciosRequest;
import co.com.woaho.response.ConsultarServiciosResponse;

public interface IServicioServices {

	ConsultarServiciosResponse consultarServicios(ConsultarServiciosRequest request);
	
	ConsultarServiciosResponse consultarServiciosCategoria(ConsultarServiciosRequest request);
}
