package co.com.woaho.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import co.com.woaho.enumeraciones.EnumGeneral;
import co.com.woaho.enumeraciones.EnumMensajes;
import co.com.woaho.interfaces.ICategoriaDao;
import co.com.woaho.interfaces.ICategoriaService;
import co.com.woaho.modelo.Categoria;
import co.com.woaho.response.ConsultarCategoriasResponse;
import co.com.woaho.utilidades.RegistrarLog;

@Service
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CategoriaService implements ICategoriaService {
	
	@Autowired
	private ICategoriaDao categoriaDao;

	private RegistrarLog logs = new RegistrarLog(CategoriaService.class);

	@Override
	public ConsultarCategoriasResponse consultarCategorias() {
		ConsultarCategoriasResponse consultarCategoriasResponse = new ConsultarCategoriasResponse();
		logs.registrarLogInfoEjecutaMetodoConParam("consultarServicios","");
		try {
			List<Categoria> listCategoria = categoriaDao.consultarCategorias();

			if(listCategoria == null || listCategoria.isEmpty()) {
				consultarCategoriasResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
				consultarCategoriasResponse.setMensajeRespuesta(EnumMensajes.NO_CATEGORIAS.getMensaje());
			}else {
				List<ConsultarCategoriasResponse.Categoria> listCategoriaDto = new ArrayList<>();
				for(Categoria categoria : listCategoria) {
					ConsultarCategoriasResponse.Categoria categoriaDto = new ConsultarCategoriasResponse.Categoria();
					categoriaDto.setCategoriaId(String.valueOf(categoria.getCategoriaId()));
					categoriaDto.setNombreCategoria(categoria.getStrDescripcion());
					categoriaDto.setImagenCategoria(new ConsultarCategoriasResponse.Categoria.Imagen(String.valueOf(categoria.getImagen().getImagenId()),categoria.getImagen().getStrRuta()));
					listCategoriaDto.add(categoriaDto);
				}
				consultarCategoriasResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_POSITIVA.getValor());
				consultarCategoriasResponse.setListCategorias(listCategoriaDto);
				consultarCategoriasResponse.setMensajeRespuesta(EnumGeneral.OK.getValor());
			}
		}catch(Exception e) {
			logs.registrarLogError("consultarCategorias", "No se ha podido procesar la peticion", e);
			consultarCategoriasResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
			consultarCategoriasResponse.setMensajeRespuesta(EnumMensajes.NO_CATEGORIAS.getMensaje());
		}
		return consultarCategoriasResponse;
	}	

}
