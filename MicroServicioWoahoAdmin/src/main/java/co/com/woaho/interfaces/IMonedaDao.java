package co.com.woaho.interfaces;

import java.util.List;

import co.com.woaho.modelo.Moneda;

public interface IMonedaDao {

	List<Moneda> obtenerMonedas();
	
	Moneda guardarActualizarMoneda(Moneda pMoneda);
	
	Moneda obtenerMonedaId(Long pId);
	
	void eliminarMoneda(Moneda pMoneda);
}
