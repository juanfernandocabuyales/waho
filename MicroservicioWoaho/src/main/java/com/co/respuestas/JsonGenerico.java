package com.co.respuestas;

import java.util.ArrayList;
import java.util.Iterator;

/**
* ****************************************************************.
* @autor: Juan Cabuyales
* @fecha: 30/06/2020
* @descripcion: Clase que permite definir coportamiento
* generales para armar Json de respuestas
* ****************************************************************
*/
public class JsonGenerico<T> implements Iterable<T> {

	private ArrayList<T> lista = new ArrayList<>();

	public JsonGenerico() {
		super();
	}

	public void add(T pObjeto) {
		lista.add(pObjeto);
	}

	public Iterator<T> iterator() {
		return lista.iterator();
	}


}
