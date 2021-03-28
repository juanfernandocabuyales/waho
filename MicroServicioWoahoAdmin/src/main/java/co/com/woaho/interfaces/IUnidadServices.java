package co.com.woaho.interfaces;

import co.com.woaho.request.ConsultarUnidadesRequest;
import co.com.woaho.request.CrearUnidadRequest;
import co.com.woaho.request.EliminarRequest;
import co.com.woaho.response.ConsultarUnidadesResponse;
import co.com.woaho.response.CrearUnidadResponse;
import co.com.woaho.response.EliminarResponse;

public interface IUnidadServices {

	ConsultarUnidadesResponse consultarUnidades(ConsultarUnidadesRequest request);

	CrearUnidadResponse crearUnidad(CrearUnidadRequest request);

	CrearUnidadResponse actualizarUnidad(CrearUnidadRequest request);

	EliminarResponse eliminarUnidad(EliminarRequest request);
}
