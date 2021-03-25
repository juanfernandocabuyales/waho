package co.com.woaho.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import co.com.woaho.conexion.Persistencia;
import co.com.woaho.interfaces.ITerritorioDao;
import co.com.woaho.modelo.Territorio;
import co.com.woaho.utilidades.RegistrarLog;
import javax.persistence.Query;

@Repository
public class TerritorioDao extends Persistencia implements ITerritorioDao {


	private RegistrarLog logs = new RegistrarLog(TerritorioDao.class);

	@SuppressWarnings("unchecked")
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

}
