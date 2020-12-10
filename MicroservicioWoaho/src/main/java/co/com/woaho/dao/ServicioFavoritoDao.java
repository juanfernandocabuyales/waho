package co.com.woaho.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.com.woaho.conexion.Persistencia;
import co.com.woaho.enumeraciones.EnumMensajes;
import co.com.woaho.interfaces.IServicioFavoritoDao;
import co.com.woaho.modelo.ServicioFavorito;
import co.com.woaho.utilidades.RegistrarLog;

@Repository
@SuppressWarnings("unchecked")
public class ServicioFavoritoDao extends Persistencia implements IServicioFavoritoDao {

	private RegistrarLog logs = new RegistrarLog(ServicioFavoritoDao.class);
	
	@Override
	public List<ServicioFavorito> obtenerServiciosUsuario(Long pIdUsuario) {
		List<ServicioFavorito> listServicios = new ArrayList<>();
		try {
			Query query = getEntityManager().createNamedQuery("ServicioFavorito.findByUsuario");
			query.setParameter("pIdUsuario", pIdUsuario);
			listServicios = query.getResultList();
			return listServicios;
		}catch (Exception e) {
			logs.registrarLogError("obtenerServiciosUsuario", EnumMensajes.NO_SOLICITUD.getMensaje(), e);
			return listServicios;
		}
	}

	@Override
	@Transactional(propagation = Propagation.NESTED)
	public ServicioFavorito guardarServicioFavorito(ServicioFavorito pServicioFavorito) {
		try {

			ServicioFavorito servicioFavorito = getEntityManager().merge(pServicioFavorito);

			getEntityManager().flush();

			getEntityManager().clear();

			return servicioFavorito;
		}catch(Exception e) {
			logs.registrarLogError("guardarServicioFavorito", EnumMensajes.NO_SOLICITUD.getMensaje(), e);
			return null;
		}		
	}

	@Override
	@Transactional(propagation = Propagation.NESTED)
	public void eliminarServicioFavorito(ServicioFavorito pServicioFavorito) {
		try {
			getEntityManager().remove(pServicioFavorito);
		}catch(Exception e) {
			logs.registrarLogError("eliminarServicioFavorito", EnumMensajes.NO_SOLICITUD.getMensaje(), e);
		}
		
	}

	@Override
	public ServicioFavorito obtenerServicioFavorito(Long pIdUsuario, Long pIdServicio) {
		try {
			Query query = getEntityManager().createNamedQuery("ServicioFavorito.findByUsuarioAndService");
			query.setParameter("pIdUsuario", pIdUsuario);
			query.setParameter("pIdServicio", pIdServicio);
			return (ServicioFavorito) query.getSingleResult();
		}catch(Exception e) {
			logs.registrarLogError("obtenerServicioFavorito", EnumMensajes.NO_SOLICITUD.getMensaje(), e);
			return null;
		}
	}

}
