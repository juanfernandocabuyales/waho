package co.com.woaho.response;

import java.util.List;

public class ConsultarTerritorioResponse extends BaseResponse {

	private List<PaisDTO> lisPaisesDto;

	public List<PaisDTO> getLisPaisesDto() {
		return lisPaisesDto;
	}

	public void setLisPaisesDto(List<PaisDTO> lisPaisesDto) {
		this.lisPaisesDto = lisPaisesDto;
	}
	
	public static class PaisDTO {
		
		private String idTerritorio;
		
		private String nombreTerritorio;

		public String getIdTerritorio() {
			return idTerritorio;
		}

		public void setIdTerritorio(String idTerritorio) {
			this.idTerritorio = idTerritorio;
		}

		public String getNombreTerritorio() {
			return nombreTerritorio;
		}

		public void setNombreTerritorio(String nombreTerritorio) {
			this.nombreTerritorio = nombreTerritorio;
		}
	}
}
