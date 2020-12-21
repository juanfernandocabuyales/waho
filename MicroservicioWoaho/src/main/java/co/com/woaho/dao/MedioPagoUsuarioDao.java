package co.com.woaho.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.com.woaho.conexion.Persistencia;
import co.com.woaho.enumeraciones.EnumMensajes;
import co.com.woaho.interfaces.IMedioPagoUsuarioDao;
import co.com.woaho.modelo.MedioPagoUsuario;
import co.com.woaho.utilidades.RegistrarLog;

@Repository
@SuppressWarnings("unchecked")
public class MedioPagoUsuarioDao extends Persistencia implements IMedioPagoUsuarioDao {

	private RegistrarLog logs = new RegistrarLog(MedioPagoUsuarioDao.class);
	
	@Override
	@Transactional(propagation = Propagation.NESTED)
	public MedioPagoUsuario guardarActualizar(MedioPagoUsuario pMedioPagoUsuario) {
		try {

			MedioPagoUsuario medioPagoUsuario = getEntityManager().merge(pMedioPagoUsuario);

			getEntityManager().flush();

			getEntityManager().clear();

			return medioPagoUsuario;
		}catch(Exception e) {
			logs.registrarLogError("guardarActualizar", EnumMensajes.NO_SOLICITUD.getMensaje(), e);
			return null;
		}	
	}

	@Override
	public List<MedioPagoUsuario> consultarMediosUsuario(Long pIdUsuario) {
		List<MedioPagoUsuario> listMediosPagos = new ArrayList<>();
		try {
			Query query = getEntityManager().createNamedQuery("MedioPagoUsuario.findUsuario");
			query.setParameter("pIdUsuario", pIdUsuario);
			listMediosPagos = query.getResultList();
			return listMediosPagos;
		}catch (Exception e) {
			logs.registrarLogError("consultarMediosUsuario", EnumMensajes.NO_SOLICITUD.getMensaje(), e);
			return listMediosPagos;
		}
	}

	@Override
	public List<MedioPagoUsuario> consultarMediosUsuarioActivo(Long pIdUsuario, Long pIdEstado) {
		List<MedioPagoUsuario> listMediosPagos = new ArrayList<>();
		try {
			Query query = getEntityManager().createNamedQuery("MedioPagoUsuario.findUsuarioActivo");
			query.setParameter("pIdUsuario", pIdUsuario);
			query.setParameter("pEstadoId", pIdEstado);
			listMediosPagos = query.getResultList();
			return listMediosPagos;
		}catch (Exception e) {
			logs.registrarLogError("consultarMediosUsuarioActivo", EnumMensajes.NO_SOLICITUD.getMensaje(), e);
			return listMediosPagos;
		}
	}
}
