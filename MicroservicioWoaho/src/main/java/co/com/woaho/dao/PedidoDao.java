package co.com.woaho.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.com.woaho.conexion.Persistencia;
import co.com.woaho.enumeraciones.EnumMensajes;
import co.com.woaho.interfaces.IPedidoDao;
import co.com.woaho.modelo.Pedido;
import co.com.woaho.utilidades.RegistrarLog;

@Repository
@SuppressWarnings("unchecked")
public class PedidoDao extends Persistencia implements IPedidoDao {

	private RegistrarLog logs = new RegistrarLog(PedidoDao.class);
	
	@Override
	public List<Pedido> obtenerPedidosUsuario(Long pIdUsuario) {
		List<Pedido> listPedidos = new ArrayList<>();
		try {
			Query query = getEntityManager().createNamedQuery("Pedido.findUser");
			query.setParameter("pIdUsario", pIdUsuario);
			listPedidos = query.getResultList();
			return listPedidos;
		}catch (Exception e) {
			logs.registrarLogError("obtenerPedidosUsuario", EnumMensajes.NO_SOLICITUD.getMensaje(), e);
			return listPedidos;
		}
	}

	@Override
	@Transactional(propagation = Propagation.NESTED)
	public Pedido crearActualizarPedido(Pedido pPedido) {
		try {

			Pedido pedido = getEntityManager().merge(pPedido);

			getEntityManager().flush();

			getEntityManager().clear();

			return pedido;
		}catch(Exception e) {
			logs.registrarLogError("crearPedido", EnumMensajes.NO_SOLICITUD.getMensaje(), e);
			return null;
		}
	}

	@Override
	public List<Pedido> obtenerPedidosProfesional(Long pIdProfesional) {
		List<Pedido> listPedidos = new ArrayList<>();
		try {
			Query query = getEntityManager().createNamedQuery("Pedido.findProfesional");
			query.setParameter("pIdProfesional", pIdProfesional);
			listPedidos = query.getResultList();
			return listPedidos;
		}catch (Exception e) {
			logs.registrarLogError("obtenerPedidosProfesional", EnumMensajes.NO_SOLICITUD.getMensaje(), e);
			return listPedidos;
		}
	}

}
