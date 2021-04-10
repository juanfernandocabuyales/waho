package co.com.woaho.request;

import co.com.woaho.dto.UsuarioDto;

public class CrearUsuarioRequest extends BaseRequest {

	private UsuarioDto usuarioDto;

	public UsuarioDto getUsuarioDto() {
		return usuarioDto;
	}

	public void setUsuarioDto(UsuarioDto usuarioDto) {
		this.usuarioDto = usuarioDto;
	}
}
