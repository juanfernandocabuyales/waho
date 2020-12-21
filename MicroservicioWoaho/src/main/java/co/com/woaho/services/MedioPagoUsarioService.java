package co.com.woaho.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import co.com.woaho.enumeraciones.EnumGeneral;
import co.com.woaho.enumeraciones.EnumMensajes;
import co.com.woaho.interfaces.IEquivalenciaIdiomaDao;
import co.com.woaho.interfaces.IMedioPagoDao;
import co.com.woaho.interfaces.IMedioPagoUsuarioDao;
import co.com.woaho.interfaces.IMedioPagoUsuarioService;
import co.com.woaho.modelo.Estado;
import co.com.woaho.modelo.MedioPago;
import co.com.woaho.modelo.MedioPagoUsuario;
import co.com.woaho.modelo.Usuario;
import co.com.woaho.request.BaseRequest;
import co.com.woaho.request.ConsultarMedioPagoUsuarioRequest;
import co.com.woaho.request.CrearMedioPagoUsuarioRequest;
import co.com.woaho.response.ConsultarMedioPagoUsuarioResponse;
import co.com.woaho.response.ConsultarMedioPagosResponse;
import co.com.woaho.response.CrearMedioPagoUsuarioResponse;
import co.com.woaho.utilidades.RegistrarLog;
import co.com.woaho.utilidades.Utilidades;

@Service
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MedioPagoUsarioService implements IMedioPagoUsuarioService {

	private RegistrarLog logs = new RegistrarLog(MedioPagoUsarioService.class);
	
	@Autowired
	private IEquivalenciaIdiomaDao equivalenciaIdioma;
	
	@Autowired
	private IMedioPagoUsuarioDao medioPagoUsuarioDao;
	
	@Autowired
	private IMedioPagoDao medioPagoDao;
	
	@Override
	public ConsultarMedioPagosResponse consultarMediosPagos(BaseRequest request) {
		ConsultarMedioPagosResponse consultarMedioPagosResponse = new ConsultarMedioPagosResponse();
		try {			
			List<MedioPago> listMediosPago = medioPagoDao.obtenerMediosPago();
			
			if(null == listMediosPago || listMediosPago.isEmpty() ) {
				consultarMedioPagosResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
				consultarMedioPagosResponse.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.NO_REGISTROS.getMensaje(), request.getIdioma(), equivalenciaIdioma));
			}else {
				List<ConsultarMedioPagosResponse.MedioPago> listMedioPagoDto = new ArrayList<>();
				listMediosPago.forEach( medioPagoItem ->{
					ConsultarMedioPagosResponse.MedioPago medioPagoDto = new ConsultarMedioPagosResponse.MedioPago();
					
					medioPagoDto.setId(String.valueOf(medioPagoItem.getMedioPagoId()));
					medioPagoDto.setNombre(medioPagoItem.getMedioPagoNombre());
					medioPagoDto.setEtiqueta(medioPagoItem.getMedioPagoEtiqueta());
					medioPagoDto.setTerritorio(medioPagoItem.getMedioPagoTerritorio().getStrNombreTerritorio());
					
					listMedioPagoDto.add(medioPagoDto);
				});
				
				consultarMedioPagosResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_POSITIVA.getValor());
				consultarMedioPagosResponse.setMensajeRespuesta(EnumGeneral.OK.getValor());
				consultarMedioPagosResponse.setListMediosPagos(listMedioPagoDto);
			}			
		}catch(Exception e) {
			logs.registrarLogError("consultarMediosPagos", "No se ha podido procesar la peticion", e);
			consultarMedioPagosResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
			consultarMedioPagosResponse.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.INCONVENIENTE_EN_OPERACION.getMensaje(), request.getIdioma(), equivalenciaIdioma));
		}
		return consultarMedioPagosResponse;
	}
	
	@Override
	public CrearMedioPagoUsuarioResponse crearMedioPagoUsuario(CrearMedioPagoUsuarioRequest request) {
		CrearMedioPagoUsuarioResponse crearMedioPagoUsuarioResponse = new CrearMedioPagoUsuarioResponse();
		try {
			
			MedioPagoUsuario medioPagoUsuario = new MedioPagoUsuario();
			medioPagoUsuario.setMedioPagoUsuarioNombre(request.getNombre());
			medioPagoUsuario.setMedioPagoUsuarioFechaVen(request.getFecha());
			medioPagoUsuario.setMedioPagoUsuarioCvc(request.getCvc());
			medioPagoUsuario.setMedioPagoUsuarioCodigo(request.getCodigo());
			
			Estado estado = new Estado();
			estado.setEstadoId(Long.parseLong(request.getEstado()));
			
			Usuario usuario = new Usuario();
			usuario.setUsuarioId(Long.parseLong(request.getIdUsuario()));
			
			MedioPago medioPago = new MedioPago();
			medioPago.setMedioPagoId(Long.parseLong(request.getMedioPago()));
			
			medioPagoUsuario.setMedioPagoUsuarioEstado(estado);
			medioPagoUsuario.setMedioPagoUsuarioUsuario(usuario);
			medioPagoUsuario.setMedioPagoUsuarioMedioPago(medioPago);
			
			medioPagoUsuarioDao.guardarActualizar(medioPagoUsuario);			
			
			crearMedioPagoUsuarioResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_POSITIVA.getValor());
			crearMedioPagoUsuarioResponse.setMensajeRespuesta(EnumGeneral.OK.getValor());
		}catch(Exception e) {
			logs.registrarLogError("crearMedioPagoUsuario", "No se ha podido procesar la peticion", e);
			crearMedioPagoUsuarioResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
			crearMedioPagoUsuarioResponse.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.INCONVENIENTE_EN_OPERACION.getMensaje(), request.getIdioma(), equivalenciaIdioma));
		}
		return crearMedioPagoUsuarioResponse;
	}

	@Override
	public CrearMedioPagoUsuarioResponse actualizarMedioPagoUsuario(CrearMedioPagoUsuarioRequest request) {
		CrearMedioPagoUsuarioResponse crearMedioPagoUsuarioResponse = new CrearMedioPagoUsuarioResponse();
		try {
			
			MedioPagoUsuario medioPagoUsuario = new MedioPagoUsuario();
			medioPagoUsuario.setMedioPagoUsuarioId(Long.parseLong(request.getIdMedioPagoUsuario()));
			medioPagoUsuario.setMedioPagoUsuarioNombre(request.getNombre());
			medioPagoUsuario.setMedioPagoUsuarioFechaVen(request.getFecha());
			medioPagoUsuario.setMedioPagoUsuarioCvc(request.getCvc());
			medioPagoUsuario.setMedioPagoUsuarioCodigo(request.getCodigo());
			
			Estado estado = new Estado();
			estado.setEstadoId(Long.parseLong(request.getEstado()));
			
			Usuario usuario = new Usuario();
			usuario.setUsuarioId(Long.parseLong(request.getIdUsuario()));
			
			MedioPago medioPago = new MedioPago();
			medioPago.setMedioPagoId(Long.parseLong(request.getMedioPago()));
			
			medioPagoUsuario.setMedioPagoUsuarioEstado(estado);
			medioPagoUsuario.setMedioPagoUsuarioUsuario(usuario);
			medioPagoUsuario.setMedioPagoUsuarioMedioPago(medioPago);
			
			medioPagoUsuarioDao.guardarActualizar(medioPagoUsuario);			
			
			crearMedioPagoUsuarioResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_POSITIVA.getValor());
			crearMedioPagoUsuarioResponse.setMensajeRespuesta(EnumGeneral.OK.getValor());
		}catch(Exception e) {
			logs.registrarLogError("actualizarMedioPagoUsuario", "No se ha podido procesar la peticion", e);
			crearMedioPagoUsuarioResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
			crearMedioPagoUsuarioResponse.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.INCONVENIENTE_EN_OPERACION.getMensaje(), request.getIdioma(), equivalenciaIdioma));
		}
		return crearMedioPagoUsuarioResponse;
	}

	@Override
	public ConsultarMedioPagoUsuarioResponse consultarMedioPagoUsuario(ConsultarMedioPagoUsuarioRequest request) {
		ConsultarMedioPagoUsuarioResponse consultarMedioPagoUsuarioResponse = new ConsultarMedioPagoUsuarioResponse();
		try {
			List<MedioPagoUsuario> medioPagoUsuarioList = medioPagoUsuarioDao.consultarMediosUsuario(Long.parseLong(request.getUsuarioId()));
			
			if(null == medioPagoUsuarioList || medioPagoUsuarioList.isEmpty()) {
				consultarMedioPagoUsuarioResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
				consultarMedioPagoUsuarioResponse.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.NO_REGISTROS.getMensaje(), request.getIdioma(), equivalenciaIdioma));
			}else {
				List<ConsultarMedioPagoUsuarioResponse.MedioPagoUsuario> medioPagoUsuarioDtoList = new ArrayList<>();
				medioPagoUsuarioList.forEach(medioPagoUsuarioItem ->{
					
					ConsultarMedioPagoUsuarioResponse.MedioPagoUsuario medioPagoUsuarioDto = new ConsultarMedioPagoUsuarioResponse.MedioPagoUsuario();
					medioPagoUsuarioDto.setId(String.valueOf(medioPagoUsuarioItem.getMedioPagoUsuarioId()));
					medioPagoUsuarioDto.setNombre(medioPagoUsuarioItem.getMedioPagoUsuarioNombre());
					medioPagoUsuarioDto.setFecha(medioPagoUsuarioItem.getMedioPagoUsuarioFechaVen());
					medioPagoUsuarioDto.setCvc(medioPagoUsuarioItem.getMedioPagoUsuarioCvc());
					medioPagoUsuarioDto.setCodigo(medioPagoUsuarioItem.getMedioPagoUsuarioCodigo());
					medioPagoUsuarioDto.setEstado(medioPagoUsuarioItem.getMedioPagoUsuarioEstado().getStrCodigoEstado());
					medioPagoUsuarioDto.setMedioPago(medioPagoUsuarioItem.getMedioPagoUsuarioMedioPago().getMedioPagoNombre());				
					
					medioPagoUsuarioDtoList.add(medioPagoUsuarioDto);
				});				
				consultarMedioPagoUsuarioResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_POSITIVA.getValor());
				consultarMedioPagoUsuarioResponse.setMensajeRespuesta(EnumGeneral.OK.getValor());
				consultarMedioPagoUsuarioResponse.setListMediosPagos(medioPagoUsuarioDtoList);
			}
		}catch(Exception e) {
			logs.registrarLogError("consultarMedioPagoUsuario", "No se ha podido procesar la peticion", e);
			consultarMedioPagoUsuarioResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
			consultarMedioPagoUsuarioResponse.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.INCONVENIENTE_EN_OPERACION.getMensaje(), request.getIdioma(), equivalenciaIdioma));
		}
		return consultarMedioPagoUsuarioResponse;
	}

	@Override
	public ConsultarMedioPagoUsuarioResponse consultarMedioPagoUsuarioEstado(ConsultarMedioPagoUsuarioRequest request) {
		ConsultarMedioPagoUsuarioResponse consultarMedioPagoUsuarioResponse = new ConsultarMedioPagoUsuarioResponse();
		try {
			List<MedioPagoUsuario> medioPagoUsuarioList = medioPagoUsuarioDao.consultarMediosUsuarioActivo(Long.parseLong(request.getUsuarioId()),Long.parseLong(request.getEstado()));
			
			if(null == medioPagoUsuarioList || medioPagoUsuarioList.isEmpty()) {
				consultarMedioPagoUsuarioResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
				consultarMedioPagoUsuarioResponse.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.NO_REGISTROS.getMensaje(), request.getIdioma(), equivalenciaIdioma));
			}else {
				List<ConsultarMedioPagoUsuarioResponse.MedioPagoUsuario> medioPagoUsuarioDtoList = new ArrayList<>();
				medioPagoUsuarioList.forEach(medioPagoUsuarioItem ->{
					
					ConsultarMedioPagoUsuarioResponse.MedioPagoUsuario medioPagoUsuarioDto = new ConsultarMedioPagoUsuarioResponse.MedioPagoUsuario();
					medioPagoUsuarioDto.setId(String.valueOf(medioPagoUsuarioItem.getMedioPagoUsuarioId()));
					medioPagoUsuarioDto.setNombre(medioPagoUsuarioItem.getMedioPagoUsuarioNombre());
					medioPagoUsuarioDto.setFecha(medioPagoUsuarioItem.getMedioPagoUsuarioFechaVen());
					medioPagoUsuarioDto.setCvc(medioPagoUsuarioItem.getMedioPagoUsuarioCvc());
					medioPagoUsuarioDto.setCodigo(medioPagoUsuarioItem.getMedioPagoUsuarioCodigo());
					medioPagoUsuarioDto.setEstado(medioPagoUsuarioItem.getMedioPagoUsuarioEstado().getStrCodigoEstado());
					medioPagoUsuarioDto.setMedioPago(medioPagoUsuarioItem.getMedioPagoUsuarioMedioPago().getMedioPagoNombre());				
					
					medioPagoUsuarioDtoList.add(medioPagoUsuarioDto);
				});				
				consultarMedioPagoUsuarioResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_POSITIVA.getValor());
				consultarMedioPagoUsuarioResponse.setMensajeRespuesta(EnumGeneral.OK.getValor());
				consultarMedioPagoUsuarioResponse.setListMediosPagos(medioPagoUsuarioDtoList);
			}
		}catch(Exception e) {
			logs.registrarLogError("consultarMedioPagoUsuarioEstado", "No se ha podido procesar la peticion", e);
			consultarMedioPagoUsuarioResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
			consultarMedioPagoUsuarioResponse.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.INCONVENIENTE_EN_OPERACION.getMensaje(), request.getIdioma(), equivalenciaIdioma));
		}
		return consultarMedioPagoUsuarioResponse;
	}
}
