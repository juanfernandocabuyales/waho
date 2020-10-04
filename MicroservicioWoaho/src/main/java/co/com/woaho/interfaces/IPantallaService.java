package co.com.woaho.interfaces;

import co.com.woaho.request.ConsultarPantallasRequest;
import co.com.woaho.response.ConsultarPantallasResponse;

public interface IPantallaService {

	ConsultarPantallasResponse obtenerMensajesPantalla(ConsultarPantallasRequest request);
}
