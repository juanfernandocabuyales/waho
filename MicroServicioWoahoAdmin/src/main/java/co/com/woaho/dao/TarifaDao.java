package co.com.woaho.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import co.com.woaho.conexion.Persistencia;
import co.com.woaho.enumeraciones.EnumMensajes;
import co.com.woaho.interfaces.ITarifaDao;
import co.com.woaho.modelo.Tarifa;
import co.com.woaho.utilidades.RegistrarLog;

@Repository
@SuppressWarnings("unchecked")
public class TarifaDao extends Persistencia implements ITarifaDao {

	private RegistrarLog logs = new RegistrarLog(TarifaDao.class);

	@Override
	public List<Tarifa> obtenerTarifaServicio(Long pIdServicio) {
		List<Tarifa> tarifa = new ArrayList<>();
		try {
			Query query = getEntityManager().createNamedQuery("Tarifa.findServicio");
			query.setParameter("pIdServicio", pIdServicio);
			tarifa = query.getResultList();
			return tarifa;
		}catch (Exception e) {
			logs.registrarLogError("obtenerTarifaServicio", EnumMensajes.NO_SOLICITUD.getMensaje(), e);
			return tarifa;
		}
	}
	
	@Override
	@Transactional
	public Tarifa guardarActualizarTarifa(Tarifa pTarifa) {
		try {
			return getEntityManager().merge(pTarifa);
		}catch(Exception e) {
			logs.registrarLogError("guardarActualizarTarifa", EnumMensajes.NO_SOLICITUD.getMensaje(), e);
			return null;
		}
	}
}
