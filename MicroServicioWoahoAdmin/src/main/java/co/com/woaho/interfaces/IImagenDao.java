package co.com.woaho.interfaces;

import java.util.List;

import co.com.woaho.modelo.Imagen;

public interface IImagenDao {

	Imagen guardarActualizarImagen(Imagen pImagen);
	
	Imagen obtenerImagen(Long pIdImagen);
	
	List<Imagen> obtenerImagenes();
}
