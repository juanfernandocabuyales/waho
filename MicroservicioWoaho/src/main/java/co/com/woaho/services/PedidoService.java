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
import co.com.woaho.interfaces.IEquivalenciaIdiomaDao;
import co.com.woaho.interfaces.IPedidoDao;
import co.com.woaho.interfaces.IPedidoService;
import co.com.woaho.interfaces.IProfesionalService;
import co.com.woaho.modelo.Cancelacion;
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
import co.com.woaho.utilidades.Utilidades;

@Service
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PedidoService implements IPedidoService{

	@Autowired
	private IPedidoDao pedidoDao;

	@Autowired
	private ICancelacionDao cancelacionDao;

	@Autowired
	private IEquivalenciaIdiomaDao equivalenciaIdiomaDao;

	@Autowired
	private IProfesionalService profesionalService;

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
					nuevoPedido.setPedidoLatitud(pedidoDto.getLat());
					nuevoPedido.setPedidoLongitud(pedidoDto.getLon());

					nuevoPedido.setPedidoServicio(new Servicio());
					nuevoPedido.getPedidoServicio().setServicioId(Long.parseLong(pedidoDto.getService().getIdService()));	

					nuevoPedido.setPedidoUsuario(new Usuario());
					nuevoPedido.getPedidoUsuario().setUsuarioId(Long.parseLong(pedidoDto.getIdPerson()));

					nuevoPedido.setPedidoLatitud(pedidoDto.getAddress().getLat());
					nuevoPedido.setPedidoLongitud(pedidoDto.getAddress().getLng());

					if(null == pedidoDto.getProfessional() || pedidoDto.getProfessional().isEmpty()) {
						Profesional profesional = profesionalService.obtenerProfesionalCercano(pedidoDto.getService().getIdService(),
								Double.parseDouble(nuevoPedido.getPedidoLatitud()), Double.parseDouble(nuevoPedido.getPedidoLongitud()));
						if(profesional != null) {
							nuevoPedido.setPedidoProfesional(profesional);
						}else {
							solicitarPedidoResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
							solicitarPedidoResponse.setMensajeRespuesta(EnumMensajes.NO_PROFESIONALES.getMensaje());
							return solicitarPedidoResponse;
						}
					}else {
						nuevoPedido.setPedidoProfesional(new Profesional());
						nuevoPedido.getPedidoProfesional().setProfesionalId(Long.parseLong(pedidoDto.getProfessional()));
					}

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
				solicitarPedidoResponse.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.NO_PEDIDOS.getMensaje(), request.getIdioma(), equivalenciaIdiomaDao));
			}			
		}catch(Exception e) {
			logs.registrarLogError("soliciarPedido", "No se ha podido procesar la peticion", e);
			solicitarPedidoResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
			solicitarPedidoResponse.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.INCONVENIENTE_EN_OPERACION.getMensaje(), request.getIdioma(), equivalenciaIdiomaDao));
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
				listPedidos.forEach( pedido ->{
					ConsultarPedidoUsuarioResponse.PedidoUsuarioDto pedidoUsuarioDto = new ConsultarPedidoUsuarioResponse.PedidoUsuarioDto();
					pedidoUsuarioDto.setIdPedido(String.valueOf(pedido.getPedidoId()));
					pedidoUsuarioDto.setServicio(pedido.getPedidoServicio().getStrNombre());
					pedidoUsuarioDto.setDescripcion(pedido.getPedidoDescripcion());
					pedidoUsuarioDto.setEstado(pedido.getPedidoEstado().getStrCodigoEstado());
					pedidoUsuarioDto.setDireccion( pedido.getPedidoDireccion() != null ? pedido.getPedidoDireccion().getStrDireccion() : "--" );
					pedidoUsuarioDto.setFechaHora(pedido.getPedidoFecha() + " " + pedido.getPedidoHora());
					pedidoUsuarioDto.setProfesional(pedido.getPedidoProfesional().getNombreCompleto());
					pedidoUsuarioDto.setMedioPago(pedido.getPedidoMedioPago().getMedioPagoNombre());
					pedidoUsuarioDto.setFechaHoraFin( (pedido.getFechafinal() == null ? "*":ProcesarCadenas.getInstance().formatearFecha("dd/MM/yyyy HH:mm:ss", pedido.getFechafinal())) );
					listPedidoUsuarioDto.add(pedidoUsuarioDto);
				});				
				consultarPedidoUsuarioResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_POSITIVA.getValor());
				consultarPedidoUsuarioResponse.setMensajeRespuesta(EnumGeneral.OK.getValor());
				consultarPedidoUsuarioResponse.setListPedidos(listPedidoUsuarioDto);
			}else {
				consultarPedidoUsuarioResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
				String equivalencia = Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.NO_PEDIDOS_PARA.getMensaje(), request.getIdioma(), equivalenciaIdiomaDao);
				consultarPedidoUsuarioResponse.setMensajeRespuesta(ProcesarCadenas.getInstance().obtenerMensajeFormat(equivalencia, "usuario"));
			}
		}catch(Exception e) {
			logs.registrarLogError("consultarPedidosUsuario", "No se ha podido procesar la peticion", e);
			consultarPedidoUsuarioResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
			consultarPedidoUsuarioResponse.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.INCONVENIENTE_EN_OPERACION.getMensaje(), request.getIdioma(), equivalenciaIdiomaDao));			
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
				listPedidos.forEach( pedido -> {
					ConsultarPedidoProfesionalResponse.PedidoProfesionalDto pedidoUsuarioDto = new ConsultarPedidoProfesionalResponse.PedidoProfesionalDto();
					pedidoUsuarioDto.setIdPedido(String.valueOf(pedido.getPedidoId()));
					pedidoUsuarioDto.setServicio(pedido.getPedidoServicio().getStrNombre());
					pedidoUsuarioDto.setDescripcion(pedido.getPedidoDescripcion());
					pedidoUsuarioDto.setEstado(pedido.getPedidoEstado().getStrCodigoEstado());
					pedidoUsuarioDto.setDireccion(pedido.getPedidoDireccion().getStrDireccion());
					pedidoUsuarioDto.setFechaHora(pedido.getPedidoFecha() + " " + pedido.getPedidoHora());
					pedidoUsuarioDto.setUsuario(pedido.getPedidoUsuario().getNombreCompleto());
					pedidoUsuarioDto.setMedioPago(pedido.getPedidoMedioPago().getMedioPagoNombre());
					pedidoUsuarioDto.setFechaHoraFinal( (pedido.getFechafinal() == null ? "*":ProcesarCadenas.getInstance().formatearFecha("dd/MM/yyyy HH:mm:ss", pedido.getFechafinal())) );
					listPedidoProfesionalDto.add(pedidoUsuarioDto);
				});

				consultarPedidoProfesionalResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_POSITIVA.getValor());
				consultarPedidoProfesionalResponse.setMensajeRespuesta(EnumGeneral.OK.getValor());
				consultarPedidoProfesionalResponse.setListPedidos(listPedidoProfesionalDto);
			}else {
				consultarPedidoProfesionalResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
				String equivalencia = Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.NO_PEDIDOS_PARA.getMensaje(), request.getIdioma(), equivalenciaIdiomaDao);
				consultarPedidoProfesionalResponse.setMensajeRespuesta(ProcesarCadenas.getInstance().obtenerMensajeFormat(equivalencia, "profesional"));
			}
		}catch(Exception e) {
			logs.registrarLogError("consultarPedidosProfesional", "No se ha podido procesar la peticion", e);
			consultarPedidoProfesionalResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
			consultarPedidoProfesionalResponse.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.INCONVENIENTE_EN_OPERACION.getMensaje(), request.getIdioma(), equivalenciaIdiomaDao));
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
				cancelarPedidoResponse.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.NO_PEDIDO.getMensaje(), request.getIdioma(), equivalenciaIdiomaDao));
			}
		}catch(Exception e) {
			logs.registrarLogError("cancelarPedido", "No se ha podido procesar la peticion", e);
			cancelarPedidoResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
			cancelarPedidoResponse.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.INCONVENIENTE_EN_OPERACION.getMensaje(), request.getIdioma(), equivalenciaIdiomaDao));
		}
		return cancelarPedidoResponse;
	}

	@Override
	public SolicitarPedidoResponse actualizarPedido(SolicitarPedidoRequest request) {
		SolicitarPedidoResponse solicitarPedidoResponse = new SolicitarPedidoResponse();
		try {

			if(request.getListPedidos() != null && !request.getListPedidos().isEmpty()) {
				request.getListPedidos().forEach( pedidoDto -> {
					Pedido nuevoPedido = new Pedido();

					nuevoPedido.setPedidoId(Long.parseLong(pedidoDto.getId()));
					nuevoPedido.setPedidoCodPromocional(pedidoDto.getCodPromocional());
					nuevoPedido.setPedidoDescripcion(pedidoDto.getPreferenceService());
					nuevoPedido.setPedidoFecha(pedidoDto.getDate());
					nuevoPedido.setPedidoHora(pedidoDto.getHour());					

					nuevoPedido.setPedidoServicio(new Servicio());
					nuevoPedido.getPedidoServicio().setServicioId(Long.parseLong(pedidoDto.getService().getIdService()));	

					nuevoPedido.setPedidoUsuario(new Usuario());
					nuevoPedido.getPedidoUsuario().setUsuarioId(Long.parseLong(pedidoDto.getIdPerson()));

					nuevoPedido.setPedidoLatitud(pedidoDto.getAddress().getLat());
					nuevoPedido.setPedidoLongitud(pedidoDto.getAddress().getLng());

					nuevoPedido.setPedidoProfesional(new Profesional());
					nuevoPedido.getPedidoProfesional().setProfesionalId(Long.parseLong(pedidoDto.getProfessional()));

					nuevoPedido.setPedidoMedioPago(new MedioPago());
					nuevoPedido.getPedidoMedioPago().setMedioPagoId(Long.parseLong(pedidoDto.getPaymentMethod()));

					nuevoPedido.setPedidoEstado(new Estado());
					nuevoPedido.getPedidoEstado().setEstadoId(EnumEstados.ESTADO_PENDIENTE.getIdEstado());

					pedidoDao.crearActualizarPedido(nuevoPedido);
				});

				solicitarPedidoResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_POSITIVA.getValor());
				solicitarPedidoResponse.setMensajeRespuesta(EnumGeneral.OK.getValor());
			}else {
				solicitarPedidoResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
				solicitarPedidoResponse.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.NO_PEDIDOS.getMensaje(), request.getIdioma(), equivalenciaIdiomaDao));
			}			
		}catch(Exception e) {
			logs.registrarLogError("actualizarPedido", "No se ha podido procesar la peticion", e);
			solicitarPedidoResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
			solicitarPedidoResponse.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.INCONVENIENTE_EN_OPERACION.getMensaje(), request.getIdioma(), equivalenciaIdiomaDao));
		}
		return solicitarPedidoResponse;
	}
}
