package co.com.woaho.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import co.com.woaho.conexion.Persistencia;
import co.com.woaho.enumeraciones.EnumMensajes;
import co.com.woaho.interfaces.IUnidadDao;
import co.com.woaho.modelo.UnidadTarifa;
import co.com.woaho.utilidades.RegistrarLog;

@Repository
@SuppressWarnings("unchecked")
public class UnidadDao extends Persistencia implements IUnidadDao {

	private RegistrarLog logs = new RegistrarLog(UnidadDao.class);

	@Override
	public List<UnidadTarifa> obtenerUnidades() {
		List<UnidadTarifa> listUnidades = new ArrayList<>();
		try {
			Query query = getEntityManager().createNamedQuery("UnidadTarifa.findAll");
			listUnidades = query.getResultList();
			return listUnidades;
		}catch (Exception e) {
			logs.registrarLogError("obtenerUnidades", EnumMensajes.NO_SOLICITUD.getMensaje(), e);
			return listUnidades;
		}
	}

	@Override
	@Transactional
	public UnidadTarifa guardarActualizarUnidad(UnidadTarifa pUnidad) {
		try {
			return getEntityManager().merge(pUnidad);
		}catch(Exception e) {
			logs.registrarLogError("guardarActualizarUnidad", EnumMensajes.NO_SOLICITUD.getMensaje(), e);
			return null;
		}
	}

	@Override
	@Transactional
	public UnidadTarifa obtenerId(Long pId) {
		UnidadTarifa unidadTarifa = null;
		try {
			Query query = getEntityManager().createNamedQuery("UnidadTarifa.findId");
			query.setParameter("pId", pId);
			unidadTarifa = (UnidadTarifa) query.getSingleResult();
			return unidadTarifa;
		}catch (Exception e) {
			logs.registrarLogError("obtenerId", EnumMensajes.NO_SOLICITUD.getMensaje(), e);
			return null;
		}
	}

	@Override
	public void eliminarUnidad(UnidadTarifa pUnidad) {
		try {
			getEntityManager().remove(pUnidad);
		}catch(Exception e) {
			logs.registrarLogError("eliminarUnidad", EnumMensajes.NO_SOLICITUD.getMensaje(), e);
		}			
	}
}
