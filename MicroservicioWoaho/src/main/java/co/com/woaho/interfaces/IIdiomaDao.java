package co.com.woaho.interfaces;

import java.util.List;

import co.com.woaho.modelo.Idioma;

public interface IIdiomaDao {

	List<Idioma> obtenerIdiomas(List<Long> pId);
}
