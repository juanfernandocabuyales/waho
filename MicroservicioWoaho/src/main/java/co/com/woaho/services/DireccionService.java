package co.com.woaho.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import co.com.woaho.enumeraciones.EnumGeneral;
import co.com.woaho.enumeraciones.EnumMensajes;
import co.com.woaho.interfaces.IDireccionDao;
import co.com.woaho.interfaces.IDireccionService;
import co.com.woaho.interfaces.IEquivalenciaIdiomaDao;
import co.com.woaho.interfaces.IUsuarioDao;
import co.com.woaho.modelo.Direccion;
import co.com.woaho.modelo.Estado;
import co.com.woaho.modelo.Territorio;
import co.com.woaho.modelo.Usuario;
import co.com.woaho.request.ActualizarCrearDireccionRequest;
import co.com.woaho.request.ConsultarDireccionRequest;
import co.com.woaho.response.ActualizarCrearDireccionResponse;
import co.com.woaho.response.ConsultarDireccionResponse;
import co.com.woaho.utilidades.Constantes;
import co.com.woaho.utilidades.RegistrarLog;
import co.com.woaho.utilidades.Utilidades;

@Service
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class DireccionService implements IDireccionService {

	private RegistrarLog logs = new RegistrarLog(DireccionService.class);

	@Autowired
	private IDireccionDao direccionDao;
	
	@Autowired
	private IUsuarioDao usuarioDao;
	
	@Autowired
	private IEquivalenciaIdiomaDao equivalenciaDao;

	@Override
	public ConsultarDireccionResponse obtenerDireccionesUsuario(ConsultarDireccionRequest request) {
		ConsultarDireccionResponse response = new ConsultarDireccionResponse();
		try {
			List<Direccion> listDirecciones = direccionDao.obtenerDireccionesUsuario(Long.parseLong(request.getIdUsuario()));
			if(listDirecciones == null || listDirecciones.isEmpty()) {
				response.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
				response.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.NO_DIRECCIONES.getMensaje(), request.getIdioma(), equivalenciaDao));
			}else {
				List<ConsultarDireccionResponse.Direccion> listDireccionesDTO = new ArrayList<>();
				for(Direccion direccion : listDirecciones) {
					ConsultarDireccionResponse.Direccion direccionDTO = new ConsultarDireccionResponse.Direccion();
					direccionDTO.setId(String.valueOf(direccion.getDireccionId()));
					direccionDTO.setLocation(new ConsultarDireccionResponse.Direccion.Location());
					direccionDTO.getLocation().setLat(direccion.getStrDireccionLatitud());
					direccionDTO.getLocation().setLng(direccion.getStrDireccionLongitud());
					direccionDTO.setPlaceId(direccion.getStrLugarId());
					direccionDTO.setMainAddress(direccion.getStrDireccion());
					direccionDTO.setName(direccion.getStrNombreDireccion());
					direccionDTO.setSecondaryAddress(Constantes.ASTERISCO);
					direccionDTO.setHome(Constantes.ASTERISCO);
					listDireccionesDTO.add(direccionDTO);
				}
				response.setCodigoRespuesta(EnumGeneral.RESPUESTA_POSITIVA.getValor());
				response.setMensajeRespuesta(EnumGeneral.OK.getValor());
				response.setListDireccion(listDireccionesDTO);
			}
		}catch(Exception e) {
			logs.registrarLogError("obtenerDireccionesUsuario", "No se ha podido procesar la peticion", e);
			response.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
			response.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.INCONVENIENTE_EN_OPERACION.getMensaje(),
					request.getIdioma(), equivalenciaDao));
		}
		return response;
	}

	@Override
	public ActualizarCrearDireccionResponse crearActualizarDireccion(ActualizarCrearDireccionRequest request) {
		ActualizarCrearDireccionResponse actualizarCrearDireccionResponse = new ActualizarCrearDireccionResponse();
		try {
			
			Usuario usuarioDireccion = usuarioDao.obtenerUsuarioId(Long.parseLong(request.getDireccionDto().getIdUsuario()));
			
			if(usuarioDireccion == null) {
				actualizarCrearDireccionResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
				actualizarCrearDireccionResponse.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.NO_USUARIO_VALIDO.getMensaje(),
						request.getIdioma(),equivalenciaDao));
			}else {
				Direccion direccionModelo = new Direccion();
				if(null != request.getDireccionDto().getId() && !request.getDireccionDto().getId().isEmpty()) {
					direccionModelo.setDireccionId(Long.parseLong(request.getDireccionDto().getId()));	
				}
				direccionModelo.setStrNombreDireccion(request.getDireccionDto().getName());
				direccionModelo.setStrDireccion(request.getDireccionDto().getMainAddress());
				direccionModelo.setUsuarioDireccion(usuarioDireccion);
				direccionModelo.setStrDireccionLatitud(request.getDireccionDto().getLocation().getLat());
				direccionModelo.setStrDireccionLongitud(request.getDireccionDto().getLocation().getLng());
				direccionModelo.setStrLugarId(request.getDireccionDto().getPlaceId());
				direccionModelo.setStrEdificacion(request.getDireccionDto().getSecondaryAddress());
				
				direccionModelo.setTerritorioDireccion(new Territorio());
				direccionModelo.getTerritorioDireccion().setIdTerritorio(Long.parseLong(request.getDireccionDto().getIdTerritorio()));
				
				direccionModelo.setEstadoDireccion(new Estado());
				direccionModelo.getEstadoDireccion().setEstadoId(Long.parseLong(request.getDireccionDto().getIdEstado()));
				
				direccionDao.crearActualizarDireccion(direccionModelo);
				
				actualizarCrearDireccionResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_POSITIVA.getValor());
				actualizarCrearDireccionResponse.setMensajeRespuesta(EnumMensajes.OK.getMensaje());
			}			
		}catch(Exception e) {
			logs.registrarLogError("crearActualizarDireccion", "No se ha podido procesar la peticion", e);
			actualizarCrearDireccionResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
			actualizarCrearDireccionResponse.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.INCONVENIENTE_EN_OPERACION.getMensaje(),
					request.getIdioma(), equivalenciaDao));
		}
		return actualizarCrearDireccionResponse;
	}

}
