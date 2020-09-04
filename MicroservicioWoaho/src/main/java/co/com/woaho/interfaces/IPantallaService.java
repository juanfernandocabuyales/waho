package co.com.woaho.interfaces;

import co.com.woaho.request.MensajesPantallaRequest;
import co.com.woaho.response.MensajePantallaResponse;

public interface IPantallaService {

	MensajePantallaResponse obtenerMensajesPantalla(MensajesPantallaRequest request);
}
