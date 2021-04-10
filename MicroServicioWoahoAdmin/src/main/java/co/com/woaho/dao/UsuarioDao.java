package co.com.woaho.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
@SuppressWarnings("unchecked")
public class UsuarioDao extends Persistencia implements IUsuarioDao {

	private RegistrarLog logs = new RegistrarLog(UsuarioDao.class);

	@Override
	@Transactional
	public Usuario crearActualizarUsuario(Usuario pUsuario) throws Exception {
		try {
			return getEntityManager().merge(pUsuario);
		}catch(Exception e) {
			logs.registrarLogError("registarUsuario", EnumMensajes.NO_SOLICITUD.getMensaje(), e);
			return null;
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
	public String generarCodigoRegistro(String pStrCelular,String pIdioma) throws Exception {
		String strRespuesta = null;
		try {
			logs.registrarLogInfoEjecutaPaqFuncConParam(EnumProcedimientos.FNDB_GENERAR_CODIGO_REGISTRO.getProcedimiento(), "pStrCelular: " + pStrCelular);
			
			
			StoredProcedureQuery query = getEntityManager().createStoredProcedureQuery(EnumProcedimientos.FNDB_GENERAR_CODIGO_REGISTRO.getProcedimiento())
					.registerStoredProcedureParameter("p_celular",String.class,ParameterMode.IN)
					.registerStoredProcedureParameter("p_idioma", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("respuesta",String.class,ParameterMode.OUT)
					.setParameter("p_idioma",pIdioma)
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
	
	@Override
	@Transactional(propagation = Propagation.NESTED)
	public String validarCodigoRegistro(String pStrCelular,String pStrCodigo,String pIdioma) throws Exception {
		String strRespuesta = null;
		try {
			logs.registrarLogInfoEjecutaPaqFuncConParam(EnumProcedimientos.FNDB_VALIDAR_CODIGO_REGISTRO.getProcedimiento(), "pStrCelular: " + pStrCelular + "pStrCodigo: "+pStrCodigo);
			
			
			StoredProcedureQuery query = getEntityManager().createStoredProcedureQuery(EnumProcedimientos.FNDB_VALIDAR_CODIGO_REGISTRO.getProcedimiento())
					.registerStoredProcedureParameter("p_celular",String.class,ParameterMode.IN)
					.registerStoredProcedureParameter("p_codigo",String.class,ParameterMode.IN)
					.registerStoredProcedureParameter("p_idioma",String.class,ParameterMode.IN)
					.registerStoredProcedureParameter("respuesta",String.class,ParameterMode.OUT)
					.setParameter("p_celular", pStrCelular)
					.setParameter("p_idioma", pIdioma)
					.setParameter("p_codigo", pStrCodigo);
			
			query.execute();
			
			strRespuesta = (String) query.getOutputParameterValue("respuesta");
			
			logs.registrarLogInfoResultado(strRespuesta);
			
			return strRespuesta;
		}catch (Exception e) {
			logs.registrarLogError("validarCodigoRegistro", EnumMensajes.NO_SOLICITUD.getMensaje(), e);
			return null;
		}
	}

	@Override
	@Transactional(propagation = Propagation.NESTED)
	public Usuario obtenerUsuarioCorreo(String pStrCorreo) throws Exception {
		Usuario usuario = null;
		try {
			Query query = getEntityManager().createNamedQuery("Usuario.buscarEmail");
			query.setParameter("pCorreo", pStrCorreo);
			usuario = (Usuario) query.getSingleResult();
			return usuario;
		}catch (Exception e) {
			logs.registrarLogError("obtenerUsuarioCorreo", EnumMensajes.NO_SOLICITUD.getMensaje(), e);
			return null;
		}
	}

	@Override
	@Transactional
	public Usuario obtenerUsuarioId(Long pIdUsuario) throws Exception {
		Usuario usuario = null;
		try {
			Query query = getEntityManager().createNamedQuery("Usuario.findId");
			query.setParameter("pId", pIdUsuario);
			usuario = (Usuario) query.getSingleResult();
			return usuario;
		}catch (Exception e) {
			logs.registrarLogError("obtenerUsuarioId", EnumMensajes.NO_SOLICITUD.getMensaje(), e);
			return null;
		}
	}

	@Override
	@Transactional
	public Usuario obtenerUsuarioAdmin(String pCorreo, Long pTipo) throws Exception {
		Usuario usuario = null;
		try {
			Query query = getEntityManager().createNamedQuery("Usuario.buscarAdmin");
			query.setParameter("pCorreo", pCorreo);
			query.setParameter("pTipo", pTipo);
			usuario = (Usuario) query.getSingleResult();
			return usuario;
		}catch (Exception e) {
			logs.registrarLogError("obtenerUsuarioAdmin", EnumMensajes.NO_SOLICITUD.getMensaje(), e);
			return null;
		}
	}

	@Override
	public List<Usuario> obtenerUsuarios(Long pTipo) {
		List<Usuario> listUsuarios = new ArrayList<>();
		try {
			Query query = getEntityManager().createNamedQuery("Usuario.buscarTipo");
			query.setParameter("pTipo", pTipo);
			listUsuarios = query.getResultList();
			return listUsuarios;
		}catch (Exception e) {
			logs.registrarLogError("obtenerUsuarios", EnumMensajes.NO_SOLICITUD.getMensaje(), e);
			return listUsuarios;
		}
	}

}
