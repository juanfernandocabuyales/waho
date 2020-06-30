package com.co.woaho.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.co.respuestas.RespuestaNegativa;
import com.co.woaho.dto.MensajeDTO;
import com.co.woaho.enumeraciones.EnumMensajes;
import com.co.woaho.enumeraciones.GeneralEnum;
import com.co.woaho.interfaces.IPantallaDao;
import com.co.woaho.interfaces.IPantallaService;
import com.co.woaho.utilidades.ProcesarCadenas;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.context.annotation.ScopedProxyMode;

@Service
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PantallaService implements IPantallaService {

	@Autowired
	IPantallaDao pantallaDao;


	@Override
	public String obtenerMensajesPantalla(int pIntPantallaId) {
		ObjectMapper mapper = new ObjectMapper();
		String resultado = null;
		RespuestaNegativa respuestaNegativa = new RespuestaNegativa();
		respuestaNegativa.setCodigoServicio(GeneralEnum.SERVICIO_CONSULTA_PANTALLAS.getValorInt());
		try {

			String strCadena = pantallaDao.consultarPantallas(pIntPantallaId);

			List<MensajeDTO> mensajesList = ProcesarCadenas.getInstance().obtenerMensajesCadena(strCadena);

			if(mensajesList != null && !mensajesList.isEmpty()) {
				//TODO pendiente por continuar
			}else {
				respuestaNegativa.setRespuesta(EnumMensajes.NO_MENSAJES_PANTALLA.getMensaje());
				resultado = mapper.writeValueAsString(respuestaNegativa);
			}


		}catch (Exception e) {

		}
		return resultado;
	}



}
