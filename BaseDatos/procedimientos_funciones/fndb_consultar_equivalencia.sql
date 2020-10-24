CREATE OR REPLACE FUNCTION woaho.fndb_consultar_equivalencia(p_cadena character varying,p_idioma character varying) 
RETURNS character varying 
LANGUAGE plpgsql
AS $$
DECLARE
	v_texto character varying;
BEGIN
   
   IF( LOWER(p_idioma) = 'es') THEN
   	RETURN p_cadena;
   ELSE
   	
   	SELECT ei.equivalencia_idioma_ingles
   	INTO v_texto
   	FROM  equivalencia_idioma ei
   	WHERE LOWER(equivalencia_idioma_original) LIKE LOWER(p_cadena);
   	
   	RETURN v_texto;
   	
   END IF;
   
END;
$$

ALTER FUNCTION woaho.fndb_consultar_equivalencia(character varying,character varying)
  OWNER TO postgres;