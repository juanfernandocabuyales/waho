package co.com.woaho.interfaces;

import co.com.woaho.request.ConsultarServiciosFavoritosRequest;
import co.com.woaho.request.CrearServicioFavoritoRequest;
import co.com.woaho.request.EliminarServicioFavoritoRequest;
import co.com.woaho.response.ConsultarServiciosResponse;
import co.com.woaho.response.CrearServicioFavoritoResponse;
import co.com.woaho.response.EliminarServicioFavoritoResponse;

public interface IServicioFavoritoService {

	ConsultarServiciosResponse consultarFavoritos (ConsultarServiciosFavoritosRequest request);
	
	CrearServicioFavoritoResponse crearServicioFavorito (CrearServicioFavoritoRequest request);
	
	EliminarServicioFavoritoResponse eliminarServicioFavorito (EliminarServicioFavoritoRequest request);
}
