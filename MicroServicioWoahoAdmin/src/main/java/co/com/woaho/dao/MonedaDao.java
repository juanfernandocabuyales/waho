package co.com.woaho.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import co.com.woaho.conexion.Persistencia;
import co.com.woaho.enumeraciones.EnumMensajes;
import co.com.woaho.interfaces.IMonedaDao;
import co.com.woaho.modelo.Moneda;
import co.com.woaho.utilidades.RegistrarLog;

@Repository
@SuppressWarnings("unchecked")
public class MonedaDao extends Persistencia implements IMonedaDao {

	private RegistrarLog logs = new RegistrarLog(CategoriaDao.class);

	@Override
	public List<Moneda> obtenerMonedas() {
		List<Moneda> listMonedas = new ArrayList<>();
		try {
			Query query = getEntityManager().createNamedQuery("Moneda.findAll");
			listMonedas = query.getResultList();
			return listMonedas;
		}catch (Exception e) {
			logs.registrarLogError("consultarMonedas", EnumMensajes.NO_SOLICITUD.getMensaje(), e);
			return listMonedas;
		}
	}

	@Override
	@Transactional
	public Moneda guardarActualizarMoneda(Moneda pMoneda) {
		try {
			return getEntityManager().merge(pMoneda);
		}catch(Exception e) {
			logs.registrarLogError("guardarActualizarMoneda", EnumMensajes.NO_SOLICITUD.getMensaje(), e);
			return null;
		}
	}

	@Override
	@Transactional
	public Moneda obtenerMonedaId(Long pId) {
		Moneda moneda = null;
		try {
			Query query = getEntityManager().createNamedQuery("Moneda.findId");
			query.setParameter("pId", pId);
			moneda = (Moneda) query.getSingleResult();
			return moneda;
		}catch (Exception e) {
			logs.registrarLogError("obtenerMonedaId", EnumMensajes.NO_SOLICITUD.getMensaje(), e);
			return null;
		}
	}

	@Override
	@Transactional
	public void eliminarMoneda(Moneda pMoneda) {
		try {
			getEntityManager().remove(pMoneda);
		}catch(Exception e) {
			logs.registrarLogError("eliminarMoneda", EnumMensajes.NO_SOLICITUD.getMensaje(), e);
		}	
	}

}
