package co.com.woaho.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import co.com.woaho.conexion.Persistencia;
import co.com.woaho.enumeraciones.EnumMensajes;
import co.com.woaho.interfaces.IIdiomaDao;
import co.com.woaho.modelo.Idioma;
import co.com.woaho.utilidades.RegistrarLog;

@Repository
public class IdiomaDao extends Persistencia implements IIdiomaDao {

	private RegistrarLog logs = new RegistrarLog(IdiomaDao.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Idioma> obtenerIdiomas(List<Long> pId) {
		List<Idioma> listIdiomas = new ArrayList<>();
		try {
			Query query = getEntityManager().createNamedQuery("Idioma.findId");
			query.setParameter("pId",pId);
			listIdiomas = query.getResultList();
			return listIdiomas;
		}catch (Exception e) {
			logs.registrarLogError("obtenerIdiomas", EnumMensajes.NO_SOLICITUD.getMensaje(), e);
			return listIdiomas;
		}
	}

}
