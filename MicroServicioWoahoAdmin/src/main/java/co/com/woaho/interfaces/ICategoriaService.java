package co.com.woaho.interfaces;

import co.com.woaho.request.ConsultarCategoriaRequest;
import co.com.woaho.response.ConsultarCategoriasResponse;

public interface ICategoriaService {

	ConsultarCategoriasResponse consultarCategorias(ConsultarCategoriaRequest request);
}
