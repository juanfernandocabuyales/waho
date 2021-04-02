package co.com.woaho.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import co.com.woaho.conexion.Persistencia;
import co.com.woaho.enumeraciones.EnumMensajes;
import co.com.woaho.interfaces.IImagenDao;
import co.com.woaho.modelo.Imagen;
import co.com.woaho.utilidades.RegistrarLog;

@Repository
@SuppressWarnings("unchecked")
public class ImagenDao extends Persistencia implements IImagenDao {

	private RegistrarLog logs = new RegistrarLog(ImagenDao.class);
	
	@Override
	@Transactional
	public Imagen guardarActualizarImagen(Imagen pImagen) {
		try {
			return getEntityManager().merge(pImagen);
		}catch(Exception e) {
			logs.registrarLogError("guardarActualizarImagen", EnumMensajes.NO_SOLICITUD.getMensaje(), e);
			return null;
		}
	}

	@Override
	@Transactional
	public Imagen obtenerImagen(Long pIdImagen) {
		Imagen imagen = null;
		try {
			Query query = getEntityManager().createNamedQuery("Imagen.findId");
			query.setParameter("pId", pIdImagen);
			imagen = (Imagen) query.getSingleResult();
			return imagen;
		}catch (Exception e) {
			logs.registrarLogError("obtenerImagen", EnumMensajes.NO_SOLICITUD.getMensaje(), e);
			return null;
		}
	}

	@Override
	public List<Imagen> obtenerImagenes() {
		List<Imagen> listImagenes = new ArrayList<>();
		try {
			Query query = getEntityManager().createNamedQuery("Imagen.findAll");
			listImagenes = query.getResultList();
			return listImagenes;
		}catch (Exception e) {
			logs.registrarLogError("obtenerImagenes", EnumMensajes.NO_SOLICITUD.getMensaje(), e);
			return listImagenes;
		}
	}
	
	@Override
	@Transactional
	public void eliminarImagen(Imagen pImagen) {
		try {
			getEntityManager().remove(pImagen);
		}catch(Exception e) {
			logs.registrarLogError("eliminarImagen", EnumMensajes.NO_SOLICITUD.getMensaje(), e);
		}	
	}
}
