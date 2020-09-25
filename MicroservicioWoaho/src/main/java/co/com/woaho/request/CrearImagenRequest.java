package co.com.woaho.request;

public class CrearImagenRequest {

	private String idImagen;
	
	private String nombreImagen;
	
	private String alto;
	
	private String ancho;

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

	public String getAlto() {
		return alto;
	}

	public void setAlto(String alto) {
		this.alto = alto;
	}

	public String getAncho() {
		return ancho;
	}

	public void setAncho(String ancho) {
		this.ancho = ancho;
	}
}
