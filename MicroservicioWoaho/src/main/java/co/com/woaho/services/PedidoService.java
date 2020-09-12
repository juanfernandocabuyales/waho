package co.com.woaho.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import co.com.woaho.enumeraciones.EnumEstados;
import co.com.woaho.enumeraciones.EnumGeneral;
import co.com.woaho.enumeraciones.EnumMensajes;
import co.com.woaho.interfaces.ICancelacionDao;
import co.com.woaho.interfaces.IPedidoDao;
import co.com.woaho.interfaces.IPedidoService;
import co.com.woaho.modelo.Cancelacion;
import co.com.woaho.modelo.Direccion;
import co.com.woaho.modelo.Estado;
import co.com.woaho.modelo.MedioPago;
import co.com.woaho.modelo.Pedido;
import co.com.woaho.modelo.Profesional;
import co.com.woaho.modelo.Servicio;
import co.com.woaho.modelo.Usuario;
import co.com.woaho.request.CancelarPedidoRequest;
import co.com.woaho.request.ConsultarPedidoProfesionalRequest;
import co.com.woaho.request.ConsultarPedidoUsuarioRequest;
import co.com.woaho.request.SolicitarPedidoRequest;
import co.com.woaho.response.CancelarPedidoResponse;
import co.com.woaho.response.ConsultarPedidoProfesionalResponse;
import co.com.woaho.response.ConsultarPedidoUsuarioResponse;
import co.com.woaho.response.SolicitarPedidoResponse;
import co.com.woaho.utilidades.ProcesarCadenas;
import co.com.woaho.utilidades.RegistrarLog;

@Service
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PedidoService implements IPedidoService{

	@Autowired
	private IPedidoDao pedidoDao;
	
	@Autowired
	private ICancelacionDao cancelacionDao;

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

	@Override
	public ConsultarPedidoUsuarioResponse consultarPedidosUsuario(ConsultarPedidoUsuarioRequest request) {
		ConsultarPedidoUsuarioResponse consultarPedidoUsuarioResponse = new ConsultarPedidoUsuarioResponse();
		try {
			List<Pedido> listPedidos = pedidoDao.obtenerPedidosUsuario(Long.parseLong(request.getIdUsuario()));
			if(listPedidos != null && !listPedidos.isEmpty()) {
				List<ConsultarPedidoUsuarioResponse.PedidoUsuarioDto> listPedidoUsuarioDto = new ArrayList<>();
				for(Pedido pedido : listPedidos) {
					ConsultarPedidoUsuarioResponse.PedidoUsuarioDto pedidoUsuarioDto = new ConsultarPedidoUsuarioResponse.PedidoUsuarioDto();
					pedidoUsuarioDto.setServicio(pedido.getPedidoServicio().getStrNombre());
					pedidoUsuarioDto.setDescripcion(pedido.getPedidoDescripcion());
					pedidoUsuarioDto.setEstado(pedido.getPedidoEstado().getStrCodigoEstado());
					pedidoUsuarioDto.setDireccion(pedido.getPedidoDireccion().getStrDireccion());
					pedidoUsuarioDto.setFechaHora(pedido.getPedidoFecha() + " " + pedido.getPedidoHora());
					pedidoUsuarioDto.setProfesional(pedido.getPedidoProfesional().getNombreCompleto());
					pedidoUsuarioDto.setMedioPago(pedido.getPedidoMedioPago().getMedioPagoNombre());
					pedidoUsuarioDto.setFechaHoraFin( (pedido.getFechafinal() == null ? "*":ProcesarCadenas.getInstance().formatearFecha("dd/MM/yyyy HH:mm:ss", pedido.getFechafinal())) );
					listPedidoUsuarioDto.add(pedidoUsuarioDto);
				}
				consultarPedidoUsuarioResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_POSITIVA.getValor());
				consultarPedidoUsuarioResponse.setMensajeRespuesta(EnumGeneral.OK.getValor());
				consultarPedidoUsuarioResponse.setListPedidos(listPedidoUsuarioDto);
			}else {
				consultarPedidoUsuarioResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
				consultarPedidoUsuarioResponse.setMensajeRespuesta(EnumMensajes.NO_PEDIDOS_PARA.getMensaje("usuario"));
			}
		}catch(Exception e) {
			logs.registrarLogError("consultarPedidosUsuario", "No se ha podido procesar la peticion", e);
			consultarPedidoUsuarioResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
			consultarPedidoUsuarioResponse.setMensajeRespuesta(EnumMensajes.INCONVENIENTE_EN_OPERACION.getMensaje());
		}
		return consultarPedidoUsuarioResponse;
	}

	@Override
	public ConsultarPedidoProfesionalResponse consultarPedidosProfesional(ConsultarPedidoProfesionalRequest request) {
		ConsultarPedidoProfesionalResponse consultarPedidoProfesionalResponse = new ConsultarPedidoProfesionalResponse();
		try {
			List<Pedido> listPedidos = pedidoDao.obtenerPedidosProfesional(Long.parseLong(request.getIdProfesional()));
			if(listPedidos != null && !listPedidos.isEmpty()) {
				List<ConsultarPedidoProfesionalResponse.PedidoProfesionalDto> listPedidoProfesionalDto = new ArrayList<>();
				for(Pedido pedido : listPedidos) {
					ConsultarPedidoProfesionalResponse.PedidoProfesionalDto pedidoUsuarioDto = new ConsultarPedidoProfesionalResponse.PedidoProfesionalDto();
					pedidoUsuarioDto.setServicio(pedido.getPedidoServicio().getStrNombre());
					pedidoUsuarioDto.setDescripcion(pedido.getPedidoDescripcion());
					pedidoUsuarioDto.setEstado(pedido.getPedidoEstado().getStrCodigoEstado());
					pedidoUsuarioDto.setDireccion(pedido.getPedidoDireccion().getStrDireccion());
					pedidoUsuarioDto.setFechaHora(pedido.getPedidoFecha() + " " + pedido.getPedidoHora());
					pedidoUsuarioDto.setUsuario(pedido.getPedidoUsuario().getNombreCompleto());
					pedidoUsuarioDto.setMedioPago(pedido.getPedidoMedioPago().getMedioPagoNombre());
					pedidoUsuarioDto.setFechaHoraFinal( (pedido.getFechafinal() == null ? "*":ProcesarCadenas.getInstance().formatearFecha("dd/MM/yyyy HH:mm:ss", pedido.getFechafinal())) );
					listPedidoProfesionalDto.add(pedidoUsuarioDto);
				}
				consultarPedidoProfesionalResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_POSITIVA.getValor());
				consultarPedidoProfesionalResponse.setMensajeRespuesta(EnumGeneral.OK.getValor());
				consultarPedidoProfesionalResponse.setListPedidos(listPedidoProfesionalDto);
			}else {
				consultarPedidoProfesionalResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
				consultarPedidoProfesionalResponse.setMensajeRespuesta(EnumMensajes.NO_PEDIDOS_PARA.getMensaje("profesional"));
			}
		}catch(Exception e) {
			logs.registrarLogError("consultarPedidosProfesional", "No se ha podido procesar la peticion", e);
			consultarPedidoProfesionalResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
			consultarPedidoProfesionalResponse.setMensajeRespuesta(EnumMensajes.INCONVENIENTE_EN_OPERACION.getMensaje());
		}
		return consultarPedidoProfesionalResponse;
	}

	@Override
	public CancelarPedidoResponse cancelarPedido(CancelarPedidoRequest request) {
		CancelarPedidoResponse cancelarPedidoResponse = new CancelarPedidoResponse();
		try {
			Pedido pedido = pedidoDao.obtenerPedido(Long.parseLong(request.getIdPedido()));
			if(pedido != null) {
				pedido.setPedidoEstado(new Estado());
				pedido.getPedidoEstado().setEstadoId(EnumEstados.ESTADO_CANCELADO.getIdEstado());
				pedido.setFechafinal(new Date());
				Cancelacion cancelacion = new Cancelacion();
				cancelacion.setCancelacionPedido(pedido);
				cancelacion.setCancelacionFecha(new Date());
				cancelacion.setCancelacionMotivo(request.getMotivo());
				cancelacionDao.crearCancelacion(cancelacion);
				pedidoDao.crearActualizarPedido(pedido);
				cancelarPedidoResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_POSITIVA.getValor());
				cancelarPedidoResponse.setMensajeRespuesta(EnumGeneral.OK.getValor());
			}else {
				cancelarPedidoResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
				cancelarPedidoResponse.setMensajeRespuesta(EnumMensajes.NO_PEDIDO.getMensaje());
			}
		}catch(Exception e) {
			logs.registrarLogError("cancelarPedido", "No se ha podido procesar la peticion", e);
			cancelarPedidoResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
			cancelarPedidoResponse.setMensajeRespuesta(EnumMensajes.INCONVENIENTE_EN_OPERACION.getMensaje());
		}
		return cancelarPedidoResponse;
	}

	@Override
	public SolicitarPedidoResponse actualizarPedido(SolicitarPedidoRequest request) {
		SolicitarPedidoResponse solicitarPedidoResponse = new SolicitarPedidoResponse();
		try {

			if(request.getListPedidos() != null && !request.getListPedidos().isEmpty()) {
				for(SolicitarPedidoRequest.PedidoDto pedidoDto : request.getListPedidos()) {
					Pedido nuevoPedido = new Pedido();
					
					nuevoPedido.setPedidoId(Long.parseLong(pedidoDto.getId()));
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
			logs.registrarLogError("actualizarPedido", "No se ha podido procesar la peticion", e);
			solicitarPedidoResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
			solicitarPedidoResponse.setMensajeRespuesta(EnumMensajes.INCONVENIENTE_EN_OPERACION.getMensaje());
		}
		return solicitarPedidoResponse;
	}
}
