package co.com.woaho.interfaces;

import java.util.List;

import co.com.woaho.modelo.TipoTerritorio;

public interface ITipoTerritorioDao {

	List<TipoTerritorio> obtenerTipos();
	
	TipoTerritorio obtenerTipo(Long pId);
	
	TipoTerritorio guardarActualizar(TipoTerritorio pTipoTerritorio);
	
	void eliminarTipo(TipoTerritorio pTipoTerritorio);
}
