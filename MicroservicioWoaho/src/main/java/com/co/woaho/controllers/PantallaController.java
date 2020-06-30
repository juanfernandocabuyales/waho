package com.co.woaho.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestParam;

import com.co.woaho.interfaces.IPantallaService;

@RestController
@RequestMapping("/pantalla")
public class PantallaController {

	@Autowired
	private IPantallaService pantallaService;
	
	/**
	 * ****************************************************************.
	 * @metodo anularBoleta
	 * @param pStrIdTransaccion
	 *            : ID de transaccion con el que se identifica la boleta
	 * @param pStrTrama:
	 *            trama que trae los datos necesarios para anular la boleta
	 * @param pStrCodDoumento:
	 *            corresponde al codigo del tipo de documento de la persona a la que
	 *            se le va anular la boleta
	 * @param pStrDocumento:
	 *            corresponde al documento de la persona a la que se le va anular
	 *            boleta
	 * @descripcion Servicio que permite anular una boleta en el sistema, realizando
	 *              los procesos de devolucion en las respectivas plataformas
	 * @autor Smarthink Consulting Group S.A.S
	 * @return Resultado del proceso de anulacion
	 *         ****************************************************************
	 */
	@PostMapping(value = "/consultarPantalla", produces = MediaType.APPLICATION_JSON_VALUE)
	public String consultarPantalla(@RequestParam("idPantalla") int pIdPantalla) {

		String respuesta = null;
		return respuesta;

	}
}
