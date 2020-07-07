CREATE OR REPLACE PROCEDURE woaho.prdb_generar_codigo_registro(IN p_celular usuario.usuario_celular%TYPE, INOUT p_resultado character varying)
LANGUAGE plpgsql    
AS $$
DECLARE
	cant_intentos numeric;
	codigo_generado character varying;
	
	v_state   text;
    v_msg     text;
    v_detail  text;
    v_hint    text;
    v_context text;
    	
BEGIN
	
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
	p_resultado := '0,' || codigo_generado ||','||p_celular;
	
EXCEPTION WHEN OTHERS THEN

	GET stacked DIAGNOSTICS
        v_state   = returned_sqlstate,
        v_msg     = message_text,
        v_detail  = pg_exception_detail,
        v_hint    = pg_exception_hint,
        v_context = pg_exception_context;
        
	p_resultado:= '1,Se ha presentado un error inesperado: '||v_state||' '||v_msg; 	
	
END;
$$;

ALTER PROCEDURE woaho.prdb_generar_codigo_registro(usuario.usuario_celular%TYPE,character varying)
  OWNER TO postgres;