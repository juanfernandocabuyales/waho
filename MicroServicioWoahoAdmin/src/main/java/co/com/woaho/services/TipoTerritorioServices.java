package co.com.woaho.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import co.com.woaho.dto.TipoDto;
import co.com.woaho.enumeraciones.EnumGeneral;
import co.com.woaho.enumeraciones.EnumMensajes;
import co.com.woaho.interfaces.IEquivalenciaIdiomaDao;
import co.com.woaho.interfaces.ITipoTerritorioDao;
import co.com.woaho.interfaces.ITipoTerritorioServices;
import co.com.woaho.modelo.TipoTerritorio;
import co.com.woaho.request.ConsultarTiposRequest;
import co.com.woaho.request.CrearTipoRequest;
import co.com.woaho.request.EliminarRequest;
import co.com.woaho.response.ConsultarTiposResponse;
import co.com.woaho.response.CrearResponse;
import co.com.woaho.response.EliminarResponse;
import co.com.woaho.utilidades.RegistrarLog;
import co.com.woaho.utilidades.Utilidades;

@Service
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TipoTerritorioServices implements ITipoTerritorioServices {
	
	@Autowired
	private ITipoTerritorioDao tipoTerritorioDao;

	@Autowired
	private IEquivalenciaIdiomaDao equivalenciaIdiomaDao;

	private RegistrarLog logs = new RegistrarLog(TipoTerritorioServices.class);

	@Override
	public CrearResponse crearTipoTerritorio(CrearTipoRequest request) {
		CrearResponse crearResponse = new CrearResponse();
		try {
			
			TipoDto tipoDto = request.getTipoDto();
			
			TipoTerritorio tipo = new TipoTerritorio();
			tipo.setStrNombreTipo(tipoDto.getNombre());
			
			tipoTerritorioDao.guardarActualizar(tipo);
			
			crearResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_POSITIVA.getValor());
			crearResponse.setMensajeRespuesta(EnumGeneral.OK.getValor());			
		}catch(Exception e) {
			logs.registrarLogError("crearTipoTerritorio", "No se ha podido procesar la peticion", e);
			crearResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
			crearResponse.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.NO_CATEGORIAS.getMensaje(),
					request.getIdioma(), equivalenciaIdiomaDao));
		}
		return crearResponse;
	}

	@Override
	public CrearResponse actualizarTipoTerritorio(CrearTipoRequest request) {
		CrearResponse crearResponse = new CrearResponse();
		try {			
			TipoDto tipoDto = request.getTipoDto();			
			TipoTerritorio tipo = tipoTerritorioDao.obtenerTipo(Long.parseLong(tipoDto.getId()));			
			if(null == tipo) {
				crearResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
				crearResponse.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.NO_REGISTROS.getMensaje(), request.getIdioma(), equivalenciaIdiomaDao));
			}else {
				tipo.setStrNombreTipo(tipoDto.getNombre());				
				tipoTerritorioDao.guardarActualizar(tipo);				
				crearResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_POSITIVA.getValor());
				crearResponse.setMensajeRespuesta(EnumGeneral.OK.getValor());	
			}					
		}catch(Exception e) {
			logs.registrarLogError("actualizarTipoTerritorio", "No se ha podido procesar la peticion", e);
			crearResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
			crearResponse.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.NO_CATEGORIAS.getMensaje(),
					request.getIdioma(), equivalenciaIdiomaDao));
		}
		return crearResponse;
	}

	@Override
	public ConsultarTiposResponse consultarTipos(ConsultarTiposRequest request) {
		ConsultarTiposResponse consultarTiposResponse = new ConsultarTiposResponse();
		try {			
			List<TipoTerritorio> listTerritorios = tipoTerritorioDao.obtenerTipos();			
			if(null == listTerritorios || listTerritorios.isEmpty()) {
				consultarTiposResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
				consultarTiposResponse.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.NO_REGISTROS.getMensaje(), request.getIdioma(), equivalenciaIdiomaDao));
			}else {
				List<TipoDto> listTiposDto = new ArrayList<>();
				listTerritorios.forEach( item ->{
					TipoDto tipoDto = new TipoDto();
					tipoDto.setId(String.valueOf(item.getIdTipoTerritorio()));
					tipoDto.setNombre(item.getStrNombreTipo());
					listTiposDto.add(tipoDto);
				});
				consultarTiposResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_POSITIVA.getValor());
				consultarTiposResponse.setMensajeRespuesta(EnumGeneral.OK.getValor());
				consultarTiposResponse.setListTipos(listTiposDto);	
			}			
		}catch(Exception e) {
			logs.registrarLogError("consultarTipos", "No se ha podido procesar la peticion", e);
			consultarTiposResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
			consultarTiposResponse.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.NO_CATEGORIAS.getMensaje(),
					request.getIdioma(), equivalenciaIdiomaDao));
		}
		return consultarTiposResponse;
	}

	@Override
	public EliminarResponse eliminarTipo(EliminarRequest request) {
		EliminarResponse eliminarResponse = new EliminarResponse();
		try {			
			TipoTerritorio tipo = tipoTerritorioDao.obtenerTipo(Long.parseLong(request.getId()));			
			if(null == tipo) {
				eliminarResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
				eliminarResponse.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.NO_REGISTROS.getMensaje(), request.getIdioma(), equivalenciaIdiomaDao));
			}else {
				tipoTerritorioDao.eliminarTipo(tipo);
				eliminarResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_POSITIVA.getValor());
				eliminarResponse.setMensajeRespuesta(EnumGeneral.OK.getValor());
			}			
		}catch(Exception e) {
			logs.registrarLogError("eliminarTipo", "No se ha podido procesar la peticion", e);
			eliminarResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
			eliminarResponse.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.NO_CATEGORIAS.getMensaje(),
					request.getIdioma(), equivalenciaIdiomaDao));
		}
		return eliminarResponse;
	}

}
