package co.com.woaho.interfaces;

import java.util.List;

import co.com.woaho.modelo.Territorio;

public interface ITerritorioDao {

	List<Territorio> obtenerTerritorios(String pStrTipoTerritorio);
}
