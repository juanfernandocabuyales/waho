package co.com.woaho.interfaces;

import java.util.List;

import co.com.woaho.modelo.Profesional;

public interface IProfesionalDao {

	List<Profesional> obtenerProfesionales(String pIdServicio);
	
	Profesional registrarProfesional(Profesional pProfesional);
}
