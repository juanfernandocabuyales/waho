package co.com.woaho.interfaces;

import java.util.List;

import co.com.woaho.modelo.ServicioFavorito;

public interface IServicioFavoritoDao {

	List<ServicioFavorito> obtenerServiciosUsuario(Long pIdUsuario);
	
	ServicioFavorito guardarServicioFavorito(ServicioFavorito pServicioFavorito);
	
	void eliminarServicioFavorito(ServicioFavorito pServicioFavorito);
	
	ServicioFavorito obtenerServicioFavorito(Long pIdUsuario,Long pIdServicio);
}
