package co.com.woaho.interfaces;

import co.com.woaho.request.ConsultarProfesionalRequest;
import co.com.woaho.request.CrearProfesionalRequest;
import co.com.woaho.response.ConsultarProfesionalResponse;
import co.com.woaho.response.CrearProfesionalResponse;

public interface IProfesionalService {

	ConsultarProfesionalResponse obtenerProfesionales(ConsultarProfesionalRequest request);
	
	CrearProfesionalResponse crearProfesional (CrearProfesionalRequest request);
}
