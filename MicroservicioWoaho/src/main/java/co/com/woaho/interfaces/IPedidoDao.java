package co.com.woaho.interfaces;

import java.util.List;

import co.com.woaho.modelo.Pedido;

public interface IPedidoDao {

	List<Pedido> obtenerPedidosUsuario(Long pIdUsuario);
	
	Pedido crearActualizarPedido(Pedido pPedido);
	
	List<Pedido> obtenerPedidosProfesional(Long pIdProfesional);
}
