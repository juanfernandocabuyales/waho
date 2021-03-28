package co.com.woaho.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import co.com.woaho.dto.UnidadDto;
import co.com.woaho.enumeraciones.EnumGeneral;
import co.com.woaho.enumeraciones.EnumMensajes;
import co.com.woaho.interfaces.IEquivalenciaIdiomaDao;
import co.com.woaho.interfaces.IUnidadDao;
import co.com.woaho.interfaces.IUnidadServices;
import co.com.woaho.modelo.UnidadTarifa;
import co.com.woaho.request.ConsultarUnidadesRequest;
import co.com.woaho.request.CrearUnidadRequest;
import co.com.woaho.request.EliminarRequest;
import co.com.woaho.response.ConsultarUnidadesResponse;
import co.com.woaho.response.CrearUnidadResponse;
import co.com.woaho.response.EliminarResponse;
import co.com.woaho.utilidades.RegistrarLog;
import co.com.woaho.utilidades.Utilidades;

@Service
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UnidadServices implements IUnidadServices {

	@Autowired
	private IUnidadDao unidadDao;

	@Autowired
	private IEquivalenciaIdiomaDao equivalenciaIdioma;

	private RegistrarLog logs = new RegistrarLog(UnidadServices.class);

	@Override
	public ConsultarUnidadesResponse consultarUnidades(ConsultarUnidadesRequest request) {
		ConsultarUnidadesResponse consultarUnidadesResponse = new ConsultarUnidadesResponse();
		try {
			List<UnidadTarifa> listUnidades = unidadDao.obtenerUnidades();

			if(null == listUnidades || listUnidades.isEmpty()) {
				consultarUnidadesResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
				consultarUnidadesResponse.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.NO_REGISTROS.getMensaje(), request.getIdioma(), equivalenciaIdioma));
			}else {
				List<UnidadDto> listUnidadesDto = new ArrayList<>();
				listUnidades.forEach(item ->{
					UnidadDto unidadDto = new UnidadDto();
					unidadDto.setIdUnidad(String.valueOf(item.getUnidadTarifaId()));
					unidadDto.setNombreUnidad(item.getStrNombre());
					listUnidadesDto.add(unidadDto);
				});
				consultarUnidadesResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_POSITIVA.getValor());
				consultarUnidadesResponse.setListUnidades(listUnidadesDto);
				consultarUnidadesResponse.setMensajeRespuesta(EnumGeneral.OK.getValor());
			}
		}catch(Exception e) {
			logs.registrarLogError("consultarUnidades", "No se ha podido procesar la peticion", e);
			consultarUnidadesResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
			consultarUnidadesResponse.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.NO_REGISTROS.getMensaje(),
					request.getIdioma(), equivalenciaIdioma));
		}
		return consultarUnidadesResponse;
	}

	@Override
	public CrearUnidadResponse crearUnidad(CrearUnidadRequest request) {
		CrearUnidadResponse crearUnidadResponse = new CrearUnidadResponse();
		try {
			UnidadDto unidadDto = request.getUnidad();

			UnidadTarifa unidad = new UnidadTarifa();
			unidad.setStrNombre(unidadDto.getNombreUnidad());

			unidadDao.guardarActualizarUnidad(unidad);

			crearUnidadResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_POSITIVA.getValor());
			crearUnidadResponse.setMensajeRespuesta(EnumGeneral.OK.getValor());			
		}catch(Exception e) {
			logs.registrarLogError("crearMoneda", "No se ha podido procesar la peticion", e);
			crearUnidadResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
			crearUnidadResponse.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.INCONVENIENTE_EN_OPERACION.getMensaje(),
					request.getIdioma(), equivalenciaIdioma));
		}
		return crearUnidadResponse;
	}

	@Override
	public CrearUnidadResponse actualizarUnidad(CrearUnidadRequest request) {
		CrearUnidadResponse crearUnidadResponse = new CrearUnidadResponse();
		try {
			UnidadDto unidadDto = request.getUnidad();

			UnidadTarifa unidad = unidadDao.obtenerId(Long.parseLong(unidadDto.getIdUnidad()));

			if(null == unidad) {
				crearUnidadResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
				crearUnidadResponse.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.NO_REGISTROS.getMensaje(), request.getIdioma(), equivalenciaIdioma));
			}else {
				unidad.setStrNombre(unidadDto.getNombreUnidad());				
				unidadDao.guardarActualizarUnidad(unidad);

				crearUnidadResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_POSITIVA.getValor());
				crearUnidadResponse.setMensajeRespuesta(EnumGeneral.OK.getValor());	
			}					
		}catch(Exception e) {
			logs.registrarLogError("crearMoneda", "No se ha podido procesar la peticion", e);
			crearUnidadResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
			crearUnidadResponse.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.INCONVENIENTE_EN_OPERACION.getMensaje(),
					request.getIdioma(), equivalenciaIdioma));
		}
		return crearUnidadResponse;
	}

	@Override
	public EliminarResponse eliminarUnidad(EliminarRequest request) {
		EliminarResponse eliminarResponse = new EliminarResponse();
		try {
			UnidadTarifa unidad = unidadDao.obtenerId(Long.parseLong(request.getId()));

			if(null == unidad) {
				eliminarResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
				eliminarResponse.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.NO_REGISTROS.getMensaje(), request.getIdioma(), equivalenciaIdioma));
			}else {
				unidadDao.eliminarUnidad(unidad);
				eliminarResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_POSITIVA.getValor());
				eliminarResponse.setMensajeRespuesta(EnumGeneral.OK.getValor());
			}
		}catch(Exception e) {
			logs.registrarLogError("eliminarUnidad", "No se ha podido procesar la peticion", e);
			eliminarResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
			eliminarResponse.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.INCONVENIENTE_EN_OPERACION.getMensaje(),
					request.getIdioma(), equivalenciaIdioma));
		}
		return eliminarResponse;
	}

}
