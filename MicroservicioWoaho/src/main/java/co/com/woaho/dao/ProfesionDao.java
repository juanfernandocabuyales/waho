package co.com.woaho.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import co.com.woaho.conexion.Persistencia;
import co.com.woaho.enumeraciones.EnumMensajes;
import co.com.woaho.interfaces.IProfesionDao;
import co.com.woaho.modelo.Profesion;
import co.com.woaho.utilidades.RegistrarLog;

@Repository
public class ProfesionDao extends Persistencia implements IProfesionDao {

	private RegistrarLog logs = new RegistrarLog(ProfesionDao.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Profesion> obtenerProfesiones(List<Long> pId) {
		List<Profesion> listProfesiones = new ArrayList<>();
		try {
			Query query = getEntityManager().createNamedQuery("Profesion.findId");
			query.setParameter("pId", pId);
			listProfesiones = query.getResultList();
			return listProfesiones;
		}catch (Exception e) {
			logs.registrarLogError("obtenerProfesiones", EnumMensajes.NO_SOLICITUD.getMensaje(), e);
			return listProfesiones;
		}
	}
}
