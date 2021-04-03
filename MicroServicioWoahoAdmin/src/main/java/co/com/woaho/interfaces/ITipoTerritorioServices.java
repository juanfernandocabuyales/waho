package co.com.woaho.interfaces;

import co.com.woaho.request.ConsultarTiposRequest;
import co.com.woaho.request.CrearTipoRequest;
import co.com.woaho.request.EliminarRequest;
import co.com.woaho.response.ConsultarTiposResponse;
import co.com.woaho.response.CrearResponse;
import co.com.woaho.response.EliminarResponse;

public interface ITipoTerritorioServices {

	CrearResponse crearTipoTerritorio(CrearTipoRequest request);
	
	CrearResponse actualizarTipoTerritorio(CrearTipoRequest request);
	
	ConsultarTiposResponse consultarTipos(ConsultarTiposRequest request);
	
	EliminarResponse eliminarTipo(EliminarRequest request);
}
