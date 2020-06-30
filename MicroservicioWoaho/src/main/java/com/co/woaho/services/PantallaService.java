package com.co.woaho.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.co.woaho.dto.MensajeDTO;
import com.co.woaho.interfaces.IPantallaDao;
import com.co.woaho.interfaces.IPantallaService;
import com.co.woaho.utilidades.ProcesarCadenas;

import org.springframework.context.annotation.ScopedProxyMode;

@Service
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PantallaService implements IPantallaService {

	@Autowired
	IPantallaDao pantallaDao;
	
	
	@Override
	public List<MensajeDTO> obtenerMensajesPantalla(int pIntPantallaId) {
		List<MensajeDTO> mensajes = new ArrayList<>();
		
		String strCadena = pantallaDao.consultarPantallas(pIntPantallaId);
		
		mensajes = ProcesarCadenas.getInstance().obtenerMensajesCadena(strCadena);
		
		return mensajes;
	}
	
	

}
