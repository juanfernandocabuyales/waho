package co.com.woaho.controllers;

import co.com.woaho.utilidades.AES256;
import co.com.woaho.utilidades.RegistrarLog;

public class BaseController {

	/**
	 * Referencia a la Clase que contiene los métodos de encripción y desencripción
	 * mediante algoritmo AES256.
	 */
	private AES256 aes256 = new AES256();

	/**
	 * Objeto que administra el log
	 */
	private RegistrarLog logs = new RegistrarLog(BaseController.class);


	public String decrypt(String request){
		try {
			String rawText = aes256.decrypt(request, "Llave");

			logs.registrarInfo("***********************************************************************");
			logs.registrarInfo("PETICION ENTRANTE ");
			logs.registrarInfo(rawText);
			logs.registrarInfo("***********************************************************************");

			return rawText;
		} catch (Exception e) {
			logs.registrarError(e);
			return request;
		}
	}

	public String encrypt(String response){
		try {
			logs.registrarInfo("***********************************************************************");
			logs.registrarInfo("RESPONDIENDO A SIMS");
			logs.registrarInfo(response);
			logs.registrarInfo("***********************************************************************");

			return aes256.encrypt(response,"");
		}catch(Exception e) {
			logs.registrarError(e);
			return response;
		}
	}
}
