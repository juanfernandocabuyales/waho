package co.com.woaho.interfaces;

import co.com.woaho.request.ConsultarTerritorioRequest;
import co.com.woaho.request.ConsultarTerritoriosRequest;
import co.com.woaho.request.CrearTerritoriosRequest;
import co.com.woaho.request.EliminarRequest;
import co.com.woaho.response.ConsultarTerritorioResponse;
import co.com.woaho.response.ConsultarTerritoriosResponse;
import co.com.woaho.response.CrearResponse;
import co.com.woaho.response.EliminarResponse;

public interface ITerritorioService {
	
	ConsultarTerritorioResponse obtenerTerritorios(ConsultarTerritorioRequest request);
	
	ConsultarTerritoriosResponse consultarTerritorios(ConsultarTerritoriosRequest request );
	
	CrearResponse crearTerritorios(CrearTerritoriosRequest request);
	
	CrearResponse actualizarTerritorios(CrearTerritoriosRequest request);
	
	EliminarResponse eliminarTerritorios(EliminarRequest request);

}
