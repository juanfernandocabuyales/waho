package co.com.woaho.interfaces;

import co.com.woaho.request.CrearProfesionalRequest;
import co.com.woaho.response.CrearProfesionalResponse;

public interface IProfesionalService {

	String obtenerProfesionales(String pIdServicios);
	
	CrearProfesionalResponse crearProfesional (CrearProfesionalRequest request);
}
