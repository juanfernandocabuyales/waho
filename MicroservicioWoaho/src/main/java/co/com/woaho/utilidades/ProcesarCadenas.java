package co.com.woaho.utilidades;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import co.com.respuestas.JsonGenerico;
import co.com.woaho.dto.DireccionDTO;
import co.com.woaho.dto.MensajeDTO;
import co.com.woaho.modelo.Direccion;
import co.com.woaho.modelo.Territorio;

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
	
	public JsonGenerico<DireccionDTO> obtenerDirecciones(List<Direccion> pListDireccion){
		JsonGenerico<DireccionDTO> listDireccionesDTO = new JsonGenerico<>();
		StringBuilder strCadena = new StringBuilder();
		for (Direccion direccion : pListDireccion) {
			DireccionDTO direccionDTO = new DireccionDTO(String.valueOf(direccion.getUsuarioId()),
														 direccion.getStrDireccion(),
														 obtenerCadenaTerritorio(direccion.getTerritorioDireccion(),strCadena),
														 direccion.getStrDireccionLatitud(),
														 direccion.getStrDireccionLongitud(),
														 direccion.getStrNombreDireccion());
			listDireccionesDTO.add(direccionDTO);
		}
		return listDireccionesDTO;
	}
	
	
	/**
	 * @author juan.cabuyales
	 * @since 06/06/2020
	 * @Descripcion: metodo encargado de retornar la cadena de territorios
	 * @param pTerritorio a consultar su ascendencia
	 * @param strCadena cadena a retornar
	 * @return cadena con el nombre de los territorios padres 
	 */
	private String obtenerCadenaTerritorio(Territorio pTerritorio,StringBuilder strCadena) {
		if(pTerritorio.getTerritorioPadre() !=  null) {
			strCadena.append(pTerritorio.getStrNombreTerritorio());
			strCadena.append(",");
			obtenerCadenaTerritorio(pTerritorio.getTerritorioPadre(),strCadena);
		}else {
			strCadena.append(pTerritorio.getStrNombreTerritorio());
		}
		return strCadena.toString();
	}

}
