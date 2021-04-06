package co.com.woaho.interfaces;

import co.com.woaho.request.ConsultarCategoriaRequest;
import co.com.woaho.request.CrearCategoriaRequest;
import co.com.woaho.request.EliminarRequest;
import co.com.woaho.response.ConsultarCategoriasResponse;
import co.com.woaho.response.CrearResponse;
import co.com.woaho.response.EliminarResponse;

public interface ICategoriaService {

	ConsultarCategoriasResponse consultarCategorias(ConsultarCategoriaRequest request);
	
	CrearResponse crearCategoria(CrearCategoriaRequest request);
	
	CrearResponse actualizarCategoria(CrearCategoriaRequest request);
	
	EliminarResponse eliminarCategoria(EliminarRequest request);
}
