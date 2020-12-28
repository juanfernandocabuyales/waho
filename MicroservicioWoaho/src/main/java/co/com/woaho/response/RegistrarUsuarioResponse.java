package co.com.woaho.response;

public class RegistrarUsuarioResponse extends BaseResponse{

	private String existeNumero;

	public String getExisteNumero() {
		return existeNumero;
	}

	public void setExisteNumero(String existeNumero) {
		this.existeNumero = existeNumero;
	}
}
