package co.com.woaho.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
	
	@Override
	@Transactional
	public Categoria guardarActualizarCategoria(Categoria pCategoria) {
		try {
			return getEntityManager().merge(pCategoria);
		}catch(Exception e) {
			logs.registrarLogError("guardarActualizarCategoria", EnumMensajes.NO_SOLICITUD.getMensaje(), e);
			return null;
		}
	}

	@Override
	@Transactional
	public Categoria obtenerCategoriaId(Long pId) {
		Categoria categoria = null;
		try {
			Query query = getEntityManager().createNamedQuery("Categoria.findId");
			query.setParameter("pId", pId);
			categoria = (Categoria) query.getSingleResult();
			return categoria;
		}catch (Exception e) {
			logs.registrarLogError("obtenerCategoriaId", EnumMensajes.NO_SOLICITUD.getMensaje(), e);
			return null;
		}
	}

	@Override
	@Transactional
	public void eliminarCategoria(Categoria pCategoria) {
		try {
			getEntityManager().remove(pCategoria);
		}catch(Exception e) {
			logs.registrarLogError("eliminarCategoria", EnumMensajes.NO_SOLICITUD.getMensaje(), e);
		}	
	}

}
