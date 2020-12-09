package co.com.woaho.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import co.com.woaho.conexion.Persistencia;
import co.com.woaho.enumeraciones.EnumMensajes;
import co.com.woaho.interfaces.IDireccionDao;
import co.com.woaho.modelo.Direccion;
import co.com.woaho.utilidades.RegistrarLog;

@Repository
public class DireccionDao extends Persistencia implements IDireccionDao {

	private RegistrarLog logs = new RegistrarLog(DireccionDao.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Direccion> obtenerDireccionesUsuario(Long idUsuario) {
		List<Direccion> listDirecciones = new ArrayList<>();
		try {
			Query query = getEntityManager().createNamedQuery("Direccion.buscarUsario");
			query.setParameter("pIdUsuario", idUsuario);
			listDirecciones = query.getResultList();
			return listDirecciones;
		}catch (Exception e) {
			logs.registrarLogError("obtenerDireccionesUsuario", EnumMensajes.NO_SOLICITUD.getMensaje(), e);
			return listDirecciones;
		}
	}

	@Override
	@Transactional
	public Direccion crearActualizarDireccion(Direccion pDireccion) throws Exception{
		try {
			return getEntityManager().merge(pDireccion);
		}catch(Exception e) {
			logs.registrarLogError("crearActualizarDireccion", EnumMensajes.NO_SOLICITUD.getMensaje(), e);
			throw new Exception(e);
		}
	}

	@Override
	@Transactional
	public void eliminarDireccion(Direccion pDireccion) throws Exception {
		try {
			getEntityManager().remove(pDireccion);
		}catch(Exception e) {
			logs.registrarLogError("eliminarDireccion", EnumMensajes.NO_SOLICITUD.getMensaje(), e);
			throw new Exception(e);
		}
	}

	@Override
	public Direccion obtenerDireccion(Long pIdDireccion) {
		Direccion direccion = null;
		try {
			Query query = getEntityManager().createNamedQuery("Direccion.findId");
			query.setParameter("pId", pIdDireccion);
			direccion = (Direccion) query.getSingleResult();
		}catch (Exception e) {
			logs.registrarLogError("obtenerDireccionesUsuario", EnumMensajes.NO_SOLICITUD.getMensaje(), e);
		}
		return direccion;
	}

}
