package co.com.woaho.utilidades;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import co.com.woaho.dto.MensajeDTO;

public class ProcesarCadenas {

	private static ProcesarCadenas instance;


	public static ProcesarCadenas getInstance() {
		if(instance == null)
			instance = new ProcesarCadenas();
		return instance;
	}

	public List<MensajeDTO> obtenerMensajesCadena(String pStrCadena){
		List<MensajeDTO> mensajes = new ArrayList<>();
		StringTokenizer tokenMensajes = new StringTokenizer(pStrCadena,"~");
		while(tokenMensajes.hasMoreTokens()) {
			StringTokenizer tokenMensaje = new  StringTokenizer(tokenMensajes.nextToken(),":");
			while(tokenMensaje.hasMoreTokens()) {
				MensajeDTO mensajeDTO = new MensajeDTO(tokenMensaje.nextToken(),tokenMensaje.nextToken());
				mensajes.add(mensajeDTO);
			}
		}
		return mensajes;
	}

}
