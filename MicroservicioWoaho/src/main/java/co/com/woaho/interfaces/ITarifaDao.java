package co.com.woaho.interfaces;

import co.com.woaho.modelo.Tarifa;

public interface ITarifaDao {

	Tarifa obtenerTarifaServicio(Long pIdServicio);
}
