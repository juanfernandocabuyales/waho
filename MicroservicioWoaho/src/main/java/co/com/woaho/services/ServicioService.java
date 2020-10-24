package co.com.woaho.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;


import co.com.woaho.enumeraciones.EnumGeneral;
import co.com.woaho.enumeraciones.EnumMensajes;
import co.com.woaho.interfaces.IEquivalenciaIdiomaDao;
import co.com.woaho.interfaces.IServicioDao;
import co.com.woaho.interfaces.IServicioServices;
import co.com.woaho.interfaces.ITarifaDao;
import co.com.woaho.modelo.EquivalenciaIdioma;
import co.com.woaho.modelo.Servicio;
import co.com.woaho.modelo.Tarifa;
import co.com.woaho.request.ConsultarServiciosRequest;
import co.com.woaho.response.ConsultarServiciosResponse;
import co.com.woaho.utilidades.Constantes;
import co.com.woaho.utilidades.RegistrarLog;
import co.com.woaho.utilidades.Utilidades;

@Service
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ServicioService implements IServicioServices {

	@Autowired
	private IServicioDao serviciosDao;
	
	@Autowired
	private ITarifaDao tarifaDao;
	
	@Autowired
	private IEquivalenciaIdiomaDao equivalenciaIdiomaDao;

	private RegistrarLog logs = new RegistrarLog(ServicioService.class);	

	@Override
	public ConsultarServiciosResponse consultarServicios(ConsultarServiciosRequest request) {
		ConsultarServiciosResponse consultarServiciosResponse = new ConsultarServiciosResponse();
		logs.registrarLogInfoEjecutaMetodoConParam("consultarServicios","Sin parametros");
		try {
			List<Servicio> listServicios = serviciosDao.consultarServicios();

			if(listServicios == null || listServicios.isEmpty()) {
				consultarServiciosResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
				consultarServiciosResponse.setMensajeRespuesta(EnumMensajes.NO_SERVICIOS.getMensaje());
			}else {
				List<ConsultarServiciosResponse.Servicio> listServiciosDto = new ArrayList<>();
				for(Servicio servicio : listServicios) {
					ConsultarServiciosResponse.Servicio servicioDto = new ConsultarServiciosResponse.Servicio();
					servicioDto.setId(String.valueOf(servicio.getServicioId()));
					servicioDto.setImage(servicio.getImagen().getStrRuta());
					EquivalenciaIdioma equivalencia  = equivalenciaIdiomaDao.obtenerEquivalencia(servicio.getStrNombre());
					if(equivalencia == null) {
						servicioDto.setName(servicio.getStrNombre());
					}else {
						if(request.getIdioma().equalsIgnoreCase(Constantes.IDIOMA_INGLES)) {
							servicioDto.setName(equivalencia.getEquivalenciaIdiomaIngles());
						}else {
							servicioDto.setName(equivalencia.getEquivalenciaIdiomaOriginal());
						}	
					}
					Tarifa tarifa = tarifaDao.obtenerTarifaServicio(servicio.getServicioId());
					if(tarifa == null) {
						servicioDto.setPrice(0);
					}else {
						servicioDto.setPrice(tarifa.getValor());	
					}
					
					servicioDto.setCategory(servicio.getCategoria().getCategoriaId());
					listServiciosDto.add(servicioDto);
				}
				consultarServiciosResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_POSITIVA.getValor());
				consultarServiciosResponse.setListServicios(listServiciosDto);
				consultarServiciosResponse.setMensajeRespuesta(EnumGeneral.OK.getValor());
			}
		}catch(Exception e) {
			logs.registrarLogError("consultarServicios", "No se ha podido procesar la peticion", e);
			consultarServiciosResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
			consultarServiciosResponse.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.NO_SERVICIOS.getMensaje(), request.getIdioma(), equivalenciaIdiomaDao));
		}
		return consultarServiciosResponse;
	}

	@Override
	public ConsultarServiciosResponse consultarServiciosCategoria(ConsultarServiciosRequest request) {
		ConsultarServiciosResponse consultarServiciosResponse = new ConsultarServiciosResponse();
		logs.registrarLogInfoEjecutaMetodoConParam("consultarServicios","");
		try {
			List<Servicio> listServicios = serviciosDao.consultarServiciosCategoria(Long.valueOf(request.getIdCategoria()));

			if(listServicios == null || listServicios.isEmpty()) {
				consultarServiciosResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
				consultarServiciosResponse.setMensajeRespuesta(EnumMensajes.NO_SERVICIOS.getMensaje());
			}else {
				List<ConsultarServiciosResponse.Servicio> listServiciosDto = new ArrayList<>();
				for(Servicio servicio : listServicios) {
					ConsultarServiciosResponse.Servicio servicioDto = new ConsultarServiciosResponse.Servicio();
					servicioDto.setId(String.valueOf(servicio.getServicioId()));
					servicioDto.setImage(servicio.getImagen().getStrRuta());
					EquivalenciaIdioma equivalencia  = equivalenciaIdiomaDao.obtenerEquivalencia(servicio.getStrNombre());
					if(equivalencia == null) {
						servicioDto.setName(servicio.getStrNombre());
					}else {
						if(request.getIdioma().equalsIgnoreCase(Constantes.IDIOMA_INGLES)) {
							servicioDto.setName(equivalencia.getEquivalenciaIdiomaIngles());
						}else {
							servicioDto.setName(equivalencia.getEquivalenciaIdiomaOriginal());
						}	
					}
					Tarifa tarifa = tarifaDao.obtenerTarifaServicio(servicio.getServicioId());
					if(tarifa == null) {
						servicioDto.setPrice(0);
					}else {
						servicioDto.setPrice(tarifa.getValor());	
					}
					servicioDto.setCategory(servicio.getCategoria().getCategoriaId());
					listServiciosDto.add(servicioDto);
				}
				consultarServiciosResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_POSITIVA.getValor());
				consultarServiciosResponse.setListServicios(listServiciosDto);
				consultarServiciosResponse.setMensajeRespuesta(EnumGeneral.OK.getValor());
			}
		}catch(Exception e) {
			logs.registrarLogError("consultarServiciosCategoria", "No se ha podido procesar la peticion", e);
			consultarServiciosResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
			consultarServiciosResponse.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.NO_SERVICIOS.getMensaje(), request.getIdioma(), equivalenciaIdiomaDao));
		}
		return consultarServiciosResponse;
	}

}
