package co.com.woaho.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import co.com.woaho.conexion.Persistencia;
import co.com.woaho.enumeraciones.EnumMensajes;
import co.com.woaho.interfaces.ITerritorioDao;
import co.com.woaho.modelo.Territorio;
import co.com.woaho.utilidades.RegistrarLog;
import javax.persistence.Query;

@Repository
@SuppressWarnings("unchecked")
public class TerritorioDao extends Persistencia implements ITerritorioDao {


	private RegistrarLog logs = new RegistrarLog(TerritorioDao.class);

	@Override
	public List<Territorio> obtenerTerritorios(Long pStrTipoTerritorio) {

		logs.registrarLogInfoEjecutaQuery("Territorio.buscarTipo");
		List<Territorio> resultado = new ArrayList<>();
		try {

			Query query = getEntityManager().createNamedQuery("Territorio.buscarTipo");
			
			query.setParameter("pTipoTerritorio", pStrTipoTerritorio);
			
			return query.getResultList();

		}catch(Exception e) {
			logs.registrarLogError("obtenerTerritorios", "No se ha podido realizar la consulta", e);
		}
		return resultado;
	}

	@Override
	@Transactional
	public Territorio obtenerTerritorio(Long pId) {
		Territorio territorio = null;
		try {
			Query query = getEntityManager().createNamedQuery("Territorio.findId");
			query.setParameter("pId", pId);
			territorio = (Territorio) query.getSingleResult();
			return territorio;
		}catch (Exception e) {
			logs.registrarLogError("obtenerTerritorio", EnumMensajes.NO_SOLICITUD.getMensaje(), e);
			return null;
		}
	}

	@Override
	@Transactional
	public Territorio guardarActualizarTerritorio(Territorio pTerritorio) {
		try {
			return getEntityManager().merge(pTerritorio);
		}catch(Exception e) {
			logs.registrarLogError("guardarActualizarTerritorio", EnumMensajes.NO_SOLICITUD.getMensaje(), e);
			return null;
		}
	}

	@Override
	@Transactional
	public void eliminarTerritorio(Territorio pTerritorio) {
		try {
			getEntityManager().remove(pTerritorio);
		}catch(Exception e) {
			logs.registrarLogError("eliminarTerritorio", EnumMensajes.NO_SOLICITUD.getMensaje(), e);
		}			
	}

	@Override
	public List<Territorio> obtenerTerritorios() {
		List<Territorio> listTerritorios = new ArrayList<>();
		try {
			Query query = getEntityManager().createNamedQuery("Territorio.findAll");
			listTerritorios = query.getResultList();
			return listTerritorios;
		}catch (Exception e) {
			logs.registrarLogError("obtenerTodosTerritorios", EnumMensajes.NO_SOLICITUD.getMensaje(), e);
			return listTerritorios;
		}
	}

}
