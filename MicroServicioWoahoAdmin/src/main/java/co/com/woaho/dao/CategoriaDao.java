package co.com.woaho.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import co.com.woaho.conexion.Persistencia;
import co.com.woaho.enumeraciones.EnumMensajes;
import co.com.woaho.interfaces.ICategoriaDao;
import co.com.woaho.modelo.Categoria;
import co.com.woaho.utilidades.RegistrarLog;

@Repository
@SuppressWarnings("unchecked")
public class CategoriaDao extends Persistencia implements ICategoriaDao {

	private RegistrarLog logs = new RegistrarLog(CategoriaDao.class);
	
	@Override
	public List<Categoria> consultarCategorias() {
		List<Categoria> listCategorias = new ArrayList<>();
		try {
			Query query = getEntityManager().createNamedQuery("Categoria.findAll");
			listCategorias = query.getResultList();
			return listCategorias;
		}catch (Exception e) {
			logs.registrarLogError("consultarCategorias", EnumMensajes.NO_SOLICITUD.getMensaje(), e);
			return listCategorias;
		}
	}

}
