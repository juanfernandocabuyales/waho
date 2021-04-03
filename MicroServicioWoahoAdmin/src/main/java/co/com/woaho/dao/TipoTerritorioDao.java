package co.com.woaho.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import co.com.woaho.conexion.Persistencia;
import co.com.woaho.enumeraciones.EnumMensajes;
import co.com.woaho.interfaces.ITipoTerritorioDao;
import co.com.woaho.modelo.TipoTerritorio;
import co.com.woaho.utilidades.RegistrarLog;

@Repository
@SuppressWarnings("unchecked")
public class TipoTerritorioDao extends Persistencia implements ITipoTerritorioDao {

	private RegistrarLog logs = new RegistrarLog(TipoTerritorioDao.class);
	
	@Override
	public List<TipoTerritorio> obtenerTipos() {
		List<TipoTerritorio> listTipos = new ArrayList<>();
		try {
			Query query = getEntityManager().createNamedQuery("TipoTerritorio.findAll");
			listTipos = query.getResultList();
			return listTipos;
		}catch (Exception e) {
			logs.registrarLogError("obtenerTipos", EnumMensajes.NO_SOLICITUD.getMensaje(), e);
			return listTipos;
		}
	}

	@Override
	@Transactional
	public TipoTerritorio obtenerTipo(Long pId) {
		TipoTerritorio tipoTerritorio = null;
		try {
			Query query = getEntityManager().createNamedQuery("TipoTerritorio.findId");
			query.setParameter("pId", pId);
			tipoTerritorio = (TipoTerritorio) query.getSingleResult();
			return tipoTerritorio;
		}catch (Exception e) {
			logs.registrarLogError("obtenerTipo", EnumMensajes.NO_SOLICITUD.getMensaje(), e);
			return null;
		}
	}

	@Override
	@Transactional
	public TipoTerritorio guardarActualizar(TipoTerritorio pTipoTerritorio) {
		try {
			return getEntityManager().merge(pTipoTerritorio);
		}catch(Exception e) {
			logs.registrarLogError("guardarActualizar", EnumMensajes.NO_SOLICITUD.getMensaje(), e);
			return null;
		}
	}

	@Override
	@Transactional
	public void eliminarTipo(TipoTerritorio pTipoTerritorio) {
		try {
			getEntityManager().remove(pTipoTerritorio);
		}catch(Exception e) {
			logs.registrarLogError("eliminarTipo", EnumMensajes.NO_SOLICITUD.getMensaje(), e);
		}			
	}

}
