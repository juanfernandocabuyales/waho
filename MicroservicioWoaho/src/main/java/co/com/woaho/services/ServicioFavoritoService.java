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
import co.com.woaho.interfaces.IServicioFavoritoDao;
import co.com.woaho.interfaces.IServicioFavoritoService;
import co.com.woaho.interfaces.ITarifaDao;
import co.com.woaho.modelo.EquivalenciaIdioma;
import co.com.woaho.modelo.Servicio;
import co.com.woaho.modelo.ServicioFavorito;
import co.com.woaho.modelo.Tarifa;
import co.com.woaho.modelo.Usuario;
import co.com.woaho.request.ConsultarServiciosFavoritosRequest;
import co.com.woaho.request.CrearServicioFavoritoRequest;
import co.com.woaho.request.EliminarServicioFavoritoRequest;
import co.com.woaho.response.ConsultarServiciosResponse;
import co.com.woaho.response.CrearServicioFavoritoResponse;
import co.com.woaho.response.EliminarServicioFavoritoResponse;
import co.com.woaho.utilidades.Constantes;
import co.com.woaho.utilidades.RegistrarLog;
import co.com.woaho.utilidades.Utilidades;

@Service
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ServicioFavoritoService implements IServicioFavoritoService {

	@Autowired
	private IServicioFavoritoDao servicioFavoritoDao;
	
	@Autowired
	private IEquivalenciaIdiomaDao equivalenciaIdiomaDao;
	
	@Autowired
	private ITarifaDao tarifaDao;

	private RegistrarLog logs = new RegistrarLog(ServicioFavoritoService.class);
	
	@Override
	public ConsultarServiciosResponse consultarFavoritos(ConsultarServiciosFavoritosRequest request) {
		ConsultarServiciosResponse consultarServiciosResponse = new ConsultarServiciosResponse();
		try {
			
			List<ServicioFavorito> listServiciosFavoritos = servicioFavoritoDao.obtenerServiciosUsuario(Long.parseLong(request.getIdUsuario()));
			
			if(null == listServiciosFavoritos || listServiciosFavoritos.isEmpty() ) {
				consultarServiciosResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
				consultarServiciosResponse.setMensajeRespuesta(EnumMensajes.NO_SERVICIOS.getMensaje());
			}else {
				List<ConsultarServiciosResponse.Servicio> listServiciosDto = new ArrayList<>();
				listServiciosFavoritos.forEach( item -> {
					ConsultarServiciosResponse.Servicio servicioDto = new ConsultarServiciosResponse.Servicio();
					Servicio servicio = item.getServicioServicio();
					
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
					
					servicioDto.setDescription(Utilidades.getInstance().obtenerEquivalencia(servicio.getStrDescripcion(), request.getIdioma(), equivalenciaIdiomaDao));
					servicioDto.setCategory(servicio.getCategoria().getCategoriaId());
					servicioDto.setClicks(Long.parseLong("0"));
					listServiciosDto.add(servicioDto);
				});
				consultarServiciosResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_POSITIVA.getValor());
				consultarServiciosResponse.setListServicios(listServiciosDto);
				consultarServiciosResponse.setMensajeRespuesta(EnumGeneral.OK.getValor());
			}			
		}catch(Exception e) {
			logs.registrarLogError("consultarFavoritos", "No se ha podido procesar la peticion", e);
			consultarServiciosResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
			consultarServiciosResponse.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.NO_CATEGORIAS.getMensaje(),
					request.getIdioma(), equivalenciaIdiomaDao));
		}
		return consultarServiciosResponse;
	}

	@Override
	public CrearServicioFavoritoResponse crearServicioFavorito(CrearServicioFavoritoRequest request) {
		CrearServicioFavoritoResponse crearServicioFavoritoResponse = new CrearServicioFavoritoResponse();
		try {
			
			ServicioFavorito servicioFavorito = new ServicioFavorito();
			servicioFavorito.setServicioUsuario(new Usuario());
			servicioFavorito.getServicioUsuario().setUsuarioId(Long.parseLong(request.getIdUsuario()));
			
			servicioFavorito.setServicioServicio(new Servicio());
			servicioFavorito.getServicioServicio().setServicioId(Long.parseLong(request.getIdServicio()));
			
			servicioFavoritoDao.guardarServicioFavorito(servicioFavorito);
			
			crearServicioFavoritoResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_POSITIVA.getValor());
			crearServicioFavoritoResponse.setMensajeRespuesta(EnumGeneral.OK.getValor());			
		}catch(Exception e) {
			logs.registrarLogError("crearServicioFavorito", "No se ha podido procesar la peticion", e);
			crearServicioFavoritoResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
			crearServicioFavoritoResponse.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.NO_CATEGORIAS.getMensaje(),
					request.getIdioma(), equivalenciaIdiomaDao));
		}
		return crearServicioFavoritoResponse;
	}

	@Override
	public EliminarServicioFavoritoResponse eliminarServicioFavorito(EliminarServicioFavoritoRequest request) {
		EliminarServicioFavoritoResponse eliminarServicioFavoritoResponse = new EliminarServicioFavoritoResponse();
		try {
			ServicioFavorito servicioFavorito = servicioFavoritoDao.obtenerServicioFavorito(Long.parseLong(request.getIdUsuario()),Long.parseLong(request.getIdServicio()));
			
			if(null == servicioFavorito) {
				eliminarServicioFavoritoResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
				eliminarServicioFavoritoResponse.setMensajeRespuesta(EnumMensajes.NO_SERVICIOS.getMensaje());
			}else {
				servicioFavoritoDao.eliminarServicioFavorito(servicioFavorito);
				eliminarServicioFavoritoResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_POSITIVA.getValor());
				eliminarServicioFavoritoResponse.setMensajeRespuesta(EnumGeneral.OK.getValor());		
			}
		}catch(Exception e) {
			logs.registrarLogError("eliminarServicioFavorito", "No se ha podido procesar la peticion", e);
			eliminarServicioFavoritoResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
			eliminarServicioFavoritoResponse.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.NO_CATEGORIAS.getMensaje(),
					request.getIdioma(), equivalenciaIdiomaDao));
		}
		return eliminarServicioFavoritoResponse;
	}

}
