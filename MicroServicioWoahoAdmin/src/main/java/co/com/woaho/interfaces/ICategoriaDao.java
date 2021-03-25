package co.com.woaho.interfaces;

import java.util.List;

import co.com.woaho.modelo.Categoria;



public interface ICategoriaDao {

	List<Categoria> consultarCategorias();
}
