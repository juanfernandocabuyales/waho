package co.com.woaho.interfaces;

import java.util.List;

import co.com.woaho.modelo.Tarifa;

public interface ITarifaDao {
	
	List<Tarifa> obtenerTarifas();

	List<Tarifa> obtenerTarifaServicio(Long pIdServicio);
	
	Tarifa guardarActualizarTarifa(Tarifa pTarifa);
	
	Tarifa obtenerTarifaId(Long pId);
}
