package co.com.woaho.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;


import java.nio.file.Path;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import co.com.woaho.enumeraciones.EnumGeneral;
import co.com.woaho.enumeraciones.EnumMensajes;
import co.com.woaho.interfaces.IEquivalenciaIdiomaDao;
import co.com.woaho.interfaces.IImagenDao;
import co.com.woaho.interfaces.IImagenService;
import co.com.woaho.modelo.Imagen;
import co.com.woaho.principal.Configuracion;
import co.com.woaho.request.ConsultarImagenesRequest;
import co.com.woaho.request.CrearImagenRequest;
import co.com.woaho.request.EliminarRequest;
import co.com.woaho.response.ConsultarImagenesResponse;
import co.com.woaho.response.CrearImagenResponse;
import co.com.woaho.response.EliminarResponse;
import co.com.woaho.utilidades.RegistrarLog;
import co.com.woaho.utilidades.Utilidades;

@Service
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ImagenService implements IImagenService {

	private RegistrarLog logs = new RegistrarLog(ImagenService.class);

	@Autowired
	private Configuracion configuracion;

	@Autowired
	private IImagenDao imagenDao;

	@Autowired
	private IEquivalenciaIdiomaDao equivalenciaIdioma;

	private Path fileStorageLocation;

	@Override
	public CrearImagenResponse guardarImagen(MultipartFile file,CrearImagenRequest request) {

		logs.registrarLogInfoEjecutaMetodoConParam("guardarImagen","");

		CrearImagenResponse crearImagenResponse = new CrearImagenResponse();

		String fileName = StringUtils.cleanPath(request.getNombreImagen());

		try {

			if(fileName.isEmpty()) {
				crearImagenResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
				crearImagenResponse.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.ARCHIVO_NULO.getMensaje(), request.getIdioma(), equivalenciaIdioma));
			}else {
				fileStorageLocation = Paths.get(configuracion.getDirectorio())
						.toAbsolutePath().normalize();

				logs.registrarInfo("fileStorageLocation:  " + fileStorageLocation);

				// Check if the file's name contains invalid characters
				if(fileName.contains("..")) {
					crearImagenResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
					crearImagenResponse.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.NOMBRE_ARCHIVO_INVALIDO.getMensaje(), request.getIdioma(), equivalenciaIdioma));
				}

				// Copy file to the target location (Replacing existing file with the same name)
				Path targetLocation = fileStorageLocation.resolve(fileName);
				Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

				String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
						.path("/imagen/downloadFile/")
						.path(fileName)
						.toUriString();

				Imagen imagen = new Imagen();
				if(request.getIdImagen() == null || request.getIdImagen().isEmpty()) {
					imagen.setImagenId(null);
				}
				imagen.setStrNombre(request.getNombreImagen());
				imagen.setStrRuta(fileDownloadUri);
				imagen.setStrAlto(request.getAlto());
				imagen.setStrAncho(request.getAncho());

				imagenDao.guardarActualizarImagen(imagen);

				crearImagenResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_POSITIVA.getValor());
				crearImagenResponse.setMensajeRespuesta(EnumGeneral.OK.getValor());
				crearImagenResponse.setUrlImagen(fileDownloadUri);
			}            
		} catch (Exception ex) {
			logs.registrarLogError("guardarImagen", "No se ha podido procesar la peticion", ex);
			crearImagenResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
			crearImagenResponse.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.INCONVENIENTE_EN_OPERACION.getMensaje(), request.getIdioma(), equivalenciaIdioma));
		}
		return crearImagenResponse;
	}

	@Override
	public Resource loadFileAsResource(String fileName){
		try {

			fileStorageLocation = Paths.get(configuracion.getDirectorio())
					.toAbsolutePath().normalize();

			Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if(resource.exists()) {
				return resource;
			} else {
				logs.registrarLogInfo("No se encontro el archivo con nombre: " + fileName);
				return null;
			}
		} catch (Exception ex) {
			logs.registrarLogError("","No se ha podido cargar el archivo: " +fileName,ex);
			return null;
		}
	}

	@Override
	public ConsultarImagenesResponse consultarImagenes(ConsultarImagenesRequest request) {
		ConsultarImagenesResponse consultarImagenesResponse = new ConsultarImagenesResponse();
		try {
			List<Imagen> listImagenes = imagenDao.obtenerImagenes();			
			if(null == listImagenes || listImagenes.isEmpty()) {
				consultarImagenesResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
				consultarImagenesResponse.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.NO_REGISTROS.getMensaje(), request.getIdioma(), equivalenciaIdioma));
			}else {
				List<ConsultarImagenesResponse.ImagenDto> listImagenesDto = new ArrayList<>();
				listImagenes.forEach(item ->{
					ConsultarImagenesResponse.ImagenDto imagenDto = new ConsultarImagenesResponse.ImagenDto();
					imagenDto.setIdImagen(String.valueOf(item.getImagenId()));
					imagenDto.setNombreImagen(item.getStrNombre());
					imagenDto.setRuta(item.getStrRuta());
					listImagenesDto.add(imagenDto);
				});
				consultarImagenesResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_POSITIVA.getValor());
				consultarImagenesResponse.setMensajeRespuesta(EnumGeneral.OK.getValor());
				consultarImagenesResponse.setListImagenes(listImagenesDto);				
			}			
		}catch(Exception e) {
			logs.registrarLogError("consultarImagenes", "No se ha podido procesar la peticion", e);
			consultarImagenesResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
			consultarImagenesResponse.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.INCONVENIENTE_EN_OPERACION.getMensaje(), request.getIdioma(), equivalenciaIdioma));
		}
		return consultarImagenesResponse;
	}

	@Override
	public EliminarResponse eliminarImagen(EliminarRequest request) {
		EliminarResponse eliminarResponse = new EliminarResponse();
		try {			
			Imagen imagen = imagenDao.obtenerImagen(Long.parseLong(request.getId()));
			boolean encontro = false;
			boolean resultado = false;
			
			if(null == imagen) {
				eliminarResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
				eliminarResponse.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.NO_REGISTROS.getMensaje(), request.getIdioma(), equivalenciaIdioma));
			}else {
				imagenDao.eliminarImagen(imagen);
				File folder = new File(configuracion.getDirectorio());
				File [] archivos = folder.listFiles();
				for(int i= 0; i < archivos.length && !encontro; ++i) {
					if(archivos[i].getName().contains(imagen.getStrNombre())) {
						resultado = archivos[i].delete();
						encontro = true;
					}
				}
				
				if(resultado) {
					eliminarResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_POSITIVA.getValor());
					eliminarResponse.setMensajeRespuesta(EnumGeneral.OK.getValor());
				}else {
					eliminarResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
					eliminarResponse.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.INCONVENIENTE_EN_OPERACION.getMensaje(), request.getIdioma(), equivalenciaIdioma));
				}
			}
		}catch(Exception e) {
			logs.registrarLogError("eliminarImagen", "No se ha podido procesar la peticion", e);
			eliminarResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
			eliminarResponse.setMensajeRespuesta(Utilidades.getInstance().obtenerEquivalencia(EnumMensajes.INCONVENIENTE_EN_OPERACION.getMensaje(), request.getIdioma(), equivalenciaIdioma));
		}
		return eliminarResponse;
	}

}
