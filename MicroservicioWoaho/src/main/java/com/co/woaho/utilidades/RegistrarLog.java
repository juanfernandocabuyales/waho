package com.co.woaho.utilidades;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


public class RegistrarLog {

	private Logger logger;
	
	private static final String HILO = "Hilo[";
	private static final String CIERRE_HILO = "]";
	private static final String INFO_METODO = "Se va a ejecutar el metodo: ";
	private static final String INFO_PARAMETROS = "con parametros: ";
	private static final String INFO_QUERY = "se ejecuta el query: ";
	private static final String ERROR_METODO = "se ha presentado un error en el metodo: ";
	private static final String INFO_PAQFUNCION = "Se va a ejecutar la funcion y/o procedimiento: ";
	private static final String INFO_RESULTADO = "resultado obtenido: ";
	private static final String INFO_SERVICIO = "Se va a ejecutar el servicio: ";
	
	public RegistrarLog(Class<?> classlog) {
		super();
		logger = LogManager.getLogger(classlog);
	}
	
	public void registrarLogInfoEjecutaMetodo(String pNombreMetodo) {
		logger.info(HILO + Thread.currentThread().getId() + CIERRE_HILO + INFO_METODO + pNombreMetodo);
	}
	
	public void registrarLogInfoEjecutaMetodoConParam(String pNombreMetodo, String pParametros) {
		logger.info(HILO + Thread.currentThread().getId() + CIERRE_HILO + INFO_METODO + pNombreMetodo + INFO_PARAMETROS
				+ pParametros);
	}
	
	public void registrarLogInfoEjecutaQuery(String pNombreQuery) {
		logger.info(HILO + Thread.currentThread().getId() + CIERRE_HILO + INFO_QUERY + pNombreQuery);
	}
	
	public void registrarLogInfoEjecutaQueryConParam(String pNombreQuery, String pParametros) {
		logger.info(HILO + Thread.currentThread().getId() + CIERRE_HILO + INFO_QUERY + pNombreQuery + INFO_PARAMETROS
				+ pParametros); 
	}
	
	public void registrarLogError(String pNombreMetodo, String pMensaje,Exception e) {
		logger.error(HILO + Thread.currentThread().getId() + CIERRE_HILO + ERROR_METODO + pNombreMetodo + " " + pMensaje,e);
	}
	
	public void registrarLogInfoEjecutaPaqFuncConParam(String pNombreFuncion, String pParametros) {
		logger.info(HILO + Thread.currentThread().getId() + CIERRE_HILO + INFO_PAQFUNCION + pNombreFuncion + INFO_PARAMETROS
				+ pParametros);
	}
	
	public void registrarLogInfoEjecutaPaqFunc(String pNombreFuncion) {
		logger.info(HILO + Thread.currentThread().getId() + CIERRE_HILO + INFO_PAQFUNCION + pNombreFuncion);
	}
	
	public void registrarLogInfoResultado(String pMensaje) {
		logger.info(HILO + "[ " + Thread.currentThread().getId() + CIERRE_HILO + INFO_RESULTADO + pMensaje);
	}
	
	public void registrarLogInfoEjecutaServicio(String pNombreServicio) {
		logger.info(HILO + "[ " + Thread.currentThread().getId() + CIERRE_HILO + INFO_SERVICIO + pNombreServicio);
	}
	
	public void registrarLogInfo(String pMensaje) {
		logger.info(HILO + "[ " + Thread.currentThread().getId() + CIERRE_HILO + pMensaje);
	}	
}
