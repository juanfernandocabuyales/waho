package co.com.woaho.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import co.com.woaho.conexion.Persistencia;
import co.com.woaho.enumeraciones.EnumMensajes;
import co.com.woaho.interfaces.IProfesionalDao;
import co.com.woaho.interfaces.IProfesionalSP;
import co.com.woaho.modelo.Profesional;
import co.com.woaho.utilidades.RegistrarLog;

@Repository
public class ProfesionalDao extends Persistencia implements IProfesionalDao {

	@Autowired
	private IProfesionalSP profesionalSP;
	
	
	private RegistrarLog logs = new RegistrarLog(ProfesionalDao.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Profesional> obtenerProfesionales(String pIdServicio) {
		List<Profesional> listProfesionales = new ArrayList<>();
		try {
			Query query = getEntityManager().createNamedQuery("Profesional.findServicio");
			query.setParameter("pServicios", pIdServicio);
			listProfesionales = query.getResultList();
			return listProfesionales;
		}catch (Exception e) {
			logs.registrarLogError("obtenerProfesionales", EnumMensajes.NO_SOLICITUD.getMensaje(), e);
			return listProfesionales;
		}
	}

	@Override
	public Profesional registrarProfesional(Profesional pProfesional){
		try {
			return profesionalSP.save(pProfesional);
		}catch(Exception e) {
			return null;
		}

	}

}
