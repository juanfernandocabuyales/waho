package com.co.woaho.dao;

import org.springframework.stereotype.Repository;

import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import javax.transaction.Transactional;
import com.co.woaho.conexion.Persistencia;
import com.co.woaho.enumeraciones.EnumProcedimientos;
import com.co.woaho.interfaces.IPantallaDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Repository
public class PantallaDao extends Persistencia implements IPantallaDao {

	private static final Logger logger = LoggerFactory.getLogger(PantallaDao.class);
	
	
	@Override
	@Transactional
	public String consultarPantallas(int intPantallaId) {
		logger.info("Se inicia llamado a procedimiento: " + EnumProcedimientos.FNDB_CONULTAR_PANTALLA.getProcedimiento());
		
		String strResultado = "";
		
		StoredProcedureQuery query = this.getEntityManager().createStoredProcedureQuery(EnumProcedimientos.FNDB_CONULTAR_PANTALLA.getProcedimiento())
				.registerStoredProcedureParameter("p_pantalla",Integer.class, ParameterMode.IN)
				.registerStoredProcedureParameter("resultado", String.class, ParameterMode.OUT)
				.setParameter("p_pantalla", intPantallaId);
		query.execute();
		
		strResultado = (String) query.getOutputParameterValue("resultado");	
		
		logger.info("resultado obtenido procedimiento: " + strResultado);

		return strResultado;
	}

}
