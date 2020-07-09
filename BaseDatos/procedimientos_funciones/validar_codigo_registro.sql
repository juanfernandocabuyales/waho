CREATE OR REPLACE FUNCTION woaho.fndb_validar_codigo_registro(p_celular character varying,p_codigo character varying)
RETURNS character varying 
LANGUAGE plpgsql
AS $$
DECLARE
	cant_intentos numeric;
	codigo_generado character varying;
	fecha_registro timestamp;
	cant_minutos_codigo numeric;
	parametro_tiempo numeric;
	
	v_state   text;
    v_msg     text;
    v_detail  text;
    v_hint    text;
    v_context text;
    	
BEGIN

	SELECT cod.codigo_numero,cod.codigo_intentos,EXTRACT(minute FROM (now() - codigo.codigo_fecha_hora_registro))::numeric
	INTO codigo_generado,cant_intentos,cant_minutos_codigo
	FROM codigo cod
	WHERE cod.codigo_celular = p_celular
	AND cod.codigo_estado = 1;
	
	IF (codigo_generado IS NULL) THEN
		RETURN '1,El codigo ingresado no se encuentra registrado.Intente nuevamente o solicite un nuevo codigo.';
	ELSE
		SELECT par.parametro_valor::numeric
		INTO parametro_tiempo
		FROM woaho.parametro par
		WHERE par.parametro_nombre = 'TIEMPO_COD_REGISTRO';
		
		IF (codigo_generado = p_codigo) THEN
			UPDATE codigo
			SET codigo_estado = 2
			WHERE codigo.codigo_celular = p_celular;
			
			IF (cant_minutos_codigo > parametro_tiempo) THEN
				RETURN '1,El codigo ingresado ha caducado.Solicite un nuevo codigo.';
			ELSE
				RETURN '0,OK';
			END IF;					
		ELSE
			IF (cant_intentos > 0) THEN
				UPDATE codigo
				SET codigo_intentos = cant_intentos - 1
				WHERE codigo.codigo_celular = p_celular;
				RETURN '1,El codigo ingresado no corresponde.Intente nuevamente o solicite un nuevo codigo.';
			ELSE
				RETURN '1,El codigo ingresado ya no es valido.Solicite un nuevo codigo.';
			END IF;
		END IF;		
	END IF;	
EXCEPTION WHEN OTHERS THEN

	GET stacked DIAGNOSTICS
        v_state   = returned_sqlstate,
        v_msg     = message_text,
        v_detail  = pg_exception_detail,
        v_hint    = pg_exception_hint,
        v_context = pg_exception_context;
        
	RETURN '1,Se ha presentado un error inesperado: '||v_state||' '||v_msg; 	
	
END;
$$;

ALTER FUNCTION woaho.fndb_validar_codigo_registro(character varying,character varying)
  OWNER TO postgres;