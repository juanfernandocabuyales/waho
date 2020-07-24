package co.com.woaho.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.com.respuestas.JsonGenerico;
import co.com.respuestas.RespuestaNegativa;
import co.com.respuestas.RespuestaPositiva;
import co.com.woaho.dto.cliente.ProfesionalDTO;
import co.com.woaho.enumeraciones.EnumGeneral;
import co.com.woaho.enumeraciones.EnumMensajes;
import co.com.woaho.interfaces.IIdiomaDao;
import co.com.woaho.interfaces.IProfesionDao;
import co.com.woaho.interfaces.IProfesionalDao;
import co.com.woaho.interfaces.IProfesionalService;
import co.com.woaho.interfaces.IServicioDao;
import co.com.woaho.modelo.Calificacion;
import co.com.woaho.modelo.Profesional;
import co.com.woaho.modelo.Ubicacion;
import co.com.woaho.utilidades.ProcesarCadenas;
import co.com.woaho.utilidades.RegistrarLog;
import co.com.woaho.utilidades.Utilidades;

@Service
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ProfesionalService implements IProfesionalService {

	@Autowired
	private IProfesionalDao profesionalDao;
	
	@Autowired
	private IIdiomaDao idiomaDao;
	
	@Autowired
	private IServicioDao servicioDao;
	
	@Autowired
	private IProfesionDao profesionDao;
	
	
	private RegistrarLog logs = new RegistrarLog(ProfesionalService.class);	
	
	private ProcesarCadenas procesarCadenas = ProcesarCadenas.getInstance();
	
	@Override
	public String obtenerProfesionales(String pIdServicios) {
		ObjectMapper mapper = new ObjectMapper();
		String resultado = null;
		RespuestaNegativa respuestaNegativa = new RespuestaNegativa();
		respuestaNegativa.setCodigoServicio(EnumGeneral.SERVICIO_CONSULTAR_PROFESIONALES.getValorInt());
		logs.registrarLogInfoEjecutaMetodoConParam("obtenerProfesionales","pIdServicios: "+pIdServicios);
		try {
			List<Profesional> listProfesionales = profesionalDao.obtenerProfesionales(pIdServicios);
			
			if(listProfesionales != null && !listProfesionales.isEmpty()) {
				
				JsonGenerico<ProfesionalDTO> objetoJson = new JsonGenerico<>();
				
				for(Profesional profesional: listProfesionales) {
					ProfesionalDTO profesionalDTO = new ProfesionalDTO();
					profesionalDTO.setId(String.valueOf(profesional.getProfesionalId()));
					ProfesionalDTO.Properties properties = new ProfesionalDTO.Properties();
					properties.setName(profesional.getStrNombre()+" " + profesional.getStrApellido());
					properties.setImage(profesional.getIcono().getStrRuta());
					properties.setProfession(procesarCadenas.obtenerProfesiones(profesionDao.obtenerProfesiones(procesarCadenas.obtenerListaLong(profesional.getStrProfesiones()))));
					properties.setNationality(profesional.getNacionalidad().getStrNombreTerritorio());
					properties.setServices(procesarCadenas.procesarCadenas(servicioDao.obtenerServicios(procesarCadenas.obtenerListaLong(profesional.getStrServicios()))));
					properties.setLanguages(procesarCadenas.obtenerIdiomas(idiomaDao.obtenerIdiomas(procesarCadenas.obtenerListaLong(profesional.getStrLenguajes()))));
					properties.setAboutme(profesional.getStrDescripcion());
					properties.setIconSize(new ProfesionalDTO.Properties.IconSize(Long.parseLong(profesional.getIcono().getStrAlto()), Long.parseLong(profesional.getIcono().getStrAncho())));
					properties.setNumberStars(profesional.getCantEstrellas());
					properties.setNumberServices(profesional.getCantServicios().intValue());
					List<ProfesionalDTO.Properties.Comments> listComentarios = new ArrayList<>();
					for(Calificacion calificacion : profesional.getCalificaciones()) {
						listComentarios.add(new ProfesionalDTO.Properties.Comments(calificacion.getStrDescripcion()));
					}
					properties.setComments(listComentarios);
					
					ProfesionalDTO.Geometry geometry = new ProfesionalDTO.Geometry();
					Ubicacion ubicacionProfesional = profesional.getUbicacion().get(0);
					geometry.setPlaceId(ubicacionProfesional.getStrLugarId());
					geometry.setLocation(new ProfesionalDTO.Geometry.Location(ubicacionProfesional.getStrLatitud(), ubicacionProfesional.getStrLongitud()));
					
					profesionalDTO.setProperties(properties);
					profesionalDTO.setGeometry(geometry);
					objetoJson.add(profesionalDTO);
				}
				
				RespuestaPositiva respuestaPositiva = new RespuestaPositiva(
						EnumGeneral.SERVICIO_CONSULTAR_PROFESIONALES.getValorInt(), objetoJson);
				resultado = mapper.writeValueAsString(respuestaPositiva);
				
			}else {
				resultado = Utilidades.getInstance().procesarException(EnumGeneral.SERVICIO_CONSULTAR_PROFESIONALES.getValorInt(), EnumMensajes.NO_PROFESIONALES.getMensaje());
			}
		}catch (Exception e) {
			logs.registrarLogError("obtenerProfesionales", "No se ha podido procesar la peticion", e);
			resultado = Utilidades.getInstance().procesarException(EnumGeneral.SERVICIO_CONSULTAR_PROFESIONALES.getValorInt(), EnumMensajes.NO_PROFESIONALES.getMensaje());
		}
		return resultado;
	}

}
