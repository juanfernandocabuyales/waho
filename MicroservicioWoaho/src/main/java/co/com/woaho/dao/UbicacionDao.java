package co.com.woaho.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.com.woaho.conexion.Persistencia;
import co.com.woaho.enumeraciones.EnumMensajes;
import co.com.woaho.interfaces.IUbicacionDao;
import co.com.woaho.modelo.Ubicacion;
import co.com.woaho.utilidades.RegistrarLog;

@Repository
public class UbicacionDao extends Persistencia implements IUbicacionDao {

	private RegistrarLog logs = new RegistrarLog(UbicacionDao.class);
	
	@Override
	@Transactional(propagation = Propagation.NESTED)
	public void registrarUbicacion(Ubicacion pUbicacion) throws Exception {
		try {

			getEntityManager().persist(pUbicacion);

			getEntityManager().flush();

			getEntityManager().clear();

		}catch(Exception e) {
			logs.registrarLogError("registrarUbicacion", EnumMensajes.NO_SOLICITUD.getMensaje(), e);
			throw new Exception(e);
		}
		
	}

}
