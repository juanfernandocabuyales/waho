package co.com.woaho.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import co.com.woaho.conexion.Persistencia;
import co.com.woaho.enumeraciones.EnumMensajes;
import co.com.woaho.interfaces.IParametroDao;
import co.com.woaho.modelo.Parametro;
import co.com.woaho.utilidades.RegistrarLog;

@SuppressWarnings("unchecked")
@Repository
public class ParametroDao extends Persistencia implements IParametroDao {
	
	private RegistrarLog logs = new RegistrarLog(ParametroDao.class);

	@Override
	public Map<String, String> obtenerParametrosCorreo(List<String> pList) {
		Map<String, String> map = new HashMap<>();
		try {
			Query query = this.getEntityManager().createNamedQuery("ParametroSistema.findCorreo");
			query.setParameter("pList", pList);
			List<Parametro> listParametros = query.getResultList();
			map =  listParametros.stream().collect(
	                Collectors.toMap(Parametro::getParametroNombre, Parametro::getParametroValor));
		} catch (Exception e) {
			logs.registrarLogError("buscarParametro", EnumMensajes.NO_SOLICITUD.getMensaje(), e);
		}
		return map;
	}

}
