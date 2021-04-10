package co.com.woaho.response;

import java.util.List;

import co.com.woaho.dto.UsuarioDto;

public class ConsultarUsuariosResponse extends BaseResponse {

	private List<UsuarioDto> listUsuarios;

	public List<UsuarioDto> getListUsuarios() {
		return listUsuarios;
	}

	public void setListUsuarios(List<UsuarioDto> listUsuarios) {
		this.listUsuarios = listUsuarios;
	}
}
