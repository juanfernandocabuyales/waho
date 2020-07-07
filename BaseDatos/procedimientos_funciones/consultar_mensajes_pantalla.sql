CREATE OR REPLACE FUNCTION woaho.consultar_mensajes_pantalla(
	p_pantalla integer)
    RETURNS character varying
    LANGUAGE 'plpgsql'

    COST 10
    VOLATILE 
    
AS $BODY$
DECLARE 
/***********************************************************************************
   Descripcion: Funcion que retorna los mensajes de la pantalla
   Autor: Juan.Cabuyales
   Fecha: 26-06-2020
************************************************************************************/

mensaje character varying;
tipo_mensaje character varying;
cadena_resultado character varying;

cu_mensajes CURSOR FOR 
			SELECT me.mensaje_mensaje,tp.tipo_nombre
			FROM mensaje me,mensaje_pantalla mp,tipo tp
			WHERE mp.mensaje_pantalla_pantalla_id = p_pantalla
			AND mp.mensaje_pantalla_mensaje_id = me.mensaje_id
			AND me.mensaje_tipo = tp.tipo_id;
		
BEGIN
	OPEN cu_mensajes;
	LOOP
		FETCH cu_mensajes INTO mensaje,tipo_mensaje;

		EXIT WHEN NOT FOUND;

		IF (cadena_resultado IS NULL) THEN
			cadena_resultado := COALESCE(mensaje,'*')||':'||COALESCE(tipo_mensaje,'*')||'~';
		ELSE
			cadena_resultado := cadena_resultado || COALESCE(mensaje,'*')||':'||COALESCE(tipo_mensaje,'*')||'~';
		END IF;

	END LOOP;
	CLOSE cu_mensajes;
	
END;
$BODY$;

ALTER FUNCTION woaho.consultar_mensajes_pantalla(integer)
    OWNER TO postgres;