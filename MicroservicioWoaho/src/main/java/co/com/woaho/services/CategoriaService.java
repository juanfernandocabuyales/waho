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
import co.com.woaho.interfaces.IEquivalenciaIdiomaDao;
import co.com.woaho.modelo.Categoria;
import co.com.woaho.modelo.EquivalenciaIdioma;
import co.com.woaho.request.ConsultarCategoriaRequest;
import co.com.woaho.response.ConsultarCategoriasResponse;
import co.com.woaho.utilidades.Constantes;
import co.com.woaho.utilidades.RegistrarLog;

@Service
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CategoriaService implements ICategoriaService {

	@Autowired
	private ICategoriaDao categoriaDao;

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
				List<ConsultarCategoriasResponse.Categoria> listCategoriaDto = new ArrayList<>();
				for(Categoria categoria : listCategoria) {
					ConsultarCategoriasResponse.Categoria categoriaDto = new ConsultarCategoriasResponse.Categoria();
					categoriaDto.setIcon(categoria.getImagen().getStrRuta());
					categoriaDto.setId(String.valueOf(categoria.getCategoriaId()));
					EquivalenciaIdioma equivalencia  = equivalenciaIdiomaDao.obtenerEquivalencia(categoria.getStrDescripcion());
					if(request.getLenguaje().equalsIgnoreCase(Constantes.IDIOMA_INGLES)) {
						categoriaDto.setName(equivalencia.getEquivalenciaIdiomaIngles());
					}else {
						categoriaDto.setName(equivalencia.getEquivalenciaIdiomaOriginal());
					}
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
