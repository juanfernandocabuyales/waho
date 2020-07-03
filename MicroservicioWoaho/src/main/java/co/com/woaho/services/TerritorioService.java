package co.com.woaho.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.com.respuestas.JsonGenerico;
import co.com.respuestas.RespuestaNegativa;
import co.com.respuestas.RespuestaPositiva;
import co.com.woaho.dto.PaisDTO;
import co.com.woaho.enumeraciones.EnumGeneral;
import co.com.woaho.enumeraciones.EnumMensajes;
import co.com.woaho.interfaces.ITerritorioDao;
import co.com.woaho.interfaces.ITerritorioService;
import co.com.woaho.modelo.Territorio;
import co.com.woaho.utilidades.RegistrarLog;
import co.com.woaho.utilidades.Utilidades;

@Service
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TerritorioService implements ITerritorioService {

	@Autowired
	private ITerritorioDao territorioDao;
	
	private RegistrarLog logs = new RegistrarLog(TerritorioService.class);	
	
	@Override
	public String obtenerTerritorios(String pStrTipoTerritorio) {
		ObjectMapper mapper = new ObjectMapper();
		String resultado = null;
		RespuestaNegativa respuestaNegativa = new RespuestaNegativa();
		respuestaNegativa.setCodigoServicio(EnumGeneral.SERVICIO_CONSULTAR_PAISES.getValorInt());
		logs.registrarLogInfoEjecutaMetodoConParam("obtenerTerritorios",pStrTipoTerritorio);
		try {
			List<Territorio> listTerritorio = territorioDao.obtenerTerritorios(pStrTipoTerritorio);
			
			if(listTerritorio != null && !listTerritorio.isEmpty()) {
				JsonGenerico<PaisDTO> objetoJson = new JsonGenerico<>();
				
				for(Territorio territorio: listTerritorio) {
					PaisDTO pais = new PaisDTO(String.valueOf(territorio.getIdTerritorio()), territorio.getStrNombreTerritorio(), territorio.getStrCodigoTerritorio());
					objetoJson.add(pais);
				}
				
				RespuestaPositiva respuestaPositiva = new RespuestaPositiva(
						EnumGeneral.SERVICIO_CONSULTAR_PAISES.getValorInt(), objetoJson);
				resultado = mapper.writeValueAsString(respuestaPositiva);
				
			}else {
				resultado = Utilidades.getInstance().procesarException(EnumGeneral.SERVICIO_CONSULTAR_PAISES.getValorInt(), EnumMensajes.NO_PAISES.getMensaje());
			}
		}catch (Exception e) {
			logs.registrarLogError("obtenerTerritorios", "No se ha podido procesar la peticion", e);
			resultado = Utilidades.getInstance().procesarException(EnumGeneral.SERVICIO_CONSULTAR_PAISES.getValorInt(), EnumMensajes.NO_PAISES.getMensaje());
		}
		return resultado;
	}

}
