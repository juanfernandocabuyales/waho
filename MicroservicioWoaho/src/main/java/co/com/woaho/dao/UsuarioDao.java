package co.com.woaho.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.com.woaho.conexion.Persistencia;
import co.com.woaho.interfaces.IUsuarioDao;
import co.com.woaho.modelo.Usuario;
import co.com.woaho.utilidades.RegistrarLog;

@Repository
public class UsuarioDao extends Persistencia implements IUsuarioDao {

	private RegistrarLog logs = new RegistrarLog(UsuarioDao.class);

	@Override
	@Transactional(propagation = Propagation.NESTED)
	public void registarUsuario(Usuario pUsuario) throws Exception {
		try {

			getEntityManager().persist(pUsuario);

			getEntityManager().flush();

			getEntityManager().clear();

		}catch(Exception e) {
			logs.registrarLogError("registarUsuario", "No se ha podido procesar la solicitud", e);
			throw new Exception(e);
		}

	}

}
