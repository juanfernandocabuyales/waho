package co.com.woaho.dao;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.com.woaho.conexion.Persistencia;
import co.com.woaho.enumeraciones.EnumMensajes;
import co.com.woaho.interfaces.IMensajeCorreoDao;
import co.com.woaho.modelo.MensajeCorreo;
import co.com.woaho.utilidades.RegistrarLog;

@Repository
public class MensajeCorreoDao extends Persistencia implements IMensajeCorreoDao {
	
	private RegistrarLog logs = new RegistrarLog(MensajeCorreoDao.class);

	@Override
	@Transactional(propagation = Propagation.NESTED)
	public MensajeCorreo obtenerMensajeIdioma(Long pCodigo, String pIdioma) {
		MensajeCorreo mensajeCorreo = null;
		try {
			Query query = getEntityManager().createNamedQuery("MensajeCorreo.findCodLeng");
			query.setParameter("pCodigo", pCodigo);
			query.setParameter("pIdioma", pIdioma);
			mensajeCorreo = (MensajeCorreo) query.getSingleResult();
			return mensajeCorreo;
		}catch (Exception e) {
			logs.registrarLogError("obtenerMensajeIdioma", EnumMensajes.NO_SOLICITUD.getMensaje(), e);
			return null;
		}
	}

}
