package co.com.woaho.interfaces;

import java.util.List;

import co.com.woaho.modelo.Profesion;

public interface IProfesionDao {

	List<Profesion> obtenerProfesiones(List<Long> pId);
}
