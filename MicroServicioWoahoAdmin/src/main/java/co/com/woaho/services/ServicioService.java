package co.com.woaho.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import co.com.woaho.dto.ServicioDto;
import co.com.woaho.dto.TarifaDto;
import co.com.woaho.enumeraciones.EnumGeneral;
import co.com.woaho.enumeraciones.EnumMensajes;
import co.com.woaho.interfaces.IEquivalenciaIdiomaDao;
import co.com.woaho.interfaces.IServicioDao;
import co.com.woaho.interfaces.IServicioServices;
import co.com.woaho.interfaces.ITarifaDao;
import co.com.woaho.modelo.Categoria;
import co.com.woaho.modelo.EquivalenciaIdioma;
import co.com.woaho.modelo.Imagen;
import co.com.woaho.modelo.Moneda;
import co.com.woaho.modelo.Servicio;
import co.com.woaho.modelo.Tarifa;
import co.com.woaho.modelo.Territorio;
import co.com.woaho.modelo.UnidadTarifa;
import co.com.woaho.request.ConsultarServiciosRequest;
import co.com.woaho.request.CrearServicioRequest;
import co.com.woaho.response.ConsultarServiciosResponse;
import co.com.woaho.response.CrearServicioResponse;
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
					servicioDto.setCodigo(String.valueOf(servicio.getServicioId()));
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
					List<Tarifa> tarifasList = tarifaDao.obtenerTarifaServicio(servicio.getServicioId());
					
					if(null == tarifasList || tarifasList.isEmpty()) {
						servicioDto.setListTarifas(new ArrayList<>());
					}else {
						List<ConsultarServiciosResponse.Servicio.TarifaServicio> tarifasListDto = new ArrayList<>();
						tarifasList.forEach(tarifa -> {
							ConsultarServiciosResponse.Servicio.TarifaServicio tarifaServicioDto = new ConsultarServiciosResponse.Servicio.TarifaServicio();
							tarifaServicioDto.setMoneda(String.valueOf(tarifa.getMoneda().getMonedaId()));
							tarifaServicioDto.setPais(String.valueOf(tarifa.getPais().getIdTerritorio()));
							tarifaServicioDto.setValor(tarifa.getValor());
							tarifaServicioDto.setUnidad(String.valueOf(tarifa.getUnidadTarifa().getUnidadTarifaId()));
							tarifasListDto.add(tarifaServicioDto);
						});
						servicioDto.setListTarifas(tarifasListDto);	
					}									
					servicioDto.setDescription(Utilidades.getInstance().obtenerEquivalencia(servicio.getStrDescripcion(), request.getIdioma(), equivalenciaIdiomaDao));
					servicioDto.setCategory(servicio.getCategoria().getCategoriaId());
					servicioDto.setClicks(Long.parseLong("0"));
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
	public CrearServicioResponse crearServicio(CrearServicioRequest request) {
		CrearServicioResponse crearServicioResponse = new CrearServicioResponse();
		try {
			ServicioDto servicioDto = request.getServicioDto();
			
			Servicio servicio = new Servicio();
			servicio.setStrNombre(servicioDto.getNombre());
			
			Imagen imagenServicio = new Imagen();
			imagenServicio.setImagenId(Long.parseLong(servicioDto.getImagen()));
			
			Categoria categoria = new Categoria();
			categoria.setCategoriaId(Long.parseLong(servicioDto.getCategoria()));
			
			Territorio pais = new Territorio();
			pais.setIdTerritorio(Long.parseLong(servicioDto.getPais()));
			
			servicio.setImagen(imagenServicio);
			servicio.setCategoria(categoria);
			servicio.setStrDescripcion(servicioDto.getDescripcion());
			servicio.setPais(pais);
			
			servicio = serviciosDao.guardarActualizarServicio(servicio);
			
			for( TarifaDto item: servicioDto.getListTarifas()) {
				Tarifa tarifaServicio = new Tarifa();
				tarifaServicio.setValor(item.getValor());
				
				Moneda moneda = new Moneda();
				moneda.setMonedaId(Long.parseLong(item.getMoneda()));
				
				Territorio paisTarifa = new Territorio();
				paisTarifa.setIdTerritorio(Long.parseLong(item.getPais()));
				
				UnidadTarifa unidad = new UnidadTarifa();
				unidad.setUnidadTarifaId(Long.parseLong(item.getUnidad()));
				
				tarifaServicio.setMoneda(moneda);
				tarifaServicio.setPais(paisTarifa);
				tarifaServicio.setServicio(servicio);
				tarifaServicio.setUnidadTarifa(unidad);
				tarifaDao.guardarActualizarTarifa(tarifaServicio);
			}
			
			crearServicioResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_POSITIVA.getValor());
			crearServicioResponse.setMensajeRespuesta(EnumGeneral.OK.getValor());
		}catch(Exception e) {
			logs.registrarLogError("crearServicio", "No se ha podido procesar la peticion", e);
			crearServicioResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
			crearServicioResponse.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.NO_SOLICITUD.getMensaje(), request.getIdioma(), equivalenciaIdiomaDao));
		}
		return crearServicioResponse;
	}		
}
