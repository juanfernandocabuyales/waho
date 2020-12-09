package co.com.woaho.interfaces;

import java.util.List;

import co.com.woaho.modelo.Direccion;

public interface IDireccionDao {

	List<Direccion> obtenerDireccionesUsuario(Long idUsuario);
	
	Direccion crearActualizarDireccion(Direccion pDireccion) throws Exception;
	
	Direccion obtenerDireccion(Long pIdDireccion);
	
	void eliminarDireccion(Direccion pDireccion) throws Exception;
}
