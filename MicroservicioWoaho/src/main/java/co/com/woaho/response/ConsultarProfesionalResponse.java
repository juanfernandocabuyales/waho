package co.com.woaho.response;

import java.util.List;

import co.com.woaho.dto.cliente.ProfesionalDTO;

public class ConsultarProfesionalResponse extends BaseResponse {

	private List<ProfesionalDTO> listProfesionales;

	public List<ProfesionalDTO> getListProfesionales() {
		return listProfesionales;
	}

	public void setListProfesionales(List<ProfesionalDTO> listProfesionales) {
		this.listProfesionales = listProfesionales;
	}
}
