package co.com.woaho.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;

import co.com.woaho.interfaces.IImagenService;
import co.com.woaho.request.ConsultarImagenesRequest;
import co.com.woaho.request.ConsultarTerritorioRequest;
import co.com.woaho.request.CrearImagenRequest;
import co.com.woaho.request.GeneralRequest;
import co.com.woaho.response.ConsultarImagenesResponse;
import co.com.woaho.response.ConsultarTerritorioResponse;
import co.com.woaho.response.CrearImagenResponse;
import co.com.woaho.response.GeneralResponse;
import co.com.woaho.utilidades.RegistrarLog;

@RestController
@RequestMapping("/imagen")
public class ImagenController {

	private RegistrarLog logs = new RegistrarLog(ImagenController.class);

	@Autowired
	private IImagenService imagenService;

	@PostMapping(value = "/guardarImagen")
	public ResponseEntity<GeneralResponse> guardarImagen(@RequestParam("file") MultipartFile file,@RequestParam("peticion") String request) {

		logs.registrarLogInfoEjecutaServicio("guardarImagen");

		Gson gson = new Gson();

		GeneralRequest peticionRequest = gson.fromJson(request, GeneralRequest.class);

		CrearImagenRequest crearImagenRequest = gson.fromJson(peticionRequest.getStrMensaje(), CrearImagenRequest.class);
		CrearImagenResponse crearImagenResponse = imagenService.guardarImagen(file,crearImagenRequest);

		GeneralResponse resp = new GeneralResponse();
		resp.setMensaje(gson.toJson(crearImagenResponse));

		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@GetMapping("/downloadFile/{fileName:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
		// Load file as Resource
		Resource resource = imagenService.loadFileAsResource(fileName);

		// Try to determine file's content type
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
			logs.registrarInfo("No se pudo determinar el tipo de archivo");
		}

		// Fallback to the default content type if type could not be determined
		if(contentType == null) {
			contentType = "application/octet-stream";
		}

		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

	@PostMapping(value = "/consultarImages", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GeneralResponse> consultarPaises(@RequestBody GeneralRequest request) {
		logs.registrarLogInfoEjecutaServicio("consultarImages",request.getStrMensaje());
		Gson gson = new Gson();
		ConsultarImagenesRequest consultarImagenesRequest = gson.fromJson(request.getStrMensaje(), ConsultarImagenesRequest.class);
		ConsultarImagenesResponse consultarImagenesResponse = imagenService.consultarImagenes(consultarImagenesRequest);

		GeneralResponse resp = new GeneralResponse();
		resp.setMensaje(gson.toJson(consultarImagenesResponse));

		logs.registrarLogInfoRespuestaServicio("consultarImages",resp.getMensaje());
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
}
