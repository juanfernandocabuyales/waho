package co.com.woaho.interfaces;

import co.com.woaho.modelo.Usuario;
import co.com.woaho.request.ConsultarUsuarioRequest;
import co.com.woaho.request.GenerarCodigoRequest;
import co.com.woaho.request.LoginAdminRequest;
import co.com.woaho.request.LoginRequest;
import co.com.woaho.request.RegistrarUsuarioRequest;
import co.com.woaho.request.ValidarCodigoRequest;
import co.com.woaho.response.ConsultarUsuarioResponse;
import co.com.woaho.response.GenerarCodigoResponse;
import co.com.woaho.response.LoginAdminResponse;
import co.com.woaho.response.LoginResponse;
import co.com.woaho.response.RegistrarUsuarioResponse;
import co.com.woaho.response.ValidarCodigoResponse;

public interface IUsuarioService {

	RegistrarUsuarioResponse registrarUsuario(RegistrarUsuarioRequest request);
	
	RegistrarUsuarioResponse actualizarUsuario(RegistrarUsuarioRequest request);
	
	ConsultarUsuarioResponse consultarUsuario(ConsultarUsuarioRequest request);
	
	GenerarCodigoResponse generarCodigoRegistro(GenerarCodigoRequest request);
	
	LoginResponse loginUsuario(LoginRequest request);
	
	ValidarCodigoResponse validarCodigoRegistro(ValidarCodigoRequest request);
	
	ValidarCodigoResponse validarCodigoLogin(ValidarCodigoRequest request);
	
	LoginAdminResponse validarLoginAdmin(LoginAdminRequest request);
	
	void generarCodigoIngreso(Usuario pUsuario,String pIdioma) throws Exception;
}
