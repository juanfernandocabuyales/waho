package co.com.woaho.request;

public class ConsultarUsuariosRequest extends BaseRequest {

	private String tipoUsuario;

	public String getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}
}
