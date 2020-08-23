package co.com.woaho.interfaces;

import co.com.woaho.request.ConsultarEtiquetasRequest;
import co.com.woaho.response.ConsultarEtiquetasResponse;

public interface IEtiquetasService {

	ConsultarEtiquetasResponse consultarEtiquetasIdioma(ConsultarEtiquetasRequest request);
	
	ConsultarEtiquetasResponse consultarEtiquetaCodigoIdioma(ConsultarEtiquetasRequest request);
}
