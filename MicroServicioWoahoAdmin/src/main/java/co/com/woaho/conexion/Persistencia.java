package co.com.woaho.conexion;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * 
 * @author Juan Fernando Cabuyales           
 * @see <b>{@code }</b> Clase encargada de manejar el acceso a base de datos
 * @date jun. 27, 2020
 */
public class Persistencia {
	
	@PersistenceContext
	private EntityManager entityManager;

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager pEntityManagerArg) {
		entityManager = pEntityManagerArg;
	}
}
