package co.com.woaho.interfaces;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import co.com.woaho.request.CrearImagenRequest;
import co.com.woaho.response.CrearImagenResponse;

public interface IImagenService {

	CrearImagenResponse guardarImagen(MultipartFile file,CrearImagenRequest request);
	
	Resource loadFileAsResource(String fileName);
}
