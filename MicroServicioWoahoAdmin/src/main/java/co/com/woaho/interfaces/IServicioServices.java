package co.com.woaho.interfaces;

import co.com.woaho.request.ConsultarServiciosRequest;
import co.com.woaho.request.CrearServicioRequest;
import co.com.woaho.response.ConsultarServiciosResponse;
import co.com.woaho.response.CrearServicioResponse;

public interface IServicioServices {

	ConsultarServiciosResponse consultarServicios(ConsultarServiciosRequest request);
	
	CrearServicioResponse crearServicio(CrearServicioRequest request);
	
	CrearServicioResponse actualizarServicio(CrearServicioRequest request);
}
