package co.com.woaho.interfaces;

import co.com.woaho.request.ActualizarCrearDireccionRequest;
import co.com.woaho.request.ConsultarDireccionRequest;
import co.com.woaho.response.ActualizarCrearDireccionResponse;
import co.com.woaho.response.ConsultarDireccionResponse;

public interface IDireccionService {

	ConsultarDireccionResponse obtenerDireccionesUsuario(ConsultarDireccionRequest request);
	
	ActualizarCrearDireccionResponse crearActualizarDireccion (ActualizarCrearDireccionRequest request);
}
