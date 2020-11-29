PGDMP         )            
    x            woaho    12.1    12.2 �               0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false                       0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false                       0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false                       1262    16385    woaho    DATABASE     w   CREATE DATABASE woaho WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'en_US.UTF-8' LC_CTYPE = 'en_US.UTF-8';
    DROP DATABASE woaho;
                postgres    false                        2615    16386    woaho    SCHEMA        CREATE SCHEMA woaho;
    DROP SCHEMA woaho;
                postgres    false                       1255    17020 -   consultar_mensajes_pantalla(integer, integer)    FUNCTION     a  CREATE FUNCTION woaho.consultar_mensajes_pantalla(p_pantalla integer, p_idioma integer) RETURNS character varying
    LANGUAGE plpgsql COST 10
    AS $$
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
			SELECT tp.tipo_nombre,tr.traduccion_traduccion 
			FROM mensaje me,mensaje_pantalla mp,tipo tp,traduccion tr 
			WHERE mp.mensaje_pantalla_pantalla_id = p_pantalla
			AND mp.mensaje_pantalla_mensaje_id = me.mensaje_id
			AND tr.traduccion_idioma = p_idioma
			AND tr.traduccion_codigo_mensaje = me.mensaje_codigo 
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
	
	RETURN cadena_resultado;
	
END;
$$;
 W   DROP FUNCTION woaho.consultar_mensajes_pantalla(p_pantalla integer, p_idioma integer);
       woaho          postgres    false    7                       1255    17035 A   fndb_consultar_equivalencia(character varying, character varying)    FUNCTION     �  CREATE FUNCTION woaho.fndb_consultar_equivalencia(p_cadena character varying, p_idioma character varying) RETURNS character varying
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
$$;
 i   DROP FUNCTION woaho.fndb_consultar_equivalencia(p_cadena character varying, p_idioma character varying);
       woaho          postgres    false    7                       1255    17021 4   fndb_consultar_pantallas(integer, character varying)    FUNCTION     e  CREATE FUNCTION woaho.fndb_consultar_pantallas(p_tipo_pantalla integer, p_idioma character varying) RETURNS character varying
    LANGUAGE plpgsql COST 10
    AS $$
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
$$;
 c   DROP FUNCTION woaho.fndb_consultar_pantallas(p_tipo_pantalla integer, p_idioma character varying);
       woaho          postgres    false    7                       1255    17036 /   fndb_generar_codigo_registro(character varying)    FUNCTION     �  CREATE FUNCTION woaho.fndb_generar_codigo_registro(p_celular character varying) RETURNS character varying
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
 O   DROP FUNCTION woaho.fndb_generar_codigo_registro(p_celular character varying);
       woaho          postgres    false    7                       1255    17038 B   fndb_generar_codigo_registro(character varying, character varying)    FUNCTION     �  CREATE FUNCTION woaho.fndb_generar_codigo_registro(p_celular character varying, p_idioma character varying) RETURNS character varying
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
 k   DROP FUNCTION woaho.fndb_generar_codigo_registro(p_celular character varying, p_idioma character varying);
       woaho          woaho    false    7                       1255    17037 U   fndb_validar_codigo_registro(character varying, character varying, character varying)    FUNCTION     W	  CREATE FUNCTION woaho.fndb_validar_codigo_registro(p_celular character varying, p_codigo character varying, p_idioma character varying) RETURNS character varying
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

	SELECT cod.codigo_numero,cod.codigo_intentos,EXTRACT(minute FROM (now() - cod.codigo_fecha_hora_registro))::numeric
	INTO codigo_generado,cant_intentos,cant_minutos_codigo
	FROM codigo cod
	WHERE cod.codigo_celular = p_celular
	AND cod.codigo_estado = 1;
	
	IF (codigo_generado IS NULL) THEN
		RETURN '1,'|| woaho.fndb_consultar_equivalencia('El codigo ingresado no se encuentra registrado.Intente nuevamente o solicite un nuevo codigo.',p_idioma);
	ELSE
		SELECT par.parametro_valor::numeric
		INTO parametro_tiempo
		FROM woaho.parametro par
		WHERE par.parametro_nombre = 'TIEMPO_COD_REGISTRO';
		
		IF (codigo_generado = p_codigo) THEN
			UPDATE codigo
			SET codigo_estado = 2
			WHERE codigo.codigo_celular = p_celular
			AND codigo.codigo_estado = 1;
			
			IF (cant_minutos_codigo > parametro_tiempo) THEN
				RETURN '1,'|| woaho.fndb_consultar_equivalencia('El codigo ingresado ha caducado.Solicite un nuevo codigo.',p_idioma);
			ELSE
				RETURN '0,OK';
			END IF;					
		ELSE
			IF (cant_intentos > 0) THEN
				UPDATE codigo
				SET codigo_intentos = cant_intentos - 1
				WHERE codigo.codigo_celular = p_celular
				AND codigo.codigo_estado = 1;
				RETURN '1,'|| woaho.fndb_consultar_equivalencia('El codigo ingresado no corresponde.Intente nuevamente o solicite un nuevo codigo.',p_idioma);
			ELSE
				RETURN '1,'|| woaho.fndb_consultar_equivalencia('El codigo ingresado ya no es valido.Solicite un nuevo codigo.',p_idioma);
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
        
	RETURN '1,'|| woaho.fndb_consultar_equivalencia('Se ha presentado un error inesperado:',p_idioma) ||' '||v_state||' '||v_msg;
	
END;
$$;
 �   DROP FUNCTION woaho.fndb_validar_codigo_registro(p_celular character varying, p_codigo character varying, p_idioma character varying);
       woaho          postgres    false    7                       1255    16937 $   generar_aleatorios(integer, integer)    FUNCTION     �   CREATE FUNCTION woaho.generar_aleatorios(pinicial integer, pfinal integer) RETURNS character varying
    LANGUAGE plpgsql
    AS $$
BEGIN
   RETURN floor(random()* (pFinal-pInicial + 1) + pInicial);
END;
$$;
 J   DROP FUNCTION woaho.generar_aleatorios(pinicial integer, pfinal integer);
       woaho          woaho    false    7            �            1259    16429    sec_calificacion    SEQUENCE     �   CREATE SEQUENCE woaho.sec_calificacion
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999
    CACHE 1
    CYCLE;
 &   DROP SEQUENCE woaho.sec_calificacion;
       woaho          postgres    false    7            �            1259    16799    calificacion    TABLE     F  CREATE TABLE woaho.calificacion (
    calificacion_id integer DEFAULT nextval('woaho.sec_calificacion'::regclass) NOT NULL,
    calificacion_usuario integer,
    calificacion_profesional integer,
    calificacion_descripcion character varying(4000),
    calificacion_calificacion integer,
    calificacion_servicio integer
);
    DROP TABLE woaho.calificacion;
       woaho         heap    postgres    false    224    7                       0    0    TABLE calificacion    COMMENT     e   COMMENT ON TABLE woaho.calificacion IS 'Tabla que contiene las calificaciones de los profesionales';
          woaho          postgres    false    252            �            1259    16441    sec_cancelacion    SEQUENCE     �   CREATE SEQUENCE woaho.sec_cancelacion
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999
    CACHE 1
    CYCLE;
 %   DROP SEQUENCE woaho.sec_cancelacion;
       woaho          postgres    false    7            �            1259    16876    cancelacion    TABLE     �   CREATE TABLE woaho.cancelacion (
    cancelacion_id integer DEFAULT nextval('woaho.sec_cancelacion'::regclass) NOT NULL,
    cancelacion_pedido integer,
    cancelacion_motivo character varying(4000),
    cancelacion_fecha timestamp without time zone
);
    DROP TABLE woaho.cancelacion;
       woaho         heap    postgres    false    230    7                       0    0    TABLE cancelacion    COMMENT     Y   COMMENT ON TABLE woaho.cancelacion IS 'Tabla que contiene las cancelaciones realizadas';
          woaho          postgres    false    255            �            1259    16409    sec_categoria    SEQUENCE     �   CREATE SEQUENCE woaho.sec_categoria
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999
    CACHE 1
    CYCLE;
 #   DROP SEQUENCE woaho.sec_categoria;
       woaho          postgres    false    7            �            1259    16632 	   categoria    TABLE     �   CREATE TABLE woaho.categoria (
    categoria_id integer DEFAULT nextval('woaho.sec_categoria'::regclass) NOT NULL,
    categoria_descripcion character varying(4000),
    categoria_imagen integer
);
    DROP TABLE woaho.categoria;
       woaho         heap    postgres    false    214    7                       0    0    TABLE categoria    COMMENT     \   COMMENT ON TABLE woaho.categoria IS 'Tabla que contiene las categorias para el aplicativo';
          woaho          postgres    false    243            �            1259    16403 
   sec_codigo    SEQUENCE     �   CREATE SEQUENCE woaho.sec_codigo
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999
    CACHE 1
    CYCLE;
     DROP SEQUENCE woaho.sec_codigo;
       woaho          postgres    false    7            �            1259    16571    codigo    TABLE     7  CREATE TABLE woaho.codigo (
    codigo_id integer DEFAULT nextval('woaho.sec_codigo'::regclass) NOT NULL,
    codigo_numero character varying(4000),
    codigo_celular character varying(4000),
    codigo_intentos integer,
    codigo_fecha_hora_registro timestamp without time zone,
    codigo_estado integer
);
    DROP TABLE woaho.codigo;
       woaho         heap    postgres    false    211    7                       0    0    TABLE codigo    COMMENT     h   COMMENT ON TABLE woaho.codigo IS 'Tabla que contiene los codigos generados para completar el registro';
          woaho          postgres    false    239            �            1259    16417    sec_codigo_promocional    SEQUENCE     �   CREATE SEQUENCE woaho.sec_codigo_promocional
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999
    CACHE 1
    CYCLE;
 ,   DROP SEQUENCE woaho.sec_codigo_promocional;
       woaho          postgres    false    7            �            1259    16741    codigo_promocional    TABLE     @  CREATE TABLE woaho.codigo_promocional (
    codigo_promocional_id integer DEFAULT nextval('woaho.sec_codigo_promocional'::regclass) NOT NULL,
    codigo_promocional_codigo character varying(4000),
    codigo_promocional_usuario integer,
    codigo_promocional_estado integer,
    codigo_promocional_promocion integer
);
 %   DROP TABLE woaho.codigo_promocional;
       woaho         heap    postgres    false    218    7                       0    0    TABLE codigo_promocional    COMMENT     l   COMMENT ON TABLE woaho.codigo_promocional IS 'Tabla que contiene los codigos promocionales del aplicativo';
          woaho          postgres    false    249            �            1259    16407    sec_direccion    SEQUENCE     �   CREATE SEQUENCE woaho.sec_direccion
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999
    CACHE 1
    CYCLE;
 #   DROP SEQUENCE woaho.sec_direccion;
       woaho          postgres    false    7            �            1259    16590 	   direccion    TABLE     �  CREATE TABLE woaho.direccion (
    direccion_id integer DEFAULT nextval('woaho.sec_direccion'::regclass) NOT NULL,
    direccion_nombre character varying(4000),
    direccion_descripcion character varying(4000),
    direccion_territorio_id integer,
    direccion_edificacion character varying(4000),
    direccion_estado integer,
    direccion_usuario integer,
    direccion_latitud character varying(4000),
    direccion_longitud character varying(4000),
    direccion_lugar_id character varying(4000)
);
    DROP TABLE woaho.direccion;
       woaho         heap    postgres    false    213    7                       0    0    TABLE direccion    COMMENT     g   COMMENT ON TABLE woaho.direccion IS 'Tabla que contiene las direcciones registradas por los usuarios';
          woaho          postgres    false    240                       1259    17022    sec_equivalencia_idioma    SEQUENCE     �   CREATE SEQUENCE woaho.sec_equivalencia_idioma
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999
    CACHE 1
    CYCLE;
 -   DROP SEQUENCE woaho.sec_equivalencia_idioma;
       woaho          postgres    false    7                       1259    17024    equivalencia_idioma    TABLE       CREATE TABLE woaho.equivalencia_idioma (
    equivalencia_idioma_id integer DEFAULT nextval('woaho.sec_equivalencia_idioma'::regclass) NOT NULL,
    equivalencia_idioma_original character varying(4000),
    equivalencia_idioma_ingles character varying(4000)
);
 &   DROP TABLE woaho.equivalencia_idioma;
       woaho         heap    postgres    false    261    7                        0    0    TABLE equivalencia_idioma    COMMENT     f   COMMENT ON TABLE woaho.equivalencia_idioma IS 'Tabla que contiene las traducciones de las etiquetas';
          woaho          postgres    false    262            �            1259    16401 
   sec_estado    SEQUENCE     �   CREATE SEQUENCE woaho.sec_estado
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999
    CACHE 1
    CYCLE;
     DROP SEQUENCE woaho.sec_estado;
       woaho          postgres    false    7            �            1259    16562    estado    TABLE     �   CREATE TABLE woaho.estado (
    estado_id integer DEFAULT nextval('woaho.sec_estado'::regclass) NOT NULL,
    estado_codigo character varying(4000)
);
    DROP TABLE woaho.estado;
       woaho         heap    postgres    false    210    7            !           0    0    TABLE estado    COMMENT     V   COMMENT ON TABLE woaho.estado IS 'Tabla que contiene los estados para el aplicativo';
          woaho          postgres    false    238            "           0    0    COLUMN estado.estado_codigo    COMMENT     b   COMMENT ON COLUMN woaho.estado.estado_codigo IS 'A activo, I inactivo, P pendiente, R rechazado';
          woaho          postgres    false    238            �            1259    16437    sec_etiqueta    SEQUENCE     �   CREATE SEQUENCE woaho.sec_etiqueta
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999
    CACHE 1
    CYCLE;
 "   DROP SEQUENCE woaho.sec_etiqueta;
       woaho          postgres    false    7                       1259    16990    etiqueta    TABLE     �   CREATE TABLE woaho.etiqueta (
    etiqueta_id integer DEFAULT nextval('woaho.sec_etiqueta'::regclass) NOT NULL,
    etiqueta_codigo character varying(4000),
    etiqueta_idioma integer,
    etiqueta_valor character varying(100)
);
    DROP TABLE woaho.etiqueta;
       woaho         heap    postgres    false    228    7            #           0    0    TABLE etiqueta    COMMENT     V   COMMENT ON TABLE woaho.etiqueta IS 'Tabla que contiene las etiquetas del aplicativo';
          woaho          postgres    false    258            �            1259    16431 
   sec_idioma    SEQUENCE     �   CREATE SEQUENCE woaho.sec_idioma
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999
    CACHE 1
    CYCLE;
     DROP SEQUENCE woaho.sec_idioma;
       woaho          postgres    false    7            �            1259    16452    idioma    TABLE     �   CREATE TABLE woaho.idioma (
    idioma_id integer DEFAULT nextval('woaho.sec_idioma'::regclass) NOT NULL,
    idioma_nombre character varying(4000),
    idioma_codigo character varying(4000)
);
    DROP TABLE woaho.idioma;
       woaho         heap    postgres    false    225    7            $           0    0    TABLE idioma    COMMENT     e   COMMENT ON TABLE woaho.idioma IS 'Tabla que contiene la información de los idiomas del aplicativo';
          woaho          postgres    false    232            �            1259    16427 
   sec_imagen    SEQUENCE     �   CREATE SEQUENCE woaho.sec_imagen
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999
    CACHE 1
    CYCLE;
     DROP SEQUENCE woaho.sec_imagen;
       woaho          postgres    false    7            �            1259    16623    imagen    TABLE       CREATE TABLE woaho.imagen (
    imagen_id integer DEFAULT nextval('woaho.sec_imagen'::regclass) NOT NULL,
    imagen_nombre character varying(4000),
    imagen_ruta character varying(4000),
    imagen_alto character varying(4000),
    imagen_ancho character varying(4000)
);
    DROP TABLE woaho.imagen;
       woaho         heap    postgres    false    223    7            %           0    0    TABLE imagen    COMMENT     W   COMMENT ON TABLE woaho.imagen IS 'Tabla que contiene las imagenes para el aplicativo';
          woaho          postgres    false    242            �            1259    16439    sec_medio_pago    SEQUENCE     �   CREATE SEQUENCE woaho.sec_medio_pago
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999
    CACHE 1
    CYCLE;
 $   DROP SEQUENCE woaho.sec_medio_pago;
       woaho          postgres    false    7            �            1259    16823 
   medio_pago    TABLE     �   CREATE TABLE woaho.medio_pago (
    medio_pago_id integer DEFAULT nextval('woaho.sec_medio_pago'::regclass) NOT NULL,
    medio_pago_nombre character varying(4000),
    medio_pago_etiqueta character varying(4000),
    medio_pago_territorio integer
);
    DROP TABLE woaho.medio_pago;
       woaho         heap    postgres    false    229    7            &           0    0    TABLE medio_pago    COMMENT     b   COMMENT ON TABLE woaho.medio_pago IS 'Tabla que contiene los medios de pagos para el aplicativo';
          woaho          postgres    false    253            �            1259    16387    sec_mensaje    SEQUENCE     �   CREATE SEQUENCE woaho.sec_mensaje
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999
    CACHE 1
    CYCLE;
 !   DROP SEQUENCE woaho.sec_mensaje;
       woaho          postgres    false    7            �            1259    16498    mensaje    TABLE     �   CREATE TABLE woaho.mensaje (
    mensaje_id integer DEFAULT nextval('woaho.sec_mensaje'::regclass) NOT NULL,
    mensaje_tipo integer,
    mensaje_codigo character varying(4000)
);
    DROP TABLE woaho.mensaje;
       woaho         heap    postgres    false    203    7            '           0    0    TABLE mensaje    COMMENT     e   COMMENT ON TABLE woaho.mensaje IS 'Tabla que contiene la información de los mensajes por pantalla';
          woaho          postgres    false    234            �            1259    16389    sec_mensaje_pantalla    SEQUENCE     �   CREATE SEQUENCE woaho.sec_mensaje_pantalla
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999
    CACHE 1
    CYCLE;
 *   DROP SEQUENCE woaho.sec_mensaje_pantalla;
       woaho          postgres    false    7                       1259    16909    mensaje_pantalla    TABLE     �   CREATE TABLE woaho.mensaje_pantalla (
    mensaje_pantalla_id integer DEFAULT nextval('woaho.sec_mensaje_pantalla'::regclass) NOT NULL,
    mensaje_pantalla_pantalla_id integer,
    mensaje_pantalla_mensaje_id integer
);
 #   DROP TABLE woaho.mensaje_pantalla;
       woaho         heap    postgres    false    204    7            (           0    0    TABLE mensaje_pantalla    COMMENT     h   COMMENT ON TABLE woaho.mensaje_pantalla IS 'Tabla que contiene la relacion entre mensajes y pantallas';
          woaho          postgres    false    257            �            1259    16411 
   sec_moneda    SEQUENCE     �   CREATE SEQUENCE woaho.sec_moneda
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999
    CACHE 1
    CYCLE;
     DROP SEQUENCE woaho.sec_moneda;
       woaho          postgres    false    7            �            1259    16646    moneda    TABLE     �   CREATE TABLE woaho.moneda (
    moneda_id integer DEFAULT nextval('woaho.sec_moneda'::regclass) NOT NULL,
    moneda_nombre character varying(4000),
    moneda_territorio integer
);
    DROP TABLE woaho.moneda;
       woaho         heap    postgres    false    215    7            )           0    0    TABLE moneda    COMMENT     V   COMMENT ON TABLE woaho.moneda IS 'Tabla que contiene las monedas para el aplicativo';
          woaho          postgres    false    244            �            1259    16391    sec_pantalla    SEQUENCE     �   CREATE SEQUENCE woaho.sec_pantalla
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999
    CACHE 1
    CYCLE;
 "   DROP SEQUENCE woaho.sec_pantalla;
       woaho          postgres    false    7                        1259    16890    pantalla    TABLE     �   CREATE TABLE woaho.pantalla (
    pantalla_id integer DEFAULT nextval('woaho.sec_pantalla'::regclass) NOT NULL,
    pantalla_nombre character varying(4000),
    pantalla_imagen integer,
    pantalla_tipo_pantalla integer
);
    DROP TABLE woaho.pantalla;
       woaho         heap    postgres    false    205    7            *           0    0    TABLE pantalla    COMMENT     Z   COMMENT ON TABLE woaho.pantalla IS 'Tabla que contiene la información de las pantallas';
          woaho          postgres    false    256            �            1259    16405    sec_parametro    SEQUENCE     �   CREATE SEQUENCE woaho.sec_parametro
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999
    CACHE 1
    CYCLE;
 #   DROP SEQUENCE woaho.sec_parametro;
       woaho          postgres    false    7            �            1259    16614 	   parametro    TABLE       CREATE TABLE woaho.parametro (
    parametro_id integer DEFAULT nextval('woaho.sec_parametro'::regclass) NOT NULL,
    parametro_nombre character varying(4000),
    parametro_valor character varying(4000),
    parametro_descripcion character varying(4000)
);
    DROP TABLE woaho.parametro;
       woaho         heap    postgres    false    212    7            +           0    0    TABLE parametro    COMMENT     X   COMMENT ON TABLE woaho.parametro IS 'Tabla que contiene los parametros del aplicativo';
          woaho          postgres    false    241            �            1259    16423 
   sec_pedido    SEQUENCE     �   CREATE SEQUENCE woaho.sec_pedido
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999
    CACHE 1
    CYCLE;
     DROP SEQUENCE woaho.sec_pedido;
       woaho          postgres    false    7            �            1259    16837    pedido    TABLE     `  CREATE TABLE woaho.pedido (
    pedido_id integer DEFAULT nextval('woaho.sec_pedido'::regclass) NOT NULL,
    pedido_servicio integer,
    pedido_usuario integer,
    pedido_descripcion character varying(4000),
    pedido_estado integer,
    pedido_direccion integer,
    pedido_cod_promocional character varying(4000),
    pedido_fecha character varying(4000),
    pedido_hora character varying(4000),
    pedido_profesional integer,
    pedido_medio_pago integer,
    pedido_fecha_final timestamp without time zone,
    pedido_latitud character varying(4000),
    pedido_longitu character varying(4000)
);
    DROP TABLE woaho.pedido;
       woaho         heap    postgres    false    221    7            ,           0    0    TABLE pedido    COMMENT     R   COMMENT ON TABLE woaho.pedido IS 'Tabla que contiene los pedidos del aplicativo';
          woaho          postgres    false    254            �            1259    16435    sec_profesion    SEQUENCE     �   CREATE SEQUENCE woaho.sec_profesion
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999
    CACHE 1
    CYCLE;
 #   DROP SEQUENCE woaho.sec_profesion;
       woaho          postgres    false    7            �            1259    16475 	   profesion    TABLE     �   CREATE TABLE woaho.profesion (
    profesion_id integer DEFAULT nextval('woaho.sec_profesion'::regclass) NOT NULL,
    profesion_nombre character varying(4000)
);
    DROP TABLE woaho.profesion;
       woaho         heap    postgres    false    227    7            -           0    0    TABLE profesion    COMMENT     l   COMMENT ON TABLE woaho.profesion IS 'Tabla que contiene la información de las profesiones del aplicativo';
          woaho          postgres    false    233            �            1259    16425    sec_profesional    SEQUENCE     �   CREATE SEQUENCE woaho.sec_profesional
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999
    CACHE 1
    CYCLE;
 %   DROP SEQUENCE woaho.sec_profesional;
       woaho          postgres    false    7            �            1259    16766    profesional    TABLE       CREATE TABLE woaho.profesional (
    profesional_id integer DEFAULT nextval('woaho.sec_profesional'::regclass) NOT NULL,
    profesional_nombre character varying(4000),
    profesional_apellido character varying(4000),
    profesional_profesiones character varying(4000),
    profesional_nacionalidad integer,
    profesional_servicios character varying(4000),
    profesional_lenguajes character varying(4000),
    profesional_descripcion character varying(4000),
    profesional_imagen_icono integer,
    profesional_cant_estrellas numeric,
    profesional_cant_servicios integer,
    profesional_distancia numeric DEFAULT 0 NOT NULL
);
    DROP TABLE woaho.profesional;
       woaho         heap    postgres    false    222    7            .           0    0    TABLE profesional    COMMENT     ]   COMMENT ON TABLE woaho.profesional IS 'Tabla que contiene los profesionales del aplicativo';
          woaho          postgres    false    250            �            1259    16419    sec_promocion    SEQUENCE     �   CREATE SEQUENCE woaho.sec_promocion
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999
    CACHE 1
    CYCLE;
 #   DROP SEQUENCE woaho.sec_promocion;
       woaho          postgres    false    7            �            1259    16722 	   promocion    TABLE       CREATE TABLE woaho.promocion (
    promocion_id integer DEFAULT nextval('woaho.sec_promocion'::regclass) NOT NULL,
    promocion_descuento integer,
    promocion_tarifa integer,
    promocion_estado integer,
    promocion_descripcion character varying(4000)
);
    DROP TABLE woaho.promocion;
       woaho         heap    postgres    false    219    7            /           0    0    TABLE promocion    COMMENT     Y   COMMENT ON TABLE woaho.promocion IS 'Tabla que contiene las promociones del aplicativo';
          woaho          postgres    false    248            �            1259    16413    sec_servicio    SEQUENCE     �   CREATE SEQUENCE woaho.sec_servicio
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999
    CACHE 1
    CYCLE;
 "   DROP SEQUENCE woaho.sec_servicio;
       woaho          postgres    false    7            �            1259    16415 
   sec_tarifa    SEQUENCE     �   CREATE SEQUENCE woaho.sec_tarifa
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999
    CACHE 1
    CYCLE;
     DROP SEQUENCE woaho.sec_tarifa;
       woaho          postgres    false    7            �            1259    16395    sec_territorio    SEQUENCE     �   CREATE SEQUENCE woaho.sec_territorio
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999
    CACHE 1
    CYCLE;
 $   DROP SEQUENCE woaho.sec_territorio;
       woaho          woaho    false    7            �            1259    16393    sec_tipo    SEQUENCE     ~   CREATE SEQUENCE woaho.sec_tipo
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999
    CACHE 1
    CYCLE;
    DROP SEQUENCE woaho.sec_tipo;
       woaho          postgres    false    7            �            1259    16397    sec_tipo_territorio    SEQUENCE     �   CREATE SEQUENCE woaho.sec_tipo_territorio
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999
    CACHE 1
    CYCLE;
 )   DROP SEQUENCE woaho.sec_tipo_territorio;
       woaho          postgres    false    7                       1259    17004    sec_traduccion    SEQUENCE     �   CREATE SEQUENCE woaho.sec_traduccion
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999
    CACHE 1
    CYCLE;
 $   DROP SEQUENCE woaho.sec_traduccion;
       woaho          postgres    false    7            �            1259    16433    sec_ubicacion    SEQUENCE     �   CREATE SEQUENCE woaho.sec_ubicacion
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999
    CACHE 1
    CYCLE;
 #   DROP SEQUENCE woaho.sec_ubicacion;
       woaho          postgres    false    7            �            1259    16421    sec_unidad_tarifa    SEQUENCE     �   CREATE SEQUENCE woaho.sec_unidad_tarifa
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999
    CACHE 1
    CYCLE;
 '   DROP SEQUENCE woaho.sec_unidad_tarifa;
       woaho          postgres    false    7            �            1259    16399    sec_usuario    SEQUENCE     �   CREATE SEQUENCE woaho.sec_usuario
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999
    CACHE 1
    CYCLE;
 !   DROP SEQUENCE woaho.sec_usuario;
       woaho          postgres    false    7            �            1259    16669    servicio    TABLE     /  CREATE TABLE woaho.servicio (
    servicio_id integer DEFAULT nextval('woaho.sec_servicio'::regclass) NOT NULL,
    servicio_nombre character varying(4000),
    servicio_imagen integer,
    servicio_categoria integer,
    servicio_territorio integer,
    servicio_descripcion character varying(4000)
);
    DROP TABLE woaho.servicio;
       woaho         heap    postgres    false    216    7            0           0    0    TABLE servicio    COMMENT     Z   COMMENT ON TABLE woaho.servicio IS 'Tabla que contiene los servicios para el aplicativo';
          woaho          postgres    false    246            �            1259    16693    tarifa    TABLE     �   CREATE TABLE woaho.tarifa (
    tarifa_id integer DEFAULT nextval('woaho.sec_tarifa'::regclass) NOT NULL,
    tarifa_valor numeric,
    tarifa_moneda integer,
    tarifa_territorio integer,
    tarifa_servicio integer,
    tarifa_unidad integer
);
    DROP TABLE woaho.tarifa;
       woaho         heap    postgres    false    217    7            1           0    0    TABLE tarifa    COMMENT     V   COMMENT ON TABLE woaho.tarifa IS 'Tabla que contiene las tarifas para el aplicativo';
          woaho          postgres    false    247            �            1259    16537 
   territorio    TABLE       CREATE TABLE woaho.territorio (
    territorio_id integer DEFAULT nextval('woaho.sec_territorio'::regclass) NOT NULL,
    territorio_nombre character varying(4000),
    territorio_padre integer,
    territorio_tipo integer,
    territorio_codigo character varying(4000)
);
    DROP TABLE woaho.territorio;
       woaho         heap    postgres    false    207    7            2           0    0    TABLE territorio    COMMENT     h   COMMENT ON TABLE woaho.territorio IS 'Tabla que contiene los territorios registrados en el aplicativo';
          woaho          postgres    false    236            �            1259    16443    tipo    TABLE     �   CREATE TABLE woaho.tipo (
    tipo_id integer DEFAULT nextval('woaho.sec_tipo'::regclass) NOT NULL,
    tipo_nombre character varying(4000)
);
    DROP TABLE woaho.tipo;
       woaho         heap    postgres    false    206    7            3           0    0 
   TABLE tipo    COMMENT     ^   COMMENT ON TABLE woaho.tipo IS 'Tabla que contiene la información de los tipos de pantalla';
          woaho          postgres    false    231            �            1259    16528    tipo_territorio    TABLE     �   CREATE TABLE woaho.tipo_territorio (
    tipo_territorio_id integer DEFAULT nextval('woaho.sec_tipo_territorio'::regclass) NOT NULL,
    tipo_territorio_nombre character varying(4000)
);
 "   DROP TABLE woaho.tipo_territorio;
       woaho         heap    postgres    false    208    7            4           0    0    TABLE tipo_territorio    COMMENT     Y   COMMENT ON TABLE woaho.tipo_territorio IS 'Tabla que contiene los tipos de territorios';
          woaho          postgres    false    235                       1259    17006 
   traduccion    TABLE       CREATE TABLE woaho.traduccion (
    traduccion_id integer DEFAULT nextval('woaho.sec_traduccion'::regclass) NOT NULL,
    traduccion_codigo_mensaje character varying(4000),
    traduccion_traduccion character varying(4000),
    traduccion_idioma integer
);
    DROP TABLE woaho.traduccion;
       woaho         heap    woaho    false    259    7            �            1259    16785 	   ubicacion    TABLE     '  CREATE TABLE woaho.ubicacion (
    ubicacion_id integer DEFAULT nextval('woaho.sec_ubicacion'::regclass) NOT NULL,
    ubicacion_profesional integer,
    ubicacion_lugar_id character varying(4000),
    ubicacion_latitud character varying(4000),
    ubicacion_longitud character varying(4000)
);
    DROP TABLE woaho.ubicacion;
       woaho         heap    postgres    false    226    7            5           0    0    TABLE ubicacion    COMMENT     _   COMMENT ON TABLE woaho.ubicacion IS 'Tabla que contiene las ubicaciones de los profesionales';
          woaho          postgres    false    251            �            1259    16660    unidad_tarifa    TABLE     �   CREATE TABLE woaho.unidad_tarifa (
    unidad_tarifa_id integer DEFAULT nextval('woaho.sec_unidad_tarifa'::regclass) NOT NULL,
    unidad_tarifa_nombre character varying(4000)
);
     DROP TABLE woaho.unidad_tarifa;
       woaho         heap    postgres    false    220    7            6           0    0    TABLE unidad_tarifa    COMMENT     i   COMMENT ON TABLE woaho.unidad_tarifa IS 'Tabla que contiene las unidades de tarifas para el aplicativo';
          woaho          postgres    false    245            �            1259    16551    usuario    TABLE     +  CREATE TABLE woaho.usuario (
    usuario_id integer DEFAULT nextval('woaho.sec_usuario'::regclass) NOT NULL,
    usuario_nombre character varying(4000),
    usuario_apellido character varying(4000),
    usuario_celular character varying(4000),
    usuario_correo character varying(4000),
    usuario_acepta_terminos character varying(4000),
    usuario_fecha_hora_acepta_terminos timestamp without time zone,
    usuario_clave character varying(4000),
    usuario_id_suscriptor character varying(4000),
    usuario_referralcode character varying(4000)
);
    DROP TABLE woaho.usuario;
       woaho         heap    postgres    false    209    7            7           0    0    TABLE usuario    COMMENT     b   COMMENT ON TABLE woaho.usuario IS 'Tabla que contiene los usuarios registrados en el aplicativo';
          woaho          postgres    false    237            	          0    16799    calificacion 
   TABLE DATA                 woaho          postgres    false    252                      0    16876    cancelacion 
   TABLE DATA                 woaho          postgres    false    255                       0    16632 	   categoria 
   TABLE DATA                 woaho          postgres    false    243            �          0    16571    codigo 
   TABLE DATA                 woaho          postgres    false    239                      0    16741    codigo_promocional 
   TABLE DATA                 woaho          postgres    false    249            �          0    16590 	   direccion 
   TABLE DATA                 woaho          postgres    false    240                      0    17024    equivalencia_idioma 
   TABLE DATA                 woaho          postgres    false    262            �          0    16562    estado 
   TABLE DATA                 woaho          postgres    false    238                      0    16990    etiqueta 
   TABLE DATA                 woaho          postgres    false    258            �          0    16452    idioma 
   TABLE DATA                 woaho          postgres    false    232            �          0    16623    imagen 
   TABLE DATA                 woaho          postgres    false    242            
          0    16823 
   medio_pago 
   TABLE DATA                 woaho          postgres    false    253            �          0    16498    mensaje 
   TABLE DATA                 woaho          postgres    false    234                      0    16909    mensaje_pantalla 
   TABLE DATA                 woaho          postgres    false    257                      0    16646    moneda 
   TABLE DATA                 woaho          postgres    false    244                      0    16890    pantalla 
   TABLE DATA                 woaho          postgres    false    256            �          0    16614 	   parametro 
   TABLE DATA                 woaho          postgres    false    241                      0    16837    pedido 
   TABLE DATA                 woaho          postgres    false    254            �          0    16475 	   profesion 
   TABLE DATA                 woaho          postgres    false    233                      0    16766    profesional 
   TABLE DATA                 woaho          postgres    false    250                      0    16722 	   promocion 
   TABLE DATA                 woaho          postgres    false    248                      0    16669    servicio 
   TABLE DATA                 woaho          postgres    false    246                      0    16693    tarifa 
   TABLE DATA                 woaho          postgres    false    247            �          0    16537 
   territorio 
   TABLE DATA                 woaho          postgres    false    236            �          0    16443    tipo 
   TABLE DATA                 woaho          postgres    false    231            �          0    16528    tipo_territorio 
   TABLE DATA                 woaho          postgres    false    235                      0    17006 
   traduccion 
   TABLE DATA                 woaho          woaho    false    260                      0    16785 	   ubicacion 
   TABLE DATA                 woaho          postgres    false    251                      0    16660    unidad_tarifa 
   TABLE DATA                 woaho          postgres    false    245            �          0    16551    usuario 
   TABLE DATA                 woaho          postgres    false    237            8           0    0    sec_calificacion    SEQUENCE SET     =   SELECT pg_catalog.setval('woaho.sec_calificacion', 7, true);
          woaho          postgres    false    224            9           0    0    sec_cancelacion    SEQUENCE SET     =   SELECT pg_catalog.setval('woaho.sec_cancelacion', 1, false);
          woaho          postgres    false    230            :           0    0    sec_categoria    SEQUENCE SET     :   SELECT pg_catalog.setval('woaho.sec_categoria', 9, true);
          woaho          postgres    false    214            ;           0    0 
   sec_codigo    SEQUENCE SET     8   SELECT pg_catalog.setval('woaho.sec_codigo', 50, true);
          woaho          postgres    false    211            <           0    0    sec_codigo_promocional    SEQUENCE SET     D   SELECT pg_catalog.setval('woaho.sec_codigo_promocional', 1, false);
          woaho          postgres    false    218            =           0    0    sec_direccion    SEQUENCE SET     :   SELECT pg_catalog.setval('woaho.sec_direccion', 5, true);
          woaho          postgres    false    213            >           0    0    sec_equivalencia_idioma    SEQUENCE SET     E   SELECT pg_catalog.setval('woaho.sec_equivalencia_idioma', 60, true);
          woaho          postgres    false    261            ?           0    0 
   sec_estado    SEQUENCE SET     7   SELECT pg_catalog.setval('woaho.sec_estado', 6, true);
          woaho          postgres    false    210            @           0    0    sec_etiqueta    SEQUENCE SET     :   SELECT pg_catalog.setval('woaho.sec_etiqueta', 40, true);
          woaho          postgres    false    228            A           0    0 
   sec_idioma    SEQUENCE SET     7   SELECT pg_catalog.setval('woaho.sec_idioma', 2, true);
          woaho          postgres    false    225            B           0    0 
   sec_imagen    SEQUENCE SET     8   SELECT pg_catalog.setval('woaho.sec_imagen', 29, true);
          woaho          postgres    false    223            C           0    0    sec_medio_pago    SEQUENCE SET     ;   SELECT pg_catalog.setval('woaho.sec_medio_pago', 3, true);
          woaho          postgres    false    229            D           0    0    sec_mensaje    SEQUENCE SET     9   SELECT pg_catalog.setval('woaho.sec_mensaje', 36, true);
          woaho          postgres    false    203            E           0    0    sec_mensaje_pantalla    SEQUENCE SET     B   SELECT pg_catalog.setval('woaho.sec_mensaje_pantalla', 12, true);
          woaho          postgres    false    204            F           0    0 
   sec_moneda    SEQUENCE SET     7   SELECT pg_catalog.setval('woaho.sec_moneda', 3, true);
          woaho          postgres    false    215            G           0    0    sec_pantalla    SEQUENCE SET     9   SELECT pg_catalog.setval('woaho.sec_pantalla', 4, true);
          woaho          postgres    false    205            H           0    0    sec_parametro    SEQUENCE SET     :   SELECT pg_catalog.setval('woaho.sec_parametro', 2, true);
          woaho          postgres    false    212            I           0    0 
   sec_pedido    SEQUENCE SET     7   SELECT pg_catalog.setval('woaho.sec_pedido', 4, true);
          woaho          postgres    false    221            J           0    0    sec_profesion    SEQUENCE SET     :   SELECT pg_catalog.setval('woaho.sec_profesion', 3, true);
          woaho          postgres    false    227            K           0    0    sec_profesional    SEQUENCE SET     <   SELECT pg_catalog.setval('woaho.sec_profesional', 1, true);
          woaho          postgres    false    222            L           0    0    sec_promocion    SEQUENCE SET     ;   SELECT pg_catalog.setval('woaho.sec_promocion', 1, false);
          woaho          postgres    false    219            M           0    0    sec_servicio    SEQUENCE SET     9   SELECT pg_catalog.setval('woaho.sec_servicio', 3, true);
          woaho          postgres    false    216            N           0    0 
   sec_tarifa    SEQUENCE SET     8   SELECT pg_catalog.setval('woaho.sec_tarifa', 12, true);
          woaho          postgres    false    217            O           0    0    sec_territorio    SEQUENCE SET     ;   SELECT pg_catalog.setval('woaho.sec_territorio', 7, true);
          woaho          woaho    false    207            P           0    0    sec_tipo    SEQUENCE SET     5   SELECT pg_catalog.setval('woaho.sec_tipo', 5, true);
          woaho          postgres    false    206            Q           0    0    sec_tipo_territorio    SEQUENCE SET     @   SELECT pg_catalog.setval('woaho.sec_tipo_territorio', 7, true);
          woaho          postgres    false    208            R           0    0    sec_traduccion    SEQUENCE SET     <   SELECT pg_catalog.setval('woaho.sec_traduccion', 26, true);
          woaho          postgres    false    259            S           0    0    sec_ubicacion    SEQUENCE SET     :   SELECT pg_catalog.setval('woaho.sec_ubicacion', 2, true);
          woaho          postgres    false    226            T           0    0    sec_unidad_tarifa    SEQUENCE SET     >   SELECT pg_catalog.setval('woaho.sec_unidad_tarifa', 5, true);
          woaho          postgres    false    220            U           0    0    sec_usuario    SEQUENCE SET     9   SELECT pg_catalog.setval('woaho.sec_usuario', 42, true);
          woaho          postgres    false    209                       2606    16807    calificacion calificacion_pkey 
   CONSTRAINT     h   ALTER TABLE ONLY woaho.calificacion
    ADD CONSTRAINT calificacion_pkey PRIMARY KEY (calificacion_id);
 G   ALTER TABLE ONLY woaho.calificacion DROP CONSTRAINT calificacion_pkey;
       woaho            postgres    false    252            %           2606    16884    cancelacion cancelacion_pkey 
   CONSTRAINT     e   ALTER TABLE ONLY woaho.cancelacion
    ADD CONSTRAINT cancelacion_pkey PRIMARY KEY (cancelacion_id);
 E   ALTER TABLE ONLY woaho.cancelacion DROP CONSTRAINT cancelacion_pkey;
       woaho            postgres    false    255                       2606    16640    categoria categoria_pkey 
   CONSTRAINT     _   ALTER TABLE ONLY woaho.categoria
    ADD CONSTRAINT categoria_pkey PRIMARY KEY (categoria_id);
 A   ALTER TABLE ONLY woaho.categoria DROP CONSTRAINT categoria_pkey;
       woaho            postgres    false    243            �           2606    16561    usuario celular_key 
   CONSTRAINT     X   ALTER TABLE ONLY woaho.usuario
    ADD CONSTRAINT celular_key UNIQUE (usuario_celular);
 <   ALTER TABLE ONLY woaho.usuario DROP CONSTRAINT celular_key;
       woaho            postgres    false    237                       2606    16579    codigo codigo_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY woaho.codigo
    ADD CONSTRAINT codigo_pkey PRIMARY KEY (codigo_id);
 ;   ALTER TABLE ONLY woaho.codigo DROP CONSTRAINT codigo_pkey;
       woaho            postgres    false    239                       2606    16749 *   codigo_promocional codigo_promocional_pkey 
   CONSTRAINT     z   ALTER TABLE ONLY woaho.codigo_promocional
    ADD CONSTRAINT codigo_promocional_pkey PRIMARY KEY (codigo_promocional_id);
 S   ALTER TABLE ONLY woaho.codigo_promocional DROP CONSTRAINT codigo_promocional_pkey;
       woaho            postgres    false    249                       2606    16598    direccion direccion_pkey 
   CONSTRAINT     _   ALTER TABLE ONLY woaho.direccion
    ADD CONSTRAINT direccion_pkey PRIMARY KEY (direccion_id);
 A   ALTER TABLE ONLY woaho.direccion DROP CONSTRAINT direccion_pkey;
       woaho            postgres    false    240            /           2606    17032 ,   equivalencia_idioma equivalencia_idioma_pkey 
   CONSTRAINT     }   ALTER TABLE ONLY woaho.equivalencia_idioma
    ADD CONSTRAINT equivalencia_idioma_pkey PRIMARY KEY (equivalencia_idioma_id);
 U   ALTER TABLE ONLY woaho.equivalencia_idioma DROP CONSTRAINT equivalencia_idioma_pkey;
       woaho            postgres    false    262                       2606    16570    estado estado_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY woaho.estado
    ADD CONSTRAINT estado_pkey PRIMARY KEY (estado_id);
 ;   ALTER TABLE ONLY woaho.estado DROP CONSTRAINT estado_pkey;
       woaho            postgres    false    238            +           2606    16998    etiqueta etiqueta_pkey 
   CONSTRAINT     \   ALTER TABLE ONLY woaho.etiqueta
    ADD CONSTRAINT etiqueta_pkey PRIMARY KEY (etiqueta_id);
 ?   ALTER TABLE ONLY woaho.etiqueta DROP CONSTRAINT etiqueta_pkey;
       woaho            postgres    false    258            �           2606    16460    idioma idioma_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY woaho.idioma
    ADD CONSTRAINT idioma_pkey PRIMARY KEY (idioma_id);
 ;   ALTER TABLE ONLY woaho.idioma DROP CONSTRAINT idioma_pkey;
       woaho            postgres    false    232                       2606    16631    imagen imagen_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY woaho.imagen
    ADD CONSTRAINT imagen_pkey PRIMARY KEY (imagen_id);
 ;   ALTER TABLE ONLY woaho.imagen DROP CONSTRAINT imagen_pkey;
       woaho            postgres    false    242            !           2606    16831    medio_pago medio_pago_pkey 
   CONSTRAINT     b   ALTER TABLE ONLY woaho.medio_pago
    ADD CONSTRAINT medio_pago_pkey PRIMARY KEY (medio_pago_id);
 C   ALTER TABLE ONLY woaho.medio_pago DROP CONSTRAINT medio_pago_pkey;
       woaho            postgres    false    253            )           2606    16914 &   mensaje_pantalla mensaje_pantalla_pkey 
   CONSTRAINT     t   ALTER TABLE ONLY woaho.mensaje_pantalla
    ADD CONSTRAINT mensaje_pantalla_pkey PRIMARY KEY (mensaje_pantalla_id);
 O   ALTER TABLE ONLY woaho.mensaje_pantalla DROP CONSTRAINT mensaje_pantalla_pkey;
       woaho            postgres    false    257            �           2606    16506    mensaje mensaje_pkey 
   CONSTRAINT     Y   ALTER TABLE ONLY woaho.mensaje
    ADD CONSTRAINT mensaje_pkey PRIMARY KEY (mensaje_id);
 =   ALTER TABLE ONLY woaho.mensaje DROP CONSTRAINT mensaje_pkey;
       woaho            postgres    false    234                       2606    16654    moneda moneda_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY woaho.moneda
    ADD CONSTRAINT moneda_pkey PRIMARY KEY (moneda_id);
 ;   ALTER TABLE ONLY woaho.moneda DROP CONSTRAINT moneda_pkey;
       woaho            postgres    false    244            '           2606    16898    pantalla pantalla_pkey 
   CONSTRAINT     \   ALTER TABLE ONLY woaho.pantalla
    ADD CONSTRAINT pantalla_pkey PRIMARY KEY (pantalla_id);
 ?   ALTER TABLE ONLY woaho.pantalla DROP CONSTRAINT pantalla_pkey;
       woaho            postgres    false    256            	           2606    16622    parametro parametro_pkey 
   CONSTRAINT     _   ALTER TABLE ONLY woaho.parametro
    ADD CONSTRAINT parametro_pkey PRIMARY KEY (parametro_id);
 A   ALTER TABLE ONLY woaho.parametro DROP CONSTRAINT parametro_pkey;
       woaho            postgres    false    241            #           2606    16845    pedido pedido_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY woaho.pedido
    ADD CONSTRAINT pedido_pkey PRIMARY KEY (pedido_id);
 ;   ALTER TABLE ONLY woaho.pedido DROP CONSTRAINT pedido_pkey;
       woaho            postgres    false    254            �           2606    16483    profesion profesion_pkey 
   CONSTRAINT     _   ALTER TABLE ONLY woaho.profesion
    ADD CONSTRAINT profesion_pkey PRIMARY KEY (profesion_id);
 A   ALTER TABLE ONLY woaho.profesion DROP CONSTRAINT profesion_pkey;
       woaho            postgres    false    233                       2606    16774    profesional profesional_pkey 
   CONSTRAINT     e   ALTER TABLE ONLY woaho.profesional
    ADD CONSTRAINT profesional_pkey PRIMARY KEY (profesional_id);
 E   ALTER TABLE ONLY woaho.profesional DROP CONSTRAINT profesional_pkey;
       woaho            postgres    false    250                       2606    16730    promocion promocion_pkey 
   CONSTRAINT     _   ALTER TABLE ONLY woaho.promocion
    ADD CONSTRAINT promocion_pkey PRIMARY KEY (promocion_id);
 A   ALTER TABLE ONLY woaho.promocion DROP CONSTRAINT promocion_pkey;
       woaho            postgres    false    248                       2606    16677    servicio servicio_pkey 
   CONSTRAINT     \   ALTER TABLE ONLY woaho.servicio
    ADD CONSTRAINT servicio_pkey PRIMARY KEY (servicio_id);
 ?   ALTER TABLE ONLY woaho.servicio DROP CONSTRAINT servicio_pkey;
       woaho            postgres    false    246                       2606    16701    tarifa tarifa_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY woaho.tarifa
    ADD CONSTRAINT tarifa_pkey PRIMARY KEY (tarifa_id);
 ;   ALTER TABLE ONLY woaho.tarifa DROP CONSTRAINT tarifa_pkey;
       woaho            postgres    false    247            �           2606    16545    territorio territorio_pkey 
   CONSTRAINT     b   ALTER TABLE ONLY woaho.territorio
    ADD CONSTRAINT territorio_pkey PRIMARY KEY (territorio_id);
 C   ALTER TABLE ONLY woaho.territorio DROP CONSTRAINT territorio_pkey;
       woaho            postgres    false    236            �           2606    16451    tipo tipo_pantalla_pkey 
   CONSTRAINT     Y   ALTER TABLE ONLY woaho.tipo
    ADD CONSTRAINT tipo_pantalla_pkey PRIMARY KEY (tipo_id);
 @   ALTER TABLE ONLY woaho.tipo DROP CONSTRAINT tipo_pantalla_pkey;
       woaho            postgres    false    231            �           2606    16536 $   tipo_territorio tipo_territorio_pkey 
   CONSTRAINT     q   ALTER TABLE ONLY woaho.tipo_territorio
    ADD CONSTRAINT tipo_territorio_pkey PRIMARY KEY (tipo_territorio_id);
 M   ALTER TABLE ONLY woaho.tipo_territorio DROP CONSTRAINT tipo_territorio_pkey;
       woaho            postgres    false    235            -           2606    17014    traduccion traduccion_pkey 
   CONSTRAINT     b   ALTER TABLE ONLY woaho.traduccion
    ADD CONSTRAINT traduccion_pkey PRIMARY KEY (traduccion_id);
 C   ALTER TABLE ONLY woaho.traduccion DROP CONSTRAINT traduccion_pkey;
       woaho            woaho    false    260                       2606    16793    ubicacion ubicacion_pkey 
   CONSTRAINT     _   ALTER TABLE ONLY woaho.ubicacion
    ADD CONSTRAINT ubicacion_pkey PRIMARY KEY (ubicacion_id);
 A   ALTER TABLE ONLY woaho.ubicacion DROP CONSTRAINT ubicacion_pkey;
       woaho            postgres    false    251                       2606    16668     unidad_tarifa unidad_tarifa_pkey 
   CONSTRAINT     k   ALTER TABLE ONLY woaho.unidad_tarifa
    ADD CONSTRAINT unidad_tarifa_pkey PRIMARY KEY (unidad_tarifa_id);
 I   ALTER TABLE ONLY woaho.unidad_tarifa DROP CONSTRAINT unidad_tarifa_pkey;
       woaho            postgres    false    245                       2606    16559    usuario usuario_pkey 
   CONSTRAINT     Y   ALTER TABLE ONLY woaho.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (usuario_id);
 =   ALTER TABLE ONLY woaho.usuario DROP CONSTRAINT usuario_pkey;
       woaho            postgres    false    237            I           2606    16813 (   calificacion FK_CALIFICACION_PROFESIONAL    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.calificacion
    ADD CONSTRAINT "FK_CALIFICACION_PROFESIONAL" FOREIGN KEY (calificacion_profesional) REFERENCES woaho.profesional(profesional_id);
 S   ALTER TABLE ONLY woaho.calificacion DROP CONSTRAINT "FK_CALIFICACION_PROFESIONAL";
       woaho          postgres    false    3099    250    252            J           2606    16818 %   calificacion FK_CALIFICACION_SERVICIO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.calificacion
    ADD CONSTRAINT "FK_CALIFICACION_SERVICIO" FOREIGN KEY (calificacion_servicio) REFERENCES woaho.servicio(servicio_id);
 P   ALTER TABLE ONLY woaho.calificacion DROP CONSTRAINT "FK_CALIFICACION_SERVICIO";
       woaho          postgres    false    246    3091    252            H           2606    16808 $   calificacion FK_CALIFICACION_USUARIO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.calificacion
    ADD CONSTRAINT "FK_CALIFICACION_USUARIO" FOREIGN KEY (calificacion_usuario) REFERENCES woaho.usuario(usuario_id);
 O   ALTER TABLE ONLY woaho.calificacion DROP CONSTRAINT "FK_CALIFICACION_USUARIO";
       woaho          postgres    false    237    252    3073            S           2606    16885 !   cancelacion FK_CANCELACION_PEDIDO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.cancelacion
    ADD CONSTRAINT "FK_CANCELACION_PEDIDO" FOREIGN KEY (cancelacion_pedido) REFERENCES woaho.pedido(pedido_id);
 L   ALTER TABLE ONLY woaho.cancelacion DROP CONSTRAINT "FK_CANCELACION_PEDIDO";
       woaho          postgres    false    255    3107    254            7           2606    16641    categoria FK_CATEGORIA_IMAGEN    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.categoria
    ADD CONSTRAINT "FK_CATEGORIA_IMAGEN" FOREIGN KEY (categoria_imagen) REFERENCES woaho.imagen(imagen_id);
 H   ALTER TABLE ONLY woaho.categoria DROP CONSTRAINT "FK_CATEGORIA_IMAGEN";
       woaho          postgres    false    242    243    3083            3           2606    16585    codigo FK_CODIGO_ESTADO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.codigo
    ADD CONSTRAINT "FK_CODIGO_ESTADO" FOREIGN KEY (codigo_estado) REFERENCES woaho.estado(estado_id);
 B   ALTER TABLE ONLY woaho.codigo DROP CONSTRAINT "FK_CODIGO_ESTADO";
       woaho          postgres    false    238    239    3075            C           2606    16755 %   codigo_promocional FK_COD_PROM_ESTADO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.codigo_promocional
    ADD CONSTRAINT "FK_COD_PROM_ESTADO" FOREIGN KEY (codigo_promocional_estado) REFERENCES woaho.estado(estado_id);
 P   ALTER TABLE ONLY woaho.codigo_promocional DROP CONSTRAINT "FK_COD_PROM_ESTADO";
       woaho          postgres    false    249    3075    238            D           2606    16760 (   codigo_promocional FK_COD_PROM_PROMOCION    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.codigo_promocional
    ADD CONSTRAINT "FK_COD_PROM_PROMOCION" FOREIGN KEY (codigo_promocional_promocion) REFERENCES woaho.promocion(promocion_id);
 S   ALTER TABLE ONLY woaho.codigo_promocional DROP CONSTRAINT "FK_COD_PROM_PROMOCION";
       woaho          postgres    false    248    3095    249            B           2606    16750 &   codigo_promocional FK_COD_PROM_USUARIO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.codigo_promocional
    ADD CONSTRAINT "FK_COD_PROM_USUARIO" FOREIGN KEY (codigo_promocional_usuario) REFERENCES woaho.usuario(usuario_id);
 Q   ALTER TABLE ONLY woaho.codigo_promocional DROP CONSTRAINT "FK_COD_PROM_USUARIO";
       woaho          postgres    false    249    237    3073            6           2606    16609    direccion FK_DIRECCION_ESTADO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.direccion
    ADD CONSTRAINT "FK_DIRECCION_ESTADO" FOREIGN KEY (direccion_estado) REFERENCES woaho.estado(estado_id);
 H   ALTER TABLE ONLY woaho.direccion DROP CONSTRAINT "FK_DIRECCION_ESTADO";
       woaho          postgres    false    240    3075    238            4           2606    16599 !   direccion FK_DIRECCION_TERRITORIO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.direccion
    ADD CONSTRAINT "FK_DIRECCION_TERRITORIO" FOREIGN KEY (direccion_territorio_id) REFERENCES woaho.territorio(territorio_id);
 L   ALTER TABLE ONLY woaho.direccion DROP CONSTRAINT "FK_DIRECCION_TERRITORIO";
       woaho          postgres    false    236    240    3069            5           2606    16604    direccion FK_DIRECCION_USUARIO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.direccion
    ADD CONSTRAINT "FK_DIRECCION_USUARIO" FOREIGN KEY (direccion_usuario) REFERENCES woaho.usuario(usuario_id);
 I   ALTER TABLE ONLY woaho.direccion DROP CONSTRAINT "FK_DIRECCION_USUARIO";
       woaho          postgres    false    3073    237    240            X           2606    16999    etiqueta FK_ETIQUETA_IDIOMA    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.etiqueta
    ADD CONSTRAINT "FK_ETIQUETA_IDIOMA" FOREIGN KEY (etiqueta_idioma) REFERENCES woaho.idioma(idioma_id);
 F   ALTER TABLE ONLY woaho.etiqueta DROP CONSTRAINT "FK_ETIQUETA_IDIOMA";
       woaho          postgres    false    3061    232    258            K           2606    16832 #   medio_pago FK_MEDIO_PAGO_TERRITORIO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.medio_pago
    ADD CONSTRAINT "FK_MEDIO_PAGO_TERRITORIO" FOREIGN KEY (medio_pago_territorio) REFERENCES woaho.territorio(territorio_id);
 N   ALTER TABLE ONLY woaho.medio_pago DROP CONSTRAINT "FK_MEDIO_PAGO_TERRITORIO";
       woaho          postgres    false    3069    253    236            V           2606    16915 ,   mensaje_pantalla FK_MENSAJE_PANTALLA_MENSAJE    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.mensaje_pantalla
    ADD CONSTRAINT "FK_MENSAJE_PANTALLA_MENSAJE" FOREIGN KEY (mensaje_pantalla_mensaje_id) REFERENCES woaho.mensaje(mensaje_id);
 W   ALTER TABLE ONLY woaho.mensaje_pantalla DROP CONSTRAINT "FK_MENSAJE_PANTALLA_MENSAJE";
       woaho          postgres    false    234    3065    257            W           2606    16920 -   mensaje_pantalla FK_MENSAJE_PANTALLA_PANTALLA    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.mensaje_pantalla
    ADD CONSTRAINT "FK_MENSAJE_PANTALLA_PANTALLA" FOREIGN KEY (mensaje_pantalla_pantalla_id) REFERENCES woaho.pantalla(pantalla_id);
 X   ALTER TABLE ONLY woaho.mensaje_pantalla DROP CONSTRAINT "FK_MENSAJE_PANTALLA_PANTALLA";
       woaho          postgres    false    257    256    3111            0           2606    16507    mensaje FK_MENSAJE_TIPO    FK CONSTRAINT        ALTER TABLE ONLY woaho.mensaje
    ADD CONSTRAINT "FK_MENSAJE_TIPO" FOREIGN KEY (mensaje_tipo) REFERENCES woaho.tipo(tipo_id);
 B   ALTER TABLE ONLY woaho.mensaje DROP CONSTRAINT "FK_MENSAJE_TIPO";
       woaho          postgres    false    3059    231    234            8           2606    16655    moneda FK_MONEDA_TERRITORIO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.moneda
    ADD CONSTRAINT "FK_MONEDA_TERRITORIO" FOREIGN KEY (moneda_territorio) REFERENCES woaho.territorio(territorio_id);
 F   ALTER TABLE ONLY woaho.moneda DROP CONSTRAINT "FK_MONEDA_TERRITORIO";
       woaho          postgres    false    244    236    3069            U           2606    16904    pantalla FK_PANTALLA_IMAGEN    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.pantalla
    ADD CONSTRAINT "FK_PANTALLA_IMAGEN" FOREIGN KEY (pantalla_imagen) REFERENCES woaho.imagen(imagen_id);
 F   ALTER TABLE ONLY woaho.pantalla DROP CONSTRAINT "FK_PANTALLA_IMAGEN";
       woaho          postgres    false    3083    256    242            T           2606    16899 !   pantalla FK_PANTALLA_TIPO_PANTLLA    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.pantalla
    ADD CONSTRAINT "FK_PANTALLA_TIPO_PANTLLA" FOREIGN KEY (pantalla_tipo_pantalla) REFERENCES woaho.tipo(tipo_id);
 L   ALTER TABLE ONLY woaho.pantalla DROP CONSTRAINT "FK_PANTALLA_TIPO_PANTLLA";
       woaho          postgres    false    256    3059    231            O           2606    16861    pedido FK_PEDIDO_DIRECCION    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.pedido
    ADD CONSTRAINT "FK_PEDIDO_DIRECCION" FOREIGN KEY (pedido_direccion) REFERENCES woaho.direccion(direccion_id);
 E   ALTER TABLE ONLY woaho.pedido DROP CONSTRAINT "FK_PEDIDO_DIRECCION";
       woaho          postgres    false    254    3079    240            N           2606    16856    pedido FK_PEDIDO_ESTADO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.pedido
    ADD CONSTRAINT "FK_PEDIDO_ESTADO" FOREIGN KEY (pedido_estado) REFERENCES woaho.estado(estado_id);
 B   ALTER TABLE ONLY woaho.pedido DROP CONSTRAINT "FK_PEDIDO_ESTADO";
       woaho          postgres    false    238    254    3075            Q           2606    16871    pedido FK_PEDIDO_MEDIO_PAGO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.pedido
    ADD CONSTRAINT "FK_PEDIDO_MEDIO_PAGO" FOREIGN KEY (pedido_medio_pago) REFERENCES woaho.medio_pago(medio_pago_id);
 F   ALTER TABLE ONLY woaho.pedido DROP CONSTRAINT "FK_PEDIDO_MEDIO_PAGO";
       woaho          postgres    false    3105    254    253            P           2606    16866    pedido FK_PEDIDO_PROFESIONAL    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.pedido
    ADD CONSTRAINT "FK_PEDIDO_PROFESIONAL" FOREIGN KEY (pedido_profesional) REFERENCES woaho.profesional(profesional_id);
 G   ALTER TABLE ONLY woaho.pedido DROP CONSTRAINT "FK_PEDIDO_PROFESIONAL";
       woaho          postgres    false    250    3099    254            L           2606    16846    pedido FK_PEDIDO_SERVICIO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.pedido
    ADD CONSTRAINT "FK_PEDIDO_SERVICIO" FOREIGN KEY (pedido_servicio) REFERENCES woaho.servicio(servicio_id);
 D   ALTER TABLE ONLY woaho.pedido DROP CONSTRAINT "FK_PEDIDO_SERVICIO";
       woaho          postgres    false    3091    246    254            M           2606    16851    pedido FK_PEDIDO_USUARIO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.pedido
    ADD CONSTRAINT "FK_PEDIDO_USUARIO" FOREIGN KEY (pedido_servicio) REFERENCES woaho.usuario(usuario_id);
 C   ALTER TABLE ONLY woaho.pedido DROP CONSTRAINT "FK_PEDIDO_USUARIO";
       woaho          postgres    false    237    254    3073            F           2606    16780     profesional FK_PROFESIONAL_ICONO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.profesional
    ADD CONSTRAINT "FK_PROFESIONAL_ICONO" FOREIGN KEY (profesional_imagen_icono) REFERENCES woaho.imagen(imagen_id);
 K   ALTER TABLE ONLY woaho.profesional DROP CONSTRAINT "FK_PROFESIONAL_ICONO";
       woaho          postgres    false    250    242    3083            E           2606    16775 %   profesional FK_PROFESIONAL_TERRITORIO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.profesional
    ADD CONSTRAINT "FK_PROFESIONAL_TERRITORIO" FOREIGN KEY (profesional_nacionalidad) REFERENCES woaho.territorio(territorio_id);
 P   ALTER TABLE ONLY woaho.profesional DROP CONSTRAINT "FK_PROFESIONAL_TERRITORIO";
       woaho          postgres    false    250    236    3069            A           2606    16736    promocion FK_PROM_ESTADO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.promocion
    ADD CONSTRAINT "FK_PROM_ESTADO" FOREIGN KEY (promocion_estado) REFERENCES woaho.estado(estado_id);
 C   ALTER TABLE ONLY woaho.promocion DROP CONSTRAINT "FK_PROM_ESTADO";
       woaho          postgres    false    248    238    3075            @           2606    16731    promocion FK_PROM_TARIFA    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.promocion
    ADD CONSTRAINT "FK_PROM_TARIFA" FOREIGN KEY (promocion_tarifa) REFERENCES woaho.tarifa(tarifa_id);
 C   ALTER TABLE ONLY woaho.promocion DROP CONSTRAINT "FK_PROM_TARIFA";
       woaho          postgres    false    247    3093    248            9           2606    16678    servicio FK_SERVICIO_CATEGORIA    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.servicio
    ADD CONSTRAINT "FK_SERVICIO_CATEGORIA" FOREIGN KEY (servicio_categoria) REFERENCES woaho.categoria(categoria_id);
 I   ALTER TABLE ONLY woaho.servicio DROP CONSTRAINT "FK_SERVICIO_CATEGORIA";
       woaho          postgres    false    243    246    3085            :           2606    16683    servicio FK_SERVICIO_IMAGEN    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.servicio
    ADD CONSTRAINT "FK_SERVICIO_IMAGEN" FOREIGN KEY (servicio_imagen) REFERENCES woaho.imagen(imagen_id);
 F   ALTER TABLE ONLY woaho.servicio DROP CONSTRAINT "FK_SERVICIO_IMAGEN";
       woaho          postgres    false    3083    246    242            ;           2606    16688    servicio FK_SERVICIO_TERRITORIO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.servicio
    ADD CONSTRAINT "FK_SERVICIO_TERRITORIO" FOREIGN KEY (servicio_territorio) REFERENCES woaho.territorio(territorio_id);
 J   ALTER TABLE ONLY woaho.servicio DROP CONSTRAINT "FK_SERVICIO_TERRITORIO";
       woaho          postgres    false    246    3069    236            <           2606    16702    tarifa FK_TARIFA_MONEDA    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.tarifa
    ADD CONSTRAINT "FK_TARIFA_MONEDA" FOREIGN KEY (tarifa_moneda) REFERENCES woaho.moneda(moneda_id);
 B   ALTER TABLE ONLY woaho.tarifa DROP CONSTRAINT "FK_TARIFA_MONEDA";
       woaho          postgres    false    3087    244    247            >           2606    16712    tarifa FK_TARIFA_SERVICIO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.tarifa
    ADD CONSTRAINT "FK_TARIFA_SERVICIO" FOREIGN KEY (tarifa_servicio) REFERENCES woaho.servicio(servicio_id);
 D   ALTER TABLE ONLY woaho.tarifa DROP CONSTRAINT "FK_TARIFA_SERVICIO";
       woaho          postgres    false    247    3091    246            =           2606    16707    tarifa FK_TARIFA_TERRITORIO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.tarifa
    ADD CONSTRAINT "FK_TARIFA_TERRITORIO" FOREIGN KEY (tarifa_territorio) REFERENCES woaho.territorio(territorio_id);
 F   ALTER TABLE ONLY woaho.tarifa DROP CONSTRAINT "FK_TARIFA_TERRITORIO";
       woaho          postgres    false    247    3069    236            ?           2606    16717    tarifa FK_TARIFA_UNIDAD    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.tarifa
    ADD CONSTRAINT "FK_TARIFA_UNIDAD" FOREIGN KEY (tarifa_unidad) REFERENCES woaho.unidad_tarifa(unidad_tarifa_id);
 B   ALTER TABLE ONLY woaho.tarifa DROP CONSTRAINT "FK_TARIFA_UNIDAD";
       woaho          postgres    false    245    3089    247            1           2606    16546 (   territorio FK_TERRITORIO_TIPO_TERRITORIO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.territorio
    ADD CONSTRAINT "FK_TERRITORIO_TIPO_TERRITORIO" FOREIGN KEY (territorio_tipo) REFERENCES woaho.tipo_territorio(tipo_territorio_id);
 S   ALTER TABLE ONLY woaho.territorio DROP CONSTRAINT "FK_TERRITORIO_TIPO_TERRITORIO";
       woaho          postgres    false    235    3067    236            Y           2606    17015    traduccion FK_TRADUCCION_IDIOMA    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.traduccion
    ADD CONSTRAINT "FK_TRADUCCION_IDIOMA" FOREIGN KEY (traduccion_idioma) REFERENCES woaho.idioma(idioma_id);
 J   ALTER TABLE ONLY woaho.traduccion DROP CONSTRAINT "FK_TRADUCCION_IDIOMA";
       woaho          woaho    false    3061    260    232            G           2606    16794 "   ubicacion FK_UBICACION_PROFESIONAL    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.ubicacion
    ADD CONSTRAINT "FK_UBICACION_PROFESIONAL" FOREIGN KEY (ubicacion_profesional) REFERENCES woaho.profesional(profesional_id);
 M   ALTER TABLE ONLY woaho.ubicacion DROP CONSTRAINT "FK_UBICACION_PROFESIONAL";
       woaho          postgres    false    251    3099    250            2           2606    16930 &   territorio fk1wg9n3xubkio4n3y16testobd    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.territorio
    ADD CONSTRAINT fk1wg9n3xubkio4n3y16testobd FOREIGN KEY (territorio_padre) REFERENCES woaho.territorio(territorio_id);
 O   ALTER TABLE ONLY woaho.territorio DROP CONSTRAINT fk1wg9n3xubkio4n3y16testobd;
       woaho          postgres    false    236    3069    236            R           2606    16925 "   pedido fkp80nbrpi74d5lutv2lo2ej3b3    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.pedido
    ADD CONSTRAINT fkp80nbrpi74d5lutv2lo2ej3b3 FOREIGN KEY (pedido_usuario) REFERENCES woaho.usuario(usuario_id);
 K   ALTER TABLE ONLY woaho.pedido DROP CONSTRAINT fkp80nbrpi74d5lutv2lo2ej3b3;
       woaho          postgres    false    237    3073    254            	   j   x���v
Q���W(�O���KN��L�LNL���Ss�	uV�0�Q0#u��J����<��Ԣ�L�"ucMk.O��2�Q0��3B�(5�4'�h�1�(.. �,�         
   x���              �   x����
�@�O�7$�oF'	FY�u��̡U����`�?��o����(��*f�7n$��E�A<��#�����[c���	}p
�	�\��r2�X�d!�����L0d�?��(b�x	9H�v��,!v���2a����v˖;&���5ΰl�L��zJ�/ؔ����� �,�%      �   @  x���Oo�E���)r[�����y9q�C%�H��}pꑯ�=oR�j]%��(�_l������O~�|y������O_�|�=���O�_���.��w��!���7F1� �������8��\�������S��: >�*�Rǆ���L&����C��0����h�h�M� �����t��Ltp����x�1f�UU�+��t���v s�I]@Ա�f��)�=_�мD���=V�"��|B�Ľ�� �LR�!�c�ȋ��"'A� �1,f��y�+�[eab�� ��*mBi٘�O���!ȁ�%��ۄ:&��_�D���8c���4�R�R3M���,)�n�_�V�Z��mB��)dY�5�|�	���GC�J�hW�Qf�vwI�R$HۄtO�[��2
���E�{�l�,��#+G�!�s�E�2�4mQ�^�t>vw)	�7�Ehe�J�.7= Q����*�Lۆvg�����!x��I�vn*MK��̗κ>�0��
�,�18ɐJ��$�JӘFfϊ�I�d�җx�F��EGF-C�N�ס4�s:�M����5�\p ����P�6xQ���kX5��qYj�W�4�V�����A����cX�V	�gªĖ�p��@
o�8�5���7��1�#��x۽�Դ��[�Ik=dO��1���Kӊ�`��@�1 @������p�ӕ#�]�j(��k�ijg�ԴM����HY�As0BN�mBi�嬃��#�' ���ͤ4��~�Fc�"�}��q���O8o�ˉ��\?;=�(C)�A��J���\;�:d�
�ћ�:7g?	�.+�n��{���+q{"X��s-$_%��V,}�	��Ȧ��Dv3�}������s�й��v��kL�>��'�A�C��-�;_�gIVu�g�����k2��٨�ޢ���_VrX�%����{����z��tZ�0tV��y.m=Ț�33�����~vQ~�'k��봽�\�!����4��7|e2ז~%d�L���ȁ�D����}�!��?�5��4�VF���ݻ �~@�         
   x���          �   �   x���M�@໿b����뮮t*�ڰ5�-�"��F%}ЯO����aޙ�G��8Z���URT(+/y���6�@�WХ�~rM���98l���3@� 0�jK�u	�,��7]Q�{�{!�|/���?-�����n'k��z���?4�K�������[�8��y�aȥ��W������,�!w?��WW�����<G�         >  x��XMo�6��W��\ۛu���N�"u������+�Jl%rCJ�l�����^������r��Lz�9�o�yy5�xs-.��7VVv��uz%k2���k�m#���~���o��k����������G�;I8�Ɵ��E�ho����	v���{בӒe� �S��צ�]�H8��VA�k��2��#_�V�����.�;�Ni�{�od��zʙr��:f�-��N�7*8_�&<W��!��gv�����ygI��w�b�m�2D0��c���:C czF�4AƯDf�����dM[e�`?��dY�o$sb� �4%�F.ȹ0%`H9��5��E��^2d1�/|�k���Y���Q��,ѫ`ՙ�9�f�9R��R�c2��i�Ą�)��s���x���9���ˆL���Q���j�u���(����WVx!��Z+��\�PWÏ��LM=٦��!w)���Σ�Ď��Եn��v�U�����ܪ=.���M8�jr+]h��~i���4D;��er%u-�'C��m��Ri��R�여��?	,�$y���lɊϴ��Z�ſ� ��}��y,re���	��Jr)���ȱh/�DF�
+qSᵭ(�j���*|YXאz,�5I��%K�M�{���= ��yfࡱ�^���x�q�&~惃r�2���c�2F����M����܊���n��(��]���c"N���6l����c�a�HXDΆ��[p����oT�Β��Bz�vT�r�О�w�֦-����*����YX,�ґ�
n+lLN�DoM�b��MsJ[<e�ӄy��E-W��D*F)T`�erP�)~��2$��q��n)���N	���,����0_��/���o��ۇ���В+	h}.an�d���z[3d���ۄ�'�����;�xG�Q�Y�k��Uys�d+���Ǖ�����J��}��Σ���LA5e�Mr���{�A�Kf����Z7��4IM�%���I��&D Pk��)R
]�`H��"�d{������tE��7TVנ��&'�;�l���a%����Jl\F���5D�I%��~����&��7xL�%
G����5��Bg����"A�a����a����"�-��9I؞=���6��W�*Q�&��g�u `P$�	9Q�lq��
Q]�>�v2�{5�ǆ[�lOǨE|�<�-��$L�8��w��p$j�#���r>������(��=�<Kۙ�&���-�1V�2x��.��� �P �]j�-�ֶ���o�K�F�����7[�94�`	U/"��Ϧ�\9ە�F��w$M��X>y7�#��p�4\s�J]���B�N�06	$�( ��|�������"���o=_���
��Ӄp됖39'����g]�2=nu}��c��~rd�������/���!k��B�z��ߔ��-�&��N.��̰g\��N����q���c��t-^m� Y<�%��tr\<[��E��[�?�=�e�\G�s�����V�n۹:���e�Q�(�"	Rf^�����i���ѿt��      �   w   x���v
Q���W(�O���K-.IL�Ws�	uV�0�QPwt��W״��$����ӏ�@�~.��~!�D�0�ru�	rt!�S�7��|<���b���������� ��Tz         
   x���          �   P   x���v
Q���W(�O����L���MTs�	uV�0�QP��s�qV�\��5��<	i2)p<<���-��� ��M      �   �  x���Ko�0�����J[^!	�z�J�����5��7�2L֏F��צu�V�1f��if�����{����B�!�aC�������>8K~��5�@%m�X/(��)~=��-�ä:r͕��0Y(��t$'B���x��:r;?����L�\GVPׄ�qrS*EY�Y�R�Z�F�ؾ	���,�--9��:V�������LFK�R�����(Jy8��$��I�^�q�F-%z�D%��P���D(YܚL�U��J�b ��2�T$'C��W�y'S(�|�*�\���Jw޾	{�1�4�S5̇U�#c�m�9�Q*��rS�Z�Wrl���ٯ�������*dv>A�-(�1�S\U&�ʖ0�WN�����VfkX�r �w����֩A(�(�MrU��J��K�+R�C������C���2e�\�̌�6���^{��c��Ȣ��3��Pc4�Υ��      
   _   x���v
Q���W(�O����MM�̏/HL�Ws�	uV�0�QPwMKM.�,�WGejZsyc�P�sP�l�I�	�@m.�IP�,�~.. J;i      �   �   x���1�@�὿"�)rIz�N
R�V��"h���tp3ٟ@�{ۮo���p��2���9���c���|kzء����E�W�h�i�:]�.u:zX\��$:�t%���Zt��<�����(���敃�+�h�%�z��զb(���`j����fS5\ڪ�h���o6E��c3�         �   x��ѽ
�@���b�Gp�RYX��h҆-��
������c����m �W�-�ZΟq��=>W��2MB���7=��ґ�b���)�0�(�0�Z
eC��C�"�1UX
w%*�1�ǖ�SP��TNk��t�Y         R   x���v
Q���W(�O������KMITs�	uV�0�QPw�P�Q0Դ��$���<4��܈��@�~@��@�\\ j�&�         j   x���v
Q���W(�O���+H�+I��ITs�	uV�0�QP��LIU(��W�Q04bMk.O�Z��ZS�AZM��j�ZR�
�kF�^����Ē"���!��� �.Ee      �   �   x���1�0����۪ �::�Z$���ѵDsփ6)I�ߋ���<��O�MQK���e��.G�Ԁ�Y�d�s��l��4�J��W�W��.���u��a#W&�V4��&X#��x�z�;�^9�nVSg�Î<���6��&Eq<U?"�;�������a<UO}�������+GT2p$3�t�H�7�[W�         �   x���v
Q���W(�O���+HM�L�Ws�	uV�0�Q0�Q ���:
�:
~�>>�����������9�ghde`�SUf�g``hbah	 ���fz&�������\���`�����Hn�8��&&��0�M�͈��x �6���\\ �n�      �   \   x���v
Q���W(�O���+(�OK-���Ss�	uV�0�QP��KO��L-�W״��$B�P�kNjrIQf2њ�A��K2s2�KA��� I1�         �  x���Mk�@໿bo�`$�1�Г�B-b����d�k��&X��;����B��̓yw��qo4a��䝭|��J��$$�3���C���kU/
�y�X�բ¡ڳڻ����z�d�Z�؆
�"��E0�$�۹A�����ֿ��шA&L>j�b-�٩ȷ�e�L�R0dZ���D-�NM��?�NT��^
��:�Eu�c�,]�F��8�fl���͝.�Ƽ��w��M�UͭgR��+7�0�]�N�6�h��߮J%;�+���EgeV1���ąʏS)���o�搷d�0O�ͮ��ݒ�/i�@���ѐI�iϥ���lr��V��9���F�%d�3���B�S�9M�����6��E�M���х�ݰ
ϭ܉���o�"�}��U{�����a�C����?+qc�         
   x���             b  x����J�@���sW�"���U�E*Z���Ow�6�ٍ�I�\>�/��n�(�$̙���:[�n`�޼�����x�c�r����2��a����� s��	��=���@�����C����zM����f�CE�:���B�m���]g��� �I�x K�F�$��b����P���&O�B��,y鐫Z�Z	�93�Ӝ���D.�(Fn��(#�Y8�=-pG�Ӏ�S&D*	^5�v���Z�:E�(�m�V�_�cU�|'�p�+W%c��"x�d�Ϛ8Ǵ��� �
�U�-ySq~Dֹn�(	&��AT-����GljI��O:N%��%6�p��qp{��t~ ��)         z   x���v
Q���W(�O���+I,�LKTs�	uV�0�Q02 C0�5��<	�3�3B�gH�>s�:d�L��g��ϔH}�@/�"�3#R�!P�	�Fsb5�i� V�0*�5A4rq B.q�      �   �   x����
�PF�>��,�@M]�
q!���I/t���ըWj�S�b�]�J[�;|3i^&Ei^��Ix�� ��iI��:)a�:`Ǥ�v�h;��Y�Yn��^�t��c>�쨇�.yLU�l���q|�dK?m�َ�*%�
b|��2�x�
�oP����_�/d�U�g�����Bj#',��)��      �   a   x���v
Q���W(�O���+�,�Ws�	uV�0�QP��LIU״��į��6 1�$1''���@�!�%�9�D(6��4��X�� �d�]�� D�B�      �   �   x���v
Q���W(�O���+�,ȏ/I-*�,�/��Ws�	uV�0�QPH�,V״��$Z�P�KjAbQIbnj^I>i����}K�2�32I�j�ꜟ[��H�>S�>�D�0�����+*JM���$ݧ�@�a�E�)`�rq ���         �  x�͕�n�@��y�������M�C�lZ����*�-��Mw"?D�ϒ�1ؖz��PU��"���o���4L2��l	�J���jQ4y^)	w��*L���νs�|
�h�JB:{�ߞE/1��������l��d��i�\ԧ�������*L�~.�!�K��e�E�h_����,��do�j¦�]��g�� ���*�E�S�8�q�-[Η@�J���L]B�<rp�^R|ZX��j4��Z[��[x��J�"l�F������cx�j�ƐGQvo���r�p��O��OK;�(-l�~(�X�SF����u+u���)�U+�'�������3�1��ANz�s
�T#A6�*@c�Z� WҊ�2|��q��x�ް�=�)��ܠ~��
6��4ɋFiGT_<��W(��K��t��,�J�"�DpX�C+���,�5+�%�ш�ZW�zڪ��E��+�¸�]�B��j��ڗy'����p[nfʿm�>!�(иa�"������φ���Y�}E�̼Ʈ�hn�p��g������Nhy�j��nv�Wo��g��7�%�Mp�u�)��5��-�� q��[p�ߩ⦓"�A;N6�B������[�;[Vr3��5�-Uc�F��h(�֑h:H��*xk�(�a���<�i�47������7��         �   x��ұn1����[����vl110�TQ����R�ބx}Lwt��K����a�]�`����2���|8����_����9�'�p;z�Rj��_���Q	�˲,����=��ē�n�V�D�b�;V��<��1��	���>a�5�bA�c�j���L�b�����4��ܶV�Fp0g�'��x�ԼO��b0���w�RR�=����uW��         d   x���v
Q���W(�O���+��LIL�/I,�LKTs�	uV�0�QP��/JT״��$R�P�K&IZ��Z�Ss�H�e��ZL�S����Az�� �mP      �   �  x����n7��~
�����W/����I���ò����A�V}��Xg����%S1(V���+�f�3}vqy�������_&�����w��~����������;�a2��wS�.�����a��Y�1N�<�~��6�b{k�.�=����	H�P]��. �^/ޝ�����xr�U�P�>w��q�����_X��OO����*y�(vWBL�[%���w6��j���~�=�R�~���7�0j���-�	��P�;zf�4-|^	gC��/`>K�̈�īDyu��av�1O�7w���,�@Xb�O/����"�Pp)�g��aǯ�S�T|,>�����/���$���P<8�b:7М�d�"���}d�/ћ�@DLm�\\�dC�m-�j�)[�x��N6�ؚhLH�bcAt�S'��Y�4U8Р�K/��A� I�l��	�N�4�.vT�b~CrL��Z~��V��t�z�l�~��P�l�+i6�g�!���ˣTbվ�:/�^RXӂ� �������T-�#E�����~�.��QO[�f[γ�^:�Rɧ�E4�뾇b�C�dC7����}l�s!p��^UJ�)����b�.R?��� B��m��U�LL���XZ�S��u��:z���\����]�Xp�s7�*�h�V�mI���o�|U�m�EW*]�B��zAm�n�� ����Z��k��t�
����SU�jS�.���ȓi���f}}c��u�#D�#�آ���e����iChagiE��.���N�uW����_��[^��<X dg��6����ؽ�RE�t��X��hZ�M�t�kz�X�-�	��}���q��������.���k�p+�ѕ&�g^Eﴹ��46��V#S��SU�Ɖ�V�I�u.c7��z���6*.ٿT��{�pu\�ޭ�w*�!��<u'�ܭ]�}��uz�抵���=���?O�*\      �               0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false                       0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false                       0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false                       1262    16385    woaho    DATABASE     w   CREATE DATABASE woaho WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'en_US.UTF-8' LC_CTYPE = 'en_US.UTF-8';
    DROP DATABASE woaho;
                postgres    false                        2615    16386    woaho    SCHEMA        CREATE SCHEMA woaho;
    DROP SCHEMA woaho;
                postgres    false                       1255    17020 -   consultar_mensajes_pantalla(integer, integer)    FUNCTION     a  CREATE FUNCTION woaho.consultar_mensajes_pantalla(p_pantalla integer, p_idioma integer) RETURNS character varying
    LANGUAGE plpgsql COST 10
    AS $$
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
			SELECT tp.tipo_nombre,tr.traduccion_traduccion 
			FROM mensaje me,mensaje_pantalla mp,tipo tp,traduccion tr 
			WHERE mp.mensaje_pantalla_pantalla_id = p_pantalla
			AND mp.mensaje_pantalla_mensaje_id = me.mensaje_id
			AND tr.traduccion_idioma = p_idioma
			AND tr.traduccion_codigo_mensaje = me.mensaje_codigo 
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
	
	RETURN cadena_resultado;
	
END;
$$;
 W   DROP FUNCTION woaho.consultar_mensajes_pantalla(p_pantalla integer, p_idioma integer);
       woaho          postgres    false    7                       1255    17035 A   fndb_consultar_equivalencia(character varying, character varying)    FUNCTION     �  CREATE FUNCTION woaho.fndb_consultar_equivalencia(p_cadena character varying, p_idioma character varying) RETURNS character varying
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
$$;
 i   DROP FUNCTION woaho.fndb_consultar_equivalencia(p_cadena character varying, p_idioma character varying);
       woaho          postgres    false    7                       1255    17021 4   fndb_consultar_pantallas(integer, character varying)    FUNCTION     e  CREATE FUNCTION woaho.fndb_consultar_pantallas(p_tipo_pantalla integer, p_idioma character varying) RETURNS character varying
    LANGUAGE plpgsql COST 10
    AS $$
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
$$;
 c   DROP FUNCTION woaho.fndb_consultar_pantallas(p_tipo_pantalla integer, p_idioma character varying);
       woaho          postgres    false    7                       1255    17036 /   fndb_generar_codigo_registro(character varying)    FUNCTION     �  CREATE FUNCTION woaho.fndb_generar_codigo_registro(p_celular character varying) RETURNS character varying
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
 O   DROP FUNCTION woaho.fndb_generar_codigo_registro(p_celular character varying);
       woaho          postgres    false    7                       1255    17038 B   fndb_generar_codigo_registro(character varying, character varying)    FUNCTION     �  CREATE FUNCTION woaho.fndb_generar_codigo_registro(p_celular character varying, p_idioma character varying) RETURNS character varying
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
 k   DROP FUNCTION woaho.fndb_generar_codigo_registro(p_celular character varying, p_idioma character varying);
       woaho          woaho    false    7                       1255    17037 U   fndb_validar_codigo_registro(character varying, character varying, character varying)    FUNCTION     W	  CREATE FUNCTION woaho.fndb_validar_codigo_registro(p_celular character varying, p_codigo character varying, p_idioma character varying) RETURNS character varying
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

	SELECT cod.codigo_numero,cod.codigo_intentos,EXTRACT(minute FROM (now() - cod.codigo_fecha_hora_registro))::numeric
	INTO codigo_generado,cant_intentos,cant_minutos_codigo
	FROM codigo cod
	WHERE cod.codigo_celular = p_celular
	AND cod.codigo_estado = 1;
	
	IF (codigo_generado IS NULL) THEN
		RETURN '1,'|| woaho.fndb_consultar_equivalencia('El codigo ingresado no se encuentra registrado.Intente nuevamente o solicite un nuevo codigo.',p_idioma);
	ELSE
		SELECT par.parametro_valor::numeric
		INTO parametro_tiempo
		FROM woaho.parametro par
		WHERE par.parametro_nombre = 'TIEMPO_COD_REGISTRO';
		
		IF (codigo_generado = p_codigo) THEN
			UPDATE codigo
			SET codigo_estado = 2
			WHERE codigo.codigo_celular = p_celular
			AND codigo.codigo_estado = 1;
			
			IF (cant_minutos_codigo > parametro_tiempo) THEN
				RETURN '1,'|| woaho.fndb_consultar_equivalencia('El codigo ingresado ha caducado.Solicite un nuevo codigo.',p_idioma);
			ELSE
				RETURN '0,OK';
			END IF;					
		ELSE
			IF (cant_intentos > 0) THEN
				UPDATE codigo
				SET codigo_intentos = cant_intentos - 1
				WHERE codigo.codigo_celular = p_celular
				AND codigo.codigo_estado = 1;
				RETURN '1,'|| woaho.fndb_consultar_equivalencia('El codigo ingresado no corresponde.Intente nuevamente o solicite un nuevo codigo.',p_idioma);
			ELSE
				RETURN '1,'|| woaho.fndb_consultar_equivalencia('El codigo ingresado ya no es valido.Solicite un nuevo codigo.',p_idioma);
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
        
	RETURN '1,'|| woaho.fndb_consultar_equivalencia('Se ha presentado un error inesperado:',p_idioma) ||' '||v_state||' '||v_msg;
	
END;
$$;
 �   DROP FUNCTION woaho.fndb_validar_codigo_registro(p_celular character varying, p_codigo character varying, p_idioma character varying);
       woaho          postgres    false    7                       1255    16937 $   generar_aleatorios(integer, integer)    FUNCTION     �   CREATE FUNCTION woaho.generar_aleatorios(pinicial integer, pfinal integer) RETURNS character varying
    LANGUAGE plpgsql
    AS $$
BEGIN
   RETURN floor(random()* (pFinal-pInicial + 1) + pInicial);
END;
$$;
 J   DROP FUNCTION woaho.generar_aleatorios(pinicial integer, pfinal integer);
       woaho          woaho    false    7            �            1259    16429    sec_calificacion    SEQUENCE     �   CREATE SEQUENCE woaho.sec_calificacion
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999
    CACHE 1
    CYCLE;
 &   DROP SEQUENCE woaho.sec_calificacion;
       woaho          postgres    false    7            �            1259    16799    calificacion    TABLE     F  CREATE TABLE woaho.calificacion (
    calificacion_id integer DEFAULT nextval('woaho.sec_calificacion'::regclass) NOT NULL,
    calificacion_usuario integer,
    calificacion_profesional integer,
    calificacion_descripcion character varying(4000),
    calificacion_calificacion integer,
    calificacion_servicio integer
);
    DROP TABLE woaho.calificacion;
       woaho         heap    postgres    false    224    7                       0    0    TABLE calificacion    COMMENT     e   COMMENT ON TABLE woaho.calificacion IS 'Tabla que contiene las calificaciones de los profesionales';
          woaho          postgres    false    252            �            1259    16441    sec_cancelacion    SEQUENCE     �   CREATE SEQUENCE woaho.sec_cancelacion
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999
    CACHE 1
    CYCLE;
 %   DROP SEQUENCE woaho.sec_cancelacion;
       woaho          postgres    false    7            �            1259    16876    cancelacion    TABLE     �   CREATE TABLE woaho.cancelacion (
    cancelacion_id integer DEFAULT nextval('woaho.sec_cancelacion'::regclass) NOT NULL,
    cancelacion_pedido integer,
    cancelacion_motivo character varying(4000),
    cancelacion_fecha timestamp without time zone
);
    DROP TABLE woaho.cancelacion;
       woaho         heap    postgres    false    230    7                       0    0    TABLE cancelacion    COMMENT     Y   COMMENT ON TABLE woaho.cancelacion IS 'Tabla que contiene las cancelaciones realizadas';
          woaho          postgres    false    255            �            1259    16409    sec_categoria    SEQUENCE     �   CREATE SEQUENCE woaho.sec_categoria
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999
    CACHE 1
    CYCLE;
 #   DROP SEQUENCE woaho.sec_categoria;
       woaho          postgres    false    7            �            1259    16632 	   categoria    TABLE     �   CREATE TABLE woaho.categoria (
    categoria_id integer DEFAULT nextval('woaho.sec_categoria'::regclass) NOT NULL,
    categoria_descripcion character varying(4000),
    categoria_imagen integer
);
    DROP TABLE woaho.categoria;
       woaho         heap    postgres    false    214    7                       0    0    TABLE categoria    COMMENT     \   COMMENT ON TABLE woaho.categoria IS 'Tabla que contiene las categorias para el aplicativo';
          woaho          postgres    false    243            �            1259    16403 
   sec_codigo    SEQUENCE     �   CREATE SEQUENCE woaho.sec_codigo
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999
    CACHE 1
    CYCLE;
     DROP SEQUENCE woaho.sec_codigo;
       woaho          postgres    false    7            �            1259    16571    codigo    TABLE     7  CREATE TABLE woaho.codigo (
    codigo_id integer DEFAULT nextval('woaho.sec_codigo'::regclass) NOT NULL,
    codigo_numero character varying(4000),
    codigo_celular character varying(4000),
    codigo_intentos integer,
    codigo_fecha_hora_registro timestamp without time zone,
    codigo_estado integer
);
    DROP TABLE woaho.codigo;
       woaho         heap    postgres    false    211    7                       0    0    TABLE codigo    COMMENT     h   COMMENT ON TABLE woaho.codigo IS 'Tabla que contiene los codigos generados para completar el registro';
          woaho          postgres    false    239            �            1259    16417    sec_codigo_promocional    SEQUENCE     �   CREATE SEQUENCE woaho.sec_codigo_promocional
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999
    CACHE 1
    CYCLE;
 ,   DROP SEQUENCE woaho.sec_codigo_promocional;
       woaho          postgres    false    7            �            1259    16741    codigo_promocional    TABLE     @  CREATE TABLE woaho.codigo_promocional (
    codigo_promocional_id integer DEFAULT nextval('woaho.sec_codigo_promocional'::regclass) NOT NULL,
    codigo_promocional_codigo character varying(4000),
    codigo_promocional_usuario integer,
    codigo_promocional_estado integer,
    codigo_promocional_promocion integer
);
 %   DROP TABLE woaho.codigo_promocional;
       woaho         heap    postgres    false    218    7                       0    0    TABLE codigo_promocional    COMMENT     l   COMMENT ON TABLE woaho.codigo_promocional IS 'Tabla que contiene los codigos promocionales del aplicativo';
          woaho          postgres    false    249            �            1259    16407    sec_direccion    SEQUENCE     �   CREATE SEQUENCE woaho.sec_direccion
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999
    CACHE 1
    CYCLE;
 #   DROP SEQUENCE woaho.sec_direccion;
       woaho          postgres    false    7            �            1259    16590 	   direccion    TABLE     �  CREATE TABLE woaho.direccion (
    direccion_id integer DEFAULT nextval('woaho.sec_direccion'::regclass) NOT NULL,
    direccion_nombre character varying(4000),
    direccion_descripcion character varying(4000),
    direccion_territorio_id integer,
    direccion_edificacion character varying(4000),
    direccion_estado integer,
    direccion_usuario integer,
    direccion_latitud character varying(4000),
    direccion_longitud character varying(4000),
    direccion_lugar_id character varying(4000)
);
    DROP TABLE woaho.direccion;
       woaho         heap    postgres    false    213    7                       0    0    TABLE direccion    COMMENT     g   COMMENT ON TABLE woaho.direccion IS 'Tabla que contiene las direcciones registradas por los usuarios';
          woaho          postgres    false    240                       1259    17022    sec_equivalencia_idioma    SEQUENCE     �   CREATE SEQUENCE woaho.sec_equivalencia_idioma
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999
    CACHE 1
    CYCLE;
 -   DROP SEQUENCE woaho.sec_equivalencia_idioma;
       woaho          postgres    false    7                       1259    17024    equivalencia_idioma    TABLE       CREATE TABLE woaho.equivalencia_idioma (
    equivalencia_idioma_id integer DEFAULT nextval('woaho.sec_equivalencia_idioma'::regclass) NOT NULL,
    equivalencia_idioma_original character varying(4000),
    equivalencia_idioma_ingles character varying(4000)
);
 &   DROP TABLE woaho.equivalencia_idioma;
       woaho         heap    postgres    false    261    7                        0    0    TABLE equivalencia_idioma    COMMENT     f   COMMENT ON TABLE woaho.equivalencia_idioma IS 'Tabla que contiene las traducciones de las etiquetas';
          woaho          postgres    false    262            �            1259    16401 
   sec_estado    SEQUENCE     �   CREATE SEQUENCE woaho.sec_estado
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999
    CACHE 1
    CYCLE;
     DROP SEQUENCE woaho.sec_estado;
       woaho          postgres    false    7            �            1259    16562    estado    TABLE     �   CREATE TABLE woaho.estado (
    estado_id integer DEFAULT nextval('woaho.sec_estado'::regclass) NOT NULL,
    estado_codigo character varying(4000)
);
    DROP TABLE woaho.estado;
       woaho         heap    postgres    false    210    7            !           0    0    TABLE estado    COMMENT     V   COMMENT ON TABLE woaho.estado IS 'Tabla que contiene los estados para el aplicativo';
          woaho          postgres    false    238            "           0    0    COLUMN estado.estado_codigo    COMMENT     b   COMMENT ON COLUMN woaho.estado.estado_codigo IS 'A activo, I inactivo, P pendiente, R rechazado';
          woaho          postgres    false    238            �            1259    16437    sec_etiqueta    SEQUENCE     �   CREATE SEQUENCE woaho.sec_etiqueta
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999
    CACHE 1
    CYCLE;
 "   DROP SEQUENCE woaho.sec_etiqueta;
       woaho          postgres    false    7                       1259    16990    etiqueta    TABLE     �   CREATE TABLE woaho.etiqueta (
    etiqueta_id integer DEFAULT nextval('woaho.sec_etiqueta'::regclass) NOT NULL,
    etiqueta_codigo character varying(4000),
    etiqueta_idioma integer,
    etiqueta_valor character varying(100)
);
    DROP TABLE woaho.etiqueta;
       woaho         heap    postgres    false    228    7            #           0    0    TABLE etiqueta    COMMENT     V   COMMENT ON TABLE woaho.etiqueta IS 'Tabla que contiene las etiquetas del aplicativo';
          woaho          postgres    false    258            �            1259    16431 
   sec_idioma    SEQUENCE     �   CREATE SEQUENCE woaho.sec_idioma
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999
    CACHE 1
    CYCLE;
     DROP SEQUENCE woaho.sec_idioma;
       woaho          postgres    false    7            �            1259    16452    idioma    TABLE     �   CREATE TABLE woaho.idioma (
    idioma_id integer DEFAULT nextval('woaho.sec_idioma'::regclass) NOT NULL,
    idioma_nombre character varying(4000),
    idioma_codigo character varying(4000)
);
    DROP TABLE woaho.idioma;
       woaho         heap    postgres    false    225    7            $           0    0    TABLE idioma    COMMENT     e   COMMENT ON TABLE woaho.idioma IS 'Tabla que contiene la información de los idiomas del aplicativo';
          woaho          postgres    false    232            �            1259    16427 
   sec_imagen    SEQUENCE     �   CREATE SEQUENCE woaho.sec_imagen
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999
    CACHE 1
    CYCLE;
     DROP SEQUENCE woaho.sec_imagen;
       woaho          postgres    false    7            �            1259    16623    imagen    TABLE       CREATE TABLE woaho.imagen (
    imagen_id integer DEFAULT nextval('woaho.sec_imagen'::regclass) NOT NULL,
    imagen_nombre character varying(4000),
    imagen_ruta character varying(4000),
    imagen_alto character varying(4000),
    imagen_ancho character varying(4000)
);
    DROP TABLE woaho.imagen;
       woaho         heap    postgres    false    223    7            %           0    0    TABLE imagen    COMMENT     W   COMMENT ON TABLE woaho.imagen IS 'Tabla que contiene las imagenes para el aplicativo';
          woaho          postgres    false    242            �            1259    16439    sec_medio_pago    SEQUENCE     �   CREATE SEQUENCE woaho.sec_medio_pago
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999
    CACHE 1
    CYCLE;
 $   DROP SEQUENCE woaho.sec_medio_pago;
       woaho          postgres    false    7            �            1259    16823 
   medio_pago    TABLE     �   CREATE TABLE woaho.medio_pago (
    medio_pago_id integer DEFAULT nextval('woaho.sec_medio_pago'::regclass) NOT NULL,
    medio_pago_nombre character varying(4000),
    medio_pago_etiqueta character varying(4000),
    medio_pago_territorio integer
);
    DROP TABLE woaho.medio_pago;
       woaho         heap    postgres    false    229    7            &           0    0    TABLE medio_pago    COMMENT     b   COMMENT ON TABLE woaho.medio_pago IS 'Tabla que contiene los medios de pagos para el aplicativo';
          woaho          postgres    false    253            �            1259    16387    sec_mensaje    SEQUENCE     �   CREATE SEQUENCE woaho.sec_mensaje
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999
    CACHE 1
    CYCLE;
 !   DROP SEQUENCE woaho.sec_mensaje;
       woaho          postgres    false    7            �            1259    16498    mensaje    TABLE     �   CREATE TABLE woaho.mensaje (
    mensaje_id integer DEFAULT nextval('woaho.sec_mensaje'::regclass) NOT NULL,
    mensaje_tipo integer,
    mensaje_codigo character varying(4000)
);
    DROP TABLE woaho.mensaje;
       woaho         heap    postgres    false    203    7            '           0    0    TABLE mensaje    COMMENT     e   COMMENT ON TABLE woaho.mensaje IS 'Tabla que contiene la información de los mensajes por pantalla';
          woaho          postgres    false    234            �            1259    16389    sec_mensaje_pantalla    SEQUENCE     �   CREATE SEQUENCE woaho.sec_mensaje_pantalla
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999
    CACHE 1
    CYCLE;
 *   DROP SEQUENCE woaho.sec_mensaje_pantalla;
       woaho          postgres    false    7                       1259    16909    mensaje_pantalla    TABLE     �   CREATE TABLE woaho.mensaje_pantalla (
    mensaje_pantalla_id integer DEFAULT nextval('woaho.sec_mensaje_pantalla'::regclass) NOT NULL,
    mensaje_pantalla_pantalla_id integer,
    mensaje_pantalla_mensaje_id integer
);
 #   DROP TABLE woaho.mensaje_pantalla;
       woaho         heap    postgres    false    204    7            (           0    0    TABLE mensaje_pantalla    COMMENT     h   COMMENT ON TABLE woaho.mensaje_pantalla IS 'Tabla que contiene la relacion entre mensajes y pantallas';
          woaho          postgres    false    257            �            1259    16411 
   sec_moneda    SEQUENCE     �   CREATE SEQUENCE woaho.sec_moneda
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999
    CACHE 1
    CYCLE;
     DROP SEQUENCE woaho.sec_moneda;
       woaho          postgres    false    7            �            1259    16646    moneda    TABLE     �   CREATE TABLE woaho.moneda (
    moneda_id integer DEFAULT nextval('woaho.sec_moneda'::regclass) NOT NULL,
    moneda_nombre character varying(4000),
    moneda_territorio integer
);
    DROP TABLE woaho.moneda;
       woaho         heap    postgres    false    215    7            )           0    0    TABLE moneda    COMMENT     V   COMMENT ON TABLE woaho.moneda IS 'Tabla que contiene las monedas para el aplicativo';
          woaho          postgres    false    244            �            1259    16391    sec_pantalla    SEQUENCE     �   CREATE SEQUENCE woaho.sec_pantalla
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999
    CACHE 1
    CYCLE;
 "   DROP SEQUENCE woaho.sec_pantalla;
       woaho          postgres    false    7                        1259    16890    pantalla    TABLE     �   CREATE TABLE woaho.pantalla (
    pantalla_id integer DEFAULT nextval('woaho.sec_pantalla'::regclass) NOT NULL,
    pantalla_nombre character varying(4000),
    pantalla_imagen integer,
    pantalla_tipo_pantalla integer
);
    DROP TABLE woaho.pantalla;
       woaho         heap    postgres    false    205    7            *           0    0    TABLE pantalla    COMMENT     Z   COMMENT ON TABLE woaho.pantalla IS 'Tabla que contiene la información de las pantallas';
          woaho          postgres    false    256            �            1259    16405    sec_parametro    SEQUENCE     �   CREATE SEQUENCE woaho.sec_parametro
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999
    CACHE 1
    CYCLE;
 #   DROP SEQUENCE woaho.sec_parametro;
       woaho          postgres    false    7            �            1259    16614 	   parametro    TABLE       CREATE TABLE woaho.parametro (
    parametro_id integer DEFAULT nextval('woaho.sec_parametro'::regclass) NOT NULL,
    parametro_nombre character varying(4000),
    parametro_valor character varying(4000),
    parametro_descripcion character varying(4000)
);
    DROP TABLE woaho.parametro;
       woaho         heap    postgres    false    212    7            +           0    0    TABLE parametro    COMMENT     X   COMMENT ON TABLE woaho.parametro IS 'Tabla que contiene los parametros del aplicativo';
          woaho          postgres    false    241            �            1259    16423 
   sec_pedido    SEQUENCE     �   CREATE SEQUENCE woaho.sec_pedido
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999
    CACHE 1
    CYCLE;
     DROP SEQUENCE woaho.sec_pedido;
       woaho          postgres    false    7            �            1259    16837    pedido    TABLE     `  CREATE TABLE woaho.pedido (
    pedido_id integer DEFAULT nextval('woaho.sec_pedido'::regclass) NOT NULL,
    pedido_servicio integer,
    pedido_usuario integer,
    pedido_descripcion character varying(4000),
    pedido_estado integer,
    pedido_direccion integer,
    pedido_cod_promocional character varying(4000),
    pedido_fecha character varying(4000),
    pedido_hora character varying(4000),
    pedido_profesional integer,
    pedido_medio_pago integer,
    pedido_fecha_final timestamp without time zone,
    pedido_latitud character varying(4000),
    pedido_longitu character varying(4000)
);
    DROP TABLE woaho.pedido;
       woaho         heap    postgres    false    221    7            ,           0    0    TABLE pedido    COMMENT     R   COMMENT ON TABLE woaho.pedido IS 'Tabla que contiene los pedidos del aplicativo';
          woaho          postgres    false    254            �            1259    16435    sec_profesion    SEQUENCE     �   CREATE SEQUENCE woaho.sec_profesion
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999
    CACHE 1
    CYCLE;
 #   DROP SEQUENCE woaho.sec_profesion;
       woaho          postgres    false    7            �            1259    16475 	   profesion    TABLE     �   CREATE TABLE woaho.profesion (
    profesion_id integer DEFAULT nextval('woaho.sec_profesion'::regclass) NOT NULL,
    profesion_nombre character varying(4000)
);
    DROP TABLE woaho.profesion;
       woaho         heap    postgres    false    227    7            -           0    0    TABLE profesion    COMMENT     l   COMMENT ON TABLE woaho.profesion IS 'Tabla que contiene la información de las profesiones del aplicativo';
          woaho          postgres    false    233            �            1259    16425    sec_profesional    SEQUENCE     �   CREATE SEQUENCE woaho.sec_profesional
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999
    CACHE 1
    CYCLE;
 %   DROP SEQUENCE woaho.sec_profesional;
       woaho          postgres    false    7            �            1259    16766    profesional    TABLE       CREATE TABLE woaho.profesional (
    profesional_id integer DEFAULT nextval('woaho.sec_profesional'::regclass) NOT NULL,
    profesional_nombre character varying(4000),
    profesional_apellido character varying(4000),
    profesional_profesiones character varying(4000),
    profesional_nacionalidad integer,
    profesional_servicios character varying(4000),
    profesional_lenguajes character varying(4000),
    profesional_descripcion character varying(4000),
    profesional_imagen_icono integer,
    profesional_cant_estrellas numeric,
    profesional_cant_servicios integer,
    profesional_distancia numeric DEFAULT 0 NOT NULL
);
    DROP TABLE woaho.profesional;
       woaho         heap    postgres    false    222    7            .           0    0    TABLE profesional    COMMENT     ]   COMMENT ON TABLE woaho.profesional IS 'Tabla que contiene los profesionales del aplicativo';
          woaho          postgres    false    250            �            1259    16419    sec_promocion    SEQUENCE     �   CREATE SEQUENCE woaho.sec_promocion
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999
    CACHE 1
    CYCLE;
 #   DROP SEQUENCE woaho.sec_promocion;
       woaho          postgres    false    7            �            1259    16722 	   promocion    TABLE       CREATE TABLE woaho.promocion (
    promocion_id integer DEFAULT nextval('woaho.sec_promocion'::regclass) NOT NULL,
    promocion_descuento integer,
    promocion_tarifa integer,
    promocion_estado integer,
    promocion_descripcion character varying(4000)
);
    DROP TABLE woaho.promocion;
       woaho         heap    postgres    false    219    7            /           0    0    TABLE promocion    COMMENT     Y   COMMENT ON TABLE woaho.promocion IS 'Tabla que contiene las promociones del aplicativo';
          woaho          postgres    false    248            �            1259    16413    sec_servicio    SEQUENCE     �   CREATE SEQUENCE woaho.sec_servicio
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999
    CACHE 1
    CYCLE;
 "   DROP SEQUENCE woaho.sec_servicio;
       woaho          postgres    false    7            �            1259    16415 
   sec_tarifa    SEQUENCE     �   CREATE SEQUENCE woaho.sec_tarifa
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999
    CACHE 1
    CYCLE;
     DROP SEQUENCE woaho.sec_tarifa;
       woaho          postgres    false    7            �            1259    16395    sec_territorio    SEQUENCE     �   CREATE SEQUENCE woaho.sec_territorio
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999
    CACHE 1
    CYCLE;
 $   DROP SEQUENCE woaho.sec_territorio;
       woaho          woaho    false    7            �            1259    16393    sec_tipo    SEQUENCE     ~   CREATE SEQUENCE woaho.sec_tipo
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999
    CACHE 1
    CYCLE;
    DROP SEQUENCE woaho.sec_tipo;
       woaho          postgres    false    7            �            1259    16397    sec_tipo_territorio    SEQUENCE     �   CREATE SEQUENCE woaho.sec_tipo_territorio
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999
    CACHE 1
    CYCLE;
 )   DROP SEQUENCE woaho.sec_tipo_territorio;
       woaho          postgres    false    7                       1259    17004    sec_traduccion    SEQUENCE     �   CREATE SEQUENCE woaho.sec_traduccion
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999
    CACHE 1
    CYCLE;
 $   DROP SEQUENCE woaho.sec_traduccion;
       woaho          postgres    false    7            �            1259    16433    sec_ubicacion    SEQUENCE     �   CREATE SEQUENCE woaho.sec_ubicacion
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999
    CACHE 1
    CYCLE;
 #   DROP SEQUENCE woaho.sec_ubicacion;
       woaho          postgres    false    7            �            1259    16421    sec_unidad_tarifa    SEQUENCE     �   CREATE SEQUENCE woaho.sec_unidad_tarifa
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999
    CACHE 1
    CYCLE;
 '   DROP SEQUENCE woaho.sec_unidad_tarifa;
       woaho          postgres    false    7            �            1259    16399    sec_usuario    SEQUENCE     �   CREATE SEQUENCE woaho.sec_usuario
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999
    CACHE 1
    CYCLE;
 !   DROP SEQUENCE woaho.sec_usuario;
       woaho          postgres    false    7            �            1259    16669    servicio    TABLE     /  CREATE TABLE woaho.servicio (
    servicio_id integer DEFAULT nextval('woaho.sec_servicio'::regclass) NOT NULL,
    servicio_nombre character varying(4000),
    servicio_imagen integer,
    servicio_categoria integer,
    servicio_territorio integer,
    servicio_descripcion character varying(4000)
);
    DROP TABLE woaho.servicio;
       woaho         heap    postgres    false    216    7            0           0    0    TABLE servicio    COMMENT     Z   COMMENT ON TABLE woaho.servicio IS 'Tabla que contiene los servicios para el aplicativo';
          woaho          postgres    false    246            �            1259    16693    tarifa    TABLE     �   CREATE TABLE woaho.tarifa (
    tarifa_id integer DEFAULT nextval('woaho.sec_tarifa'::regclass) NOT NULL,
    tarifa_valor numeric,
    tarifa_moneda integer,
    tarifa_territorio integer,
    tarifa_servicio integer,
    tarifa_unidad integer
);
    DROP TABLE woaho.tarifa;
       woaho         heap    postgres    false    217    7            1           0    0    TABLE tarifa    COMMENT     V   COMMENT ON TABLE woaho.tarifa IS 'Tabla que contiene las tarifas para el aplicativo';
          woaho          postgres    false    247            �            1259    16537 
   territorio    TABLE       CREATE TABLE woaho.territorio (
    territorio_id integer DEFAULT nextval('woaho.sec_territorio'::regclass) NOT NULL,
    territorio_nombre character varying(4000),
    territorio_padre integer,
    territorio_tipo integer,
    territorio_codigo character varying(4000)
);
    DROP TABLE woaho.territorio;
       woaho         heap    postgres    false    207    7            2           0    0    TABLE territorio    COMMENT     h   COMMENT ON TABLE woaho.territorio IS 'Tabla que contiene los territorios registrados en el aplicativo';
          woaho          postgres    false    236            �            1259    16443    tipo    TABLE     �   CREATE TABLE woaho.tipo (
    tipo_id integer DEFAULT nextval('woaho.sec_tipo'::regclass) NOT NULL,
    tipo_nombre character varying(4000)
);
    DROP TABLE woaho.tipo;
       woaho         heap    postgres    false    206    7            3           0    0 
   TABLE tipo    COMMENT     ^   COMMENT ON TABLE woaho.tipo IS 'Tabla que contiene la información de los tipos de pantalla';
          woaho          postgres    false    231            �            1259    16528    tipo_territorio    TABLE     �   CREATE TABLE woaho.tipo_territorio (
    tipo_territorio_id integer DEFAULT nextval('woaho.sec_tipo_territorio'::regclass) NOT NULL,
    tipo_territorio_nombre character varying(4000)
);
 "   DROP TABLE woaho.tipo_territorio;
       woaho         heap    postgres    false    208    7            4           0    0    TABLE tipo_territorio    COMMENT     Y   COMMENT ON TABLE woaho.tipo_territorio IS 'Tabla que contiene los tipos de territorios';
          woaho          postgres    false    235                       1259    17006 
   traduccion    TABLE       CREATE TABLE woaho.traduccion (
    traduccion_id integer DEFAULT nextval('woaho.sec_traduccion'::regclass) NOT NULL,
    traduccion_codigo_mensaje character varying(4000),
    traduccion_traduccion character varying(4000),
    traduccion_idioma integer
);
    DROP TABLE woaho.traduccion;
       woaho         heap    woaho    false    259    7            �            1259    16785 	   ubicacion    TABLE     '  CREATE TABLE woaho.ubicacion (
    ubicacion_id integer DEFAULT nextval('woaho.sec_ubicacion'::regclass) NOT NULL,
    ubicacion_profesional integer,
    ubicacion_lugar_id character varying(4000),
    ubicacion_latitud character varying(4000),
    ubicacion_longitud character varying(4000)
);
    DROP TABLE woaho.ubicacion;
       woaho         heap    postgres    false    226    7            5           0    0    TABLE ubicacion    COMMENT     _   COMMENT ON TABLE woaho.ubicacion IS 'Tabla que contiene las ubicaciones de los profesionales';
          woaho          postgres    false    251            �            1259    16660    unidad_tarifa    TABLE     �   CREATE TABLE woaho.unidad_tarifa (
    unidad_tarifa_id integer DEFAULT nextval('woaho.sec_unidad_tarifa'::regclass) NOT NULL,
    unidad_tarifa_nombre character varying(4000)
);
     DROP TABLE woaho.unidad_tarifa;
       woaho         heap    postgres    false    220    7            6           0    0    TABLE unidad_tarifa    COMMENT     i   COMMENT ON TABLE woaho.unidad_tarifa IS 'Tabla que contiene las unidades de tarifas para el aplicativo';
          woaho          postgres    false    245            �            1259    16551    usuario    TABLE     +  CREATE TABLE woaho.usuario (
    usuario_id integer DEFAULT nextval('woaho.sec_usuario'::regclass) NOT NULL,
    usuario_nombre character varying(4000),
    usuario_apellido character varying(4000),
    usuario_celular character varying(4000),
    usuario_correo character varying(4000),
    usuario_acepta_terminos character varying(4000),
    usuario_fecha_hora_acepta_terminos timestamp without time zone,
    usuario_clave character varying(4000),
    usuario_id_suscriptor character varying(4000),
    usuario_referralcode character varying(4000)
);
    DROP TABLE woaho.usuario;
       woaho         heap    postgres    false    209    7            7           0    0    TABLE usuario    COMMENT     b   COMMENT ON TABLE woaho.usuario IS 'Tabla que contiene los usuarios registrados en el aplicativo';
          woaho          postgres    false    237            	          0    16799    calificacion 
   TABLE DATA                 woaho          postgres    false    252                      0    16876    cancelacion 
   TABLE DATA                 woaho          postgres    false    255   z                   0    16632 	   categoria 
   TABLE DATA                 woaho          postgres    false    243           �          0    16571    codigo 
   TABLE DATA                 woaho          postgres    false    239   �                  0    16741    codigo_promocional 
   TABLE DATA                 woaho          postgres    false    249   P       �          0    16590 	   direccion 
   TABLE DATA                 woaho          postgres    false    240                     0    17024    equivalencia_idioma 
   TABLE DATA                 woaho          postgres    false    262   �        �          0    16562    estado 
   TABLE DATA                 woaho          postgres    false    238   N                 0    16990    etiqueta 
   TABLE DATA                 woaho          postgres    false    258   �        �          0    16452    idioma 
   TABLE DATA                 woaho          postgres    false    232           �          0    16623    imagen 
   TABLE DATA                 woaho          postgres    false    242   `        
          0    16823 
   medio_pago 
   TABLE DATA                 woaho          postgres    false    253   �       �          0    16498    mensaje 
   TABLE DATA                 woaho          postgres    false    234   o                  0    16909    mensaje_pantalla 
   TABLE DATA                 woaho          postgres    false    257   �                  0    16646    moneda 
   TABLE DATA                 woaho          postgres    false    244   �                  0    16890    pantalla 
   TABLE DATA                 woaho          postgres    false    256   b        �          0    16614 	   parametro 
   TABLE DATA                 woaho          postgres    false    241   z                  0    16837    pedido 
   TABLE DATA                 woaho          postgres    false    254   �        �          0    16475 	   profesion 
   TABLE DATA                 woaho          postgres    false    233   �                  0    16766    profesional 
   TABLE DATA                 woaho          postgres    false    250   l                  0    16722 	   promocion 
   TABLE DATA                 woaho          postgres    false    248   �                 0    16669    servicio 
   TABLE DATA                 woaho          postgres    false    246                     0    16693    tarifa 
   TABLE DATA                 woaho          postgres    false    247   r       �          0    16537 
   territorio 
   TABLE DATA                 woaho          postgres    false    236   �        �          0    16443    tipo 
   TABLE DATA                 woaho          postgres    false    231   �        �          0    16528    tipo_territorio 
   TABLE DATA                 woaho          postgres    false    235   q                  0    17006 
   traduccion 
   TABLE DATA                 woaho          woaho    false    260   �                  0    16785 	   ubicacion 
   TABLE DATA                 woaho          postgres    false    251   �                 0    16660    unidad_tarifa 
   TABLE DATA                 woaho          postgres    false    245   �        �          0    16551    usuario 
   TABLE DATA                 woaho          postgres    false    237   t        8           0    0    sec_calificacion    SEQUENCE SET     =   SELECT pg_catalog.setval('woaho.sec_calificacion', 7, true);
          woaho          postgres    false    224            9           0    0    sec_cancelacion    SEQUENCE SET     =   SELECT pg_catalog.setval('woaho.sec_cancelacion', 1, false);
          woaho          postgres    false    230            :           0    0    sec_categoria    SEQUENCE SET     :   SELECT pg_catalog.setval('woaho.sec_categoria', 9, true);
          woaho          postgres    false    214            ;           0    0 
   sec_codigo    SEQUENCE SET     8   SELECT pg_catalog.setval('woaho.sec_codigo', 50, true);
          woaho          postgres    false    211            <           0    0    sec_codigo_promocional    SEQUENCE SET     D   SELECT pg_catalog.setval('woaho.sec_codigo_promocional', 1, false);
          woaho          postgres    false    218            =           0    0    sec_direccion    SEQUENCE SET     :   SELECT pg_catalog.setval('woaho.sec_direccion', 5, true);
          woaho          postgres    false    213            >           0    0    sec_equivalencia_idioma    SEQUENCE SET     E   SELECT pg_catalog.setval('woaho.sec_equivalencia_idioma', 60, true);
          woaho          postgres    false    261            ?           0    0 
   sec_estado    SEQUENCE SET     7   SELECT pg_catalog.setval('woaho.sec_estado', 6, true);
          woaho          postgres    false    210            @           0    0    sec_etiqueta    SEQUENCE SET     :   SELECT pg_catalog.setval('woaho.sec_etiqueta', 40, true);
          woaho          postgres    false    228            A           0    0 
   sec_idioma    SEQUENCE SET     7   SELECT pg_catalog.setval('woaho.sec_idioma', 2, true);
          woaho          postgres    false    225            B           0    0 
   sec_imagen    SEQUENCE SET     8   SELECT pg_catalog.setval('woaho.sec_imagen', 29, true);
          woaho          postgres    false    223            C           0    0    sec_medio_pago    SEQUENCE SET     ;   SELECT pg_catalog.setval('woaho.sec_medio_pago', 3, true);
          woaho          postgres    false    229            D           0    0    sec_mensaje    SEQUENCE SET     9   SELECT pg_catalog.setval('woaho.sec_mensaje', 36, true);
          woaho          postgres    false    203            E           0    0    sec_mensaje_pantalla    SEQUENCE SET     B   SELECT pg_catalog.setval('woaho.sec_mensaje_pantalla', 12, true);
          woaho          postgres    false    204            F           0    0 
   sec_moneda    SEQUENCE SET     7   SELECT pg_catalog.setval('woaho.sec_moneda', 3, true);
          woaho          postgres    false    215            G           0    0    sec_pantalla    SEQUENCE SET     9   SELECT pg_catalog.setval('woaho.sec_pantalla', 4, true);
          woaho          postgres    false    205            H           0    0    sec_parametro    SEQUENCE SET     :   SELECT pg_catalog.setval('woaho.sec_parametro', 2, true);
          woaho          postgres    false    212            I           0    0 
   sec_pedido    SEQUENCE SET     7   SELECT pg_catalog.setval('woaho.sec_pedido', 4, true);
          woaho          postgres    false    221            J           0    0    sec_profesion    SEQUENCE SET     :   SELECT pg_catalog.setval('woaho.sec_profesion', 3, true);
          woaho          postgres    false    227            K           0    0    sec_profesional    SEQUENCE SET     <   SELECT pg_catalog.setval('woaho.sec_profesional', 1, true);
          woaho          postgres    false    222            L           0    0    sec_promocion    SEQUENCE SET     ;   SELECT pg_catalog.setval('woaho.sec_promocion', 1, false);
          woaho          postgres    false    219            M           0    0    sec_servicio    SEQUENCE SET     9   SELECT pg_catalog.setval('woaho.sec_servicio', 3, true);
          woaho          postgres    false    216            N           0    0 
   sec_tarifa    SEQUENCE SET     8   SELECT pg_catalog.setval('woaho.sec_tarifa', 12, true);
          woaho          postgres    false    217            O           0    0    sec_territorio    SEQUENCE SET     ;   SELECT pg_catalog.setval('woaho.sec_territorio', 7, true);
          woaho          woaho    false    207            P           0    0    sec_tipo    SEQUENCE SET     5   SELECT pg_catalog.setval('woaho.sec_tipo', 5, true);
          woaho          postgres    false    206            Q           0    0    sec_tipo_territorio    SEQUENCE SET     @   SELECT pg_catalog.setval('woaho.sec_tipo_territorio', 7, true);
          woaho          postgres    false    208            R           0    0    sec_traduccion    SEQUENCE SET     <   SELECT pg_catalog.setval('woaho.sec_traduccion', 26, true);
          woaho          postgres    false    259            S           0    0    sec_ubicacion    SEQUENCE SET     :   SELECT pg_catalog.setval('woaho.sec_ubicacion', 2, true);
          woaho          postgres    false    226            T           0    0    sec_unidad_tarifa    SEQUENCE SET     >   SELECT pg_catalog.setval('woaho.sec_unidad_tarifa', 5, true);
          woaho          postgres    false    220            U           0    0    sec_usuario    SEQUENCE SET     9   SELECT pg_catalog.setval('woaho.sec_usuario', 42, true);
          woaho          postgres    false    209                       2606    16807    calificacion calificacion_pkey 
   CONSTRAINT     h   ALTER TABLE ONLY woaho.calificacion
    ADD CONSTRAINT calificacion_pkey PRIMARY KEY (calificacion_id);
 G   ALTER TABLE ONLY woaho.calificacion DROP CONSTRAINT calificacion_pkey;
       woaho            postgres    false    252            %           2606    16884    cancelacion cancelacion_pkey 
   CONSTRAINT     e   ALTER TABLE ONLY woaho.cancelacion
    ADD CONSTRAINT cancelacion_pkey PRIMARY KEY (cancelacion_id);
 E   ALTER TABLE ONLY woaho.cancelacion DROP CONSTRAINT cancelacion_pkey;
       woaho            postgres    false    255                       2606    16640    categoria categoria_pkey 
   CONSTRAINT     _   ALTER TABLE ONLY woaho.categoria
    ADD CONSTRAINT categoria_pkey PRIMARY KEY (categoria_id);
 A   ALTER TABLE ONLY woaho.categoria DROP CONSTRAINT categoria_pkey;
       woaho            postgres    false    243            �           2606    16561    usuario celular_key 
   CONSTRAINT     X   ALTER TABLE ONLY woaho.usuario
    ADD CONSTRAINT celular_key UNIQUE (usuario_celular);
 <   ALTER TABLE ONLY woaho.usuario DROP CONSTRAINT celular_key;
       woaho            postgres    false    237                       2606    16579    codigo codigo_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY woaho.codigo
    ADD CONSTRAINT codigo_pkey PRIMARY KEY (codigo_id);
 ;   ALTER TABLE ONLY woaho.codigo DROP CONSTRAINT codigo_pkey;
       woaho            postgres    false    239                       2606    16749 *   codigo_promocional codigo_promocional_pkey 
   CONSTRAINT     z   ALTER TABLE ONLY woaho.codigo_promocional
    ADD CONSTRAINT codigo_promocional_pkey PRIMARY KEY (codigo_promocional_id);
 S   ALTER TABLE ONLY woaho.codigo_promocional DROP CONSTRAINT codigo_promocional_pkey;
       woaho            postgres    false    249                       2606    16598    direccion direccion_pkey 
   CONSTRAINT     _   ALTER TABLE ONLY woaho.direccion
    ADD CONSTRAINT direccion_pkey PRIMARY KEY (direccion_id);
 A   ALTER TABLE ONLY woaho.direccion DROP CONSTRAINT direccion_pkey;
       woaho            postgres    false    240            /           2606    17032 ,   equivalencia_idioma equivalencia_idioma_pkey 
   CONSTRAINT     }   ALTER TABLE ONLY woaho.equivalencia_idioma
    ADD CONSTRAINT equivalencia_idioma_pkey PRIMARY KEY (equivalencia_idioma_id);
 U   ALTER TABLE ONLY woaho.equivalencia_idioma DROP CONSTRAINT equivalencia_idioma_pkey;
       woaho            postgres    false    262                       2606    16570    estado estado_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY woaho.estado
    ADD CONSTRAINT estado_pkey PRIMARY KEY (estado_id);
 ;   ALTER TABLE ONLY woaho.estado DROP CONSTRAINT estado_pkey;
       woaho            postgres    false    238            +           2606    16998    etiqueta etiqueta_pkey 
   CONSTRAINT     \   ALTER TABLE ONLY woaho.etiqueta
    ADD CONSTRAINT etiqueta_pkey PRIMARY KEY (etiqueta_id);
 ?   ALTER TABLE ONLY woaho.etiqueta DROP CONSTRAINT etiqueta_pkey;
       woaho            postgres    false    258            �           2606    16460    idioma idioma_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY woaho.idioma
    ADD CONSTRAINT idioma_pkey PRIMARY KEY (idioma_id);
 ;   ALTER TABLE ONLY woaho.idioma DROP CONSTRAINT idioma_pkey;
       woaho            postgres    false    232                       2606    16631    imagen imagen_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY woaho.imagen
    ADD CONSTRAINT imagen_pkey PRIMARY KEY (imagen_id);
 ;   ALTER TABLE ONLY woaho.imagen DROP CONSTRAINT imagen_pkey;
       woaho            postgres    false    242            !           2606    16831    medio_pago medio_pago_pkey 
   CONSTRAINT     b   ALTER TABLE ONLY woaho.medio_pago
    ADD CONSTRAINT medio_pago_pkey PRIMARY KEY (medio_pago_id);
 C   ALTER TABLE ONLY woaho.medio_pago DROP CONSTRAINT medio_pago_pkey;
       woaho            postgres    false    253            )           2606    16914 &   mensaje_pantalla mensaje_pantalla_pkey 
   CONSTRAINT     t   ALTER TABLE ONLY woaho.mensaje_pantalla
    ADD CONSTRAINT mensaje_pantalla_pkey PRIMARY KEY (mensaje_pantalla_id);
 O   ALTER TABLE ONLY woaho.mensaje_pantalla DROP CONSTRAINT mensaje_pantalla_pkey;
       woaho            postgres    false    257            �           2606    16506    mensaje mensaje_pkey 
   CONSTRAINT     Y   ALTER TABLE ONLY woaho.mensaje
    ADD CONSTRAINT mensaje_pkey PRIMARY KEY (mensaje_id);
 =   ALTER TABLE ONLY woaho.mensaje DROP CONSTRAINT mensaje_pkey;
       woaho            postgres    false    234                       2606    16654    moneda moneda_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY woaho.moneda
    ADD CONSTRAINT moneda_pkey PRIMARY KEY (moneda_id);
 ;   ALTER TABLE ONLY woaho.moneda DROP CONSTRAINT moneda_pkey;
       woaho            postgres    false    244            '           2606    16898    pantalla pantalla_pkey 
   CONSTRAINT     \   ALTER TABLE ONLY woaho.pantalla
    ADD CONSTRAINT pantalla_pkey PRIMARY KEY (pantalla_id);
 ?   ALTER TABLE ONLY woaho.pantalla DROP CONSTRAINT pantalla_pkey;
       woaho            postgres    false    256            	           2606    16622    parametro parametro_pkey 
   CONSTRAINT     _   ALTER TABLE ONLY woaho.parametro
    ADD CONSTRAINT parametro_pkey PRIMARY KEY (parametro_id);
 A   ALTER TABLE ONLY woaho.parametro DROP CONSTRAINT parametro_pkey;
       woaho            postgres    false    241            #           2606    16845    pedido pedido_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY woaho.pedido
    ADD CONSTRAINT pedido_pkey PRIMARY KEY (pedido_id);
 ;   ALTER TABLE ONLY woaho.pedido DROP CONSTRAINT pedido_pkey;
       woaho            postgres    false    254            �           2606    16483    profesion profesion_pkey 
   CONSTRAINT     _   ALTER TABLE ONLY woaho.profesion
    ADD CONSTRAINT profesion_pkey PRIMARY KEY (profesion_id);
 A   ALTER TABLE ONLY woaho.profesion DROP CONSTRAINT profesion_pkey;
       woaho            postgres    false    233                       2606    16774    profesional profesional_pkey 
   CONSTRAINT     e   ALTER TABLE ONLY woaho.profesional
    ADD CONSTRAINT profesional_pkey PRIMARY KEY (profesional_id);
 E   ALTER TABLE ONLY woaho.profesional DROP CONSTRAINT profesional_pkey;
       woaho            postgres    false    250                       2606    16730    promocion promocion_pkey 
   CONSTRAINT     _   ALTER TABLE ONLY woaho.promocion
    ADD CONSTRAINT promocion_pkey PRIMARY KEY (promocion_id);
 A   ALTER TABLE ONLY woaho.promocion DROP CONSTRAINT promocion_pkey;
       woaho            postgres    false    248                       2606    16677    servicio servicio_pkey 
   CONSTRAINT     \   ALTER TABLE ONLY woaho.servicio
    ADD CONSTRAINT servicio_pkey PRIMARY KEY (servicio_id);
 ?   ALTER TABLE ONLY woaho.servicio DROP CONSTRAINT servicio_pkey;
       woaho            postgres    false    246                       2606    16701    tarifa tarifa_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY woaho.tarifa
    ADD CONSTRAINT tarifa_pkey PRIMARY KEY (tarifa_id);
 ;   ALTER TABLE ONLY woaho.tarifa DROP CONSTRAINT tarifa_pkey;
       woaho            postgres    false    247            �           2606    16545    territorio territorio_pkey 
   CONSTRAINT     b   ALTER TABLE ONLY woaho.territorio
    ADD CONSTRAINT territorio_pkey PRIMARY KEY (territorio_id);
 C   ALTER TABLE ONLY woaho.territorio DROP CONSTRAINT territorio_pkey;
       woaho            postgres    false    236            �           2606    16451    tipo tipo_pantalla_pkey 
   CONSTRAINT     Y   ALTER TABLE ONLY woaho.tipo
    ADD CONSTRAINT tipo_pantalla_pkey PRIMARY KEY (tipo_id);
 @   ALTER TABLE ONLY woaho.tipo DROP CONSTRAINT tipo_pantalla_pkey;
       woaho            postgres    false    231            �           2606    16536 $   tipo_territorio tipo_territorio_pkey 
   CONSTRAINT     q   ALTER TABLE ONLY woaho.tipo_territorio
    ADD CONSTRAINT tipo_territorio_pkey PRIMARY KEY (tipo_territorio_id);
 M   ALTER TABLE ONLY woaho.tipo_territorio DROP CONSTRAINT tipo_territorio_pkey;
       woaho            postgres    false    235            -           2606    17014    traduccion traduccion_pkey 
   CONSTRAINT     b   ALTER TABLE ONLY woaho.traduccion
    ADD CONSTRAINT traduccion_pkey PRIMARY KEY (traduccion_id);
 C   ALTER TABLE ONLY woaho.traduccion DROP CONSTRAINT traduccion_pkey;
       woaho            woaho    false    260                       2606    16793    ubicacion ubicacion_pkey 
   CONSTRAINT     _   ALTER TABLE ONLY woaho.ubicacion
    ADD CONSTRAINT ubicacion_pkey PRIMARY KEY (ubicacion_id);
 A   ALTER TABLE ONLY woaho.ubicacion DROP CONSTRAINT ubicacion_pkey;
       woaho            postgres    false    251                       2606    16668     unidad_tarifa unidad_tarifa_pkey 
   CONSTRAINT     k   ALTER TABLE ONLY woaho.unidad_tarifa
    ADD CONSTRAINT unidad_tarifa_pkey PRIMARY KEY (unidad_tarifa_id);
 I   ALTER TABLE ONLY woaho.unidad_tarifa DROP CONSTRAINT unidad_tarifa_pkey;
       woaho            postgres    false    245                       2606    16559    usuario usuario_pkey 
   CONSTRAINT     Y   ALTER TABLE ONLY woaho.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (usuario_id);
 =   ALTER TABLE ONLY woaho.usuario DROP CONSTRAINT usuario_pkey;
       woaho            postgres    false    237            I           2606    16813 (   calificacion FK_CALIFICACION_PROFESIONAL    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.calificacion
    ADD CONSTRAINT "FK_CALIFICACION_PROFESIONAL" FOREIGN KEY (calificacion_profesional) REFERENCES woaho.profesional(profesional_id);
 S   ALTER TABLE ONLY woaho.calificacion DROP CONSTRAINT "FK_CALIFICACION_PROFESIONAL";
       woaho          postgres    false    3099    250    252            J           2606    16818 %   calificacion FK_CALIFICACION_SERVICIO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.calificacion
    ADD CONSTRAINT "FK_CALIFICACION_SERVICIO" FOREIGN KEY (calificacion_servicio) REFERENCES woaho.servicio(servicio_id);
 P   ALTER TABLE ONLY woaho.calificacion DROP CONSTRAINT "FK_CALIFICACION_SERVICIO";
       woaho          postgres    false    246    3091    252            H           2606    16808 $   calificacion FK_CALIFICACION_USUARIO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.calificacion
    ADD CONSTRAINT "FK_CALIFICACION_USUARIO" FOREIGN KEY (calificacion_usuario) REFERENCES woaho.usuario(usuario_id);
 O   ALTER TABLE ONLY woaho.calificacion DROP CONSTRAINT "FK_CALIFICACION_USUARIO";
       woaho          postgres    false    237    252    3073            S           2606    16885 !   cancelacion FK_CANCELACION_PEDIDO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.cancelacion
    ADD CONSTRAINT "FK_CANCELACION_PEDIDO" FOREIGN KEY (cancelacion_pedido) REFERENCES woaho.pedido(pedido_id);
 L   ALTER TABLE ONLY woaho.cancelacion DROP CONSTRAINT "FK_CANCELACION_PEDIDO";
       woaho          postgres    false    255    3107    254            7           2606    16641    categoria FK_CATEGORIA_IMAGEN    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.categoria
    ADD CONSTRAINT "FK_CATEGORIA_IMAGEN" FOREIGN KEY (categoria_imagen) REFERENCES woaho.imagen(imagen_id);
 H   ALTER TABLE ONLY woaho.categoria DROP CONSTRAINT "FK_CATEGORIA_IMAGEN";
       woaho          postgres    false    242    243    3083            3           2606    16585    codigo FK_CODIGO_ESTADO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.codigo
    ADD CONSTRAINT "FK_CODIGO_ESTADO" FOREIGN KEY (codigo_estado) REFERENCES woaho.estado(estado_id);
 B   ALTER TABLE ONLY woaho.codigo DROP CONSTRAINT "FK_CODIGO_ESTADO";
       woaho          postgres    false    238    239    3075            C           2606    16755 %   codigo_promocional FK_COD_PROM_ESTADO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.codigo_promocional
    ADD CONSTRAINT "FK_COD_PROM_ESTADO" FOREIGN KEY (codigo_promocional_estado) REFERENCES woaho.estado(estado_id);
 P   ALTER TABLE ONLY woaho.codigo_promocional DROP CONSTRAINT "FK_COD_PROM_ESTADO";
       woaho          postgres    false    249    3075    238            D           2606    16760 (   codigo_promocional FK_COD_PROM_PROMOCION    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.codigo_promocional
    ADD CONSTRAINT "FK_COD_PROM_PROMOCION" FOREIGN KEY (codigo_promocional_promocion) REFERENCES woaho.promocion(promocion_id);
 S   ALTER TABLE ONLY woaho.codigo_promocional DROP CONSTRAINT "FK_COD_PROM_PROMOCION";
       woaho          postgres    false    248    3095    249            B           2606    16750 &   codigo_promocional FK_COD_PROM_USUARIO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.codigo_promocional
    ADD CONSTRAINT "FK_COD_PROM_USUARIO" FOREIGN KEY (codigo_promocional_usuario) REFERENCES woaho.usuario(usuario_id);
 Q   ALTER TABLE ONLY woaho.codigo_promocional DROP CONSTRAINT "FK_COD_PROM_USUARIO";
       woaho          postgres    false    249    237    3073            6           2606    16609    direccion FK_DIRECCION_ESTADO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.direccion
    ADD CONSTRAINT "FK_DIRECCION_ESTADO" FOREIGN KEY (direccion_estado) REFERENCES woaho.estado(estado_id);
 H   ALTER TABLE ONLY woaho.direccion DROP CONSTRAINT "FK_DIRECCION_ESTADO";
       woaho          postgres    false    240    3075    238            4           2606    16599 !   direccion FK_DIRECCION_TERRITORIO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.direccion
    ADD CONSTRAINT "FK_DIRECCION_TERRITORIO" FOREIGN KEY (direccion_territorio_id) REFERENCES woaho.territorio(territorio_id);
 L   ALTER TABLE ONLY woaho.direccion DROP CONSTRAINT "FK_DIRECCION_TERRITORIO";
       woaho          postgres    false    236    240    3069            5           2606    16604    direccion FK_DIRECCION_USUARIO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.direccion
    ADD CONSTRAINT "FK_DIRECCION_USUARIO" FOREIGN KEY (direccion_usuario) REFERENCES woaho.usuario(usuario_id);
 I   ALTER TABLE ONLY woaho.direccion DROP CONSTRAINT "FK_DIRECCION_USUARIO";
       woaho          postgres    false    3073    237    240            X           2606    16999    etiqueta FK_ETIQUETA_IDIOMA    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.etiqueta
    ADD CONSTRAINT "FK_ETIQUETA_IDIOMA" FOREIGN KEY (etiqueta_idioma) REFERENCES woaho.idioma(idioma_id);
 F   ALTER TABLE ONLY woaho.etiqueta DROP CONSTRAINT "FK_ETIQUETA_IDIOMA";
       woaho          postgres    false    3061    232    258            K           2606    16832 #   medio_pago FK_MEDIO_PAGO_TERRITORIO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.medio_pago
    ADD CONSTRAINT "FK_MEDIO_PAGO_TERRITORIO" FOREIGN KEY (medio_pago_territorio) REFERENCES woaho.territorio(territorio_id);
 N   ALTER TABLE ONLY woaho.medio_pago DROP CONSTRAINT "FK_MEDIO_PAGO_TERRITORIO";
       woaho          postgres    false    3069    253    236            V           2606    16915 ,   mensaje_pantalla FK_MENSAJE_PANTALLA_MENSAJE    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.mensaje_pantalla
    ADD CONSTRAINT "FK_MENSAJE_PANTALLA_MENSAJE" FOREIGN KEY (mensaje_pantalla_mensaje_id) REFERENCES woaho.mensaje(mensaje_id);
 W   ALTER TABLE ONLY woaho.mensaje_pantalla DROP CONSTRAINT "FK_MENSAJE_PANTALLA_MENSAJE";
       woaho          postgres    false    234    3065    257            W           2606    16920 -   mensaje_pantalla FK_MENSAJE_PANTALLA_PANTALLA    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.mensaje_pantalla
    ADD CONSTRAINT "FK_MENSAJE_PANTALLA_PANTALLA" FOREIGN KEY (mensaje_pantalla_pantalla_id) REFERENCES woaho.pantalla(pantalla_id);
 X   ALTER TABLE ONLY woaho.mensaje_pantalla DROP CONSTRAINT "FK_MENSAJE_PANTALLA_PANTALLA";
       woaho          postgres    false    257    256    3111            0           2606    16507    mensaje FK_MENSAJE_TIPO    FK CONSTRAINT        ALTER TABLE ONLY woaho.mensaje
    ADD CONSTRAINT "FK_MENSAJE_TIPO" FOREIGN KEY (mensaje_tipo) REFERENCES woaho.tipo(tipo_id);
 B   ALTER TABLE ONLY woaho.mensaje DROP CONSTRAINT "FK_MENSAJE_TIPO";
       woaho          postgres    false    3059    231    234            8           2606    16655    moneda FK_MONEDA_TERRITORIO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.moneda
    ADD CONSTRAINT "FK_MONEDA_TERRITORIO" FOREIGN KEY (moneda_territorio) REFERENCES woaho.territorio(territorio_id);
 F   ALTER TABLE ONLY woaho.moneda DROP CONSTRAINT "FK_MONEDA_TERRITORIO";
       woaho          postgres    false    244    236    3069            U           2606    16904    pantalla FK_PANTALLA_IMAGEN    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.pantalla
    ADD CONSTRAINT "FK_PANTALLA_IMAGEN" FOREIGN KEY (pantalla_imagen) REFERENCES woaho.imagen(imagen_id);
 F   ALTER TABLE ONLY woaho.pantalla DROP CONSTRAINT "FK_PANTALLA_IMAGEN";
       woaho          postgres    false    3083    256    242            T           2606    16899 !   pantalla FK_PANTALLA_TIPO_PANTLLA    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.pantalla
    ADD CONSTRAINT "FK_PANTALLA_TIPO_PANTLLA" FOREIGN KEY (pantalla_tipo_pantalla) REFERENCES woaho.tipo(tipo_id);
 L   ALTER TABLE ONLY woaho.pantalla DROP CONSTRAINT "FK_PANTALLA_TIPO_PANTLLA";
       woaho          postgres    false    256    3059    231            O           2606    16861    pedido FK_PEDIDO_DIRECCION    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.pedido
    ADD CONSTRAINT "FK_PEDIDO_DIRECCION" FOREIGN KEY (pedido_direccion) REFERENCES woaho.direccion(direccion_id);
 E   ALTER TABLE ONLY woaho.pedido DROP CONSTRAINT "FK_PEDIDO_DIRECCION";
       woaho          postgres    false    254    3079    240            N           2606    16856    pedido FK_PEDIDO_ESTADO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.pedido
    ADD CONSTRAINT "FK_PEDIDO_ESTADO" FOREIGN KEY (pedido_estado) REFERENCES woaho.estado(estado_id);
 B   ALTER TABLE ONLY woaho.pedido DROP CONSTRAINT "FK_PEDIDO_ESTADO";
       woaho          postgres    false    238    254    3075            Q           2606    16871    pedido FK_PEDIDO_MEDIO_PAGO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.pedido
    ADD CONSTRAINT "FK_PEDIDO_MEDIO_PAGO" FOREIGN KEY (pedido_medio_pago) REFERENCES woaho.medio_pago(medio_pago_id);
 F   ALTER TABLE ONLY woaho.pedido DROP CONSTRAINT "FK_PEDIDO_MEDIO_PAGO";
       woaho          postgres    false    3105    254    253            P           2606    16866    pedido FK_PEDIDO_PROFESIONAL    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.pedido
    ADD CONSTRAINT "FK_PEDIDO_PROFESIONAL" FOREIGN KEY (pedido_profesional) REFERENCES woaho.profesional(profesional_id);
 G   ALTER TABLE ONLY woaho.pedido DROP CONSTRAINT "FK_PEDIDO_PROFESIONAL";
       woaho          postgres    false    250    3099    254            L           2606    16846    pedido FK_PEDIDO_SERVICIO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.pedido
    ADD CONSTRAINT "FK_PEDIDO_SERVICIO" FOREIGN KEY (pedido_servicio) REFERENCES woaho.servicio(servicio_id);
 D   ALTER TABLE ONLY woaho.pedido DROP CONSTRAINT "FK_PEDIDO_SERVICIO";
       woaho          postgres    false    3091    246    254            M           2606    16851    pedido FK_PEDIDO_USUARIO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.pedido
    ADD CONSTRAINT "FK_PEDIDO_USUARIO" FOREIGN KEY (pedido_servicio) REFERENCES woaho.usuario(usuario_id);
 C   ALTER TABLE ONLY woaho.pedido DROP CONSTRAINT "FK_PEDIDO_USUARIO";
       woaho          postgres    false    237    254    3073            F           2606    16780     profesional FK_PROFESIONAL_ICONO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.profesional
    ADD CONSTRAINT "FK_PROFESIONAL_ICONO" FOREIGN KEY (profesional_imagen_icono) REFERENCES woaho.imagen(imagen_id);
 K   ALTER TABLE ONLY woaho.profesional DROP CONSTRAINT "FK_PROFESIONAL_ICONO";
       woaho          postgres    false    250    242    3083            E           2606    16775 %   profesional FK_PROFESIONAL_TERRITORIO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.profesional
    ADD CONSTRAINT "FK_PROFESIONAL_TERRITORIO" FOREIGN KEY (profesional_nacionalidad) REFERENCES woaho.territorio(territorio_id);
 P   ALTER TABLE ONLY woaho.profesional DROP CONSTRAINT "FK_PROFESIONAL_TERRITORIO";
       woaho          postgres    false    250    236    3069            A           2606    16736    promocion FK_PROM_ESTADO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.promocion
    ADD CONSTRAINT "FK_PROM_ESTADO" FOREIGN KEY (promocion_estado) REFERENCES woaho.estado(estado_id);
 C   ALTER TABLE ONLY woaho.promocion DROP CONSTRAINT "FK_PROM_ESTADO";
       woaho          postgres    false    248    238    3075            @           2606    16731    promocion FK_PROM_TARIFA    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.promocion
    ADD CONSTRAINT "FK_PROM_TARIFA" FOREIGN KEY (promocion_tarifa) REFERENCES woaho.tarifa(tarifa_id);
 C   ALTER TABLE ONLY woaho.promocion DROP CONSTRAINT "FK_PROM_TARIFA";
       woaho          postgres    false    247    3093    248            9           2606    16678    servicio FK_SERVICIO_CATEGORIA    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.servicio
    ADD CONSTRAINT "FK_SERVICIO_CATEGORIA" FOREIGN KEY (servicio_categoria) REFERENCES woaho.categoria(categoria_id);
 I   ALTER TABLE ONLY woaho.servicio DROP CONSTRAINT "FK_SERVICIO_CATEGORIA";
       woaho          postgres    false    243    246    3085            :           2606    16683    servicio FK_SERVICIO_IMAGEN    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.servicio
    ADD CONSTRAINT "FK_SERVICIO_IMAGEN" FOREIGN KEY (servicio_imagen) REFERENCES woaho.imagen(imagen_id);
 F   ALTER TABLE ONLY woaho.servicio DROP CONSTRAINT "FK_SERVICIO_IMAGEN";
       woaho          postgres    false    3083    246    242            ;           2606    16688    servicio FK_SERVICIO_TERRITORIO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.servicio
    ADD CONSTRAINT "FK_SERVICIO_TERRITORIO" FOREIGN KEY (servicio_territorio) REFERENCES woaho.territorio(territorio_id);
 J   ALTER TABLE ONLY woaho.servicio DROP CONSTRAINT "FK_SERVICIO_TERRITORIO";
       woaho          postgres    false    246    3069    236            <           2606    16702    tarifa FK_TARIFA_MONEDA    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.tarifa
    ADD CONSTRAINT "FK_TARIFA_MONEDA" FOREIGN KEY (tarifa_moneda) REFERENCES woaho.moneda(moneda_id);
 B   ALTER TABLE ONLY woaho.tarifa DROP CONSTRAINT "FK_TARIFA_MONEDA";
       woaho          postgres    false    3087    244    247            >           2606    16712    tarifa FK_TARIFA_SERVICIO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.tarifa
    ADD CONSTRAINT "FK_TARIFA_SERVICIO" FOREIGN KEY (tarifa_servicio) REFERENCES woaho.servicio(servicio_id);
 D   ALTER TABLE ONLY woaho.tarifa DROP CONSTRAINT "FK_TARIFA_SERVICIO";
       woaho          postgres    false    247    3091    246            =           2606    16707    tarifa FK_TARIFA_TERRITORIO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.tarifa
    ADD CONSTRAINT "FK_TARIFA_TERRITORIO" FOREIGN KEY (tarifa_territorio) REFERENCES woaho.territorio(territorio_id);
 F   ALTER TABLE ONLY woaho.tarifa DROP CONSTRAINT "FK_TARIFA_TERRITORIO";
       woaho          postgres    false    247    3069    236            ?           2606    16717    tarifa FK_TARIFA_UNIDAD    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.tarifa
    ADD CONSTRAINT "FK_TARIFA_UNIDAD" FOREIGN KEY (tarifa_unidad) REFERENCES woaho.unidad_tarifa(unidad_tarifa_id);
 B   ALTER TABLE ONLY woaho.tarifa DROP CONSTRAINT "FK_TARIFA_UNIDAD";
       woaho          postgres    false    245    3089    247            1           2606    16546 (   territorio FK_TERRITORIO_TIPO_TERRITORIO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.territorio
    ADD CONSTRAINT "FK_TERRITORIO_TIPO_TERRITORIO" FOREIGN KEY (territorio_tipo) REFERENCES woaho.tipo_territorio(tipo_territorio_id);
 S   ALTER TABLE ONLY woaho.territorio DROP CONSTRAINT "FK_TERRITORIO_TIPO_TERRITORIO";
       woaho          postgres    false    235    3067    236            Y           2606    17015    traduccion FK_TRADUCCION_IDIOMA    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.traduccion
    ADD CONSTRAINT "FK_TRADUCCION_IDIOMA" FOREIGN KEY (traduccion_idioma) REFERENCES woaho.idioma(idioma_id);
 J   ALTER TABLE ONLY woaho.traduccion DROP CONSTRAINT "FK_TRADUCCION_IDIOMA";
       woaho          woaho    false    3061    260    232            G           2606    16794 "   ubicacion FK_UBICACION_PROFESIONAL    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.ubicacion
    ADD CONSTRAINT "FK_UBICACION_PROFESIONAL" FOREIGN KEY (ubicacion_profesional) REFERENCES woaho.profesional(profesional_id);
 M   ALTER TABLE ONLY woaho.ubicacion DROP CONSTRAINT "FK_UBICACION_PROFESIONAL";
       woaho          postgres    false    251    3099    250            2           2606    16930 &   territorio fk1wg9n3xubkio4n3y16testobd    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.territorio
    ADD CONSTRAINT fk1wg9n3xubkio4n3y16testobd FOREIGN KEY (territorio_padre) REFERENCES woaho.territorio(territorio_id);
 O   ALTER TABLE ONLY woaho.territorio DROP CONSTRAINT fk1wg9n3xubkio4n3y16testobd;
       woaho          postgres    false    236    3069    236            R           2606    16925 "   pedido fkp80nbrpi74d5lutv2lo2ej3b3    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.pedido
    ADD CONSTRAINT fkp80nbrpi74d5lutv2lo2ej3b3 FOREIGN KEY (pedido_usuario) REFERENCES woaho.usuario(usuario_id);
 K   ALTER TABLE ONLY woaho.pedido DROP CONSTRAINT fkp80nbrpi74d5lutv2lo2ej3b3;
       woaho          postgres    false    237    3073    254           