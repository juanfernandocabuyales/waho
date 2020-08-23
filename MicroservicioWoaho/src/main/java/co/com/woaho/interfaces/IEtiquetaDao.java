package co.com.woaho.interfaces;

import java.util.List;

import co.com.woaho.modelo.Etiqueta;

public interface IEtiquetaDao {

	List<Etiqueta> obtenerEtiquetasIdioma(Long pIdioma);
	
	Etiqueta obtenerEtiquetaCodigoIdioma(String pCodigo,Long pIdioma);
}
