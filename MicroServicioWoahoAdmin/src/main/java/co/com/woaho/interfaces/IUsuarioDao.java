package co.com.woaho.interfaces;

import java.util.List;

import co.com.woaho.modelo.Usuario;

public interface IUsuarioDao {
	
	List<Usuario> obtenerUsuarios(Long pTipo);

	Usuario crearActualizarUsuario(Usuario pUsuario) throws Exception;
	
	Usuario obtenerUsuarioCelular(String pStrCelular) throws Exception;
	
	String generarCodigoRegistro(String pStrCelular,String pIdioma) throws Exception;
	
	Usuario obtenerUsuarioCorreo(String pStrCorreo) throws Exception;
	
	String validarCodigoRegistro(String pStrCelular,String pStrCodigo,String pIdioma) throws Exception;
	
	Usuario obtenerUsuarioId(Long pIdUsuario) throws Exception;
	
	Usuario obtenerUsuarioAdmin(String pCorreo,Long pTipo) throws Exception;
}
