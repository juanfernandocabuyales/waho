package co.com.woaho.interfaces;

import java.util.List;

import co.com.woaho.modelo.Servicio;

public interface IServicioDao {

	List<Servicio> obtenerServiciosId(List<Long> ids);
	
	List<Servicio> consultarServicios();
	
	List<Servicio> consultarServiciosCategoria(Long pIdCategoria);
	
	Servicio guardarActualizarServicio(Servicio pServicio);
}
