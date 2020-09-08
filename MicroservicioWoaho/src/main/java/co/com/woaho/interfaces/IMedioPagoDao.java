package co.com.woaho.interfaces;

import java.util.List;

import co.com.woaho.modelo.MedioPago;

public interface IMedioPagoDao {

	MedioPago obtenerMedioPago(Long pIdMedioPago);
	
	List<MedioPago> obtenerMediosPago();
}
