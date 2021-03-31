package co.com.woaho.dto;

import java.util.List;

public class ServicioDto {
	
	private String codigo;

	private String nombre;
	
	private String imagen;
	
	private String codigoImagen;
	
	private String categoria;
	
	private String pais;
	
	private String descripcion;
	
	private List<TarifaDto> listTarifas;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getImagen() {
		return imagen;
	}

	public String getCodigoImagen() {
		return codigoImagen;
	}

	public void setCodigoImagen(String codigoImagen) {
		this.codigoImagen = codigoImagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<TarifaDto> getListTarifas() {
		return listTarifas;
	}

	public void setListTarifas(List<TarifaDto> listTarifas) {
		this.listTarifas = listTarifas;
	}
}
