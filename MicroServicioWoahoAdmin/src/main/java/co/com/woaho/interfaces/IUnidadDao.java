package co.com.woaho.interfaces;

import java.util.List;

import co.com.woaho.modelo.UnidadTarifa;

public interface IUnidadDao {

	List<UnidadTarifa> obtenerUnidades();
	
	UnidadTarifa guardarActualizarUnidad(UnidadTarifa pUnidad);
	
	UnidadTarifa obtenerId(Long pId);
	
	void eliminarUnidad(UnidadTarifa pUnidad);
}
