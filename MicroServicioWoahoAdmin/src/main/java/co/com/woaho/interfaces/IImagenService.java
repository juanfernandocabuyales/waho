package co.com.woaho.interfaces;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import co.com.woaho.request.ConsultarImagenesRequest;
import co.com.woaho.request.CrearImagenRequest;
import co.com.woaho.request.EliminarRequest;
import co.com.woaho.response.ConsultarImagenesResponse;
import co.com.woaho.response.CrearImagenResponse;
import co.com.woaho.response.EliminarResponse;

public interface IImagenService {

	CrearImagenResponse guardarImagen(MultipartFile file,CrearImagenRequest request);
	
	Resource loadFileAsResource(String fileName);
	
	ConsultarImagenesResponse consultarImagenes(ConsultarImagenesRequest request);
	
	EliminarResponse eliminarImagen(EliminarRequest request);
}
