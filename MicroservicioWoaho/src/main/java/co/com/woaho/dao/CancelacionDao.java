package co.com.woaho.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.com.woaho.conexion.Persistencia;
import co.com.woaho.enumeraciones.EnumMensajes;
import co.com.woaho.interfaces.ICancelacionDao;
import co.com.woaho.modelo.Cancelacion;
import co.com.woaho.utilidades.RegistrarLog;

@Repository
public class CancelacionDao extends Persistencia implements ICancelacionDao{

	private RegistrarLog logs = new RegistrarLog(CancelacionDao.class);
	
	@Override
	@Transactional(propagation = Propagation.NESTED)
	public Cancelacion crearCancelacion(Cancelacion pCancelacion) {
		try {

			Cancelacion cancelacion = getEntityManager().merge(pCancelacion);

			getEntityManager().flush();

			getEntityManager().clear();

			return cancelacion;
		}catch(Exception e) {
			logs.registrarLogError("crearCancelacion", EnumMensajes.NO_SOLICITUD.getMensaje(), e);
			return null;
		}
	}

	
}
