package co.com.woaho.dao;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import co.com.woaho.conexion.Persistencia;
import co.com.woaho.enumeraciones.EnumMensajes;
import co.com.woaho.interfaces.ITarifaDao;
import co.com.woaho.modelo.Tarifa;
import co.com.woaho.utilidades.RegistrarLog;

@Repository
public class TarifaDao extends Persistencia implements ITarifaDao {

	private RegistrarLog logs = new RegistrarLog(TarifaDao.class);

	@Override
	public Tarifa obtenerTarifaServicio(Long pIdServicio) {
		Tarifa tarifa = null;
		try {
			Query query = getEntityManager().createNamedQuery("Tarifa.findServicio");
			query.setParameter("pIdServicio", pIdServicio);
			tarifa = (Tarifa) query.getSingleResult();
			return tarifa;
		}catch (Exception e) {
			logs.registrarLogError("obtenerTarifaServicio", EnumMensajes.NO_SOLICITUD.getMensaje(), e);
			return null;
		}
	}
}
