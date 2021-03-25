package co.com.woaho.interfaces;

import co.com.woaho.request.ConsultarTerritorioRequest;
import co.com.woaho.response.ConsultarTerritorioResponse;

public interface ITerritorioService {
	
	ConsultarTerritorioResponse obtenerTerritorios(ConsultarTerritorioRequest request);

}
