package co.com.woaho.response;

import java.util.List;

public class ConsultarImagenesResponse extends BaseResponse {

	private List<ImagenDto> listImagenes;

	public List<ImagenDto> getListImagenes() {
		return listImagenes;
	}

	public void setListImagenes(List<ImagenDto> listImagenes) {
		this.listImagenes = listImagenes;
	}

	public static class ImagenDto {

		private String idImagen;

		private String nombreImagen;

		public String getIdImagen() {
			return idImagen;
		}

		public void setIdImagen(String idImagen) {
			this.idImagen = idImagen;
		}

		public String getNombreImagen() {
			return nombreImagen;
		}

		public void setNombreImagen(String nombreImagen) {
			this.nombreImagen = nombreImagen;
		}
	}
}
