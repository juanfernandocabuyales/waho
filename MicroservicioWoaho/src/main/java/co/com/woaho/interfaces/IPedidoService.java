package co.com.woaho.interfaces;

import co.com.woaho.request.SolicitarPedidoRequest;
import co.com.woaho.response.SolicitarPedidoResponse;

public interface IPedidoService {

	SolicitarPedidoResponse soliciarPedido(SolicitarPedidoRequest request);
}
