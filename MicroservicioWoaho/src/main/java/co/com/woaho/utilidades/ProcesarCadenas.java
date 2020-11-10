package co.com.woaho.utilidades;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import co.com.respuestas.JsonGenerico;
import co.com.woaho.dto.DireccionDTO;
import co.com.woaho.dto.MensajeDTO;
import co.com.woaho.enumeraciones.EnumGeneral;
import co.com.woaho.modelo.Direccion;
import co.com.woaho.modelo.Idioma;
import co.com.woaho.modelo.Profesion;
import co.com.woaho.modelo.Servicio;
import co.com.woaho.modelo.Territorio;
import co.com.woaho.response.ConsultarPantallasResponse;

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
			DireccionDTO direccionDTO = new DireccionDTO(String.valueOf(direccion.getDireccionId()),
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

	public String obtenerIdiomas(List<Idioma> listIdiomas) {
		StringBuilder strBuilder = new StringBuilder();
		for (int i = 0; i < listIdiomas.size(); i++) {
			strBuilder.append(listIdiomas.get(i).getStrNombre());
			if(i<listIdiomas.size()) {
				strBuilder.append(EnumGeneral.COMA.getValor());
			}
		}
		return  strBuilder.toString();
	}

	public String procesarCadenas(List<Servicio> listServicios) {
		StringBuilder strBuilder = new StringBuilder();
		for (int i = 0; i < listServicios.size(); i++) {
			strBuilder.append(listServicios.get(i).getStrNombre());
			if(i<listServicios.size()) {
				strBuilder.append(EnumGeneral.COMA.getValor());
			}
		}
		return  strBuilder.toString();
	}

	public String obtenerProfesiones(List<Profesion> listProfesion) {
		StringBuilder strBuilder = new StringBuilder();
		for (int i = 0; i < listProfesion.size(); i++) {
			strBuilder.append(listProfesion.get(i).getStrNombre());
			if(i<listProfesion.size()) {
				strBuilder.append(EnumGeneral.COMA.getValor());
			}
		}
		return strBuilder.toString();
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
	
	public List<ConsultarPantallasResponse.Slide> obtenerSlides(String pStrTrama){
		List<ConsultarPantallasResponse.Slide> listSlides = new ArrayList<>();
		StringTokenizer tokens = new StringTokenizer(pStrTrama,"|");
		while(tokens.hasMoreTokens()) {
			String [] elementos = tokens.nextToken().split("\\;");
			ConsultarPantallasResponse.Slide slide = new ConsultarPantallasResponse.Slide();
			slide.setImage(elementos[2]);
			List<MensajeDTO> listMensajes = obtenerMensajesCadena(elementos[3]);
			for(MensajeDTO mensaje : listMensajes) {
				if(mensaje.getStrTipo().equalsIgnoreCase(Constantes.TITULO)) {
					slide.setTitle(mensaje.getStrMensaje());
				}else if(mensaje.getStrTipo().equalsIgnoreCase(Constantes.SUB_TITULO)) {
					slide.setSubtitle(mensaje.getStrMensaje());
				}else {
					slide.setFooter(mensaje.getStrMensaje());
				}
			}
			listSlides.add(slide);
		}		
		return listSlides;
	}
}
