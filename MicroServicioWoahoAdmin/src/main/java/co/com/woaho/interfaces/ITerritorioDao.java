package co.com.woaho.interfaces;

import java.util.List;

import co.com.woaho.modelo.Territorio;

public interface ITerritorioDao {

	List<Territorio> obtenerTerritorios(Long pStrTipoTerritorio);
	
	Territorio obtenerTerritorio(Long pId);
	
	Territorio guardarActualizarTerritorio(Territorio pTerritorio);
	
	void eliminarTerritorio(Territorio pTerritorio);
	
	List<Territorio> obtenerTerritorios();
}
