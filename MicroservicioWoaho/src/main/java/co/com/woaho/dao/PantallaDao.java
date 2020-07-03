package co.com.woaho.dao;

import org.springframework.stereotype.Repository;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import javax.transaction.Transactional;

import co.com.woaho.conexion.Persistencia;
import co.com.woaho.enumeraciones.EnumProcedimientos;
import co.com.woaho.interfaces.IPantallaDao;
import co.com.woaho.utilidades.RegistrarLog;

@Repository
public class PantallaDao extends Persistencia implements IPantallaDao {
	
	private RegistrarLog logs = new RegistrarLog(PantallaDao.class);	
	
	@Override
	@Transactional
	public String consultarPantallas(int intPantallaId) {
		logs.registrarLogInfoEjecutaPaqFunc(EnumProcedimientos.FNDB_CONULTAR_PANTALLA.getProcedimiento());
		
		String strResultado = "";
		
		StoredProcedureQuery query = this.getEntityManager().createStoredProcedureQuery(EnumProcedimientos.FNDB_CONULTAR_PANTALLA.getProcedimiento())
				.registerStoredProcedureParameter("p_pantalla",Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter("resultado", String.class, ParameterMode.OUT)
				.setParameter("p_pantalla", intPantallaId);
		query.execute();
		
		strResultado = (String) query.getOutputParameterValue("resultado");	
		
		logs.registrarLogInfoResultado(strResultado);

		return strResultado;
	}

}
