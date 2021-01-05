CREATE OR REPLACE FUNCTION woaho.fndb_generar_codigo_registro(p_celular character varying,p_idioma character varying)
RETURNS character varying 
LANGUAGE plpgsql
AS $$
DECLARE
	cant_intentos numeric;
	codigo_generado character varying;
	codigo_anterior character varying;
	
	v_state   text;
    v_msg     text;
    v_detail  text;
    v_hint    text;
    v_context text;
    	
BEGIN
	
	SELECT codigo_numero
	INTO codigo_anterior
	FROM codigo cod
	WHERE cod.codigo_celular = p_celular
	AND cod.codigo_estado = 1;
	
	IF (codigo_anterior IS NULL) THEN
		SELECT (pa.parametro_valor::numeric)
		INTO cant_intentos
		FROM parametro pa
		WHERE pa.parametro_nombre = 'CANT_INT_COD_REGISTRO';
	
		codigo_generado := generar_aleatorios(111111,999999)::character varying;
	
		INSERT INTO codigo (codigo_numero,
						codigo_celular,
						codigo_intentos,
						codigo_fecha_hora_registro,
						codigo_estado) 
				VALUES (codigo_generado,
						p_celular,
						cant_intentos,
						NOW(),
						1);
	ELSE
		codigo_generado := codigo_anterior;
	END IF;	
	
	RETURN '0,' || codigo_generado ||','||p_celular;
	
EXCEPTION WHEN OTHERS THEN

	GET stacked DIAGNOSTICS
        v_state   = returned_sqlstate,
        v_msg     = message_text,
        v_detail  = pg_exception_detail,
        v_hint    = pg_exception_hint,
        v_context = pg_exception_context;
        
	RETURN '1,'|| woaho.fndb_consultar_equivalencia('Se ha presentado un error inesperado:',p_idioma) ||' '||v_state||' '||v_msg; 	
	
END;
$$;

ALTER FUNCTION woaho.fndb_generar_codigo_registro(character varying,character varying)
  OWNER TO postgres;