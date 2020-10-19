CREATE OR REPLACE FUNCTION woaho.fndb_consultar_pantallas(p_tipo_pantalla integer,p_idioma character varying)
    RETURNS character varying
    LANGUAGE 'plpgsql'
    COST 10
    VOLATILE 
    
AS $BODY$
DECLARE 
/***********************************************************************************
   Descripcion: Funcion que retorna las pantallas correspondientes al tipo
   Autor: Juan.Cabuyales
   Fecha: 04-10-2020
************************************************************************************/

mensaje character varying;
tipo_mensaje character varying;
cadena_resultado character varying;

pantalla_id integer;
pantalla_nombre character varying;
pantalla_imagen character varying;
cadena_mensajes character varying;

idioma_id integer;

cu_pantallas CURSOR FOR 
			SELECT pa.pantalla_id,pa.pantalla_nombre,im.imagen_ruta
			FROM pantalla pa, imagen im
			WHERE pa.pantalla_tipo_pantalla = p_tipo_pantalla
			AND pa.pantalla_imagen = im.imagen_id;
		
BEGIN
	
	SELECT i.idioma_id
	INTO idioma_id
	FROM idioma i
	WHERE idioma_codigo = p_idioma;  
	
	OPEN cu_pantallas;
	LOOP
		FETCH cu_pantallas INTO pantalla_id,pantalla_nombre,pantalla_imagen;

		EXIT WHEN NOT FOUND;
		
		cadena_mensajes := woaho.consultar_mensajes_pantalla(pantalla_id,idioma_id);
		
		IF (cadena_resultado IS NULL) THEN
			cadena_resultado := pantalla_id ||';'||pantalla_nombre||';'||pantalla_imagen||';'||cadena_mensajes||'|';
		ELSE
			cadena_resultado := cadena_resultado || pantalla_id ||';'||pantalla_nombre||';'||pantalla_imagen||';'||cadena_mensajes||'|';
		END IF;
	END LOOP;
	CLOSE cu_pantallas;
	
	RETURN cadena_resultado;
	
END;
$BODY$;

ALTER FUNCTION woaho.fndb_consultar_pantallas(integer,character varying)
    OWNER TO postgres;