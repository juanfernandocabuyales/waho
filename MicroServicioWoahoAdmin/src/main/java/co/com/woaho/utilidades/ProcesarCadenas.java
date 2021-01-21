package co.com.woaho.utilidades;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import co.com.woaho.enumeraciones.EnumGeneral;

public class ProcesarCadenas {

	private static ProcesarCadenas instance;


	public static ProcesarCadenas getInstance() {
		if(instance == null)
			instance = new ProcesarCadenas();
		return instance;
	}
	public List<Long> obtenerListaLong(String pCadena){
		List<Long> listadoLong = new ArrayList<>();
		String [] tokens = pCadena.split(EnumGeneral.GUION.getValor());
		for(String token : tokens) {
			listadoLong.add(Long.parseLong(token));
		}
		return listadoLong;
	}
	
	public String obtenerMensajeFormat(String pForma, Object ...objects) {
		return MessageFormat.format(pForma, objects);
	}
	
	public String formatearFecha(String pFormato, Date pFecha) {
		SimpleDateFormat format = new SimpleDateFormat(pFormato);
		return format.format(pFecha);
	}
	
	public String cambiarSeparador(String pCadena,String pSeparador,String pNuevoSeparador) {
		StringBuilder strNuevaCadena = new StringBuilder();
		String [] tokens = pCadena.split(pSeparador);
		if(tokens == null || tokens.length == 0) {
			return pCadena;
		}else {
			for(String token : tokens) {
				strNuevaCadena.append(token);
				strNuevaCadena.append(pNuevoSeparador);
			}
			return strNuevaCadena.substring(0, strNuevaCadena.length() -1 );
		}
	}
}
