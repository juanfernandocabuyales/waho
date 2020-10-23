package co.com.woaho.interfaces;

import java.util.List;

import co.com.woaho.modelo.EquivalenciaIdioma;

public interface IEquivalenciaIdiomaDao {

	List<EquivalenciaIdioma> obtenerEquivalencias();
	
	EquivalenciaIdioma obtenerEquivalencia(String pTexto);
}
