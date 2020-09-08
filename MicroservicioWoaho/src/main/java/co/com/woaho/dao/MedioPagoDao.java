package co.com.woaho.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.com.woaho.conexion.Persistencia;
import co.com.woaho.enumeraciones.EnumMensajes;
import co.com.woaho.interfaces.IMedioPagoDao;
import co.com.woaho.modelo.MedioPago;
import co.com.woaho.utilidades.RegistrarLog;

@Repository
@SuppressWarnings("unchecked")
public class MedioPagoDao extends Persistencia implements IMedioPagoDao {

	private RegistrarLog logs = new RegistrarLog(MedioPagoDao.class);

	@Override
	@Transactional(propagation = Propagation.NESTED)
	public MedioPago obtenerMedioPago(Long pIdMedioPago) {
		MedioPago medioPago = null;
		try {
			Query query = getEntityManager().createNamedQuery("MedioPago.buscarMedioPago");
			query.setParameter("pIdMedio", pIdMedioPago);
			medioPago = (MedioPago) query.getSingleResult();
			return medioPago;
		}catch (Exception e) {
			logs.registrarLogError("obtenerMedioPago", EnumMensajes.NO_SOLICITUD.getMensaje(), e);
			return null;
		}
	}	
	
	@Override
	public List<MedioPago> obtenerMediosPago() {
		List<MedioPago> listMedioPagos = new ArrayList<>();
		try {
			Query query = getEntityManager().createNamedQuery("MedioPago.findAll");
			listMedioPagos = query.getResultList();
			return listMedioPagos;
		}catch (Exception e) {
			logs.registrarLogError("obtenerMediosPago", EnumMensajes.NO_SOLICITUD.getMensaje(), e);
			return listMedioPagos;
		}
	}
}
