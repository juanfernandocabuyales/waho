package co.com.woaho.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import co.com.woaho.dto.CategoriaDto;
import co.com.woaho.enumeraciones.EnumGeneral;
import co.com.woaho.enumeraciones.EnumMensajes;
import co.com.woaho.interfaces.ICategoriaDao;
import co.com.woaho.interfaces.ICategoriaService;
import co.com.woaho.interfaces.IEquivalenciaIdiomaDao;
import co.com.woaho.interfaces.IServicioDao;
import co.com.woaho.modelo.Categoria;
import co.com.woaho.modelo.Imagen;
import co.com.woaho.modelo.Servicio;
import co.com.woaho.request.ConsultarCategoriaRequest;
import co.com.woaho.request.CrearCategoriaRequest;
import co.com.woaho.request.EliminarRequest;
import co.com.woaho.response.ConsultarCategoriasResponse;
import co.com.woaho.response.CrearResponse;
import co.com.woaho.response.EliminarResponse;
import co.com.woaho.utilidades.RegistrarLog;
import co.com.woaho.utilidades.Utilidades;

@Service
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CategoriaService implements ICategoriaService {

	@Autowired
	private ICategoriaDao categoriaDao;
	
	@Autowired
	private IServicioDao servicioDao;

	@Autowired
	private IEquivalenciaIdiomaDao equivalenciaIdiomaDao;

	private RegistrarLog logs = new RegistrarLog(CategoriaService.class);

	@Override
	public ConsultarCategoriasResponse consultarCategorias(ConsultarCategoriaRequest request) {
		ConsultarCategoriasResponse consultarCategoriasResponse = new ConsultarCategoriasResponse();
		logs.registrarLogInfoEjecutaMetodoConParam("consultarCategorias","");
		try {
			List<Categoria> listCategoria = categoriaDao.consultarCategorias();
			if(listCategoria == null || listCategoria.isEmpty()) {
				consultarCategoriasResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
				consultarCategoriasResponse.setMensajeRespuesta(EnumMensajes.NO_CATEGORIAS.getMensaje());
			}else {
				List<CategoriaDto> listCategoriaDto = new ArrayList<>();
				listCategoria.forEach( item ->{
					CategoriaDto categoriaDto = new CategoriaDto();
					categoriaDto.setId(String.valueOf(item.getCategoriaId()));
					categoriaDto.setNombre(item.getStrDescripcion());
					categoriaDto.setIdImagen(String.valueOf(item.getImagen().getImagenId()));
					listCategoriaDto.add(categoriaDto);
				});
				consultarCategoriasResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_POSITIVA.getValor());
				consultarCategoriasResponse.setListCategorias(listCategoriaDto);
				consultarCategoriasResponse.setMensajeRespuesta(EnumGeneral.OK.getValor());
			}
		}catch(Exception e) {
			logs.registrarLogError("consultarCategorias", "No se ha podido procesar la peticion", e);
			consultarCategoriasResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
			consultarCategoriasResponse.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.INCONVENIENTE_EN_OPERACION.getMensaje(),
					request.getIdioma(), equivalenciaIdiomaDao));
		}
		return consultarCategoriasResponse;
	}

	@Override
	public CrearResponse crearCategoria(CrearCategoriaRequest request) {
		CrearResponse crearResponse = new CrearResponse();
		try {			
			CategoriaDto categoriaDto = request.getCategoriaDto();
			Categoria categoria = new Categoria();			
			Imagen imagen = new Imagen();
			imagen.setImagenId(Long.parseLong(categoriaDto.getIdImagen()));
			
			categoria.setStrDescripcion(categoriaDto.getNombre());
			categoria.setImagen(imagen);
			categoriaDao.guardarActualizarCategoria(categoria);
		}catch(Exception e) {
			logs.registrarLogError("crearCategoria", "No se ha podido procesar la peticion", e);
			crearResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
			crearResponse.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.INCONVENIENTE_EN_OPERACION.getMensaje(),
					request.getIdioma(), equivalenciaIdiomaDao));
		}
		return crearResponse;
	}

	@Override
	public CrearResponse actualizarCategoria(CrearCategoriaRequest request) {
		CrearResponse crearResponse = new CrearResponse();
		try {			
			CategoriaDto categoriaDto = request.getCategoriaDto();
			Categoria categoria = categoriaDao.obtenerCategoriaId(Long.parseLong(categoriaDto.getId()));
			
			if(null == categoria) {
				crearResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
				crearResponse.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.NO_REGISTROS.getMensaje(), request.getIdioma(), equivalenciaIdiomaDao));
			}else {
				Imagen imagen = new Imagen();
				imagen.setImagenId(Long.parseLong(categoriaDto.getIdImagen()));				
				categoria.setStrDescripcion(categoriaDto.getNombre());
				categoria.setImagen(imagen);
				categoriaDao.guardarActualizarCategoria(categoria);
			}			
		}catch(Exception e) {
			logs.registrarLogError("actualizarCategoria", "No se ha podido procesar la peticion", e);
			crearResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
			crearResponse.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.INCONVENIENTE_EN_OPERACION.getMensaje(),
					request.getIdioma(), equivalenciaIdiomaDao));
		}
		return crearResponse;
	}

	@Override
	public EliminarResponse eliminarCategoria(EliminarRequest request) {
		EliminarResponse eliminarResponse = new EliminarResponse();
		try {			
			Categoria categoria = categoriaDao.obtenerCategoriaId(Long.parseLong(request.getId()));
			if(null == categoria) {
				eliminarResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
				eliminarResponse.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.NO_REGISTROS.getMensaje(), request.getIdioma(), equivalenciaIdiomaDao));
			}else {
				List<Servicio> listServicios = servicioDao.consultarServicios();
				Optional<Servicio> servicio = listServicios.stream().filter(item -> item.getCategoria().getCategoriaId().equals(categoria.getCategoriaId())).findFirst();
				if(!servicio.isPresent()) {
					categoriaDao.eliminarCategoria(categoria);
					eliminarResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_POSITIVA.getValor());
					eliminarResponse.setMensajeRespuesta(EnumGeneral.OK.getValor());
				}else {
					eliminarResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
					eliminarResponse.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.NO_ELIMINAR_CATEGORIA.getMensaje(), request.getIdioma(), equivalenciaIdiomaDao));
				}
			}			
		}catch(Exception e) {
			logs.registrarLogError("eliminarCategoria", "No se ha podido procesar la peticion", e);
			eliminarResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
			eliminarResponse.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.INCONVENIENTE_EN_OPERACION.getMensaje(),
					request.getIdioma(), equivalenciaIdiomaDao));
		}
		return eliminarResponse;
	}	
}
