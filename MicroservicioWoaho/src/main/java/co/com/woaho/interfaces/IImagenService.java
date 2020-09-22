package co.com.woaho.interfaces;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import co.com.woaho.response.CrearImagenResponse;

public interface IImagenService {

	CrearImagenResponse guardarImagen(MultipartFile file);
	
	Resource loadFileAsResource(String fileName);
}
