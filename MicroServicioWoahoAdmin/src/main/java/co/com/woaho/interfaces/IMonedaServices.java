package co.com.woaho.interfaces;

import co.com.woaho.request.ConsultarMonedasRequest;
import co.com.woaho.request.CrearMonedaRequest;
import co.com.woaho.request.EliminarMonedaRequest;
import co.com.woaho.response.ConsultarMonedasResponse;
import co.com.woaho.response.CrearMonedaResponse;
import co.com.woaho.response.EliminarMonedaResponse;

public interface IMonedaServices {

	ConsultarMonedasResponse consultarMonedas(ConsultarMonedasRequest request);
	
	CrearMonedaResponse crearMoneda(CrearMonedaRequest request);
	
	CrearMonedaResponse actualizarMoneda(CrearMonedaRequest request);
	
	EliminarMonedaResponse eliminarMoneda(EliminarMonedaRequest request);
}
