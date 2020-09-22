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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import co.com.woaho.enumeraciones.EnumGeneral;
import co.com.woaho.enumeraciones.EnumMensajes;
import co.com.woaho.interfaces.IImagenService;
import co.com.woaho.principal.Configuracion;
import co.com.woaho.response.CrearImagenResponse;
import co.com.woaho.utilidades.RegistrarLog;

@Service
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ImagenService implements IImagenService {

	private RegistrarLog logs = new RegistrarLog(ImagenService.class);
	
	@Autowired
	private Configuracion configuracion;
	
	private Path fileStorageLocation;
	
	@Override
	public CrearImagenResponse guardarImagen(MultipartFile file) {
		
		logs.registrarLogInfoEjecutaMetodoConParam("guardarImagen","");
		
		CrearImagenResponse crearImagenResponse = new CrearImagenResponse();
		
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		
		try {
			
			fileStorageLocation = Paths.get(configuracion.getDirectorio())
	                .toAbsolutePath().normalize();
			
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
            	crearImagenResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
            	crearImagenResponse.setMensajeRespuesta(EnumMensajes.NOMBRE_ARCHIVO_INVALIDO.getMensaje());
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/imagen/downloadFile/")
                    .path(fileName)
                    .toUriString();
            
            crearImagenResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_POSITIVA.getValor());
            crearImagenResponse.setMensajeRespuesta(EnumGeneral.OK.getValor());
            crearImagenResponse.setUrlImagen(fileDownloadUri);
            
        } catch (Exception ex) {
        	logs.registrarLogError("guardarImagen", "No se ha podido procesar la peticion", ex);
        	crearImagenResponse.setCodigoRespuesta(EnumGeneral.RESPUESTA_NEGATIVA.getValor());
        	crearImagenResponse.setMensajeRespuesta(EnumMensajes.INCONVENIENTE_EN_OPERACION.getMensaje());
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

}
