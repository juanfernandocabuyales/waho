package co.com.woaho.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import co.com.woaho.conexion.Persistencia;
import co.com.woaho.enumeraciones.EnumMensajes;
import co.com.woaho.interfaces.IServicioDao;
import co.com.woaho.modelo.Servicio;
import co.com.woaho.utilidades.RegistrarLog;

@Repository
public class ServicioDao extends Persistencia implements IServicioDao {

	private RegistrarLog logs = new RegistrarLog(ServicioDao.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Servicio> obtenerServicios(List<Long> ids) {
		List<Servicio> listServicios = new ArrayList<>();
		try {
			Query query = getEntityManager().createNamedQuery("Servicio.findId");
			query.setParameter("pId", ids);
			listServicios = query.getResultList();
			return listServicios;
		}catch (Exception e) {
			logs.registrarLogError("obtenerServicios", EnumMensajes.NO_SOLICITUD.getMensaje(), e);
			return listServicios;
		}
	}

}
