package co.com.woaho.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import co.com.woaho.enumeraciones.EnumGeneral;
import co.com.woaho.enumeraciones.EnumMensajes;
import co.com.woaho.interfaces.IDireccionDao;
import co.com.woaho.interfaces.IDireccionService;
import co.com.woaho.modelo.Direccion;
import co.com.woaho.request.ConsultarDireccionRequest;
import co.com.woaho.response.ConsultarDireccionResponse;
import co.com.woaho.utilidades.Constantes;
import co.com.woaho.utilidades.RegistrarLog;

@Service
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class DireccionService implements IDireccionService {

	private RegistrarLog logs = new RegistrarLog(DireccionService.class);

	@Autowired
	private IDireccionDao direccionDao;

	@Override
	public ConsultarDireccionResponse obtenerDireccionesUsuario(ConsultarDireccionRequest request) {
		ConsultarDireccionResponse response = new ConsultarDireccionResponse();
		try {
			List<Direccion> listDirecciones = direccionDao.obtenerDireccionesUsuario(Long.parseLong(request.getIdUsuario()));
			if(listDirecciones == null || listDirecciones.isEmpty()) {
				response.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
				response.setMensajeRespuesta(EnumMensajes.NO_DIRECCIONES.getMensaje());
			}else {
				List<ConsultarDireccionResponse.Direccion> listDireccionesDTO = new ArrayList<>();
				for(Direccion direccion : listDirecciones) {
					ConsultarDireccionResponse.Direccion direccionDTO = new ConsultarDireccionResponse.Direccion();
					direccionDTO.setId(String.valueOf(direccion.getDireccionId()));
					direccionDTO.setLocation(new ConsultarDireccionResponse.Direccion.Location());
					direccionDTO.getLocation().setLat(direccion.getStrDireccionLatitud());
					direccionDTO.getLocation().setLng(direccion.getStrDireccionLongitud());
					direccionDTO.setPlaceId(direccion.getStrLugarId());
					direccionDTO.setMainAddress(direccion.getStrDireccion());
					direccionDTO.setName(direccion.getStrNombreDireccion());
					direccionDTO.setSecondaryAddress(Constantes.ASTERISCO);
					direccionDTO.setHome(Constantes.ASTERISCO);
					listDireccionesDTO.add(direccionDTO);
				}
				response.setCodigoRespuesta(EnumGeneral.RESPUESTA_POSITIVA.getValor());
				response.setMensajeRespuesta(EnumGeneral.OK.getValor());
				response.setListDireccion(listDireccionesDTO);
			}
		}catch(Exception e) {
			logs.registrarLogError("obtenerDireccionesUsuario", "No se ha podido procesar la peticion", e);
			response.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
			response.setMensajeRespuesta(EnumMensajes.INCONVENIENTE_EN_OPERACION.getMensaje());
		}
		return response;
	}

}
