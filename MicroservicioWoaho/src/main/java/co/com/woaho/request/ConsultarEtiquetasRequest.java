package co.com.woaho.request;

public class ConsultarEtiquetasRequest {

	private String codigoEtiqueta;
	
	private String idiomaEtiqueta;

	public String getCodigoEtiqueta() {
		return codigoEtiqueta;
	}

	public void setCodigoEtiqueta(String codigoEtiqueta) {
		this.codigoEtiqueta = codigoEtiqueta;
	}

	public String getIdiomaEtiqueta() {
		return idiomaEtiqueta;
	}

	public void setIdiomaEtiqueta(String idiomaEtiqueta) {
		this.idiomaEtiqueta = idiomaEtiqueta;
	}

	@Override
	public String toString() {
		return "ConsultarEtiquetasRequest [codigoEtiqueta=" + ((codigoEtiqueta == null) ? "*":codigoEtiqueta )+
				", idiomaEtiqueta=" + ((idiomaEtiqueta == null) ? "*":idiomaEtiqueta )
				+ "]";
	}
}
