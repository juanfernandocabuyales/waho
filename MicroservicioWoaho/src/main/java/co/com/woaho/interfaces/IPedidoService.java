package co.com.woaho.interfaces;

import co.com.woaho.request.CancelarPedidoRequest;
import co.com.woaho.request.ConsultarPedidoProfesionalRequest;
import co.com.woaho.request.ConsultarPedidoUsuarioRequest;
import co.com.woaho.request.SolicitarPedidoRequest;
import co.com.woaho.response.CancelarPedidoResponse;
import co.com.woaho.response.ConsultarPedidoProfesionalResponse;
import co.com.woaho.response.ConsultarPedidoUsuarioResponse;
import co.com.woaho.response.SolicitarPedidoResponse;

public interface IPedidoService {

	SolicitarPedidoResponse soliciarPedido(SolicitarPedidoRequest request);
	
	ConsultarPedidoUsuarioResponse consultarPedidosUsuario(ConsultarPedidoUsuarioRequest request);
	
	ConsultarPedidoProfesionalResponse consultarPedidosProfesional(ConsultarPedidoProfesionalRequest request);
	
	CancelarPedidoResponse cancelarPedido (CancelarPedidoRequest request);
	
	SolicitarPedidoResponse actualizarPedido(SolicitarPedidoRequest request);
}
