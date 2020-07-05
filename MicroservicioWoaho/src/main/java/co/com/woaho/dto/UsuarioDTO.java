package co.com.woaho.dto;

public class UsuarioDTO {

	private String id;
	private String name;
	private String lastName;
	private String cell;
	private String email;
	private String checkTerminos;	
	
	public UsuarioDTO(String id, String name, String lastName, String cell, String email, String checkTerminos) {
		super();
		this.id = id;
		this.name = name;
		this.lastName = lastName;
		this.cell = cell;
		this.email = email;
		this.checkTerminos = checkTerminos;
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getCell() {
		return cell;
	}
	public void setCell(String cell) {
		this.cell = cell;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCheckTerminos() {
		return checkTerminos;
	}
	public void setCheckTerminos(String checkTerminos) {
		this.checkTerminos = checkTerminos;
	}
	public String getNombreApellido() {
		return this.name + " " + this.lastName;
	}

	@Override
	public String toString() {
		return "UsuarioDTO [id=" + id + ", name=" + name + ", lastName=" + lastName + ", cell=" + cell + ", email="
				+ email + ", checkTerminos=" + checkTerminos + "]";
	}
}
