package co.com.woaho.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import co.com.woaho.enumeraciones.EnumEstados;
import co.com.woaho.enumeraciones.EnumGeneral;
import co.com.woaho.enumeraciones.EnumMensajes;
import co.com.woaho.interfaces.IPedidoDao;
import co.com.woaho.interfaces.IPedidoService;
import co.com.woaho.modelo.Direccion;
import co.com.woaho.modelo.Estado;
import co.com.woaho.modelo.MedioPago;
import co.com.woaho.modelo.Pedido;
import co.com.woaho.modelo.Profesional;
import co.com.woaho.modelo.Servicio;
import co.com.woaho.modelo.Usuario;
import co.com.woaho.request.SolicitarPedidoRequest;
import co.com.woaho.response.SolicitarPedidoResponse;
import co.com.woaho.utilidades.RegistrarLog;

@Service
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PedidoService implements IPedidoService{
	
	@Autowired
	private IPedidoDao pedidoDao;

	private RegistrarLog logs = new RegistrarLog(PedidoService.class);

	@Override
	public SolicitarPedidoResponse soliciarPedido(SolicitarPedidoRequest request) {
		SolicitarPedidoResponse solicitarPedidoResponse = new SolicitarPedidoResponse();
		try {
			
			if(request.getListPedidos() != null && !request.getListPedidos().isEmpty()) {
				for(SolicitarPedidoRequest.PedidoDto pedidoDto : request.getListPedidos()) {
					Pedido nuevoPedido = new Pedido();
					
					nuevoPedido.setPedidoCodPromocional(pedidoDto.getCodPromocional());
					nuevoPedido.setPedidoDescripcion(pedidoDto.getPreferenceService());
					nuevoPedido.setPedidoFecha(pedidoDto.getDate());
					nuevoPedido.setPedidoHora(pedidoDto.getHour());					
					
					nuevoPedido.setPedidoServicio(new Servicio());
					nuevoPedido.getPedidoServicio().setServicioId(Long.parseLong(pedidoDto.getIdService()));	
					
					nuevoPedido.setPedidoUsuario(new Usuario());
					nuevoPedido.getPedidoUsuario().setUsuarioId(Long.parseLong(pedidoDto.getIdPerson()));
					
					nuevoPedido.setPedidoDireccion(new Direccion());
					nuevoPedido.getPedidoDireccion().setDireccionId(Long.parseLong(pedidoDto.getAddress()));
					
					nuevoPedido.setPedidoProfesional(new Profesional());
					nuevoPedido.getPedidoProfesional().setProfesionalId(Long.parseLong(pedidoDto.getProfessional()));
					
					nuevoPedido.setPedidoMedioPago(new MedioPago());
					nuevoPedido.getPedidoMedioPago().setMedioPagoId(Long.parseLong(pedidoDto.getPaymentMethod()));
					
					nuevoPedido.setPedidoEstado(new Estado());
					nuevoPedido.getPedidoEstado().setEstadoId(EnumEstados.ESTADO_PENDIENTE.getIdEstado());
					
					pedidoDao.crearActualizarPedido(nuevoPedido);
				}
				solicitarPedidoResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_POSITIVA.getValor());
				solicitarPedidoResponse.setMensajeRespuesta(EnumGeneral.OK.getValor());
			}else {
				solicitarPedidoResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
				solicitarPedidoResponse.setMensajeRespuesta(EnumMensajes.NO_PEDIDOS.getMensaje());
			}			
		}catch(Exception e) {
			logs.registrarLogError("soliciarPedido", "No se ha podido procesar la peticion", e);
			solicitarPedidoResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
			solicitarPedidoResponse.setMensajeRespuesta(EnumMensajes.INCONVENIENTE_EN_OPERACION.getMensaje());
		}
		return solicitarPedidoResponse;
	}
}
