package co.com.woaho.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import co.com.woaho.dto.MonedaDto;
import co.com.woaho.enumeraciones.EnumGeneral;
import co.com.woaho.enumeraciones.EnumMensajes;
import co.com.woaho.interfaces.IEquivalenciaIdiomaDao;
import co.com.woaho.interfaces.IMonedaDao;
import co.com.woaho.interfaces.IMonedaServices;
import co.com.woaho.interfaces.ITarifaDao;
import co.com.woaho.interfaces.ITerritorioDao;
import co.com.woaho.modelo.Moneda;
import co.com.woaho.modelo.Servicio;
import co.com.woaho.modelo.Tarifa;
import co.com.woaho.modelo.Territorio;
import co.com.woaho.request.ConsultarMonedasRequest;
import co.com.woaho.request.CrearMonedaRequest;
import co.com.woaho.request.EliminarMonedaRequest;
import co.com.woaho.response.ConsultarMonedasResponse;
import co.com.woaho.response.CrearMonedaResponse;
import co.com.woaho.response.EliminarMonedaResponse;
import co.com.woaho.utilidades.RegistrarLog;
import co.com.woaho.utilidades.Utilidades;

@Service
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MonedaService implements IMonedaServices {
	
	@Autowired
	private IMonedaDao monedaDao;
	
	@Autowired
	private IEquivalenciaIdiomaDao equivalenciaIdioma;
	
	@Autowired
	private ITerritorioDao territorioDao;
	
	@Autowired
	private ITarifaDao tarifaDao;

	private RegistrarLog logs = new RegistrarLog(MonedaService.class);

	@Override
	public ConsultarMonedasResponse consultarMonedas(ConsultarMonedasRequest request) {
		ConsultarMonedasResponse consultarMonedasResponse = new ConsultarMonedasResponse();
		try {
			List<Moneda> listMonedas = monedaDao.obtenerMonedas();
			
			if(null == listMonedas || listMonedas.isEmpty()) {
				consultarMonedasResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
				consultarMonedasResponse.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.NO_REGISTROS.getMensaje(), request.getIdioma(), equivalenciaIdioma));
			}else {
				List<MonedaDto> listMonedasDto = new ArrayList<>();
				listMonedas.forEach(item ->{
					MonedaDto monedaDto = new MonedaDto();
					monedaDto.setIdMoneda(String.valueOf(item.getMonedaId()));
					monedaDto.setIdTerritorio(String.valueOf(item.getPais().getIdTerritorio()));
					monedaDto.setNombreMoneda(item.getStrNombre());
					listMonedasDto.add(monedaDto);
				});
				consultarMonedasResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_POSITIVA.getValor());
				consultarMonedasResponse.setListMonedas(listMonedasDto);
				consultarMonedasResponse.setMensajeRespuesta(EnumGeneral.OK.getValor());
			}			
		}catch(Exception e) {
			logs.registrarLogError("consultarMonedas", "No se ha podido procesar la peticion", e);
			consultarMonedasResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
			consultarMonedasResponse.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.NO_REGISTROS.getMensaje(),
					request.getIdioma(), equivalenciaIdioma));
		}
		return consultarMonedasResponse;
	}

	@Override
	public CrearMonedaResponse crearMoneda(CrearMonedaRequest request) {
		CrearMonedaResponse crearMonedaResponse = new CrearMonedaResponse();
		try {
			MonedaDto monedaDto = request.getMoneda();
			Territorio pais = territorioDao.obtenerTerritorio(Long.parseLong(monedaDto.getIdTerritorio()));			
			if(null == pais) {
				crearMonedaResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
				crearMonedaResponse.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.NO_PAISES.getMensaje(), request.getIdioma(), equivalenciaIdioma));
			}else {
				Moneda moneda = new Moneda();
				moneda.setPais(pais);
				moneda.setStrNombre(monedaDto.getNombreMoneda());
				monedaDao.guardarActualizarMoneda(moneda);
				
				crearMonedaResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_POSITIVA.getValor());
				crearMonedaResponse.setMensajeRespuesta(EnumGeneral.OK.getValor());
			}			
		}catch(Exception e) {
			logs.registrarLogError("crearMoneda", "No se ha podido procesar la peticion", e);
			crearMonedaResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
			crearMonedaResponse.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.INCONVENIENTE_EN_OPERACION.getMensaje(),
					request.getIdioma(), equivalenciaIdioma));
		}
		return crearMonedaResponse;
	}

	@Override
	public CrearMonedaResponse actualizarMoneda(CrearMonedaRequest request) {
		CrearMonedaResponse crearMonedaResponse = new CrearMonedaResponse();
		try {
			MonedaDto monedaDto = request.getMoneda();
			Territorio pais = territorioDao.obtenerTerritorio(Long.parseLong(monedaDto.getIdTerritorio()));			
			if(null == pais) {
				crearMonedaResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
				crearMonedaResponse.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.NO_PAISES.getMensaje(), request.getIdioma(), equivalenciaIdioma));
			}else {
				Moneda moneda = monedaDao.obtenerMonedaId(Long.parseLong(monedaDto.getIdMoneda()));
				if(null == moneda) {
					crearMonedaResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
					crearMonedaResponse.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.NO_REGISTROS.getMensaje(), request.getIdioma(), equivalenciaIdioma));
				}else {
					moneda.setPais(pais);
					moneda.setStrNombre(monedaDto.getNombreMoneda());
					monedaDao.guardarActualizarMoneda(moneda);
					
					crearMonedaResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_POSITIVA.getValor());
					crearMonedaResponse.setMensajeRespuesta(EnumGeneral.OK.getValor());
				}				
			}			
		}catch(Exception e) {
			logs.registrarLogError("crearMoneda", "No se ha podido procesar la peticion", e);
			crearMonedaResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
			crearMonedaResponse.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.INCONVENIENTE_EN_OPERACION.getMensaje(),
					request.getIdioma(), equivalenciaIdioma));
		}
		return crearMonedaResponse;
	}

	@Override
	public EliminarMonedaResponse eliminarMoneda(EliminarMonedaRequest request) {
		EliminarMonedaResponse eliminarMonedaResponse = new EliminarMonedaResponse();
		try {
			Moneda moneda = monedaDao.obtenerMonedaId(Long.parseLong(request.getIdMoneda()));
			if(null == moneda) {
				eliminarMonedaResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
				eliminarMonedaResponse.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.NO_REGISTROS.getMensaje(), request.getIdioma(), equivalenciaIdioma));
			}else {
				List<Tarifa> listTarifas = tarifaDao.obtenerTarifas();
				Optional<Tarifa> tarifa = listTarifas.stream().filter(item -> item.getMoneda().getMonedaId().equals(moneda.getMonedaId())).findFirst();
				if(tarifa.isPresent()) {
					eliminarMonedaResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
					eliminarMonedaResponse.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.NO_ELIMINAR_MONEDA.getMensaje(), request.getIdioma(), equivalenciaIdioma));
				}else {
					monedaDao.eliminarMoneda(moneda);				
					eliminarMonedaResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_POSITIVA.getValor());
					eliminarMonedaResponse.setMensajeRespuesta(EnumGeneral.OK.getValor());
				}				
			}			
		}catch(Exception e) {
			logs.registrarLogError("eliminarMoneda", "No se ha podido procesar la peticion", e);
			eliminarMonedaResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
			eliminarMonedaResponse.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.INCONVENIENTE_EN_OPERACION.getMensaje(),
					request.getIdioma(), equivalenciaIdioma));
		}
		return eliminarMonedaResponse;
	}

}
