package co.com.woaho.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import co.com.woaho.conexion.Persistencia;
import co.com.woaho.enumeraciones.EnumMensajes;
import co.com.woaho.interfaces.IEquivalenciaIdiomaDao;
import co.com.woaho.modelo.EquivalenciaIdioma;
import co.com.woaho.utilidades.RegistrarLog;

@Repository
@SuppressWarnings("unchecked")
public class EquivalenciaIdiomaDao extends Persistencia implements IEquivalenciaIdiomaDao {

	private RegistrarLog logs = new RegistrarLog(EquivalenciaIdiomaDao.class);
	
	@Override
	public List<EquivalenciaIdioma> obtenerEquivalencias() {
		List<EquivalenciaIdioma> listEquivalencias = new ArrayList<>();
		try {
			Query query = getEntityManager().createNamedQuery("EquivalenciaIdioma.findAll");
			listEquivalencias = query.getResultList();
			return listEquivalencias;
		}catch(Exception e) {
			logs.registrarLogError("obtenerEquivalencias", EnumMensajes.NO_SOLICITUD.getMensaje(), e);
			return listEquivalencias;
		}
	}

	@Override
	public EquivalenciaIdioma obtenerEquivalencia(String pTexto) {
		EquivalenciaIdioma equivalencia = null;
		try {
			Query query = getEntityManager().createNamedQuery("EquivalenciaIdioma.findEquivalencia");
			query.setParameter("pTexto", pTexto.toLowerCase());
			equivalencia = (EquivalenciaIdioma) query.getSingleResult();
			return equivalencia;
		}catch(Exception e) {
			logs.registrarLogError("obtenerEquivalencia", EnumMensajes.NO_SOLICITUD.getMensaje(), e);
			return equivalencia;
		}
	}

}
