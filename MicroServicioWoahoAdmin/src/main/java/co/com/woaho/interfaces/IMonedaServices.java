package co.com.woaho.interfaces;

import co.com.woaho.request.ConsultarMonedasRequest;
import co.com.woaho.request.CrearMonedaRequest;
import co.com.woaho.request.EliminarRequest;
import co.com.woaho.response.ConsultarMonedasResponse;
import co.com.woaho.response.CrearResponse;
import co.com.woaho.response.EliminarResponse;

public interface IMonedaServices {

	ConsultarMonedasResponse consultarMonedas(ConsultarMonedasRequest request);
	
	CrearResponse crearMoneda(CrearMonedaRequest request);
	
	CrearResponse actualizarMoneda(CrearMonedaRequest request);
	
	EliminarResponse eliminarMoneda(EliminarRequest request);
}
