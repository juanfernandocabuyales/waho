package co.com.woaho.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;


import co.com.woaho.enumeraciones.EnumGeneral;
import co.com.woaho.enumeraciones.EnumMensajes;
import co.com.woaho.interfaces.IServicioDao;
import co.com.woaho.interfaces.IServicioServices;
import co.com.woaho.modelo.Servicio;
import co.com.woaho.response.ConsultarServiciosResponse;
import co.com.woaho.utilidades.RegistrarLog;

@Service
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ServicioService implements IServicioServices {

	@Autowired
	private IServicioDao serviciosDao;

	private RegistrarLog logs = new RegistrarLog(ServicioService.class);	

	@Override
	public ConsultarServiciosResponse consultarServicios() {
		ConsultarServiciosResponse consultarServiciosResponse = new ConsultarServiciosResponse();
		logs.registrarLogInfoEjecutaMetodoConParam("consultarServicios","");
		try {
			List<Servicio> listServicios = serviciosDao.consultarServicios();

			if(listServicios == null || listServicios.isEmpty()) {
				consultarServiciosResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
				consultarServiciosResponse.setMensajeRespuesta(EnumMensajes.NO_SERVICIOS.getMensaje());
			}else {
				List<ConsultarServiciosResponse.Servicio> listServiciosDto = new ArrayList<>();
				for(Servicio servicio : listServicios) {
					ConsultarServiciosResponse.Servicio servicioDto = new ConsultarServiciosResponse.Servicio();
					servicioDto.setIdServicio(String.valueOf(servicio.getServicioId()));
					servicioDto.setNombreServicio(servicio.getStrNombre());
					servicioDto.setCategoria(new ConsultarServiciosResponse.Servicio.Categoria(String.valueOf(servicio.getCategoria().getCategoriaId()),servicio.getCategoria().getStrDescripcion()));
					servicioDto.setImagen(new ConsultarServiciosResponse.Servicio.Imagen(String.valueOf(servicio.getImagen().getImagenId()),servicio.getImagen().getStrRuta()));
					listServiciosDto.add(servicioDto);
				}
				consultarServiciosResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_POSITIVA.getValor());
				consultarServiciosResponse.setListServicios(listServiciosDto);
				consultarServiciosResponse.setMensajeRespuesta(EnumGeneral.OK.getValor());
			}
		}catch(Exception e) {
			logs.registrarLogError("consultarServicios", "No se ha podido procesar la peticion", e);
			consultarServiciosResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
			consultarServiciosResponse.setMensajeRespuesta(EnumMensajes.NO_SERVICIOS.getMensaje());
		}
		return consultarServiciosResponse;
	}

}
