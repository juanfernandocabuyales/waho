package co.com.woaho.request;

public class RegistrarUsuarioRequest{

	private UsuarioDTO usuarioDto;	
	
	public UsuarioDTO getUsuarioDto() {
		return usuarioDto;
	}

	public void setUsuarioDto(UsuarioDTO usuarioDto) {
		this.usuarioDto = usuarioDto;
	}

	public static class UsuarioDTO{
		private String id;
		private String name;
		private String lastName;
		private String cell;
		private String email;
		private String checkTerminos;
		private String password;
		
		public UsuarioDTO(String id, String name, String lastName, String cell, String email, String checkTerminos,String password) {
			super();
			this.id = id;
			this.name = name;
			this.lastName = lastName;
			this.cell = cell;
			this.email = email;
			this.checkTerminos = checkTerminos;
			this.password = password;
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

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}


		@Override
		public String toString() {
			return "UsuarioDTO [id=" + id + ", name=" + name + ", lastName=" + lastName + ", cell=" + cell + ", email="
					+ email + ", checkTerminos=" + checkTerminos + "]";
		}
	}
}
