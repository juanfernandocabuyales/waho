PGDMP     $                    y            woaho    12.1    12.2    ?           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            @           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            A           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            B           1262    16385    woaho    DATABASE     w   CREATE DATABASE woaho WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'en_US.UTF-8' LC_CTYPE = 'en_US.UTF-8';
    DROP DATABASE woaho;
                postgres    false                        2615    16386    woaho    SCHEMA        CREATE SCHEMA woaho;
    DROP SCHEMA woaho;
                postgres    false                       1255    17020 -   consultar_mensajes_pantalla(integer, integer)    FUNCTION     a  CREATE FUNCTION woaho.consultar_mensajes_pantalla(p_pantalla integer, p_idioma integer) RETURNS character varying
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
       woaho          postgres    false    7                       1255    17035 A   fndb_consultar_equivalencia(character varying, character varying)    FUNCTION     �  CREATE FUNCTION woaho.fndb_consultar_equivalencia(p_cadena character varying, p_idioma character varying) RETURNS character varying
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
       woaho          postgres    false    7                       1255    17021 4   fndb_consultar_pantallas(integer, character varying)    FUNCTION     e  CREATE FUNCTION woaho.fndb_consultar_pantallas(p_tipo_pantalla integer, p_idioma character varying) RETURNS character varying
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
       woaho          postgres    false    7                       1255    17038 B   fndb_generar_codigo_registro(character varying, character varying)    FUNCTION     �  CREATE FUNCTION woaho.fndb_generar_codigo_registro(p_celular character varying, p_idioma character varying) RETURNS character varying
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

	IF (codigo_anterior IS NULL ) THEN
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
 k   DROP FUNCTION woaho.fndb_generar_codigo_registro(p_celular character varying, p_idioma character varying);
       woaho          woaho    false    7                       1255    17037 U   fndb_validar_codigo_registro(character varying, character varying, character varying)    FUNCTION     W	  CREATE FUNCTION woaho.fndb_validar_codigo_registro(p_celular character varying, p_codigo character varying, p_idioma character varying) RETURNS character varying
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
       woaho          postgres    false    7                       1255    16937 $   generar_aleatorios(integer, integer)    FUNCTION     �   CREATE FUNCTION woaho.generar_aleatorios(pinicial integer, pfinal integer) RETURNS character varying
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
       woaho         heap    postgres    false    224    7            C           0    0    TABLE calificacion    COMMENT     e   COMMENT ON TABLE woaho.calificacion IS 'Tabla que contiene las calificaciones de los profesionales';
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
       woaho         heap    postgres    false    230    7            D           0    0    TABLE cancelacion    COMMENT     Y   COMMENT ON TABLE woaho.cancelacion IS 'Tabla que contiene las cancelaciones realizadas';
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
       woaho         heap    postgres    false    214    7            E           0    0    TABLE categoria    COMMENT     \   COMMENT ON TABLE woaho.categoria IS 'Tabla que contiene las categorias para el aplicativo';
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
       woaho         heap    postgres    false    211    7            F           0    0    TABLE codigo    COMMENT     h   COMMENT ON TABLE woaho.codigo IS 'Tabla que contiene los codigos generados para completar el registro';
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
       woaho         heap    postgres    false    218    7            G           0    0    TABLE codigo_promocional    COMMENT     l   COMMENT ON TABLE woaho.codigo_promocional IS 'Tabla que contiene los codigos promocionales del aplicativo';
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
       woaho         heap    postgres    false    213    7            H           0    0    TABLE direccion    COMMENT     g   COMMENT ON TABLE woaho.direccion IS 'Tabla que contiene las direcciones registradas por los usuarios';
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
       woaho         heap    postgres    false    261    7            I           0    0    TABLE equivalencia_idioma    COMMENT     f   COMMENT ON TABLE woaho.equivalencia_idioma IS 'Tabla que contiene las traducciones de las etiquetas';
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
       woaho         heap    postgres    false    210    7            J           0    0    TABLE estado    COMMENT     V   COMMENT ON TABLE woaho.estado IS 'Tabla que contiene los estados para el aplicativo';
          woaho          postgres    false    238            K           0    0    COLUMN estado.estado_codigo    COMMENT     b   COMMENT ON COLUMN woaho.estado.estado_codigo IS 'A activo, I inactivo, P pendiente, R rechazado';
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
       woaho         heap    postgres    false    228    7            L           0    0    TABLE etiqueta    COMMENT     V   COMMENT ON TABLE woaho.etiqueta IS 'Tabla que contiene las etiquetas del aplicativo';
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
       woaho         heap    postgres    false    225    7            M           0    0    TABLE idioma    COMMENT     e   COMMENT ON TABLE woaho.idioma IS 'Tabla que contiene la información de los idiomas del aplicativo';
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
       woaho         heap    postgres    false    223    7            N           0    0    TABLE imagen    COMMENT     W   COMMENT ON TABLE woaho.imagen IS 'Tabla que contiene las imagenes para el aplicativo';
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
       woaho         heap    postgres    false    229    7            O           0    0    TABLE medio_pago    COMMENT     b   COMMENT ON TABLE woaho.medio_pago IS 'Tabla que contiene los medios de pagos para el aplicativo';
          woaho          postgres    false    253            	           1259    17066    sec_medio_pago_usuario    SEQUENCE     �   CREATE SEQUENCE woaho.sec_medio_pago_usuario
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999
    CACHE 1
    CYCLE;
 ,   DROP SEQUENCE woaho.sec_medio_pago_usuario;
       woaho          postgres    false    7            
           1259    17068    medio_pago_usuario    TABLE     �  CREATE TABLE woaho.medio_pago_usuario (
    medio_pago_usuario_id integer DEFAULT nextval('woaho.sec_medio_pago_usuario'::regclass) NOT NULL,
    medio_pago_usuario_nombre character varying(4000),
    medio_pago_usuario_fecha_vencimiento character varying(4000),
    medio_pago_usuario_cvc character varying(4000),
    medio_pago_usuario_codigo character varying(4000),
    medio_pago_usuario_estado integer,
    medio_pago_usuario_usuario integer,
    medio_pago_usuario_medio_pago integer
);
 %   DROP TABLE woaho.medio_pago_usuario;
       woaho         heap    postgres    false    265    7            P           0    0    TABLE medio_pago_usuario    COMMENT     f   COMMENT ON TABLE woaho.medio_pago_usuario IS 'Tabla que contiene los medios de pago para el usuario';
          woaho          postgres    false    266            �            1259    16387    sec_mensaje    SEQUENCE     �   CREATE SEQUENCE woaho.sec_mensaje
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
       woaho         heap    postgres    false    203    7            Q           0    0    TABLE mensaje    COMMENT     e   COMMENT ON TABLE woaho.mensaje IS 'Tabla que contiene la información de los mensajes por pantalla';
          woaho          postgres    false    234                       1259    17093    sec_mensaje_correo    SEQUENCE     �   CREATE SEQUENCE woaho.sec_mensaje_correo
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999
    CACHE 1
    CYCLE;
 (   DROP SEQUENCE woaho.sec_mensaje_correo;
       woaho          postgres    false    7                       1259    17095    mensaje_correo    TABLE       CREATE TABLE woaho.mensaje_correo (
    mensaje_correo_id integer DEFAULT nextval('woaho.sec_mensaje_correo'::regclass) NOT NULL,
    mensaje_correo_codigo integer,
    mensaje_correo_mensaje character varying(4000),
    mensaje_correo_idioma character varying(4000)
);
 !   DROP TABLE woaho.mensaje_correo;
       woaho         heap    postgres    false    267    7            R           0    0    TABLE mensaje_correo    COMMENT     q   COMMENT ON TABLE woaho.mensaje_correo IS 'Tabla que contiene los mensajes que se envian por correo electronico';
          woaho          postgres    false    268            �            1259    16389    sec_mensaje_pantalla    SEQUENCE     �   CREATE SEQUENCE woaho.sec_mensaje_pantalla
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
       woaho         heap    postgres    false    204    7            S           0    0    TABLE mensaje_pantalla    COMMENT     h   COMMENT ON TABLE woaho.mensaje_pantalla IS 'Tabla que contiene la relacion entre mensajes y pantallas';
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
       woaho         heap    postgres    false    215    7            T           0    0    TABLE moneda    COMMENT     V   COMMENT ON TABLE woaho.moneda IS 'Tabla que contiene las monedas para el aplicativo';
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
       woaho         heap    postgres    false    205    7            U           0    0    TABLE pantalla    COMMENT     Z   COMMENT ON TABLE woaho.pantalla IS 'Tabla que contiene la información de las pantallas';
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
       woaho         heap    postgres    false    212    7            V           0    0    TABLE parametro    COMMENT     X   COMMENT ON TABLE woaho.parametro IS 'Tabla que contiene los parametros del aplicativo';
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
       woaho         heap    postgres    false    221    7            W           0    0    TABLE pedido    COMMENT     R   COMMENT ON TABLE woaho.pedido IS 'Tabla que contiene los pedidos del aplicativo';
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
       woaho         heap    postgres    false    227    7            X           0    0    TABLE profesion    COMMENT     l   COMMENT ON TABLE woaho.profesion IS 'Tabla que contiene la información de las profesiones del aplicativo';
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
       woaho         heap    postgres    false    222    7            Y           0    0    TABLE profesional    COMMENT     ]   COMMENT ON TABLE woaho.profesional IS 'Tabla que contiene los profesionales del aplicativo';
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
       woaho         heap    postgres    false    219    7            Z           0    0    TABLE promocion    COMMENT     Y   COMMENT ON TABLE woaho.promocion IS 'Tabla que contiene las promociones del aplicativo';
          woaho          postgres    false    248            �            1259    16413    sec_servicio    SEQUENCE     �   CREATE SEQUENCE woaho.sec_servicio
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999
    CACHE 1
    CYCLE;
 "   DROP SEQUENCE woaho.sec_servicio;
       woaho          postgres    false    7                       1259    17043    sec_servicio_favorito    SEQUENCE     �   CREATE SEQUENCE woaho.sec_servicio_favorito
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999
    CACHE 1
    CYCLE;
 +   DROP SEQUENCE woaho.sec_servicio_favorito;
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
       woaho         heap    postgres    false    216    7            [           0    0    TABLE servicio    COMMENT     Z   COMMENT ON TABLE woaho.servicio IS 'Tabla que contiene los servicios para el aplicativo';
          woaho          postgres    false    246                       1259    17045    servicio_favorito    TABLE     �   CREATE TABLE woaho.servicio_favorito (
    servicio_favorito_id integer DEFAULT nextval('woaho.sec_servicio_favorito'::regclass) NOT NULL,
    servicio_favorito_servicio integer,
    servicio_favorito_usuario integer
);
 $   DROP TABLE woaho.servicio_favorito;
       woaho         heap    postgres    false    263    7            \           0    0    TABLE servicio_favorito    COMMENT     f   COMMENT ON TABLE woaho.servicio_favorito IS 'Tabla que contiene los servicios favoritos del usuario';
          woaho          postgres    false    264            �            1259    16693    tarifa    TABLE     �   CREATE TABLE woaho.tarifa (
    tarifa_id integer DEFAULT nextval('woaho.sec_tarifa'::regclass) NOT NULL,
    tarifa_valor numeric,
    tarifa_moneda integer,
    tarifa_territorio integer,
    tarifa_servicio integer,
    tarifa_unidad integer
);
    DROP TABLE woaho.tarifa;
       woaho         heap    postgres    false    217    7            ]           0    0    TABLE tarifa    COMMENT     V   COMMENT ON TABLE woaho.tarifa IS 'Tabla que contiene las tarifas para el aplicativo';
          woaho          postgres    false    247            �            1259    16537 
   territorio    TABLE     0  CREATE TABLE woaho.territorio (
    territorio_id integer DEFAULT nextval('woaho.sec_territorio'::regclass) NOT NULL,
    territorio_nombre character varying(4000),
    territorio_padre integer,
    territorio_tipo integer,
    territorio_codigo character varying(4000),
    territorio_imagen integer
);
    DROP TABLE woaho.territorio;
       woaho         heap    postgres    false    207    7            ^           0    0    TABLE territorio    COMMENT     h   COMMENT ON TABLE woaho.territorio IS 'Tabla que contiene los territorios registrados en el aplicativo';
          woaho          postgres    false    236            �            1259    16443    tipo    TABLE     �   CREATE TABLE woaho.tipo (
    tipo_id integer DEFAULT nextval('woaho.sec_tipo'::regclass) NOT NULL,
    tipo_nombre character varying(4000)
);
    DROP TABLE woaho.tipo;
       woaho         heap    postgres    false    206    7            _           0    0 
   TABLE tipo    COMMENT     ^   COMMENT ON TABLE woaho.tipo IS 'Tabla que contiene la información de los tipos de pantalla';
          woaho          postgres    false    231            �            1259    16528    tipo_territorio    TABLE     �   CREATE TABLE woaho.tipo_territorio (
    tipo_territorio_id integer DEFAULT nextval('woaho.sec_tipo_territorio'::regclass) NOT NULL,
    tipo_territorio_nombre character varying(4000)
);
 "   DROP TABLE woaho.tipo_territorio;
       woaho         heap    postgres    false    208    7            `           0    0    TABLE tipo_territorio    COMMENT     Y   COMMENT ON TABLE woaho.tipo_territorio IS 'Tabla que contiene los tipos de territorios';
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
       woaho         heap    postgres    false    226    7            a           0    0    TABLE ubicacion    COMMENT     _   COMMENT ON TABLE woaho.ubicacion IS 'Tabla que contiene las ubicaciones de los profesionales';
          woaho          postgres    false    251            �            1259    16660    unidad_tarifa    TABLE     �   CREATE TABLE woaho.unidad_tarifa (
    unidad_tarifa_id integer DEFAULT nextval('woaho.sec_unidad_tarifa'::regclass) NOT NULL,
    unidad_tarifa_nombre character varying(4000)
);
     DROP TABLE woaho.unidad_tarifa;
       woaho         heap    postgres    false    220    7            b           0    0    TABLE unidad_tarifa    COMMENT     i   COMMENT ON TABLE woaho.unidad_tarifa IS 'Tabla que contiene las unidades de tarifas para el aplicativo';
          woaho          postgres    false    245            �            1259    16551    usuario    TABLE     �  CREATE TABLE woaho.usuario (
    usuario_id integer DEFAULT nextval('woaho.sec_usuario'::regclass) NOT NULL,
    usuario_nombre character varying(4000),
    usuario_apellido character varying(4000),
    usuario_celular character varying(4000),
    usuario_correo character varying(4000),
    usuario_acepta_terminos character varying(4000),
    usuario_fecha_hora_acepta_terminos timestamp without time zone,
    usuario_clave character varying(4000),
    usuario_id_suscriptor character varying(4000),
    usuario_referralcode character varying(4000),
    usuario_referral_code character varying(255),
    usuario_tipo integer DEFAULT 2
);
    DROP TABLE woaho.usuario;
       woaho         heap    postgres    false    209    7            c           0    0    TABLE usuario    COMMENT     b   COMMENT ON TABLE woaho.usuario IS 'Tabla que contiene los usuarios registrados en el aplicativo';
          woaho          postgres    false    237            ,          0    16799    calificacion 
   TABLE DATA                 woaho          postgres    false    252            /          0    16876    cancelacion 
   TABLE DATA                 woaho          postgres    false    255            #          0    16632 	   categoria 
   TABLE DATA                 woaho          postgres    false    243                      0    16571    codigo 
   TABLE DATA                 woaho          postgres    false    239            )          0    16741    codigo_promocional 
   TABLE DATA                 woaho          postgres    false    249                       0    16590 	   direccion 
   TABLE DATA                 woaho          postgres    false    240            6          0    17024    equivalencia_idioma 
   TABLE DATA                 woaho          postgres    false    262                      0    16562    estado 
   TABLE DATA                 woaho          postgres    false    238            2          0    16990    etiqueta 
   TABLE DATA                 woaho          postgres    false    258                      0    16452    idioma 
   TABLE DATA                 woaho          postgres    false    232            "          0    16623    imagen 
   TABLE DATA                 woaho          postgres    false    242            -          0    16823 
   medio_pago 
   TABLE DATA                 woaho          postgres    false    253            :          0    17068    medio_pago_usuario 
   TABLE DATA                 woaho          postgres    false    266                      0    16498    mensaje 
   TABLE DATA                 woaho          postgres    false    234            <          0    17095    mensaje_correo 
   TABLE DATA                 woaho          postgres    false    268            1          0    16909    mensaje_pantalla 
   TABLE DATA                 woaho          postgres    false    257            $          0    16646    moneda 
   TABLE DATA                 woaho          postgres    false    244            0          0    16890    pantalla 
   TABLE DATA                 woaho          postgres    false    256            !          0    16614 	   parametro 
   TABLE DATA                 woaho          postgres    false    241            .          0    16837    pedido 
   TABLE DATA                 woaho          postgres    false    254                      0    16475 	   profesion 
   TABLE DATA                 woaho          postgres    false    233            *          0    16766    profesional 
   TABLE DATA                 woaho          postgres    false    250            (          0    16722 	   promocion 
   TABLE DATA                 woaho          postgres    false    248            &          0    16669    servicio 
   TABLE DATA                 woaho          postgres    false    246            8          0    17045    servicio_favorito 
   TABLE DATA                 woaho          postgres    false    264            '          0    16693    tarifa 
   TABLE DATA                 woaho          postgres    false    247                      0    16537 
   territorio 
   TABLE DATA                 woaho          postgres    false    236                      0    16443    tipo 
   TABLE DATA                 woaho          postgres    false    231                      0    16528    tipo_territorio 
   TABLE DATA                 woaho          postgres    false    235            4          0    17006 
   traduccion 
   TABLE DATA                 woaho          woaho    false    260            +          0    16785 	   ubicacion 
   TABLE DATA                 woaho          postgres    false    251            %          0    16660    unidad_tarifa 
   TABLE DATA                 woaho          postgres    false    245                      0    16551    usuario 
   TABLE DATA                 woaho          postgres    false    237            d           0    0    sec_calificacion    SEQUENCE SET     =   SELECT pg_catalog.setval('woaho.sec_calificacion', 7, true);
          woaho          postgres    false    224            e           0    0    sec_cancelacion    SEQUENCE SET     =   SELECT pg_catalog.setval('woaho.sec_cancelacion', 1, false);
          woaho          postgres    false    230            f           0    0    sec_categoria    SEQUENCE SET     :   SELECT pg_catalog.setval('woaho.sec_categoria', 9, true);
          woaho          postgres    false    214            g           0    0 
   sec_codigo    SEQUENCE SET     9   SELECT pg_catalog.setval('woaho.sec_codigo', 277, true);
          woaho          postgres    false    211            h           0    0    sec_codigo_promocional    SEQUENCE SET     D   SELECT pg_catalog.setval('woaho.sec_codigo_promocional', 1, false);
          woaho          postgres    false    218            i           0    0    sec_direccion    SEQUENCE SET     ;   SELECT pg_catalog.setval('woaho.sec_direccion', 11, true);
          woaho          postgres    false    213            j           0    0    sec_equivalencia_idioma    SEQUENCE SET     E   SELECT pg_catalog.setval('woaho.sec_equivalencia_idioma', 61, true);
          woaho          postgres    false    261            k           0    0 
   sec_estado    SEQUENCE SET     7   SELECT pg_catalog.setval('woaho.sec_estado', 6, true);
          woaho          postgres    false    210            l           0    0    sec_etiqueta    SEQUENCE SET     :   SELECT pg_catalog.setval('woaho.sec_etiqueta', 40, true);
          woaho          postgres    false    228            m           0    0 
   sec_idioma    SEQUENCE SET     7   SELECT pg_catalog.setval('woaho.sec_idioma', 2, true);
          woaho          postgres    false    225            n           0    0 
   sec_imagen    SEQUENCE SET     8   SELECT pg_catalog.setval('woaho.sec_imagen', 32, true);
          woaho          postgres    false    223            o           0    0    sec_medio_pago    SEQUENCE SET     ;   SELECT pg_catalog.setval('woaho.sec_medio_pago', 3, true);
          woaho          postgres    false    229            p           0    0    sec_medio_pago_usuario    SEQUENCE SET     C   SELECT pg_catalog.setval('woaho.sec_medio_pago_usuario', 1, true);
          woaho          postgres    false    265            q           0    0    sec_mensaje    SEQUENCE SET     9   SELECT pg_catalog.setval('woaho.sec_mensaje', 36, true);
          woaho          postgres    false    203            r           0    0    sec_mensaje_correo    SEQUENCE SET     ?   SELECT pg_catalog.setval('woaho.sec_mensaje_correo', 2, true);
          woaho          postgres    false    267            s           0    0    sec_mensaje_pantalla    SEQUENCE SET     B   SELECT pg_catalog.setval('woaho.sec_mensaje_pantalla', 12, true);
          woaho          postgres    false    204            t           0    0 
   sec_moneda    SEQUENCE SET     7   SELECT pg_catalog.setval('woaho.sec_moneda', 3, true);
          woaho          postgres    false    215            u           0    0    sec_pantalla    SEQUENCE SET     9   SELECT pg_catalog.setval('woaho.sec_pantalla', 4, true);
          woaho          postgres    false    205            v           0    0    sec_parametro    SEQUENCE SET     ;   SELECT pg_catalog.setval('woaho.sec_parametro', 10, true);
          woaho          postgres    false    212            w           0    0 
   sec_pedido    SEQUENCE SET     7   SELECT pg_catalog.setval('woaho.sec_pedido', 4, true);
          woaho          postgres    false    221            x           0    0    sec_profesion    SEQUENCE SET     :   SELECT pg_catalog.setval('woaho.sec_profesion', 3, true);
          woaho          postgres    false    227            y           0    0    sec_profesional    SEQUENCE SET     <   SELECT pg_catalog.setval('woaho.sec_profesional', 1, true);
          woaho          postgres    false    222            z           0    0    sec_promocion    SEQUENCE SET     ;   SELECT pg_catalog.setval('woaho.sec_promocion', 1, false);
          woaho          postgres    false    219            {           0    0    sec_servicio    SEQUENCE SET     9   SELECT pg_catalog.setval('woaho.sec_servicio', 3, true);
          woaho          postgres    false    216            |           0    0    sec_servicio_favorito    SEQUENCE SET     B   SELECT pg_catalog.setval('woaho.sec_servicio_favorito', 3, true);
          woaho          postgres    false    263            }           0    0 
   sec_tarifa    SEQUENCE SET     8   SELECT pg_catalog.setval('woaho.sec_tarifa', 12, true);
          woaho          postgres    false    217            ~           0    0    sec_territorio    SEQUENCE SET     ;   SELECT pg_catalog.setval('woaho.sec_territorio', 7, true);
          woaho          woaho    false    207                       0    0    sec_tipo    SEQUENCE SET     5   SELECT pg_catalog.setval('woaho.sec_tipo', 5, true);
          woaho          postgres    false    206            �           0    0    sec_tipo_territorio    SEQUENCE SET     @   SELECT pg_catalog.setval('woaho.sec_tipo_territorio', 7, true);
          woaho          postgres    false    208            �           0    0    sec_traduccion    SEQUENCE SET     <   SELECT pg_catalog.setval('woaho.sec_traduccion', 26, true);
          woaho          postgres    false    259            �           0    0    sec_ubicacion    SEQUENCE SET     :   SELECT pg_catalog.setval('woaho.sec_ubicacion', 2, true);
          woaho          postgres    false    226            �           0    0    sec_unidad_tarifa    SEQUENCE SET     >   SELECT pg_catalog.setval('woaho.sec_unidad_tarifa', 5, true);
          woaho          postgres    false    220            �           0    0    sec_usuario    SEQUENCE SET     9   SELECT pg_catalog.setval('woaho.sec_usuario', 62, true);
          woaho          postgres    false    209            6           2606    16807    calificacion calificacion_pkey 
   CONSTRAINT     h   ALTER TABLE ONLY woaho.calificacion
    ADD CONSTRAINT calificacion_pkey PRIMARY KEY (calificacion_id);
 G   ALTER TABLE ONLY woaho.calificacion DROP CONSTRAINT calificacion_pkey;
       woaho            postgres    false    252            <           2606    16884    cancelacion cancelacion_pkey 
   CONSTRAINT     e   ALTER TABLE ONLY woaho.cancelacion
    ADD CONSTRAINT cancelacion_pkey PRIMARY KEY (cancelacion_id);
 E   ALTER TABLE ONLY woaho.cancelacion DROP CONSTRAINT cancelacion_pkey;
       woaho            postgres    false    255            $           2606    16640    categoria categoria_pkey 
   CONSTRAINT     _   ALTER TABLE ONLY woaho.categoria
    ADD CONSTRAINT categoria_pkey PRIMARY KEY (categoria_id);
 A   ALTER TABLE ONLY woaho.categoria DROP CONSTRAINT categoria_pkey;
       woaho            postgres    false    243                       2606    16561    usuario celular_key 
   CONSTRAINT     X   ALTER TABLE ONLY woaho.usuario
    ADD CONSTRAINT celular_key UNIQUE (usuario_celular);
 <   ALTER TABLE ONLY woaho.usuario DROP CONSTRAINT celular_key;
       woaho            postgres    false    237                       2606    16579    codigo codigo_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY woaho.codigo
    ADD CONSTRAINT codigo_pkey PRIMARY KEY (codigo_id);
 ;   ALTER TABLE ONLY woaho.codigo DROP CONSTRAINT codigo_pkey;
       woaho            postgres    false    239            0           2606    16749 *   codigo_promocional codigo_promocional_pkey 
   CONSTRAINT     z   ALTER TABLE ONLY woaho.codigo_promocional
    ADD CONSTRAINT codigo_promocional_pkey PRIMARY KEY (codigo_promocional_id);
 S   ALTER TABLE ONLY woaho.codigo_promocional DROP CONSTRAINT codigo_promocional_pkey;
       woaho            postgres    false    249                       2606    16598    direccion direccion_pkey 
   CONSTRAINT     _   ALTER TABLE ONLY woaho.direccion
    ADD CONSTRAINT direccion_pkey PRIMARY KEY (direccion_id);
 A   ALTER TABLE ONLY woaho.direccion DROP CONSTRAINT direccion_pkey;
       woaho            postgres    false    240            F           2606    17032 ,   equivalencia_idioma equivalencia_idioma_pkey 
   CONSTRAINT     }   ALTER TABLE ONLY woaho.equivalencia_idioma
    ADD CONSTRAINT equivalencia_idioma_pkey PRIMARY KEY (equivalencia_idioma_id);
 U   ALTER TABLE ONLY woaho.equivalencia_idioma DROP CONSTRAINT equivalencia_idioma_pkey;
       woaho            postgres    false    262                       2606    16570    estado estado_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY woaho.estado
    ADD CONSTRAINT estado_pkey PRIMARY KEY (estado_id);
 ;   ALTER TABLE ONLY woaho.estado DROP CONSTRAINT estado_pkey;
       woaho            postgres    false    238            B           2606    16998    etiqueta etiqueta_pkey 
   CONSTRAINT     \   ALTER TABLE ONLY woaho.etiqueta
    ADD CONSTRAINT etiqueta_pkey PRIMARY KEY (etiqueta_id);
 ?   ALTER TABLE ONLY woaho.etiqueta DROP CONSTRAINT etiqueta_pkey;
       woaho            postgres    false    258                       2606    16460    idioma idioma_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY woaho.idioma
    ADD CONSTRAINT idioma_pkey PRIMARY KEY (idioma_id);
 ;   ALTER TABLE ONLY woaho.idioma DROP CONSTRAINT idioma_pkey;
       woaho            postgres    false    232            "           2606    16631    imagen imagen_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY woaho.imagen
    ADD CONSTRAINT imagen_pkey PRIMARY KEY (imagen_id);
 ;   ALTER TABLE ONLY woaho.imagen DROP CONSTRAINT imagen_pkey;
       woaho            postgres    false    242            8           2606    16831    medio_pago medio_pago_pkey 
   CONSTRAINT     b   ALTER TABLE ONLY woaho.medio_pago
    ADD CONSTRAINT medio_pago_pkey PRIMARY KEY (medio_pago_id);
 C   ALTER TABLE ONLY woaho.medio_pago DROP CONSTRAINT medio_pago_pkey;
       woaho            postgres    false    253            J           2606    17076 *   medio_pago_usuario medio_pago_usuario_pkey 
   CONSTRAINT     z   ALTER TABLE ONLY woaho.medio_pago_usuario
    ADD CONSTRAINT medio_pago_usuario_pkey PRIMARY KEY (medio_pago_usuario_id);
 S   ALTER TABLE ONLY woaho.medio_pago_usuario DROP CONSTRAINT medio_pago_usuario_pkey;
       woaho            postgres    false    266            L           2606    17103 "   mensaje_correo mensaje_correo_pkey 
   CONSTRAINT     n   ALTER TABLE ONLY woaho.mensaje_correo
    ADD CONSTRAINT mensaje_correo_pkey PRIMARY KEY (mensaje_correo_id);
 K   ALTER TABLE ONLY woaho.mensaje_correo DROP CONSTRAINT mensaje_correo_pkey;
       woaho            postgres    false    268            @           2606    16914 &   mensaje_pantalla mensaje_pantalla_pkey 
   CONSTRAINT     t   ALTER TABLE ONLY woaho.mensaje_pantalla
    ADD CONSTRAINT mensaje_pantalla_pkey PRIMARY KEY (mensaje_pantalla_id);
 O   ALTER TABLE ONLY woaho.mensaje_pantalla DROP CONSTRAINT mensaje_pantalla_pkey;
       woaho            postgres    false    257                       2606    16506    mensaje mensaje_pkey 
   CONSTRAINT     Y   ALTER TABLE ONLY woaho.mensaje
    ADD CONSTRAINT mensaje_pkey PRIMARY KEY (mensaje_id);
 =   ALTER TABLE ONLY woaho.mensaje DROP CONSTRAINT mensaje_pkey;
       woaho            postgres    false    234            &           2606    16654    moneda moneda_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY woaho.moneda
    ADD CONSTRAINT moneda_pkey PRIMARY KEY (moneda_id);
 ;   ALTER TABLE ONLY woaho.moneda DROP CONSTRAINT moneda_pkey;
       woaho            postgres    false    244            >           2606    16898    pantalla pantalla_pkey 
   CONSTRAINT     \   ALTER TABLE ONLY woaho.pantalla
    ADD CONSTRAINT pantalla_pkey PRIMARY KEY (pantalla_id);
 ?   ALTER TABLE ONLY woaho.pantalla DROP CONSTRAINT pantalla_pkey;
       woaho            postgres    false    256                        2606    16622    parametro parametro_pkey 
   CONSTRAINT     _   ALTER TABLE ONLY woaho.parametro
    ADD CONSTRAINT parametro_pkey PRIMARY KEY (parametro_id);
 A   ALTER TABLE ONLY woaho.parametro DROP CONSTRAINT parametro_pkey;
       woaho            postgres    false    241            :           2606    16845    pedido pedido_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY woaho.pedido
    ADD CONSTRAINT pedido_pkey PRIMARY KEY (pedido_id);
 ;   ALTER TABLE ONLY woaho.pedido DROP CONSTRAINT pedido_pkey;
       woaho            postgres    false    254                       2606    16483    profesion profesion_pkey 
   CONSTRAINT     _   ALTER TABLE ONLY woaho.profesion
    ADD CONSTRAINT profesion_pkey PRIMARY KEY (profesion_id);
 A   ALTER TABLE ONLY woaho.profesion DROP CONSTRAINT profesion_pkey;
       woaho            postgres    false    233            2           2606    16774    profesional profesional_pkey 
   CONSTRAINT     e   ALTER TABLE ONLY woaho.profesional
    ADD CONSTRAINT profesional_pkey PRIMARY KEY (profesional_id);
 E   ALTER TABLE ONLY woaho.profesional DROP CONSTRAINT profesional_pkey;
       woaho            postgres    false    250            .           2606    16730    promocion promocion_pkey 
   CONSTRAINT     _   ALTER TABLE ONLY woaho.promocion
    ADD CONSTRAINT promocion_pkey PRIMARY KEY (promocion_id);
 A   ALTER TABLE ONLY woaho.promocion DROP CONSTRAINT promocion_pkey;
       woaho            postgres    false    248            H           2606    17050 (   servicio_favorito servicio_favorito_pkey 
   CONSTRAINT     w   ALTER TABLE ONLY woaho.servicio_favorito
    ADD CONSTRAINT servicio_favorito_pkey PRIMARY KEY (servicio_favorito_id);
 Q   ALTER TABLE ONLY woaho.servicio_favorito DROP CONSTRAINT servicio_favorito_pkey;
       woaho            postgres    false    264            *           2606    16677    servicio servicio_pkey 
   CONSTRAINT     \   ALTER TABLE ONLY woaho.servicio
    ADD CONSTRAINT servicio_pkey PRIMARY KEY (servicio_id);
 ?   ALTER TABLE ONLY woaho.servicio DROP CONSTRAINT servicio_pkey;
       woaho            postgres    false    246            ,           2606    16701    tarifa tarifa_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY woaho.tarifa
    ADD CONSTRAINT tarifa_pkey PRIMARY KEY (tarifa_id);
 ;   ALTER TABLE ONLY woaho.tarifa DROP CONSTRAINT tarifa_pkey;
       woaho            postgres    false    247                       2606    16545    territorio territorio_pkey 
   CONSTRAINT     b   ALTER TABLE ONLY woaho.territorio
    ADD CONSTRAINT territorio_pkey PRIMARY KEY (territorio_id);
 C   ALTER TABLE ONLY woaho.territorio DROP CONSTRAINT territorio_pkey;
       woaho            postgres    false    236            
           2606    16451    tipo tipo_pantalla_pkey 
   CONSTRAINT     Y   ALTER TABLE ONLY woaho.tipo
    ADD CONSTRAINT tipo_pantalla_pkey PRIMARY KEY (tipo_id);
 @   ALTER TABLE ONLY woaho.tipo DROP CONSTRAINT tipo_pantalla_pkey;
       woaho            postgres    false    231                       2606    16536 $   tipo_territorio tipo_territorio_pkey 
   CONSTRAINT     q   ALTER TABLE ONLY woaho.tipo_territorio
    ADD CONSTRAINT tipo_territorio_pkey PRIMARY KEY (tipo_territorio_id);
 M   ALTER TABLE ONLY woaho.tipo_territorio DROP CONSTRAINT tipo_territorio_pkey;
       woaho            postgres    false    235            D           2606    17014    traduccion traduccion_pkey 
   CONSTRAINT     b   ALTER TABLE ONLY woaho.traduccion
    ADD CONSTRAINT traduccion_pkey PRIMARY KEY (traduccion_id);
 C   ALTER TABLE ONLY woaho.traduccion DROP CONSTRAINT traduccion_pkey;
       woaho            woaho    false    260            4           2606    16793    ubicacion ubicacion_pkey 
   CONSTRAINT     _   ALTER TABLE ONLY woaho.ubicacion
    ADD CONSTRAINT ubicacion_pkey PRIMARY KEY (ubicacion_id);
 A   ALTER TABLE ONLY woaho.ubicacion DROP CONSTRAINT ubicacion_pkey;
       woaho            postgres    false    251            (           2606    16668     unidad_tarifa unidad_tarifa_pkey 
   CONSTRAINT     k   ALTER TABLE ONLY woaho.unidad_tarifa
    ADD CONSTRAINT unidad_tarifa_pkey PRIMARY KEY (unidad_tarifa_id);
 I   ALTER TABLE ONLY woaho.unidad_tarifa DROP CONSTRAINT unidad_tarifa_pkey;
       woaho            postgres    false    245                       2606    16559    usuario usuario_pkey 
   CONSTRAINT     Y   ALTER TABLE ONLY woaho.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (usuario_id);
 =   ALTER TABLE ONLY woaho.usuario DROP CONSTRAINT usuario_pkey;
       woaho            postgres    false    237            g           2606    16813 (   calificacion FK_CALIFICACION_PROFESIONAL    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.calificacion
    ADD CONSTRAINT "FK_CALIFICACION_PROFESIONAL" FOREIGN KEY (calificacion_profesional) REFERENCES woaho.profesional(profesional_id);
 S   ALTER TABLE ONLY woaho.calificacion DROP CONSTRAINT "FK_CALIFICACION_PROFESIONAL";
       woaho          postgres    false    250    3122    252            h           2606    16818 %   calificacion FK_CALIFICACION_SERVICIO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.calificacion
    ADD CONSTRAINT "FK_CALIFICACION_SERVICIO" FOREIGN KEY (calificacion_servicio) REFERENCES woaho.servicio(servicio_id);
 P   ALTER TABLE ONLY woaho.calificacion DROP CONSTRAINT "FK_CALIFICACION_SERVICIO";
       woaho          postgres    false    246    3114    252            f           2606    16808 $   calificacion FK_CALIFICACION_USUARIO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.calificacion
    ADD CONSTRAINT "FK_CALIFICACION_USUARIO" FOREIGN KEY (calificacion_usuario) REFERENCES woaho.usuario(usuario_id);
 O   ALTER TABLE ONLY woaho.calificacion DROP CONSTRAINT "FK_CALIFICACION_USUARIO";
       woaho          postgres    false    3096    252    237            q           2606    16885 !   cancelacion FK_CANCELACION_PEDIDO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.cancelacion
    ADD CONSTRAINT "FK_CANCELACION_PEDIDO" FOREIGN KEY (cancelacion_pedido) REFERENCES woaho.pedido(pedido_id);
 L   ALTER TABLE ONLY woaho.cancelacion DROP CONSTRAINT "FK_CANCELACION_PEDIDO";
       woaho          postgres    false    255    3130    254            U           2606    16641    categoria FK_CATEGORIA_IMAGEN    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.categoria
    ADD CONSTRAINT "FK_CATEGORIA_IMAGEN" FOREIGN KEY (categoria_imagen) REFERENCES woaho.imagen(imagen_id);
 H   ALTER TABLE ONLY woaho.categoria DROP CONSTRAINT "FK_CATEGORIA_IMAGEN";
       woaho          postgres    false    242    3106    243            Q           2606    16585    codigo FK_CODIGO_ESTADO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.codigo
    ADD CONSTRAINT "FK_CODIGO_ESTADO" FOREIGN KEY (codigo_estado) REFERENCES woaho.estado(estado_id);
 B   ALTER TABLE ONLY woaho.codigo DROP CONSTRAINT "FK_CODIGO_ESTADO";
       woaho          postgres    false    3098    238    239            a           2606    16755 %   codigo_promocional FK_COD_PROM_ESTADO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.codigo_promocional
    ADD CONSTRAINT "FK_COD_PROM_ESTADO" FOREIGN KEY (codigo_promocional_estado) REFERENCES woaho.estado(estado_id);
 P   ALTER TABLE ONLY woaho.codigo_promocional DROP CONSTRAINT "FK_COD_PROM_ESTADO";
       woaho          postgres    false    3098    249    238            b           2606    16760 (   codigo_promocional FK_COD_PROM_PROMOCION    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.codigo_promocional
    ADD CONSTRAINT "FK_COD_PROM_PROMOCION" FOREIGN KEY (codigo_promocional_promocion) REFERENCES woaho.promocion(promocion_id);
 S   ALTER TABLE ONLY woaho.codigo_promocional DROP CONSTRAINT "FK_COD_PROM_PROMOCION";
       woaho          postgres    false    249    248    3118            `           2606    16750 &   codigo_promocional FK_COD_PROM_USUARIO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.codigo_promocional
    ADD CONSTRAINT "FK_COD_PROM_USUARIO" FOREIGN KEY (codigo_promocional_usuario) REFERENCES woaho.usuario(usuario_id);
 Q   ALTER TABLE ONLY woaho.codigo_promocional DROP CONSTRAINT "FK_COD_PROM_USUARIO";
       woaho          postgres    false    3096    249    237            T           2606    16609    direccion FK_DIRECCION_ESTADO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.direccion
    ADD CONSTRAINT "FK_DIRECCION_ESTADO" FOREIGN KEY (direccion_estado) REFERENCES woaho.estado(estado_id);
 H   ALTER TABLE ONLY woaho.direccion DROP CONSTRAINT "FK_DIRECCION_ESTADO";
       woaho          postgres    false    3098    238    240            R           2606    16599 !   direccion FK_DIRECCION_TERRITORIO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.direccion
    ADD CONSTRAINT "FK_DIRECCION_TERRITORIO" FOREIGN KEY (direccion_territorio_id) REFERENCES woaho.territorio(territorio_id);
 L   ALTER TABLE ONLY woaho.direccion DROP CONSTRAINT "FK_DIRECCION_TERRITORIO";
       woaho          postgres    false    240    3092    236            S           2606    16604    direccion FK_DIRECCION_USUARIO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.direccion
    ADD CONSTRAINT "FK_DIRECCION_USUARIO" FOREIGN KEY (direccion_usuario) REFERENCES woaho.usuario(usuario_id);
 I   ALTER TABLE ONLY woaho.direccion DROP CONSTRAINT "FK_DIRECCION_USUARIO";
       woaho          postgres    false    237    3096    240            v           2606    16999    etiqueta FK_ETIQUETA_IDIOMA    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.etiqueta
    ADD CONSTRAINT "FK_ETIQUETA_IDIOMA" FOREIGN KEY (etiqueta_idioma) REFERENCES woaho.idioma(idioma_id);
 F   ALTER TABLE ONLY woaho.etiqueta DROP CONSTRAINT "FK_ETIQUETA_IDIOMA";
       woaho          postgres    false    258    232    3084            x           2606    17051 &   servicio_favorito FK_FAVORITO_SERVICIO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.servicio_favorito
    ADD CONSTRAINT "FK_FAVORITO_SERVICIO" FOREIGN KEY (servicio_favorito_servicio) REFERENCES woaho.servicio(servicio_id);
 Q   ALTER TABLE ONLY woaho.servicio_favorito DROP CONSTRAINT "FK_FAVORITO_SERVICIO";
       woaho          postgres    false    264    246    3114            y           2606    17056 %   servicio_favorito FK_FAVORITO_USUARIO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.servicio_favorito
    ADD CONSTRAINT "FK_FAVORITO_USUARIO" FOREIGN KEY (servicio_favorito_usuario) REFERENCES woaho.usuario(usuario_id);
 P   ALTER TABLE ONLY woaho.servicio_favorito DROP CONSTRAINT "FK_FAVORITO_USUARIO";
       woaho          postgres    false    237    3096    264            i           2606    16832 #   medio_pago FK_MEDIO_PAGO_TERRITORIO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.medio_pago
    ADD CONSTRAINT "FK_MEDIO_PAGO_TERRITORIO" FOREIGN KEY (medio_pago_territorio) REFERENCES woaho.territorio(territorio_id);
 N   ALTER TABLE ONLY woaho.medio_pago DROP CONSTRAINT "FK_MEDIO_PAGO_TERRITORIO";
       woaho          postgres    false    3092    253    236            z           2606    17077 /   medio_pago_usuario FK_MEDIO_PAGO_USUARIO_ESTADO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.medio_pago_usuario
    ADD CONSTRAINT "FK_MEDIO_PAGO_USUARIO_ESTADO" FOREIGN KEY (medio_pago_usuario_estado) REFERENCES woaho.estado(estado_id);
 Z   ALTER TABLE ONLY woaho.medio_pago_usuario DROP CONSTRAINT "FK_MEDIO_PAGO_USUARIO_ESTADO";
       woaho          postgres    false    3098    266    238            |           2606    17087 3   medio_pago_usuario FK_MEDIO_PAGO_USUARIO_MEDIO_PAGO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.medio_pago_usuario
    ADD CONSTRAINT "FK_MEDIO_PAGO_USUARIO_MEDIO_PAGO" FOREIGN KEY (medio_pago_usuario_medio_pago) REFERENCES woaho.medio_pago(medio_pago_id);
 ^   ALTER TABLE ONLY woaho.medio_pago_usuario DROP CONSTRAINT "FK_MEDIO_PAGO_USUARIO_MEDIO_PAGO";
       woaho          postgres    false    3128    266    253            {           2606    17082 0   medio_pago_usuario FK_MEDIO_PAGO_USUARIO_USUARIO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.medio_pago_usuario
    ADD CONSTRAINT "FK_MEDIO_PAGO_USUARIO_USUARIO" FOREIGN KEY (medio_pago_usuario_usuario) REFERENCES woaho.usuario(usuario_id);
 [   ALTER TABLE ONLY woaho.medio_pago_usuario DROP CONSTRAINT "FK_MEDIO_PAGO_USUARIO_USUARIO";
       woaho          postgres    false    266    237    3096            t           2606    16915 ,   mensaje_pantalla FK_MENSAJE_PANTALLA_MENSAJE    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.mensaje_pantalla
    ADD CONSTRAINT "FK_MENSAJE_PANTALLA_MENSAJE" FOREIGN KEY (mensaje_pantalla_mensaje_id) REFERENCES woaho.mensaje(mensaje_id);
 W   ALTER TABLE ONLY woaho.mensaje_pantalla DROP CONSTRAINT "FK_MENSAJE_PANTALLA_MENSAJE";
       woaho          postgres    false    3088    234    257            u           2606    16920 -   mensaje_pantalla FK_MENSAJE_PANTALLA_PANTALLA    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.mensaje_pantalla
    ADD CONSTRAINT "FK_MENSAJE_PANTALLA_PANTALLA" FOREIGN KEY (mensaje_pantalla_pantalla_id) REFERENCES woaho.pantalla(pantalla_id);
 X   ALTER TABLE ONLY woaho.mensaje_pantalla DROP CONSTRAINT "FK_MENSAJE_PANTALLA_PANTALLA";
       woaho          postgres    false    256    257    3134            M           2606    16507    mensaje FK_MENSAJE_TIPO    FK CONSTRAINT        ALTER TABLE ONLY woaho.mensaje
    ADD CONSTRAINT "FK_MENSAJE_TIPO" FOREIGN KEY (mensaje_tipo) REFERENCES woaho.tipo(tipo_id);
 B   ALTER TABLE ONLY woaho.mensaje DROP CONSTRAINT "FK_MENSAJE_TIPO";
       woaho          postgres    false    3082    234    231            V           2606    16655    moneda FK_MONEDA_TERRITORIO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.moneda
    ADD CONSTRAINT "FK_MONEDA_TERRITORIO" FOREIGN KEY (moneda_territorio) REFERENCES woaho.territorio(territorio_id);
 F   ALTER TABLE ONLY woaho.moneda DROP CONSTRAINT "FK_MONEDA_TERRITORIO";
       woaho          postgres    false    244    3092    236            s           2606    16904    pantalla FK_PANTALLA_IMAGEN    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.pantalla
    ADD CONSTRAINT "FK_PANTALLA_IMAGEN" FOREIGN KEY (pantalla_imagen) REFERENCES woaho.imagen(imagen_id);
 F   ALTER TABLE ONLY woaho.pantalla DROP CONSTRAINT "FK_PANTALLA_IMAGEN";
       woaho          postgres    false    242    256    3106            r           2606    16899 !   pantalla FK_PANTALLA_TIPO_PANTLLA    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.pantalla
    ADD CONSTRAINT "FK_PANTALLA_TIPO_PANTLLA" FOREIGN KEY (pantalla_tipo_pantalla) REFERENCES woaho.tipo(tipo_id);
 L   ALTER TABLE ONLY woaho.pantalla DROP CONSTRAINT "FK_PANTALLA_TIPO_PANTLLA";
       woaho          postgres    false    3082    256    231            m           2606    16861    pedido FK_PEDIDO_DIRECCION    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.pedido
    ADD CONSTRAINT "FK_PEDIDO_DIRECCION" FOREIGN KEY (pedido_direccion) REFERENCES woaho.direccion(direccion_id);
 E   ALTER TABLE ONLY woaho.pedido DROP CONSTRAINT "FK_PEDIDO_DIRECCION";
       woaho          postgres    false    240    254    3102            l           2606    16856    pedido FK_PEDIDO_ESTADO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.pedido
    ADD CONSTRAINT "FK_PEDIDO_ESTADO" FOREIGN KEY (pedido_estado) REFERENCES woaho.estado(estado_id);
 B   ALTER TABLE ONLY woaho.pedido DROP CONSTRAINT "FK_PEDIDO_ESTADO";
       woaho          postgres    false    254    238    3098            o           2606    16871    pedido FK_PEDIDO_MEDIO_PAGO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.pedido
    ADD CONSTRAINT "FK_PEDIDO_MEDIO_PAGO" FOREIGN KEY (pedido_medio_pago) REFERENCES woaho.medio_pago(medio_pago_id);
 F   ALTER TABLE ONLY woaho.pedido DROP CONSTRAINT "FK_PEDIDO_MEDIO_PAGO";
       woaho          postgres    false    254    253    3128            n           2606    16866    pedido FK_PEDIDO_PROFESIONAL    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.pedido
    ADD CONSTRAINT "FK_PEDIDO_PROFESIONAL" FOREIGN KEY (pedido_profesional) REFERENCES woaho.profesional(profesional_id);
 G   ALTER TABLE ONLY woaho.pedido DROP CONSTRAINT "FK_PEDIDO_PROFESIONAL";
       woaho          postgres    false    250    3122    254            j           2606    16846    pedido FK_PEDIDO_SERVICIO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.pedido
    ADD CONSTRAINT "FK_PEDIDO_SERVICIO" FOREIGN KEY (pedido_servicio) REFERENCES woaho.servicio(servicio_id);
 D   ALTER TABLE ONLY woaho.pedido DROP CONSTRAINT "FK_PEDIDO_SERVICIO";
       woaho          postgres    false    254    246    3114            k           2606    16851    pedido FK_PEDIDO_USUARIO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.pedido
    ADD CONSTRAINT "FK_PEDIDO_USUARIO" FOREIGN KEY (pedido_servicio) REFERENCES woaho.usuario(usuario_id);
 C   ALTER TABLE ONLY woaho.pedido DROP CONSTRAINT "FK_PEDIDO_USUARIO";
       woaho          postgres    false    237    254    3096            d           2606    16780     profesional FK_PROFESIONAL_ICONO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.profesional
    ADD CONSTRAINT "FK_PROFESIONAL_ICONO" FOREIGN KEY (profesional_imagen_icono) REFERENCES woaho.imagen(imagen_id);
 K   ALTER TABLE ONLY woaho.profesional DROP CONSTRAINT "FK_PROFESIONAL_ICONO";
       woaho          postgres    false    250    3106    242            c           2606    16775 %   profesional FK_PROFESIONAL_TERRITORIO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.profesional
    ADD CONSTRAINT "FK_PROFESIONAL_TERRITORIO" FOREIGN KEY (profesional_nacionalidad) REFERENCES woaho.territorio(territorio_id);
 P   ALTER TABLE ONLY woaho.profesional DROP CONSTRAINT "FK_PROFESIONAL_TERRITORIO";
       woaho          postgres    false    3092    250    236            _           2606    16736    promocion FK_PROM_ESTADO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.promocion
    ADD CONSTRAINT "FK_PROM_ESTADO" FOREIGN KEY (promocion_estado) REFERENCES woaho.estado(estado_id);
 C   ALTER TABLE ONLY woaho.promocion DROP CONSTRAINT "FK_PROM_ESTADO";
       woaho          postgres    false    248    238    3098            ^           2606    16731    promocion FK_PROM_TARIFA    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.promocion
    ADD CONSTRAINT "FK_PROM_TARIFA" FOREIGN KEY (promocion_tarifa) REFERENCES woaho.tarifa(tarifa_id);
 C   ALTER TABLE ONLY woaho.promocion DROP CONSTRAINT "FK_PROM_TARIFA";
       woaho          postgres    false    3116    247    248            W           2606    16678    servicio FK_SERVICIO_CATEGORIA    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.servicio
    ADD CONSTRAINT "FK_SERVICIO_CATEGORIA" FOREIGN KEY (servicio_categoria) REFERENCES woaho.categoria(categoria_id);
 I   ALTER TABLE ONLY woaho.servicio DROP CONSTRAINT "FK_SERVICIO_CATEGORIA";
       woaho          postgres    false    3108    243    246            X           2606    16683    servicio FK_SERVICIO_IMAGEN    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.servicio
    ADD CONSTRAINT "FK_SERVICIO_IMAGEN" FOREIGN KEY (servicio_imagen) REFERENCES woaho.imagen(imagen_id);
 F   ALTER TABLE ONLY woaho.servicio DROP CONSTRAINT "FK_SERVICIO_IMAGEN";
       woaho          postgres    false    242    246    3106            Y           2606    16688    servicio FK_SERVICIO_TERRITORIO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.servicio
    ADD CONSTRAINT "FK_SERVICIO_TERRITORIO" FOREIGN KEY (servicio_territorio) REFERENCES woaho.territorio(territorio_id);
 J   ALTER TABLE ONLY woaho.servicio DROP CONSTRAINT "FK_SERVICIO_TERRITORIO";
       woaho          postgres    false    3092    236    246            Z           2606    16702    tarifa FK_TARIFA_MONEDA    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.tarifa
    ADD CONSTRAINT "FK_TARIFA_MONEDA" FOREIGN KEY (tarifa_moneda) REFERENCES woaho.moneda(moneda_id);
 B   ALTER TABLE ONLY woaho.tarifa DROP CONSTRAINT "FK_TARIFA_MONEDA";
       woaho          postgres    false    247    244    3110            \           2606    16712    tarifa FK_TARIFA_SERVICIO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.tarifa
    ADD CONSTRAINT "FK_TARIFA_SERVICIO" FOREIGN KEY (tarifa_servicio) REFERENCES woaho.servicio(servicio_id);
 D   ALTER TABLE ONLY woaho.tarifa DROP CONSTRAINT "FK_TARIFA_SERVICIO";
       woaho          postgres    false    3114    247    246            [           2606    16707    tarifa FK_TARIFA_TERRITORIO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.tarifa
    ADD CONSTRAINT "FK_TARIFA_TERRITORIO" FOREIGN KEY (tarifa_territorio) REFERENCES woaho.territorio(territorio_id);
 F   ALTER TABLE ONLY woaho.tarifa DROP CONSTRAINT "FK_TARIFA_TERRITORIO";
       woaho          postgres    false    3092    236    247            ]           2606    16717    tarifa FK_TARIFA_UNIDAD    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.tarifa
    ADD CONSTRAINT "FK_TARIFA_UNIDAD" FOREIGN KEY (tarifa_unidad) REFERENCES woaho.unidad_tarifa(unidad_tarifa_id);
 B   ALTER TABLE ONLY woaho.tarifa DROP CONSTRAINT "FK_TARIFA_UNIDAD";
       woaho          postgres    false    247    3112    245            N           2606    16546 (   territorio FK_TERRITORIO_TIPO_TERRITORIO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.territorio
    ADD CONSTRAINT "FK_TERRITORIO_TIPO_TERRITORIO" FOREIGN KEY (territorio_tipo) REFERENCES woaho.tipo_territorio(tipo_territorio_id);
 S   ALTER TABLE ONLY woaho.territorio DROP CONSTRAINT "FK_TERRITORIO_TIPO_TERRITORIO";
       woaho          postgres    false    3090    236    235            w           2606    17015    traduccion FK_TRADUCCION_IDIOMA    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.traduccion
    ADD CONSTRAINT "FK_TRADUCCION_IDIOMA" FOREIGN KEY (traduccion_idioma) REFERENCES woaho.idioma(idioma_id);
 J   ALTER TABLE ONLY woaho.traduccion DROP CONSTRAINT "FK_TRADUCCION_IDIOMA";
       woaho          woaho    false    232    260    3084            e           2606    16794 "   ubicacion FK_UBICACION_PROFESIONAL    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.ubicacion
    ADD CONSTRAINT "FK_UBICACION_PROFESIONAL" FOREIGN KEY (ubicacion_profesional) REFERENCES woaho.profesional(profesional_id);
 M   ALTER TABLE ONLY woaho.ubicacion DROP CONSTRAINT "FK_UBICACION_PROFESIONAL";
       woaho          postgres    false    3122    251    250            O           2606    16930 &   territorio fk1wg9n3xubkio4n3y16testobd    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.territorio
    ADD CONSTRAINT fk1wg9n3xubkio4n3y16testobd FOREIGN KEY (territorio_padre) REFERENCES woaho.territorio(territorio_id);
 O   ALTER TABLE ONLY woaho.territorio DROP CONSTRAINT fk1wg9n3xubkio4n3y16testobd;
       woaho          postgres    false    236    3092    236            P           2606    17061 &   territorio fkn3m1oitow4b70wae6i40gd87y    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.territorio
    ADD CONSTRAINT fkn3m1oitow4b70wae6i40gd87y FOREIGN KEY (territorio_imagen) REFERENCES woaho.imagen(imagen_id);
 O   ALTER TABLE ONLY woaho.territorio DROP CONSTRAINT fkn3m1oitow4b70wae6i40gd87y;
       woaho          postgres    false    236    3106    242            p           2606    16925 "   pedido fkp80nbrpi74d5lutv2lo2ej3b3    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.pedido
    ADD CONSTRAINT fkp80nbrpi74d5lutv2lo2ej3b3 FOREIGN KEY (pedido_usuario) REFERENCES woaho.usuario(usuario_id);
 K   ALTER TABLE ONLY woaho.pedido DROP CONSTRAINT fkp80nbrpi74d5lutv2lo2ej3b3;
       woaho          postgres    false    237    254    3096            ,   j   x���v
Q���W(�O���KN��L�LNL���Ss�	uV�0�Q0#u��J����<��Ԣ�L�"ucMk.O��2�Q0��3B�(5�4'�h�1�(.. �,�      /   
   x���          #   �   x����
�@�O�7$�oF'	FY�u��̡U����`�?��o����(��*f�7n$��E�A<��#�����[c���	}p
�	�\��r2�X�d!�����L0d�?��(b�x	9H�v��,!v���2a����v˖;&���5ΰl�L��zJ�/ؔ����� �,�%         g   x���
�0 �~E6T�4%V'�Q𵋂:u���vw\?��u�/O��x�w��6�@N"%d�1Z�daKL�̤@���X� rkuK�n�&�ҀE���"�      )   
   x���              a  x���Ao�0��~�&;�%@��B��6�qSqfR��e?�
�h���i��^�����z�|8[ /XL�A�L�I^�q��-X>��pn�4W��ƢH�M�NU�� }_���eS��f���&�B�����ցzuˉGfb4�úDOG����4�f�6�I ���*1$�r܁P�!�aW���6��S�DGc�;�qT�}%�b#��q���1X�B��vW��8��9g���"!ؚSUi%�Y�u�٣au�\l�b��HR�V�EgB���:*�B��\t��{Wń^x-B�RЎoWW	?&<j��t���29��%���`��xf�Ε�2�C�`��ظ      6   u  x��XMo7��W��Tˊ;=���H� r�TP�ъ�.���r� ?*�W���!��ul�����y��fȋ����+qqy����raG���+Y�)��C+mk)�>}���T�0~(����~O�j�ǟ\�$� _I������ygI�~MU��%�%�z!�S��צ�]�#H8��VA�3k��2�E���.zM��]�v�"�Ҧ��9��d���#��U��Z�M���6*8^���!�ۧv�\˘��Β��w�b�m�2DpR�E?��u� ��)�����2	.!zeM������e����'��)�k䂜sS")G��yEE�te��Y���ѕ�M��Y�s�(���9��U@u*}��9�O)�D)�1�9��h�Ą�)!��K�lUYq_q�3ZQe�5����	�$�@M�Q|p�_Z�I@����
_*���b�_B}\�?�54}�h�Bp��ܥtRTR�Z��;�P׸u��íj�����`�V�qQ\��h��ɭt��J��5zVQ�����J�J�O����4��Ң�ݧ:��-Y�o�X��H�(���Ɋ�4�T����! v��.��?桘˕uX��N'LK+ɥj�jA�E{1#2�UX�����͂���۬�u5��bY��c8�d)��0���C�x[!�l,4��sT4o6�\�68('�)�9�>�(ct���n�MUW�V|��g�X���VKEa��zǃ� ���V:mc����O)[�@�EĬ��;���{mv{d5�,��.�GiG� 6�����K46m���Ta΀6da��JGb*��0��8���̋�
�f��x�`�	��K)�J��[��8K�3,��
I��7� iP���Rzm���Y(K�sa�&�u$���o������}�%SR�u��ɮͅ��f d���c�w�̹lb����c�Q�^6k�!����V��4+m����J��}��Σ���LAe�M2���{�AN��^;9��nn��)��XK��-1��d<���g)�.+0�Z�"�d����zvW0M�b��7TV[���&�;�l���a%X\Ms�E%6,#�˻�KLդ��Q��9���İu��TY�pT���g:����	2 D���a�����<�-�w9I؎=���6���W�*Q�&�wg�kO��H�r�>�b�+D��4�	>�j���>XmǠE|���-�� n1l-o	;"䑨,��.J���8�2vN�<.w<�$qlkz��K8UlQ��z��q Н]/��A�@���~*��l����`����I�k�jshv���0J�<�B��ٶ\ld�n��]C���ɻ��͆[(��{8-tѦ�[&��T�IX/�Gy$p�����l������x��L�8�e�<���a9�3B��]q�fL!���V�w׺9��G�1�v���N0�N8� �?���1�r���	"�C����1Sl���u�=L�8_vW��]u���|�,��ْQ:�9&�t�e���0�V��p�~Gk.��眲=ڿ�U��v�Nh]�2�(��b�)3�`�CXat���N#�xAu���*�tj7|�* ͢ 8aM�_3���;��W��L��c/
�!7� ��|��?B�g         w   x���v
Q���W(�O���K-.IL�Ws�	uV�0�QPwt��W״��$����ӏ�@�~.��~!�D�0�ru�	rt!�S�7��|<���b���������� ��Tz      2   
   x���             P   x���v
Q���W(�O����L���MTs�	uV�0�QP��s�qV�\��5��<	i2)p<<���-��� ��M      "      x���K��0����$Ȼm��8,R%�H�µ�&����?(�{�l�u����\�p&��̌�l��o�>���/��Ɛ6�'m����o�����]0�3ܑ��$�-��ř9�O��f��aR��J�~�LG�J�Arɉ��8�����<���4B(<S�ґ54�~�JQV�a֦T�S�1�o�C�7iGK��%���C�wF��8���hE���QKy���d]��<L�,L�����4�(�%���2��e$zA���$��TY*�	T,���rP��L�bc\eV�L�@�)�tr*�&�[�y}�r�`�4�k5�Ī� Α1��y�(�Ƨ�4�������*b�����~B8��2��\���'�EK�z� 1��/)�*��r L�T�����U+��Jlh5��3�Uga�4 J�S�&�*-m�G��ޕ')�9�Ukek�E���	2e�\��MKlq[c��X/!�"�A�Q��{zG�,��O����o��W��Wi�o�U~"�W���P�3'���l�e3�      -   _   x���v
Q���W(�O����MM�̏/HL�Ws�	uV�0�QPwMKM.�,�WGejZsyc�P�sP�l�I�	�@m.�IP�,�~.. J;i      :   \   x���v
Q���W(�O����MM�̏/HLϏ/-.M,��Ws�	uV�0�QP�,NTp��IQr��L@c#C�@5�:
F��\\\ F>�         �   x���1�@�὿"�)rIz�N
R�V��"h���tp3ٟ@�{ۮo���p��2���9���c���|kzء����E�W�h�i�:]�.u:zX\��$:�t%���Zt��<�����(���敃�+�h�%�z��զb(���`j����fS5\ڪ�h���o6E��c3�      <   �  x����j�@��y��%�4=�Ɛ8.�S�9��4�6�v�ݕ\�0=��S�/�e�Ih�`����������4�/ni�\��A�d�(}���4�����w<���Mɹ��)]�㛧�Y5�-���ze�5�L�Ė*��\(�oLf4�}'C���2uѸ�Q�$.�{���$��Ykڇ�L�RŞ�{.��[{	�W�dM�W�O�.�!zu�1���������w9�1ah�>�B��f-$�2[�0�P�j�D@�#{�4�8�͕��m9����Ϯ����򆂖MΩ]2/�y]I@�I)�[�bS�u�	���,S'r&�d�ҝ���K��4�>;Ou��l.1	/���Z�q+Y�=�ve�Z����AJÐn�����Y�N���I-kg��J�؅�i��T�P25۟����(v�c�N��Lv�"�u�͚Ҥ��?	ǧ�f�J����B:\_�q/΅�_�����Z��XK+�r�EbHdv�iT�[�z��	��?���d���@�咃+�����y��D�4�ݵb���EB��1yD���<���X�Z¶�B;�Cf5Ծ�0�l�r�.��I��i$e�Ҷ��
C��E;<o�Ƃ#���9�uGb�?����T�,<�V8��*Ȕ;�R �Չ��0K�*V�Bq)Ӿyɨv�={��{��K���\�>      1   �   x��ѽ
�@���b�Gp�RYX��h҆-��
������c����m �W�-�ZΟq��=>W��2MB���7=��ґ�b���)�0�(�0�Z
eC��C�"�1UX
w%*�1�ǖ�SP��TNk��t�Y      $   R   x���v
Q���W(�O������KMITs�	uV�0�QPw�P�Q0Դ��$���<4��܈��@�~@��@�\\ j�&�      0   j   x���v
Q���W(�O���+H�+I��ITs�	uV�0�QP��LIU(��W�Q04bMk.O�Z��ZS�AZM��j�ZR�
�kF�^����Ē"���!��� �.Ee      !   h  x���Mk�0����u�R��v�,�f5k�;e��%^jH���~���à��]������Lb�S�H�)�4�hƍ������f�n:B�(���]R�BO��>DB;U�)��v�E����2���rie+,��M�J㛭,U���j@n�2'x��+#3��Ci�Nɺ�gQ�B�>=�{�Z	�wޒF;�O`�Vs`m(�l��mk׌�Z�j���s"�r��2p���P�V�<��<#�s�����;{��a�i�b��=��ٵ���T.H?��t����,L	�2�/�/�w��,����S��6�t�6����倌m�#^�F��'4�^7X.��_�ү��oHc�      .   �   x���v
Q���W(�O���+HM�L�Ws�	uV�0�Q0�Q ���:
�:
~�>>�����������9�ghde`�SUf�g``hbah	 ���fz&�������\���`�����Hn�8��&&��0�M�͈��x �6���\\ �n�         \   x���v
Q���W(�O���+(�OK-���Ss�	uV�0�QP��KO��L-�W״��$B�P�kNjrIQf2њ�A��K2s2�KA��� I1�      *   �  x���Mk�@໿bo�`$�1�Г�B-b����d�k��&X��;����B��̓yw��qo4a��䝭|��J��$$�3���C���kU/
�y�X�բ¡ڳڻ����z�d�Z�؆
�"��E0�$�۹A�����ֿ��шA&L>j�b-�٩ȷ�e�L�R0dZ���D-�NM��?�NT��^
��:�Eu�c�,]�F��8�fl���͝.�Ƽ��w��M�UͭgR��+7�0�]�N�6�h��߮J%;�+���EgeV1���ąʏS)���o�搷d�0O�ͮ��ݒ�/i�@���ѐI�iϥ���lr��V��9���F�%d�3���B�S�9M�����6��E�M���х�ݰ
ϭ܉���o�"�}��U{�����a�C����?+qc�      (   
   x���          &   b  x����J�@���sW�"���U�E*Z���Ow�6�ٍ�I�\>�/��n�(�$̙���:[�n`�޼�����x�c�r����2��a����� s��	��=���@�����C����zM����f�CE�:���B�m���]g��� �I�x K�F�$��b����P���&O�B��,y鐫Z�Z	�93�Ӝ���D.�(Fn��(#�Y8�=-pG�Ӏ�S&D*	^5�v���Z�:E�(�m�V�_�cU�|'�p�+W%c��"x�d�Ϛ8Ǵ��� �
�U�-ySq~Dֹn�(	&��AT-����GljI��O:N%��%6�p��qp{��t~ ��)      8   I   x���v
Q���W(�O���+N-*�L�̏OK,�/�,�Ws�	uV�0�Q0�Q0Դ��$A����D �2!Q      '   z   x���v
Q���W(�O���+I,�LKTs�	uV�0�Q02 C0�5��<	�3�3B�gH�>s�:d�L��g��ϔH}�@/�"�3#R�!P�	�Fsb5�i� V�0*�5A4rq B.q�         �   x����
�@�OqwI4��h�B0��7h`j`4�Z��Xw�M��V�e8�$Y�%$Y���Z�\k�+-�۴����k��C�%Dxm�v�9�����Ȫ4�o�d�S*B9<)G�7		(��5���6!�\s�L���(%��(�}��P�j,dV�]��꠺z�9f4w�f���]4꧕kF�e� -ђ          a   x���v
Q���W(�O���+�,�Ws�	uV�0�QP��LIU״��į��6 1�$1''���@�!�%�9�D(6��4��X�� �d�]�� D�B�         �   x���v
Q���W(�O���+�,ȏ/I-*�,�/��Ws�	uV�0�QPH�,V״��$Z�P�KjAbQIbnj^I>i����}K�2�32I�j�ꜟ[��H�>S�>�D�0�����+*JM���$ݧ�@�a�E�)`�rq ���      4   �  x�͕�n�@��y�������M�C�lZ����*�-��Mw"?D�ϒ�1ؖz��PU��"���o���4L2��l	�J���jQ4y^)	w��*L���νs�|
�h�JB:{�ߞE/1��������l��d��i�\ԧ�������*L�~.�!�K��e�E�h_����,��do�j¦�]��g�� ���*�E�S�8�q�-[Η@�J���L]B�<rp�^R|ZX��j4��Z[��[x��J�"l�F������cx�j�ƐGQvo���r�p��O��OK;�(-l�~(�X�SF����u+u���)�U+�'�������3�1��ANz�s
�T#A6�*@c�Z� WҊ�2|��q��x�ް�=�)��ܠ~��
6��4ɋFiGT_<��W(��K��t��,�J�"�DpX�C+���,�5+�%�ш�ZW�zڪ��E��+�¸�]�B��j��ڗy'����p[nfʿm�>!�(иa�"������φ���Y�}E�̼Ʈ�hn�p��g������Nhy�j��nv�Wo��g��7�%�Mp�u�)��5��-�� q��[p�ߩ⦓"�A;N6�B������[�;[Vr3��5�-Uc�F��h(�֑h:H��*xk�(�a���<�i�47������7��      +   �   x��ұn1����[����vl110�TQ����R�ބx}Lwt��K����a�]�`����2���|8����_����9�'�p;z�Rj��_���Q	�˲,����=��ē�n�V�D�b�;V��<��1��	���>a�5�bA�c�j���L�b�����4��ܶV�Fp0g�'��x�ԼO��b0���w�RR�=����uW��      %   d   x���v
Q���W(�O���+��LIL�/I,�LKTs�	uV�0�QP��/JT״��$R�P�K&IZ��Z�Ss�H�e��ZL�S����Az�� �mP           x����n�F����S��ݙ��a )�� n��q�^�D�vl����[��b�!-Y���H�J�l+ԧٙ3g�~�����ś��_��B-o���٢������q�</F��k}U�������34�[a�~ş1��_�gj����РK�K���
\eAƓ����3�=9:��~��ٛG�Oq�39�u3?���zqK�C�zt����i���ۈT�XYS�p�k&:N� euSډ�a<��m06��h~�'=�rvUǯ�5Β���PSB(4V�*t*���?R+�+��f DXA<d@�(rt�6J?�!fl��A�P�V�فF?d8�� ��<�)G0���%��J� Si�
ǡ��଴9� �0���I
[^N
La���e!*�ठ�z�I�H��,ơ.	�� 8�ʹ��$е�EmE�)�u�m9�n�\pq<�O؍O�:�%s���r�"0��ަ��Uǚt�QE(�fC����&-�ݑ�qQq����.����,�p���*�1d�`�c$ŏ��$�,;��F��,&�K���rc��c��Os�b�)MQ�'X���MY�{_�P��I��
ֱI���y�EI*\ː�q_���p��qd�@r����螠R2�ű��Ib�I��'�`L�Wgau�F��� (���@������6��=q�����0��)QE��1*��QV�RnQDH��f�n����f>�ʻ�?�Lgs�ߢ�=]���le�2~x�ń��`����K_a�D�1RJ���\4���O����%�W|e�(iLl_섑WR��E�I��[|�4M()���Lf��"�(>�+�T�.9 W���F�a�Jښ�L��a%5vx���(���C�s�����z�qx�Z�f���BIcD�g�ӌMV�=�PjS V��0C)���F��-�Q�m�mP�V`Þ�ځ3�g��w�^c�򇦿K� ��``w]�}Va!݀G����*���4�����<fr���H�cnc?Y�&��K��X�0�r<i\�8ij
�>�xh�r��2&Ȇ����B�!�A9�,����{IN,;�}EmL� �����5�W�t�J�_��f\��byQ_�������ƴ�_�fUH����!̦7���YW�� �}��C�e$f�����I���Ny{�B���!��i�?ټ^c�>B��[J��N�`��
jJ�. 5��M}��+�>�f�D��Qv �~0��o�8�L��Z����Nr`�蔏��,;p���4��� {��φ�?D0,����6`��/�6}����O�\�V�V�`d�7Fy:|nA�j�*���h�j��\N��9k]�z�z˕��Q�wk��\��X(&�x���hn�Or{1��$�����st;�Ȕ#���L�y���df�j�^9Z��~뽓Ķ���5���L�6ء�֙�Vs�dD��6�F�Vޠ��r�l>/��?�X��Η����� �A��K-��kwhG���/_�8����x��x�����٫/F�	`�ٳ�2ܕ�         ?           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            @           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            A           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            B           1262    16385    woaho    DATABASE     w   CREATE DATABASE woaho WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'en_US.UTF-8' LC_CTYPE = 'en_US.UTF-8';
    DROP DATABASE woaho;
                postgres    false                        2615    16386    woaho    SCHEMA        CREATE SCHEMA woaho;
    DROP SCHEMA woaho;
                postgres    false                       1255    17020 -   consultar_mensajes_pantalla(integer, integer)    FUNCTION     a  CREATE FUNCTION woaho.consultar_mensajes_pantalla(p_pantalla integer, p_idioma integer) RETURNS character varying
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
       woaho          postgres    false    7                       1255    17035 A   fndb_consultar_equivalencia(character varying, character varying)    FUNCTION     �  CREATE FUNCTION woaho.fndb_consultar_equivalencia(p_cadena character varying, p_idioma character varying) RETURNS character varying
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
       woaho          postgres    false    7                       1255    17021 4   fndb_consultar_pantallas(integer, character varying)    FUNCTION     e  CREATE FUNCTION woaho.fndb_consultar_pantallas(p_tipo_pantalla integer, p_idioma character varying) RETURNS character varying
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
       woaho          postgres    false    7                       1255    17038 B   fndb_generar_codigo_registro(character varying, character varying)    FUNCTION     �  CREATE FUNCTION woaho.fndb_generar_codigo_registro(p_celular character varying, p_idioma character varying) RETURNS character varying
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

	IF (codigo_anterior IS NULL ) THEN
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
 k   DROP FUNCTION woaho.fndb_generar_codigo_registro(p_celular character varying, p_idioma character varying);
       woaho          woaho    false    7                       1255    17037 U   fndb_validar_codigo_registro(character varying, character varying, character varying)    FUNCTION     W	  CREATE FUNCTION woaho.fndb_validar_codigo_registro(p_celular character varying, p_codigo character varying, p_idioma character varying) RETURNS character varying
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
       woaho          postgres    false    7                       1255    16937 $   generar_aleatorios(integer, integer)    FUNCTION     �   CREATE FUNCTION woaho.generar_aleatorios(pinicial integer, pfinal integer) RETURNS character varying
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
       woaho         heap    postgres    false    224    7            C           0    0    TABLE calificacion    COMMENT     e   COMMENT ON TABLE woaho.calificacion IS 'Tabla que contiene las calificaciones de los profesionales';
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
       woaho         heap    postgres    false    230    7            D           0    0    TABLE cancelacion    COMMENT     Y   COMMENT ON TABLE woaho.cancelacion IS 'Tabla que contiene las cancelaciones realizadas';
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
       woaho         heap    postgres    false    214    7            E           0    0    TABLE categoria    COMMENT     \   COMMENT ON TABLE woaho.categoria IS 'Tabla que contiene las categorias para el aplicativo';
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
       woaho         heap    postgres    false    211    7            F           0    0    TABLE codigo    COMMENT     h   COMMENT ON TABLE woaho.codigo IS 'Tabla que contiene los codigos generados para completar el registro';
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
       woaho         heap    postgres    false    218    7            G           0    0    TABLE codigo_promocional    COMMENT     l   COMMENT ON TABLE woaho.codigo_promocional IS 'Tabla que contiene los codigos promocionales del aplicativo';
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
       woaho         heap    postgres    false    213    7            H           0    0    TABLE direccion    COMMENT     g   COMMENT ON TABLE woaho.direccion IS 'Tabla que contiene las direcciones registradas por los usuarios';
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
       woaho         heap    postgres    false    261    7            I           0    0    TABLE equivalencia_idioma    COMMENT     f   COMMENT ON TABLE woaho.equivalencia_idioma IS 'Tabla que contiene las traducciones de las etiquetas';
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
       woaho         heap    postgres    false    210    7            J           0    0    TABLE estado    COMMENT     V   COMMENT ON TABLE woaho.estado IS 'Tabla que contiene los estados para el aplicativo';
          woaho          postgres    false    238            K           0    0    COLUMN estado.estado_codigo    COMMENT     b   COMMENT ON COLUMN woaho.estado.estado_codigo IS 'A activo, I inactivo, P pendiente, R rechazado';
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
       woaho         heap    postgres    false    228    7            L           0    0    TABLE etiqueta    COMMENT     V   COMMENT ON TABLE woaho.etiqueta IS 'Tabla que contiene las etiquetas del aplicativo';
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
       woaho         heap    postgres    false    225    7            M           0    0    TABLE idioma    COMMENT     e   COMMENT ON TABLE woaho.idioma IS 'Tabla que contiene la información de los idiomas del aplicativo';
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
       woaho         heap    postgres    false    223    7            N           0    0    TABLE imagen    COMMENT     W   COMMENT ON TABLE woaho.imagen IS 'Tabla que contiene las imagenes para el aplicativo';
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
       woaho         heap    postgres    false    229    7            O           0    0    TABLE medio_pago    COMMENT     b   COMMENT ON TABLE woaho.medio_pago IS 'Tabla que contiene los medios de pagos para el aplicativo';
          woaho          postgres    false    253            	           1259    17066    sec_medio_pago_usuario    SEQUENCE     �   CREATE SEQUENCE woaho.sec_medio_pago_usuario
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999
    CACHE 1
    CYCLE;
 ,   DROP SEQUENCE woaho.sec_medio_pago_usuario;
       woaho          postgres    false    7            
           1259    17068    medio_pago_usuario    TABLE     �  CREATE TABLE woaho.medio_pago_usuario (
    medio_pago_usuario_id integer DEFAULT nextval('woaho.sec_medio_pago_usuario'::regclass) NOT NULL,
    medio_pago_usuario_nombre character varying(4000),
    medio_pago_usuario_fecha_vencimiento character varying(4000),
    medio_pago_usuario_cvc character varying(4000),
    medio_pago_usuario_codigo character varying(4000),
    medio_pago_usuario_estado integer,
    medio_pago_usuario_usuario integer,
    medio_pago_usuario_medio_pago integer
);
 %   DROP TABLE woaho.medio_pago_usuario;
       woaho         heap    postgres    false    265    7            P           0    0    TABLE medio_pago_usuario    COMMENT     f   COMMENT ON TABLE woaho.medio_pago_usuario IS 'Tabla que contiene los medios de pago para el usuario';
          woaho          postgres    false    266            �            1259    16387    sec_mensaje    SEQUENCE     �   CREATE SEQUENCE woaho.sec_mensaje
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
       woaho         heap    postgres    false    203    7            Q           0    0    TABLE mensaje    COMMENT     e   COMMENT ON TABLE woaho.mensaje IS 'Tabla que contiene la información de los mensajes por pantalla';
          woaho          postgres    false    234                       1259    17093    sec_mensaje_correo    SEQUENCE     �   CREATE SEQUENCE woaho.sec_mensaje_correo
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999
    CACHE 1
    CYCLE;
 (   DROP SEQUENCE woaho.sec_mensaje_correo;
       woaho          postgres    false    7                       1259    17095    mensaje_correo    TABLE       CREATE TABLE woaho.mensaje_correo (
    mensaje_correo_id integer DEFAULT nextval('woaho.sec_mensaje_correo'::regclass) NOT NULL,
    mensaje_correo_codigo integer,
    mensaje_correo_mensaje character varying(4000),
    mensaje_correo_idioma character varying(4000)
);
 !   DROP TABLE woaho.mensaje_correo;
       woaho         heap    postgres    false    267    7            R           0    0    TABLE mensaje_correo    COMMENT     q   COMMENT ON TABLE woaho.mensaje_correo IS 'Tabla que contiene los mensajes que se envian por correo electronico';
          woaho          postgres    false    268            �            1259    16389    sec_mensaje_pantalla    SEQUENCE     �   CREATE SEQUENCE woaho.sec_mensaje_pantalla
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
       woaho         heap    postgres    false    204    7            S           0    0    TABLE mensaje_pantalla    COMMENT     h   COMMENT ON TABLE woaho.mensaje_pantalla IS 'Tabla que contiene la relacion entre mensajes y pantallas';
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
       woaho         heap    postgres    false    215    7            T           0    0    TABLE moneda    COMMENT     V   COMMENT ON TABLE woaho.moneda IS 'Tabla que contiene las monedas para el aplicativo';
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
       woaho         heap    postgres    false    205    7            U           0    0    TABLE pantalla    COMMENT     Z   COMMENT ON TABLE woaho.pantalla IS 'Tabla que contiene la información de las pantallas';
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
       woaho         heap    postgres    false    212    7            V           0    0    TABLE parametro    COMMENT     X   COMMENT ON TABLE woaho.parametro IS 'Tabla que contiene los parametros del aplicativo';
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
       woaho         heap    postgres    false    221    7            W           0    0    TABLE pedido    COMMENT     R   COMMENT ON TABLE woaho.pedido IS 'Tabla que contiene los pedidos del aplicativo';
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
       woaho         heap    postgres    false    227    7            X           0    0    TABLE profesion    COMMENT     l   COMMENT ON TABLE woaho.profesion IS 'Tabla que contiene la información de las profesiones del aplicativo';
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
       woaho         heap    postgres    false    222    7            Y           0    0    TABLE profesional    COMMENT     ]   COMMENT ON TABLE woaho.profesional IS 'Tabla que contiene los profesionales del aplicativo';
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
       woaho         heap    postgres    false    219    7            Z           0    0    TABLE promocion    COMMENT     Y   COMMENT ON TABLE woaho.promocion IS 'Tabla que contiene las promociones del aplicativo';
          woaho          postgres    false    248            �            1259    16413    sec_servicio    SEQUENCE     �   CREATE SEQUENCE woaho.sec_servicio
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999
    CACHE 1
    CYCLE;
 "   DROP SEQUENCE woaho.sec_servicio;
       woaho          postgres    false    7                       1259    17043    sec_servicio_favorito    SEQUENCE     �   CREATE SEQUENCE woaho.sec_servicio_favorito
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999999
    CACHE 1
    CYCLE;
 +   DROP SEQUENCE woaho.sec_servicio_favorito;
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
       woaho         heap    postgres    false    216    7            [           0    0    TABLE servicio    COMMENT     Z   COMMENT ON TABLE woaho.servicio IS 'Tabla que contiene los servicios para el aplicativo';
          woaho          postgres    false    246                       1259    17045    servicio_favorito    TABLE     �   CREATE TABLE woaho.servicio_favorito (
    servicio_favorito_id integer DEFAULT nextval('woaho.sec_servicio_favorito'::regclass) NOT NULL,
    servicio_favorito_servicio integer,
    servicio_favorito_usuario integer
);
 $   DROP TABLE woaho.servicio_favorito;
       woaho         heap    postgres    false    263    7            \           0    0    TABLE servicio_favorito    COMMENT     f   COMMENT ON TABLE woaho.servicio_favorito IS 'Tabla que contiene los servicios favoritos del usuario';
          woaho          postgres    false    264            �            1259    16693    tarifa    TABLE     �   CREATE TABLE woaho.tarifa (
    tarifa_id integer DEFAULT nextval('woaho.sec_tarifa'::regclass) NOT NULL,
    tarifa_valor numeric,
    tarifa_moneda integer,
    tarifa_territorio integer,
    tarifa_servicio integer,
    tarifa_unidad integer
);
    DROP TABLE woaho.tarifa;
       woaho         heap    postgres    false    217    7            ]           0    0    TABLE tarifa    COMMENT     V   COMMENT ON TABLE woaho.tarifa IS 'Tabla que contiene las tarifas para el aplicativo';
          woaho          postgres    false    247            �            1259    16537 
   territorio    TABLE     0  CREATE TABLE woaho.territorio (
    territorio_id integer DEFAULT nextval('woaho.sec_territorio'::regclass) NOT NULL,
    territorio_nombre character varying(4000),
    territorio_padre integer,
    territorio_tipo integer,
    territorio_codigo character varying(4000),
    territorio_imagen integer
);
    DROP TABLE woaho.territorio;
       woaho         heap    postgres    false    207    7            ^           0    0    TABLE territorio    COMMENT     h   COMMENT ON TABLE woaho.territorio IS 'Tabla que contiene los territorios registrados en el aplicativo';
          woaho          postgres    false    236            �            1259    16443    tipo    TABLE     �   CREATE TABLE woaho.tipo (
    tipo_id integer DEFAULT nextval('woaho.sec_tipo'::regclass) NOT NULL,
    tipo_nombre character varying(4000)
);
    DROP TABLE woaho.tipo;
       woaho         heap    postgres    false    206    7            _           0    0 
   TABLE tipo    COMMENT     ^   COMMENT ON TABLE woaho.tipo IS 'Tabla que contiene la información de los tipos de pantalla';
          woaho          postgres    false    231            �            1259    16528    tipo_territorio    TABLE     �   CREATE TABLE woaho.tipo_territorio (
    tipo_territorio_id integer DEFAULT nextval('woaho.sec_tipo_territorio'::regclass) NOT NULL,
    tipo_territorio_nombre character varying(4000)
);
 "   DROP TABLE woaho.tipo_territorio;
       woaho         heap    postgres    false    208    7            `           0    0    TABLE tipo_territorio    COMMENT     Y   COMMENT ON TABLE woaho.tipo_territorio IS 'Tabla que contiene los tipos de territorios';
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
       woaho         heap    postgres    false    226    7            a           0    0    TABLE ubicacion    COMMENT     _   COMMENT ON TABLE woaho.ubicacion IS 'Tabla que contiene las ubicaciones de los profesionales';
          woaho          postgres    false    251            �            1259    16660    unidad_tarifa    TABLE     �   CREATE TABLE woaho.unidad_tarifa (
    unidad_tarifa_id integer DEFAULT nextval('woaho.sec_unidad_tarifa'::regclass) NOT NULL,
    unidad_tarifa_nombre character varying(4000)
);
     DROP TABLE woaho.unidad_tarifa;
       woaho         heap    postgres    false    220    7            b           0    0    TABLE unidad_tarifa    COMMENT     i   COMMENT ON TABLE woaho.unidad_tarifa IS 'Tabla que contiene las unidades de tarifas para el aplicativo';
          woaho          postgres    false    245            �            1259    16551    usuario    TABLE     �  CREATE TABLE woaho.usuario (
    usuario_id integer DEFAULT nextval('woaho.sec_usuario'::regclass) NOT NULL,
    usuario_nombre character varying(4000),
    usuario_apellido character varying(4000),
    usuario_celular character varying(4000),
    usuario_correo character varying(4000),
    usuario_acepta_terminos character varying(4000),
    usuario_fecha_hora_acepta_terminos timestamp without time zone,
    usuario_clave character varying(4000),
    usuario_id_suscriptor character varying(4000),
    usuario_referralcode character varying(4000),
    usuario_referral_code character varying(255),
    usuario_tipo integer DEFAULT 2
);
    DROP TABLE woaho.usuario;
       woaho         heap    postgres    false    209    7            c           0    0    TABLE usuario    COMMENT     b   COMMENT ON TABLE woaho.usuario IS 'Tabla que contiene los usuarios registrados en el aplicativo';
          woaho          postgres    false    237            ,          0    16799    calificacion 
   TABLE DATA                 woaho          postgres    false    252            /          0    16876    cancelacion 
   TABLE DATA                 woaho          postgres    false    255   z        #          0    16632 	   categoria 
   TABLE DATA                 woaho          postgres    false    243                     0    16571    codigo 
   TABLE DATA                 woaho          postgres    false    239   �        )          0    16741    codigo_promocional 
   TABLE DATA                 woaho          postgres    false    249   w                   0    16590 	   direccion 
   TABLE DATA                 woaho          postgres    false    240           6          0    17024    equivalencia_idioma 
   TABLE DATA                 woaho          postgres    false    262   q                 0    16562    estado 
   TABLE DATA                 woaho          postgres    false    238   �       2          0    16990    etiqueta 
   TABLE DATA                 woaho          postgres    false    258   �                  0    16452    idioma 
   TABLE DATA                 woaho          postgres    false    232           "          0    16623    imagen 
   TABLE DATA                 woaho          postgres    false    242   `        -          0    16823 
   medio_pago 
   TABLE DATA                 woaho          postgres    false    253          :          0    17068    medio_pago_usuario 
   TABLE DATA                 woaho          postgres    false    266   o                  0    16498    mensaje 
   TABLE DATA                 woaho          postgres    false    234   l        <          0    17095    mensaje_correo 
   TABLE DATA                 woaho          postgres    false    268   �        1          0    16909    mensaje_pantalla 
   TABLE DATA                 woaho          postgres    false    257   �       $          0    16646    moneda 
   TABLE DATA                 woaho          postgres    false    244   �        0          0    16890    pantalla 
   TABLE DATA                 woaho          postgres    false    256   b        !          0    16614 	   parametro 
   TABLE DATA                 woaho          postgres    false    241   z        .          0    16837    pedido 
   TABLE DATA                 woaho          postgres    false    254   x                 0    16475 	   profesion 
   TABLE DATA                 woaho          postgres    false    233   �        *          0    16766    profesional 
   TABLE DATA                 woaho          postgres    false    250   l        (          0    16722 	   promocion 
   TABLE DATA                 woaho          postgres    false    248   �       &          0    16669    servicio 
   TABLE DATA                 woaho          postgres    false    246           8          0    17045    servicio_favorito 
   TABLE DATA                 woaho          postgres    false    264   r       '          0    16693    tarifa 
   TABLE DATA                 woaho          postgres    false    247   Y                  0    16537 
   territorio 
   TABLE DATA                 woaho          postgres    false    236   �                  0    16443    tipo 
   TABLE DATA                 woaho          postgres    false    231   �                  0    16528    tipo_territorio 
   TABLE DATA                 woaho          postgres    false    235   q        4          0    17006 
   traduccion 
   TABLE DATA                 woaho          woaho    false    260   �        +          0    16785 	   ubicacion 
   TABLE DATA                 woaho          postgres    false    251   �       %          0    16660    unidad_tarifa 
   TABLE DATA                 woaho          postgres    false    245   �                  0    16551    usuario 
   TABLE DATA                 woaho          postgres    false    237   t        d           0    0    sec_calificacion    SEQUENCE SET     =   SELECT pg_catalog.setval('woaho.sec_calificacion', 7, true);
          woaho          postgres    false    224            e           0    0    sec_cancelacion    SEQUENCE SET     =   SELECT pg_catalog.setval('woaho.sec_cancelacion', 1, false);
          woaho          postgres    false    230            f           0    0    sec_categoria    SEQUENCE SET     :   SELECT pg_catalog.setval('woaho.sec_categoria', 9, true);
          woaho          postgres    false    214            g           0    0 
   sec_codigo    SEQUENCE SET     9   SELECT pg_catalog.setval('woaho.sec_codigo', 277, true);
          woaho          postgres    false    211            h           0    0    sec_codigo_promocional    SEQUENCE SET     D   SELECT pg_catalog.setval('woaho.sec_codigo_promocional', 1, false);
          woaho          postgres    false    218            i           0    0    sec_direccion    SEQUENCE SET     ;   SELECT pg_catalog.setval('woaho.sec_direccion', 11, true);
          woaho          postgres    false    213            j           0    0    sec_equivalencia_idioma    SEQUENCE SET     E   SELECT pg_catalog.setval('woaho.sec_equivalencia_idioma', 61, true);
          woaho          postgres    false    261            k           0    0 
   sec_estado    SEQUENCE SET     7   SELECT pg_catalog.setval('woaho.sec_estado', 6, true);
          woaho          postgres    false    210            l           0    0    sec_etiqueta    SEQUENCE SET     :   SELECT pg_catalog.setval('woaho.sec_etiqueta', 40, true);
          woaho          postgres    false    228            m           0    0 
   sec_idioma    SEQUENCE SET     7   SELECT pg_catalog.setval('woaho.sec_idioma', 2, true);
          woaho          postgres    false    225            n           0    0 
   sec_imagen    SEQUENCE SET     8   SELECT pg_catalog.setval('woaho.sec_imagen', 32, true);
          woaho          postgres    false    223            o           0    0    sec_medio_pago    SEQUENCE SET     ;   SELECT pg_catalog.setval('woaho.sec_medio_pago', 3, true);
          woaho          postgres    false    229            p           0    0    sec_medio_pago_usuario    SEQUENCE SET     C   SELECT pg_catalog.setval('woaho.sec_medio_pago_usuario', 1, true);
          woaho          postgres    false    265            q           0    0    sec_mensaje    SEQUENCE SET     9   SELECT pg_catalog.setval('woaho.sec_mensaje', 36, true);
          woaho          postgres    false    203            r           0    0    sec_mensaje_correo    SEQUENCE SET     ?   SELECT pg_catalog.setval('woaho.sec_mensaje_correo', 2, true);
          woaho          postgres    false    267            s           0    0    sec_mensaje_pantalla    SEQUENCE SET     B   SELECT pg_catalog.setval('woaho.sec_mensaje_pantalla', 12, true);
          woaho          postgres    false    204            t           0    0 
   sec_moneda    SEQUENCE SET     7   SELECT pg_catalog.setval('woaho.sec_moneda', 3, true);
          woaho          postgres    false    215            u           0    0    sec_pantalla    SEQUENCE SET     9   SELECT pg_catalog.setval('woaho.sec_pantalla', 4, true);
          woaho          postgres    false    205            v           0    0    sec_parametro    SEQUENCE SET     ;   SELECT pg_catalog.setval('woaho.sec_parametro', 10, true);
          woaho          postgres    false    212            w           0    0 
   sec_pedido    SEQUENCE SET     7   SELECT pg_catalog.setval('woaho.sec_pedido', 4, true);
          woaho          postgres    false    221            x           0    0    sec_profesion    SEQUENCE SET     :   SELECT pg_catalog.setval('woaho.sec_profesion', 3, true);
          woaho          postgres    false    227            y           0    0    sec_profesional    SEQUENCE SET     <   SELECT pg_catalog.setval('woaho.sec_profesional', 1, true);
          woaho          postgres    false    222            z           0    0    sec_promocion    SEQUENCE SET     ;   SELECT pg_catalog.setval('woaho.sec_promocion', 1, false);
          woaho          postgres    false    219            {           0    0    sec_servicio    SEQUENCE SET     9   SELECT pg_catalog.setval('woaho.sec_servicio', 3, true);
          woaho          postgres    false    216            |           0    0    sec_servicio_favorito    SEQUENCE SET     B   SELECT pg_catalog.setval('woaho.sec_servicio_favorito', 3, true);
          woaho          postgres    false    263            }           0    0 
   sec_tarifa    SEQUENCE SET     8   SELECT pg_catalog.setval('woaho.sec_tarifa', 12, true);
          woaho          postgres    false    217            ~           0    0    sec_territorio    SEQUENCE SET     ;   SELECT pg_catalog.setval('woaho.sec_territorio', 7, true);
          woaho          woaho    false    207                       0    0    sec_tipo    SEQUENCE SET     5   SELECT pg_catalog.setval('woaho.sec_tipo', 5, true);
          woaho          postgres    false    206            �           0    0    sec_tipo_territorio    SEQUENCE SET     @   SELECT pg_catalog.setval('woaho.sec_tipo_territorio', 7, true);
          woaho          postgres    false    208            �           0    0    sec_traduccion    SEQUENCE SET     <   SELECT pg_catalog.setval('woaho.sec_traduccion', 26, true);
          woaho          postgres    false    259            �           0    0    sec_ubicacion    SEQUENCE SET     :   SELECT pg_catalog.setval('woaho.sec_ubicacion', 2, true);
          woaho          postgres    false    226            �           0    0    sec_unidad_tarifa    SEQUENCE SET     >   SELECT pg_catalog.setval('woaho.sec_unidad_tarifa', 5, true);
          woaho          postgres    false    220            �           0    0    sec_usuario    SEQUENCE SET     9   SELECT pg_catalog.setval('woaho.sec_usuario', 62, true);
          woaho          postgres    false    209            6           2606    16807    calificacion calificacion_pkey 
   CONSTRAINT     h   ALTER TABLE ONLY woaho.calificacion
    ADD CONSTRAINT calificacion_pkey PRIMARY KEY (calificacion_id);
 G   ALTER TABLE ONLY woaho.calificacion DROP CONSTRAINT calificacion_pkey;
       woaho            postgres    false    252            <           2606    16884    cancelacion cancelacion_pkey 
   CONSTRAINT     e   ALTER TABLE ONLY woaho.cancelacion
    ADD CONSTRAINT cancelacion_pkey PRIMARY KEY (cancelacion_id);
 E   ALTER TABLE ONLY woaho.cancelacion DROP CONSTRAINT cancelacion_pkey;
       woaho            postgres    false    255            $           2606    16640    categoria categoria_pkey 
   CONSTRAINT     _   ALTER TABLE ONLY woaho.categoria
    ADD CONSTRAINT categoria_pkey PRIMARY KEY (categoria_id);
 A   ALTER TABLE ONLY woaho.categoria DROP CONSTRAINT categoria_pkey;
       woaho            postgres    false    243                       2606    16561    usuario celular_key 
   CONSTRAINT     X   ALTER TABLE ONLY woaho.usuario
    ADD CONSTRAINT celular_key UNIQUE (usuario_celular);
 <   ALTER TABLE ONLY woaho.usuario DROP CONSTRAINT celular_key;
       woaho            postgres    false    237                       2606    16579    codigo codigo_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY woaho.codigo
    ADD CONSTRAINT codigo_pkey PRIMARY KEY (codigo_id);
 ;   ALTER TABLE ONLY woaho.codigo DROP CONSTRAINT codigo_pkey;
       woaho            postgres    false    239            0           2606    16749 *   codigo_promocional codigo_promocional_pkey 
   CONSTRAINT     z   ALTER TABLE ONLY woaho.codigo_promocional
    ADD CONSTRAINT codigo_promocional_pkey PRIMARY KEY (codigo_promocional_id);
 S   ALTER TABLE ONLY woaho.codigo_promocional DROP CONSTRAINT codigo_promocional_pkey;
       woaho            postgres    false    249                       2606    16598    direccion direccion_pkey 
   CONSTRAINT     _   ALTER TABLE ONLY woaho.direccion
    ADD CONSTRAINT direccion_pkey PRIMARY KEY (direccion_id);
 A   ALTER TABLE ONLY woaho.direccion DROP CONSTRAINT direccion_pkey;
       woaho            postgres    false    240            F           2606    17032 ,   equivalencia_idioma equivalencia_idioma_pkey 
   CONSTRAINT     }   ALTER TABLE ONLY woaho.equivalencia_idioma
    ADD CONSTRAINT equivalencia_idioma_pkey PRIMARY KEY (equivalencia_idioma_id);
 U   ALTER TABLE ONLY woaho.equivalencia_idioma DROP CONSTRAINT equivalencia_idioma_pkey;
       woaho            postgres    false    262                       2606    16570    estado estado_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY woaho.estado
    ADD CONSTRAINT estado_pkey PRIMARY KEY (estado_id);
 ;   ALTER TABLE ONLY woaho.estado DROP CONSTRAINT estado_pkey;
       woaho            postgres    false    238            B           2606    16998    etiqueta etiqueta_pkey 
   CONSTRAINT     \   ALTER TABLE ONLY woaho.etiqueta
    ADD CONSTRAINT etiqueta_pkey PRIMARY KEY (etiqueta_id);
 ?   ALTER TABLE ONLY woaho.etiqueta DROP CONSTRAINT etiqueta_pkey;
       woaho            postgres    false    258                       2606    16460    idioma idioma_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY woaho.idioma
    ADD CONSTRAINT idioma_pkey PRIMARY KEY (idioma_id);
 ;   ALTER TABLE ONLY woaho.idioma DROP CONSTRAINT idioma_pkey;
       woaho            postgres    false    232            "           2606    16631    imagen imagen_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY woaho.imagen
    ADD CONSTRAINT imagen_pkey PRIMARY KEY (imagen_id);
 ;   ALTER TABLE ONLY woaho.imagen DROP CONSTRAINT imagen_pkey;
       woaho            postgres    false    242            8           2606    16831    medio_pago medio_pago_pkey 
   CONSTRAINT     b   ALTER TABLE ONLY woaho.medio_pago
    ADD CONSTRAINT medio_pago_pkey PRIMARY KEY (medio_pago_id);
 C   ALTER TABLE ONLY woaho.medio_pago DROP CONSTRAINT medio_pago_pkey;
       woaho            postgres    false    253            J           2606    17076 *   medio_pago_usuario medio_pago_usuario_pkey 
   CONSTRAINT     z   ALTER TABLE ONLY woaho.medio_pago_usuario
    ADD CONSTRAINT medio_pago_usuario_pkey PRIMARY KEY (medio_pago_usuario_id);
 S   ALTER TABLE ONLY woaho.medio_pago_usuario DROP CONSTRAINT medio_pago_usuario_pkey;
       woaho            postgres    false    266            L           2606    17103 "   mensaje_correo mensaje_correo_pkey 
   CONSTRAINT     n   ALTER TABLE ONLY woaho.mensaje_correo
    ADD CONSTRAINT mensaje_correo_pkey PRIMARY KEY (mensaje_correo_id);
 K   ALTER TABLE ONLY woaho.mensaje_correo DROP CONSTRAINT mensaje_correo_pkey;
       woaho            postgres    false    268            @           2606    16914 &   mensaje_pantalla mensaje_pantalla_pkey 
   CONSTRAINT     t   ALTER TABLE ONLY woaho.mensaje_pantalla
    ADD CONSTRAINT mensaje_pantalla_pkey PRIMARY KEY (mensaje_pantalla_id);
 O   ALTER TABLE ONLY woaho.mensaje_pantalla DROP CONSTRAINT mensaje_pantalla_pkey;
       woaho            postgres    false    257                       2606    16506    mensaje mensaje_pkey 
   CONSTRAINT     Y   ALTER TABLE ONLY woaho.mensaje
    ADD CONSTRAINT mensaje_pkey PRIMARY KEY (mensaje_id);
 =   ALTER TABLE ONLY woaho.mensaje DROP CONSTRAINT mensaje_pkey;
       woaho            postgres    false    234            &           2606    16654    moneda moneda_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY woaho.moneda
    ADD CONSTRAINT moneda_pkey PRIMARY KEY (moneda_id);
 ;   ALTER TABLE ONLY woaho.moneda DROP CONSTRAINT moneda_pkey;
       woaho            postgres    false    244            >           2606    16898    pantalla pantalla_pkey 
   CONSTRAINT     \   ALTER TABLE ONLY woaho.pantalla
    ADD CONSTRAINT pantalla_pkey PRIMARY KEY (pantalla_id);
 ?   ALTER TABLE ONLY woaho.pantalla DROP CONSTRAINT pantalla_pkey;
       woaho            postgres    false    256                        2606    16622    parametro parametro_pkey 
   CONSTRAINT     _   ALTER TABLE ONLY woaho.parametro
    ADD CONSTRAINT parametro_pkey PRIMARY KEY (parametro_id);
 A   ALTER TABLE ONLY woaho.parametro DROP CONSTRAINT parametro_pkey;
       woaho            postgres    false    241            :           2606    16845    pedido pedido_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY woaho.pedido
    ADD CONSTRAINT pedido_pkey PRIMARY KEY (pedido_id);
 ;   ALTER TABLE ONLY woaho.pedido DROP CONSTRAINT pedido_pkey;
       woaho            postgres    false    254                       2606    16483    profesion profesion_pkey 
   CONSTRAINT     _   ALTER TABLE ONLY woaho.profesion
    ADD CONSTRAINT profesion_pkey PRIMARY KEY (profesion_id);
 A   ALTER TABLE ONLY woaho.profesion DROP CONSTRAINT profesion_pkey;
       woaho            postgres    false    233            2           2606    16774    profesional profesional_pkey 
   CONSTRAINT     e   ALTER TABLE ONLY woaho.profesional
    ADD CONSTRAINT profesional_pkey PRIMARY KEY (profesional_id);
 E   ALTER TABLE ONLY woaho.profesional DROP CONSTRAINT profesional_pkey;
       woaho            postgres    false    250            .           2606    16730    promocion promocion_pkey 
   CONSTRAINT     _   ALTER TABLE ONLY woaho.promocion
    ADD CONSTRAINT promocion_pkey PRIMARY KEY (promocion_id);
 A   ALTER TABLE ONLY woaho.promocion DROP CONSTRAINT promocion_pkey;
       woaho            postgres    false    248            H           2606    17050 (   servicio_favorito servicio_favorito_pkey 
   CONSTRAINT     w   ALTER TABLE ONLY woaho.servicio_favorito
    ADD CONSTRAINT servicio_favorito_pkey PRIMARY KEY (servicio_favorito_id);
 Q   ALTER TABLE ONLY woaho.servicio_favorito DROP CONSTRAINT servicio_favorito_pkey;
       woaho            postgres    false    264            *           2606    16677    servicio servicio_pkey 
   CONSTRAINT     \   ALTER TABLE ONLY woaho.servicio
    ADD CONSTRAINT servicio_pkey PRIMARY KEY (servicio_id);
 ?   ALTER TABLE ONLY woaho.servicio DROP CONSTRAINT servicio_pkey;
       woaho            postgres    false    246            ,           2606    16701    tarifa tarifa_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY woaho.tarifa
    ADD CONSTRAINT tarifa_pkey PRIMARY KEY (tarifa_id);
 ;   ALTER TABLE ONLY woaho.tarifa DROP CONSTRAINT tarifa_pkey;
       woaho            postgres    false    247                       2606    16545    territorio territorio_pkey 
   CONSTRAINT     b   ALTER TABLE ONLY woaho.territorio
    ADD CONSTRAINT territorio_pkey PRIMARY KEY (territorio_id);
 C   ALTER TABLE ONLY woaho.territorio DROP CONSTRAINT territorio_pkey;
       woaho            postgres    false    236            
           2606    16451    tipo tipo_pantalla_pkey 
   CONSTRAINT     Y   ALTER TABLE ONLY woaho.tipo
    ADD CONSTRAINT tipo_pantalla_pkey PRIMARY KEY (tipo_id);
 @   ALTER TABLE ONLY woaho.tipo DROP CONSTRAINT tipo_pantalla_pkey;
       woaho            postgres    false    231                       2606    16536 $   tipo_territorio tipo_territorio_pkey 
   CONSTRAINT     q   ALTER TABLE ONLY woaho.tipo_territorio
    ADD CONSTRAINT tipo_territorio_pkey PRIMARY KEY (tipo_territorio_id);
 M   ALTER TABLE ONLY woaho.tipo_territorio DROP CONSTRAINT tipo_territorio_pkey;
       woaho            postgres    false    235            D           2606    17014    traduccion traduccion_pkey 
   CONSTRAINT     b   ALTER TABLE ONLY woaho.traduccion
    ADD CONSTRAINT traduccion_pkey PRIMARY KEY (traduccion_id);
 C   ALTER TABLE ONLY woaho.traduccion DROP CONSTRAINT traduccion_pkey;
       woaho            woaho    false    260            4           2606    16793    ubicacion ubicacion_pkey 
   CONSTRAINT     _   ALTER TABLE ONLY woaho.ubicacion
    ADD CONSTRAINT ubicacion_pkey PRIMARY KEY (ubicacion_id);
 A   ALTER TABLE ONLY woaho.ubicacion DROP CONSTRAINT ubicacion_pkey;
       woaho            postgres    false    251            (           2606    16668     unidad_tarifa unidad_tarifa_pkey 
   CONSTRAINT     k   ALTER TABLE ONLY woaho.unidad_tarifa
    ADD CONSTRAINT unidad_tarifa_pkey PRIMARY KEY (unidad_tarifa_id);
 I   ALTER TABLE ONLY woaho.unidad_tarifa DROP CONSTRAINT unidad_tarifa_pkey;
       woaho            postgres    false    245                       2606    16559    usuario usuario_pkey 
   CONSTRAINT     Y   ALTER TABLE ONLY woaho.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (usuario_id);
 =   ALTER TABLE ONLY woaho.usuario DROP CONSTRAINT usuario_pkey;
       woaho            postgres    false    237            g           2606    16813 (   calificacion FK_CALIFICACION_PROFESIONAL    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.calificacion
    ADD CONSTRAINT "FK_CALIFICACION_PROFESIONAL" FOREIGN KEY (calificacion_profesional) REFERENCES woaho.profesional(profesional_id);
 S   ALTER TABLE ONLY woaho.calificacion DROP CONSTRAINT "FK_CALIFICACION_PROFESIONAL";
       woaho          postgres    false    250    3122    252            h           2606    16818 %   calificacion FK_CALIFICACION_SERVICIO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.calificacion
    ADD CONSTRAINT "FK_CALIFICACION_SERVICIO" FOREIGN KEY (calificacion_servicio) REFERENCES woaho.servicio(servicio_id);
 P   ALTER TABLE ONLY woaho.calificacion DROP CONSTRAINT "FK_CALIFICACION_SERVICIO";
       woaho          postgres    false    246    3114    252            f           2606    16808 $   calificacion FK_CALIFICACION_USUARIO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.calificacion
    ADD CONSTRAINT "FK_CALIFICACION_USUARIO" FOREIGN KEY (calificacion_usuario) REFERENCES woaho.usuario(usuario_id);
 O   ALTER TABLE ONLY woaho.calificacion DROP CONSTRAINT "FK_CALIFICACION_USUARIO";
       woaho          postgres    false    3096    252    237            q           2606    16885 !   cancelacion FK_CANCELACION_PEDIDO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.cancelacion
    ADD CONSTRAINT "FK_CANCELACION_PEDIDO" FOREIGN KEY (cancelacion_pedido) REFERENCES woaho.pedido(pedido_id);
 L   ALTER TABLE ONLY woaho.cancelacion DROP CONSTRAINT "FK_CANCELACION_PEDIDO";
       woaho          postgres    false    255    3130    254            U           2606    16641    categoria FK_CATEGORIA_IMAGEN    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.categoria
    ADD CONSTRAINT "FK_CATEGORIA_IMAGEN" FOREIGN KEY (categoria_imagen) REFERENCES woaho.imagen(imagen_id);
 H   ALTER TABLE ONLY woaho.categoria DROP CONSTRAINT "FK_CATEGORIA_IMAGEN";
       woaho          postgres    false    242    3106    243            Q           2606    16585    codigo FK_CODIGO_ESTADO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.codigo
    ADD CONSTRAINT "FK_CODIGO_ESTADO" FOREIGN KEY (codigo_estado) REFERENCES woaho.estado(estado_id);
 B   ALTER TABLE ONLY woaho.codigo DROP CONSTRAINT "FK_CODIGO_ESTADO";
       woaho          postgres    false    3098    238    239            a           2606    16755 %   codigo_promocional FK_COD_PROM_ESTADO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.codigo_promocional
    ADD CONSTRAINT "FK_COD_PROM_ESTADO" FOREIGN KEY (codigo_promocional_estado) REFERENCES woaho.estado(estado_id);
 P   ALTER TABLE ONLY woaho.codigo_promocional DROP CONSTRAINT "FK_COD_PROM_ESTADO";
       woaho          postgres    false    3098    249    238            b           2606    16760 (   codigo_promocional FK_COD_PROM_PROMOCION    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.codigo_promocional
    ADD CONSTRAINT "FK_COD_PROM_PROMOCION" FOREIGN KEY (codigo_promocional_promocion) REFERENCES woaho.promocion(promocion_id);
 S   ALTER TABLE ONLY woaho.codigo_promocional DROP CONSTRAINT "FK_COD_PROM_PROMOCION";
       woaho          postgres    false    249    248    3118            `           2606    16750 &   codigo_promocional FK_COD_PROM_USUARIO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.codigo_promocional
    ADD CONSTRAINT "FK_COD_PROM_USUARIO" FOREIGN KEY (codigo_promocional_usuario) REFERENCES woaho.usuario(usuario_id);
 Q   ALTER TABLE ONLY woaho.codigo_promocional DROP CONSTRAINT "FK_COD_PROM_USUARIO";
       woaho          postgres    false    3096    249    237            T           2606    16609    direccion FK_DIRECCION_ESTADO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.direccion
    ADD CONSTRAINT "FK_DIRECCION_ESTADO" FOREIGN KEY (direccion_estado) REFERENCES woaho.estado(estado_id);
 H   ALTER TABLE ONLY woaho.direccion DROP CONSTRAINT "FK_DIRECCION_ESTADO";
       woaho          postgres    false    3098    238    240            R           2606    16599 !   direccion FK_DIRECCION_TERRITORIO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.direccion
    ADD CONSTRAINT "FK_DIRECCION_TERRITORIO" FOREIGN KEY (direccion_territorio_id) REFERENCES woaho.territorio(territorio_id);
 L   ALTER TABLE ONLY woaho.direccion DROP CONSTRAINT "FK_DIRECCION_TERRITORIO";
       woaho          postgres    false    240    3092    236            S           2606    16604    direccion FK_DIRECCION_USUARIO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.direccion
    ADD CONSTRAINT "FK_DIRECCION_USUARIO" FOREIGN KEY (direccion_usuario) REFERENCES woaho.usuario(usuario_id);
 I   ALTER TABLE ONLY woaho.direccion DROP CONSTRAINT "FK_DIRECCION_USUARIO";
       woaho          postgres    false    237    3096    240            v           2606    16999    etiqueta FK_ETIQUETA_IDIOMA    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.etiqueta
    ADD CONSTRAINT "FK_ETIQUETA_IDIOMA" FOREIGN KEY (etiqueta_idioma) REFERENCES woaho.idioma(idioma_id);
 F   ALTER TABLE ONLY woaho.etiqueta DROP CONSTRAINT "FK_ETIQUETA_IDIOMA";
       woaho          postgres    false    258    232    3084            x           2606    17051 &   servicio_favorito FK_FAVORITO_SERVICIO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.servicio_favorito
    ADD CONSTRAINT "FK_FAVORITO_SERVICIO" FOREIGN KEY (servicio_favorito_servicio) REFERENCES woaho.servicio(servicio_id);
 Q   ALTER TABLE ONLY woaho.servicio_favorito DROP CONSTRAINT "FK_FAVORITO_SERVICIO";
       woaho          postgres    false    264    246    3114            y           2606    17056 %   servicio_favorito FK_FAVORITO_USUARIO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.servicio_favorito
    ADD CONSTRAINT "FK_FAVORITO_USUARIO" FOREIGN KEY (servicio_favorito_usuario) REFERENCES woaho.usuario(usuario_id);
 P   ALTER TABLE ONLY woaho.servicio_favorito DROP CONSTRAINT "FK_FAVORITO_USUARIO";
       woaho          postgres    false    237    3096    264            i           2606    16832 #   medio_pago FK_MEDIO_PAGO_TERRITORIO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.medio_pago
    ADD CONSTRAINT "FK_MEDIO_PAGO_TERRITORIO" FOREIGN KEY (medio_pago_territorio) REFERENCES woaho.territorio(territorio_id);
 N   ALTER TABLE ONLY woaho.medio_pago DROP CONSTRAINT "FK_MEDIO_PAGO_TERRITORIO";
       woaho          postgres    false    3092    253    236            z           2606    17077 /   medio_pago_usuario FK_MEDIO_PAGO_USUARIO_ESTADO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.medio_pago_usuario
    ADD CONSTRAINT "FK_MEDIO_PAGO_USUARIO_ESTADO" FOREIGN KEY (medio_pago_usuario_estado) REFERENCES woaho.estado(estado_id);
 Z   ALTER TABLE ONLY woaho.medio_pago_usuario DROP CONSTRAINT "FK_MEDIO_PAGO_USUARIO_ESTADO";
       woaho          postgres    false    3098    266    238            |           2606    17087 3   medio_pago_usuario FK_MEDIO_PAGO_USUARIO_MEDIO_PAGO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.medio_pago_usuario
    ADD CONSTRAINT "FK_MEDIO_PAGO_USUARIO_MEDIO_PAGO" FOREIGN KEY (medio_pago_usuario_medio_pago) REFERENCES woaho.medio_pago(medio_pago_id);
 ^   ALTER TABLE ONLY woaho.medio_pago_usuario DROP CONSTRAINT "FK_MEDIO_PAGO_USUARIO_MEDIO_PAGO";
       woaho          postgres    false    3128    266    253            {           2606    17082 0   medio_pago_usuario FK_MEDIO_PAGO_USUARIO_USUARIO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.medio_pago_usuario
    ADD CONSTRAINT "FK_MEDIO_PAGO_USUARIO_USUARIO" FOREIGN KEY (medio_pago_usuario_usuario) REFERENCES woaho.usuario(usuario_id);
 [   ALTER TABLE ONLY woaho.medio_pago_usuario DROP CONSTRAINT "FK_MEDIO_PAGO_USUARIO_USUARIO";
       woaho          postgres    false    266    237    3096            t           2606    16915 ,   mensaje_pantalla FK_MENSAJE_PANTALLA_MENSAJE    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.mensaje_pantalla
    ADD CONSTRAINT "FK_MENSAJE_PANTALLA_MENSAJE" FOREIGN KEY (mensaje_pantalla_mensaje_id) REFERENCES woaho.mensaje(mensaje_id);
 W   ALTER TABLE ONLY woaho.mensaje_pantalla DROP CONSTRAINT "FK_MENSAJE_PANTALLA_MENSAJE";
       woaho          postgres    false    3088    234    257            u           2606    16920 -   mensaje_pantalla FK_MENSAJE_PANTALLA_PANTALLA    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.mensaje_pantalla
    ADD CONSTRAINT "FK_MENSAJE_PANTALLA_PANTALLA" FOREIGN KEY (mensaje_pantalla_pantalla_id) REFERENCES woaho.pantalla(pantalla_id);
 X   ALTER TABLE ONLY woaho.mensaje_pantalla DROP CONSTRAINT "FK_MENSAJE_PANTALLA_PANTALLA";
       woaho          postgres    false    256    257    3134            M           2606    16507    mensaje FK_MENSAJE_TIPO    FK CONSTRAINT        ALTER TABLE ONLY woaho.mensaje
    ADD CONSTRAINT "FK_MENSAJE_TIPO" FOREIGN KEY (mensaje_tipo) REFERENCES woaho.tipo(tipo_id);
 B   ALTER TABLE ONLY woaho.mensaje DROP CONSTRAINT "FK_MENSAJE_TIPO";
       woaho          postgres    false    3082    234    231            V           2606    16655    moneda FK_MONEDA_TERRITORIO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.moneda
    ADD CONSTRAINT "FK_MONEDA_TERRITORIO" FOREIGN KEY (moneda_territorio) REFERENCES woaho.territorio(territorio_id);
 F   ALTER TABLE ONLY woaho.moneda DROP CONSTRAINT "FK_MONEDA_TERRITORIO";
       woaho          postgres    false    244    3092    236            s           2606    16904    pantalla FK_PANTALLA_IMAGEN    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.pantalla
    ADD CONSTRAINT "FK_PANTALLA_IMAGEN" FOREIGN KEY (pantalla_imagen) REFERENCES woaho.imagen(imagen_id);
 F   ALTER TABLE ONLY woaho.pantalla DROP CONSTRAINT "FK_PANTALLA_IMAGEN";
       woaho          postgres    false    242    256    3106            r           2606    16899 !   pantalla FK_PANTALLA_TIPO_PANTLLA    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.pantalla
    ADD CONSTRAINT "FK_PANTALLA_TIPO_PANTLLA" FOREIGN KEY (pantalla_tipo_pantalla) REFERENCES woaho.tipo(tipo_id);
 L   ALTER TABLE ONLY woaho.pantalla DROP CONSTRAINT "FK_PANTALLA_TIPO_PANTLLA";
       woaho          postgres    false    3082    256    231            m           2606    16861    pedido FK_PEDIDO_DIRECCION    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.pedido
    ADD CONSTRAINT "FK_PEDIDO_DIRECCION" FOREIGN KEY (pedido_direccion) REFERENCES woaho.direccion(direccion_id);
 E   ALTER TABLE ONLY woaho.pedido DROP CONSTRAINT "FK_PEDIDO_DIRECCION";
       woaho          postgres    false    240    254    3102            l           2606    16856    pedido FK_PEDIDO_ESTADO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.pedido
    ADD CONSTRAINT "FK_PEDIDO_ESTADO" FOREIGN KEY (pedido_estado) REFERENCES woaho.estado(estado_id);
 B   ALTER TABLE ONLY woaho.pedido DROP CONSTRAINT "FK_PEDIDO_ESTADO";
       woaho          postgres    false    254    238    3098            o           2606    16871    pedido FK_PEDIDO_MEDIO_PAGO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.pedido
    ADD CONSTRAINT "FK_PEDIDO_MEDIO_PAGO" FOREIGN KEY (pedido_medio_pago) REFERENCES woaho.medio_pago(medio_pago_id);
 F   ALTER TABLE ONLY woaho.pedido DROP CONSTRAINT "FK_PEDIDO_MEDIO_PAGO";
       woaho          postgres    false    254    253    3128            n           2606    16866    pedido FK_PEDIDO_PROFESIONAL    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.pedido
    ADD CONSTRAINT "FK_PEDIDO_PROFESIONAL" FOREIGN KEY (pedido_profesional) REFERENCES woaho.profesional(profesional_id);
 G   ALTER TABLE ONLY woaho.pedido DROP CONSTRAINT "FK_PEDIDO_PROFESIONAL";
       woaho          postgres    false    250    3122    254            j           2606    16846    pedido FK_PEDIDO_SERVICIO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.pedido
    ADD CONSTRAINT "FK_PEDIDO_SERVICIO" FOREIGN KEY (pedido_servicio) REFERENCES woaho.servicio(servicio_id);
 D   ALTER TABLE ONLY woaho.pedido DROP CONSTRAINT "FK_PEDIDO_SERVICIO";
       woaho          postgres    false    254    246    3114            k           2606    16851    pedido FK_PEDIDO_USUARIO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.pedido
    ADD CONSTRAINT "FK_PEDIDO_USUARIO" FOREIGN KEY (pedido_servicio) REFERENCES woaho.usuario(usuario_id);
 C   ALTER TABLE ONLY woaho.pedido DROP CONSTRAINT "FK_PEDIDO_USUARIO";
       woaho          postgres    false    237    254    3096            d           2606    16780     profesional FK_PROFESIONAL_ICONO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.profesional
    ADD CONSTRAINT "FK_PROFESIONAL_ICONO" FOREIGN KEY (profesional_imagen_icono) REFERENCES woaho.imagen(imagen_id);
 K   ALTER TABLE ONLY woaho.profesional DROP CONSTRAINT "FK_PROFESIONAL_ICONO";
       woaho          postgres    false    250    3106    242            c           2606    16775 %   profesional FK_PROFESIONAL_TERRITORIO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.profesional
    ADD CONSTRAINT "FK_PROFESIONAL_TERRITORIO" FOREIGN KEY (profesional_nacionalidad) REFERENCES woaho.territorio(territorio_id);
 P   ALTER TABLE ONLY woaho.profesional DROP CONSTRAINT "FK_PROFESIONAL_TERRITORIO";
       woaho          postgres    false    3092    250    236            _           2606    16736    promocion FK_PROM_ESTADO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.promocion
    ADD CONSTRAINT "FK_PROM_ESTADO" FOREIGN KEY (promocion_estado) REFERENCES woaho.estado(estado_id);
 C   ALTER TABLE ONLY woaho.promocion DROP CONSTRAINT "FK_PROM_ESTADO";
       woaho          postgres    false    248    238    3098            ^           2606    16731    promocion FK_PROM_TARIFA    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.promocion
    ADD CONSTRAINT "FK_PROM_TARIFA" FOREIGN KEY (promocion_tarifa) REFERENCES woaho.tarifa(tarifa_id);
 C   ALTER TABLE ONLY woaho.promocion DROP CONSTRAINT "FK_PROM_TARIFA";
       woaho          postgres    false    3116    247    248            W           2606    16678    servicio FK_SERVICIO_CATEGORIA    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.servicio
    ADD CONSTRAINT "FK_SERVICIO_CATEGORIA" FOREIGN KEY (servicio_categoria) REFERENCES woaho.categoria(categoria_id);
 I   ALTER TABLE ONLY woaho.servicio DROP CONSTRAINT "FK_SERVICIO_CATEGORIA";
       woaho          postgres    false    3108    243    246            X           2606    16683    servicio FK_SERVICIO_IMAGEN    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.servicio
    ADD CONSTRAINT "FK_SERVICIO_IMAGEN" FOREIGN KEY (servicio_imagen) REFERENCES woaho.imagen(imagen_id);
 F   ALTER TABLE ONLY woaho.servicio DROP CONSTRAINT "FK_SERVICIO_IMAGEN";
       woaho          postgres    false    242    246    3106            Y           2606    16688    servicio FK_SERVICIO_TERRITORIO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.servicio
    ADD CONSTRAINT "FK_SERVICIO_TERRITORIO" FOREIGN KEY (servicio_territorio) REFERENCES woaho.territorio(territorio_id);
 J   ALTER TABLE ONLY woaho.servicio DROP CONSTRAINT "FK_SERVICIO_TERRITORIO";
       woaho          postgres    false    3092    236    246            Z           2606    16702    tarifa FK_TARIFA_MONEDA    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.tarifa
    ADD CONSTRAINT "FK_TARIFA_MONEDA" FOREIGN KEY (tarifa_moneda) REFERENCES woaho.moneda(moneda_id);
 B   ALTER TABLE ONLY woaho.tarifa DROP CONSTRAINT "FK_TARIFA_MONEDA";
       woaho          postgres    false    247    244    3110            \           2606    16712    tarifa FK_TARIFA_SERVICIO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.tarifa
    ADD CONSTRAINT "FK_TARIFA_SERVICIO" FOREIGN KEY (tarifa_servicio) REFERENCES woaho.servicio(servicio_id);
 D   ALTER TABLE ONLY woaho.tarifa DROP CONSTRAINT "FK_TARIFA_SERVICIO";
       woaho          postgres    false    3114    247    246            [           2606    16707    tarifa FK_TARIFA_TERRITORIO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.tarifa
    ADD CONSTRAINT "FK_TARIFA_TERRITORIO" FOREIGN KEY (tarifa_territorio) REFERENCES woaho.territorio(territorio_id);
 F   ALTER TABLE ONLY woaho.tarifa DROP CONSTRAINT "FK_TARIFA_TERRITORIO";
       woaho          postgres    false    3092    236    247            ]           2606    16717    tarifa FK_TARIFA_UNIDAD    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.tarifa
    ADD CONSTRAINT "FK_TARIFA_UNIDAD" FOREIGN KEY (tarifa_unidad) REFERENCES woaho.unidad_tarifa(unidad_tarifa_id);
 B   ALTER TABLE ONLY woaho.tarifa DROP CONSTRAINT "FK_TARIFA_UNIDAD";
       woaho          postgres    false    247    3112    245            N           2606    16546 (   territorio FK_TERRITORIO_TIPO_TERRITORIO    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.territorio
    ADD CONSTRAINT "FK_TERRITORIO_TIPO_TERRITORIO" FOREIGN KEY (territorio_tipo) REFERENCES woaho.tipo_territorio(tipo_territorio_id);
 S   ALTER TABLE ONLY woaho.territorio DROP CONSTRAINT "FK_TERRITORIO_TIPO_TERRITORIO";
       woaho          postgres    false    3090    236    235            w           2606    17015    traduccion FK_TRADUCCION_IDIOMA    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.traduccion
    ADD CONSTRAINT "FK_TRADUCCION_IDIOMA" FOREIGN KEY (traduccion_idioma) REFERENCES woaho.idioma(idioma_id);
 J   ALTER TABLE ONLY woaho.traduccion DROP CONSTRAINT "FK_TRADUCCION_IDIOMA";
       woaho          woaho    false    232    260    3084            e           2606    16794 "   ubicacion FK_UBICACION_PROFESIONAL    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.ubicacion
    ADD CONSTRAINT "FK_UBICACION_PROFESIONAL" FOREIGN KEY (ubicacion_profesional) REFERENCES woaho.profesional(profesional_id);
 M   ALTER TABLE ONLY woaho.ubicacion DROP CONSTRAINT "FK_UBICACION_PROFESIONAL";
       woaho          postgres    false    3122    251    250            O           2606    16930 &   territorio fk1wg9n3xubkio4n3y16testobd    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.territorio
    ADD CONSTRAINT fk1wg9n3xubkio4n3y16testobd FOREIGN KEY (territorio_padre) REFERENCES woaho.territorio(territorio_id);
 O   ALTER TABLE ONLY woaho.territorio DROP CONSTRAINT fk1wg9n3xubkio4n3y16testobd;
       woaho          postgres    false    236    3092    236            P           2606    17061 &   territorio fkn3m1oitow4b70wae6i40gd87y    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.territorio
    ADD CONSTRAINT fkn3m1oitow4b70wae6i40gd87y FOREIGN KEY (territorio_imagen) REFERENCES woaho.imagen(imagen_id);
 O   ALTER TABLE ONLY woaho.territorio DROP CONSTRAINT fkn3m1oitow4b70wae6i40gd87y;
       woaho          postgres    false    236    3106    242            p           2606    16925 "   pedido fkp80nbrpi74d5lutv2lo2ej3b3    FK CONSTRAINT     �   ALTER TABLE ONLY woaho.pedido
    ADD CONSTRAINT fkp80nbrpi74d5lutv2lo2ej3b3 FOREIGN KEY (pedido_usuario) REFERENCES woaho.usuario(usuario_id);
 K   ALTER TABLE ONLY woaho.pedido DROP CONSTRAINT fkp80nbrpi74d5lutv2lo2ej3b3;
       woaho          postgres    false    237    254    3096           