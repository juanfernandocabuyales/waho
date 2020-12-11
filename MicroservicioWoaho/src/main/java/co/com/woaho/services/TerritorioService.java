package co.com.woaho.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import co.com.woaho.dto.PaisDTO;
import co.com.woaho.enumeraciones.EnumGeneral;
import co.com.woaho.enumeraciones.EnumMensajes;
import co.com.woaho.interfaces.IEquivalenciaIdiomaDao;
import co.com.woaho.interfaces.ITerritorioDao;
import co.com.woaho.interfaces.ITerritorioService;
import co.com.woaho.modelo.Territorio;
import co.com.woaho.request.ConsultarTerritorioRequest;
import co.com.woaho.response.ConsultarTerritorioResponse;
import co.com.woaho.utilidades.RegistrarLog;
import co.com.woaho.utilidades.Utilidades;

@Service
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TerritorioService implements ITerritorioService {

	@Autowired
	private ITerritorioDao territorioDao;
	
	@Autowired
	private IEquivalenciaIdiomaDao equivalenciaIdiomaDao;
	
	private RegistrarLog logs = new RegistrarLog(TerritorioService.class);	
	
	@Override
	public ConsultarTerritorioResponse obtenerTerritorios(ConsultarTerritorioRequest request) {
		logs.registrarLogInfoEjecutaMetodo("obtenerTerritorios");
		ConsultarTerritorioResponse response = new ConsultarTerritorioResponse();
		try {
			List<Territorio> listTerritorio = territorioDao.obtenerTerritorios(request.getTipoTerritorio());
			
			if(listTerritorio != null && !listTerritorio.isEmpty()) {
				List<PaisDTO> listPaisesDto = new ArrayList<>();
				
				for(Territorio territorio: listTerritorio) {
					PaisDTO pais = new PaisDTO(String.valueOf(territorio.getIdTerritorio()),
											   territorio.getStrNombreTerritorio(),
											   territorio.getStrCodigoTerritorio(),
											   (null == territorio.getTerritorioImagen() ? "*" :territorio.getTerritorioImagen().getStrRuta()));
					listPaisesDto.add(pais);
				}
				response.setCodigoRespuesta(EnumGeneral.RESPUESTA_POSITIVA.getValor());
				response.setMensajeRespuesta(EnumGeneral.OK.getValor());
				response.setLisPaisesDto(listPaisesDto);				
			}else {
				response.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
				response.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.NO_PAISES.getMensaje(), request.getIdioma(), equivalenciaIdiomaDao));
			}
		}catch (Exception e) {
			logs.registrarLogError("obtenerTerritorios", "No se ha podido procesar la peticion", e);
			response.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
			response.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.INCONVENIENTE_EN_OPERACION.getMensaje(), request.getIdioma(), equivalenciaIdiomaDao));
		}
		return response;
	}

}
