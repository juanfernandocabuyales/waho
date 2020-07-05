package co.com.woaho.interfaces;

import co.com.woaho.modelo.Usuario;

public interface IUsuarioDao {

	void registarUsuario(Usuario pUsuario) throws Exception;
}
