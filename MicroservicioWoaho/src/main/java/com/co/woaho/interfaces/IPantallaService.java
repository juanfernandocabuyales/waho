package com.co.woaho.interfaces;

import java.util.List;

import com.co.woaho.dto.MensajeDTO;

public interface IPantallaService {

	List<MensajeDTO> obtenerMensajesPantalla(int pIntPantallaId);
}
