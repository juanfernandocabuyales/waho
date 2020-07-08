package co.com.woaho.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.ParameterMode;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

import co.com.woaho.conexion.Persistencia;
import co.com.woaho.enumeraciones.EnumMensajes;
import co.com.woaho.enumeraciones.EnumProcedimientos;
import co.com.woaho.interfaces.IUsuarioDao;
import co.com.woaho.modelo.Usuario;
import co.com.woaho.utilidades.RegistrarLog;

@Repository
public class UsuarioDao extends Persistencia implements IUsuarioDao {

	private RegistrarLog logs = new RegistrarLog(UsuarioDao.class);

	@Override
	@Transactional(propagation = Propagation.NESTED)
	public void registarUsuario(Usuario pUsuario) throws Exception {
		try {

			getEntityManager().persist(pUsuario);

			getEntityManager().flush();

			getEntityManager().clear();

		}catch(Exception e) {
			logs.registrarLogError("registarUsuario", EnumMensajes.NO_SOLICITUD.getMensaje(), e);
			throw new Exception(e);
		}

	}

	@Override
	@Transactional(propagation = Propagation.NESTED)
	public void actualizarUsuario(Usuario pUsuario) throws Exception {
		try {

			getEntityManager().merge(pUsuario);

			getEntityManager().flush();

			getEntityManager().clear();

		}catch(Exception e) {
			logs.registrarLogError("actualizarUsuario", EnumMensajes.NO_SOLICITUD.getMensaje(), e);
			throw new Exception(e);
		}		
	}

	@Override
	@Transactional(propagation = Propagation.NESTED)
	public Usuario obtenerUsuarioCelular(String pStrCelular) throws Exception {
		Usuario usuario = null;
		try {
			Query query = getEntityManager().createNamedQuery("Usuario.buscarCelular");
			query.setParameter("pCelular", pStrCelular);
			usuario = (Usuario) query.getSingleResult();
			return usuario;
		}catch (Exception e) {
			logs.registrarLogError("obtenerUsuarioCelular", EnumMensajes.NO_SOLICITUD.getMensaje(), e);
			return null;
		}
	}

	@Override
	@Transactional(propagation = Propagation.NESTED)
	public String generarCodigoRegistro(String pStrCelular) throws Exception {
		String strRespuesta = null;
		try {
			logs.registrarLogInfoEjecutaPaqFuncConParam(EnumProcedimientos.FNDB_GENERAR_CODIGO_REGISTRO.getProcedimiento(), "pStrCelular: " + pStrCelular);
			
			
			StoredProcedureQuery query = getEntityManager().createStoredProcedureQuery(EnumProcedimientos.FNDB_GENERAR_CODIGO_REGISTRO.getProcedimiento())
					.registerStoredProcedureParameter("p_celular",String.class,ParameterMode.IN)
					.registerStoredProcedureParameter("respuesta",String.class,ParameterMode.OUT)
					.setParameter("p_celular", pStrCelular);
			
			query.execute();
			
			strRespuesta = (String) query.getOutputParameterValue("respuesta");
			
			logs.registrarLogInfoResultado(strRespuesta);
			
			return strRespuesta;
		}catch (Exception e) {
			logs.registrarLogError("generarCodigoRegistro", EnumMensajes.NO_SOLICITUD.getMensaje(), e);
			return null;
		}
	}

}
