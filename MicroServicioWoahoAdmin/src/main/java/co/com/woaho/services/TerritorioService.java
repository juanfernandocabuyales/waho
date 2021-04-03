package co.com.woaho.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import co.com.woaho.dto.TerritorioDto;
import co.com.woaho.enumeraciones.EnumGeneral;
import co.com.woaho.enumeraciones.EnumMensajes;
import co.com.woaho.interfaces.IEquivalenciaIdiomaDao;
import co.com.woaho.interfaces.ITerritorioDao;
import co.com.woaho.interfaces.ITerritorioService;
import co.com.woaho.modelo.Territorio;
import co.com.woaho.request.ConsultarTerritorioRequest;
import co.com.woaho.request.ConsultarTerritoriosRequest;
import co.com.woaho.request.CrearTerritoriosRequest;
import co.com.woaho.request.EliminarRequest;
import co.com.woaho.response.ConsultarTerritorioResponse;
import co.com.woaho.response.ConsultarTerritoriosResponse;
import co.com.woaho.response.CrearResponse;
import co.com.woaho.response.EliminarResponse;
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
			List<Territorio> listTerritorio = territorioDao.obtenerTerritorios(Long.parseLong(request.getTipoTerritorio()));
			
			if(listTerritorio != null && !listTerritorio.isEmpty()) {
				List<ConsultarTerritorioResponse.PaisDTO> listPaisesDto = new ArrayList<>();
				listTerritorio.forEach(item ->{
					ConsultarTerritorioResponse.PaisDTO pais = new ConsultarTerritorioResponse.PaisDTO();
					pais.setIdTerritorio(String.valueOf(item.getIdTerritorio()));
					pais.setNombreTerritorio(item.getStrNombreTerritorio());
					listPaisesDto.add(pais);
				});
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

	@Override
	public ConsultarTerritoriosResponse consultarTerritorios(ConsultarTerritoriosRequest request) {
		ConsultarTerritoriosResponse consultarTerritoriosResponse = new ConsultarTerritoriosResponse();
		try {			
			List<Territorio> listTerritorios = territorioDao.obtenerTerritorios();			
			if(null == listTerritorios || listTerritorios.isEmpty()) {
				consultarTerritoriosResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
				consultarTerritoriosResponse.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.NO_REGISTROS.getMensaje(), request.getIdioma(), equivalenciaIdiomaDao));
			}else {
				List<TerritorioDto> listTerritoriosDto = new ArrayList<>();
				listTerritorios.forEach( item ->{
					TerritorioDto territorioDto = new TerritorioDto();
					territorioDto.setId(String.valueOf(item.getIdTerritorio()));
					territorioDto.setNombre(item.getStrNombreTerritorio());
					territorioDto.setIdPadre(String.valueOf(item.getTerritorioPadre().getIdTerritorio()));
					territorioDto.setIdTipo(String.valueOf(item.getTipoTerritorio().getIdTipoTerritorio()));
					territorioDto.setCodigo(item.getStrCodigoTerritorio());
					territorioDto.setIdImagen(String.valueOf(item.getTerritorioImagen().getImagenId()));
					listTerritoriosDto.add(territorioDto);
				});
				consultarTerritoriosResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_POSITIVA.getValor());
				consultarTerritoriosResponse.setMensajeRespuesta(EnumGeneral.OK.getValor());
				consultarTerritoriosResponse.setListTerritorios(listTerritoriosDto);
			}			
		}catch(Exception e) {
			logs.registrarLogError("consultarTerritorios", "No se ha podido procesar la peticion", e);
			consultarTerritoriosResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
			consultarTerritoriosResponse.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.INCONVENIENTE_EN_OPERACION.getMensaje(), request.getIdioma(), equivalenciaIdiomaDao));
		}
		return consultarTerritoriosResponse;
	}

	@Override
	public CrearResponse crearTerritorios(CrearTerritoriosRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CrearResponse actualizarTerritorios(CrearTerritoriosRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EliminarResponse eliminarTerritorios(EliminarRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
