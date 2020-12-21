package co.com.woaho.interfaces;

import java.util.List;

import co.com.woaho.modelo.MedioPagoUsuario;

public interface IMedioPagoUsuarioDao {

	MedioPagoUsuario guardarActualizar(MedioPagoUsuario pMedioPagoUsuario);
	
	List<MedioPagoUsuario> consultarMediosUsuario(Long pIdUsuario);
	
	List<MedioPagoUsuario> consultarMediosUsuarioActivo(Long pIdUsuario,Long pIdEstado);
}
