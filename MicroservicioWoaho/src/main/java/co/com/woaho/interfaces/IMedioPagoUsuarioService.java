package co.com.woaho.interfaces;

import co.com.woaho.request.BaseRequest;
import co.com.woaho.request.ConsultarMedioPagoUsuarioRequest;
import co.com.woaho.request.CrearMedioPagoUsuarioRequest;
import co.com.woaho.response.ConsultarMedioPagoUsuarioResponse;
import co.com.woaho.response.ConsultarMedioPagosResponse;
import co.com.woaho.response.CrearMedioPagoUsuarioResponse;

public interface IMedioPagoUsuarioService {

	ConsultarMedioPagosResponse consultarMediosPagos(BaseRequest request);
	
	CrearMedioPagoUsuarioResponse crearMedioPagoUsuario(CrearMedioPagoUsuarioRequest request);
	
	CrearMedioPagoUsuarioResponse actualizarMedioPagoUsuario(CrearMedioPagoUsuarioRequest request);
	
	ConsultarMedioPagoUsuarioResponse consultarMedioPagoUsuario(ConsultarMedioPagoUsuarioRequest request);
	
	ConsultarMedioPagoUsuarioResponse consultarMedioPagoUsuarioEstado(ConsultarMedioPagoUsuarioRequest request);
}
