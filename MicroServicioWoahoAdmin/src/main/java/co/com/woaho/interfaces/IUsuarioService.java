package co.com.woaho.interfaces;

import co.com.woaho.request.ConsultarUsuariosRequest;
import co.com.woaho.request.CrearUsuarioRequest;
import co.com.woaho.request.LoginAdminRequest;
import co.com.woaho.response.ConsultarUsuariosResponse;
import co.com.woaho.response.CrearResponse;
import co.com.woaho.response.LoginAdminResponse;

public interface IUsuarioService {

	CrearResponse registrarUsuario(CrearUsuarioRequest request);
	
	CrearResponse actualizarUsuario(CrearUsuarioRequest request);
	
	ConsultarUsuariosResponse consultarUsuarios(ConsultarUsuariosRequest request);
	
	LoginAdminResponse validarLoginAdmin(LoginAdminRequest request);

}
