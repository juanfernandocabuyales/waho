package co.com.woaho.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import co.com.woaho.conexion.Persistencia;
import co.com.woaho.enumeraciones.EnumMensajes;
import co.com.woaho.interfaces.IEtiquetaDao;
import co.com.woaho.modelo.Etiqueta;
import co.com.woaho.utilidades.RegistrarLog;

@Repository
@SuppressWarnings("unchecked")
public class EtiquetaDao extends Persistencia implements IEtiquetaDao {

	private RegistrarLog logs = new RegistrarLog(EtiquetaDao.class);
	
	@Override
	public List<Etiqueta> obtenerEtiquetasIdioma(Long pIdioma) {
		List<Etiqueta> listEtiquetas = new ArrayList<>();
		try {
			Query query = getEntityManager().createNamedQuery("Etiqueta.etiquetaIdioma");
			query.setParameter("pIdioma", pIdioma);
			listEtiquetas = query.getResultList();
			return listEtiquetas;
		}catch (Exception e) {
			logs.registrarLogError("obtenerEtiquetasIdioma", EnumMensajes.NO_SOLICITUD.getMensaje(), e);
			return listEtiquetas;
		}
	}

	@Override
	public Etiqueta obtenerEtiquetaCodigoIdioma(String pCodigo, Long pIdioma) {
		Etiqueta etiqueta = null;
		try {
			Query query = getEntityManager().createNamedQuery("Etiqueta.etiquetaCodigoIdioma");
			query.setParameter("pIdioma", pIdioma);
			query.setParameter("pCodigo", pCodigo);
			etiqueta = (Etiqueta)query.getSingleResult();
			return etiqueta;
		}catch(Exception e) {
			logs.registrarLogError("obtenerEtiquetaCodigoIdioma", EnumMensajes.NO_SOLICITUD.getMensaje(), e);
			return etiqueta;
		}
	}

}
