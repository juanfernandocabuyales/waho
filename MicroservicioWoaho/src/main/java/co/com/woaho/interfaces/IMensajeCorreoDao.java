package co.com.woaho.interfaces;

import co.com.woaho.modelo.MensajeCorreo;

public interface IMensajeCorreoDao {

	MensajeCorreo obtenerMensajeIdioma(Long pCodigo,String pIdioma);
}
