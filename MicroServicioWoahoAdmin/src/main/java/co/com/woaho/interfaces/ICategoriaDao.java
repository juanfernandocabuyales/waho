package co.com.woaho.interfaces;

import java.util.List;

import co.com.woaho.modelo.Categoria;



public interface ICategoriaDao {

	List<Categoria> consultarCategorias();
	
	Categoria guardarActualizarCategoria(Categoria pCategoria);
	
	Categoria obtenerCategoriaId(Long pId);
	
	void eliminarCategoria(Categoria pCategoria);
}
