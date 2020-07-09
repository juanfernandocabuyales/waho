package co.com.woaho.interfaces;


public interface IUsuarioService {

	String registrarUsuario(String pCadenaUsuarioDTO);
	
	String actualizarUsuario(String pCadenaUsuarioDTO);
	
	String consultarUsuario(String pCelular);
	
	String generarCodigoRegistro(String pCelular);
	
	String validarLogin(String pCorreo,String pClave);
	
	String validarCodigoRegistro(String pCelular,String pCodigo);
}
