package co.com.woaho.dto;

public class DireccionDTO {

	private String strDireccionId;
	
	private String strDescripcion;
	
	private String strTerritorio;
	
	private String strLatitud;
	
	private String strLongitud;
	
	private String strNombre;	

	public DireccionDTO(String strDireccionId, String strDescripcion, String strTerritorio, String strLatitud,
			String strLongitud,String strNombre) {
		super();
		this.strDireccionId = strDireccionId;
		this.strDescripcion = strDescripcion;
		this.strTerritorio = strTerritorio;
		this.strLatitud = strLatitud;
		this.strLongitud = strLongitud;
		this.strNombre = strNombre;
	}

	public String getStrDireccionId() {
		return strDireccionId;
	}

	public void setStrDireccionId(String strDireccionId) {
		this.strDireccionId = strDireccionId;
	}

	public String getStrDescripcion() {
		return strDescripcion;
	}

	public void setStrDescripcion(String strDescripcion) {
		this.strDescripcion = strDescripcion;
	}

	public String getStrTerritorio() {
		return strTerritorio;
	}

	public void setStrTerritorio(String strTerritorio) {
		this.strTerritorio = strTerritorio;
	}

	public String getStrLatitud() {
		return strLatitud;
	}

	public void setStrLatitud(String strLatitud) {
		this.strLatitud = strLatitud;
	}

	public String getStrLongitud() {
		return strLongitud;
	}

	public void setStrLongitud(String strLongitud) {
		this.strLongitud = strLongitud;
	}

	public String getStrNombre() {
		return strNombre;
	}

	public void setStrNombre(String strNombre) {
		this.strNombre = strNombre;
	}	
}
