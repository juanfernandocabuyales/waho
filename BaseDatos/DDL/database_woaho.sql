/***************************************************************************************************
	  Zona de secuencias
***************************************************************************************************/
CREATE SEQUENCE woaho.sec_mensaje CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 999999 CACHE 1;
ALTER SEQUENCE woaho.sec_mensaje OWNER TO postgres;

CREATE SEQUENCE woaho.sec_mensaje_pantalla CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 999999 CACHE 1;
ALTER SEQUENCE woaho.sec_mensaje_pantalla OWNER TO postgres;

CREATE SEQUENCE woaho.sec_pantalla CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 999999 CACHE 1;
ALTER SEQUENCE woaho.sec_pantalla OWNER TO postgres;

CREATE SEQUENCE woaho.sec_tipo CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 999999 CACHE 1;
ALTER SEQUENCE woaho.sec_tipo OWNER TO postgres;

CREATE SEQUENCE woaho.sec_territorio CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 999999 CACHE 1;
ALTER SEQUENCE woaho.sec_tipo OWNER TO postgres;

CREATE SEQUENCE woaho.sec_tipo_territorio CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 999999 CACHE 1;
ALTER SEQUENCE woaho.sec_tipo_territorio OWNER TO postgres;

CREATE SEQUENCE woaho.sec_usuario CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 999999 CACHE 1;
ALTER SEQUENCE woaho.sec_usuario OWNER TO postgres;

CREATE SEQUENCE woaho.sec_estado CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 999999 CACHE 1;
ALTER SEQUENCE woaho.sec_estado OWNER TO postgres;

CREATE SEQUENCE woaho.sec_codigo CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 999999 CACHE 1;
ALTER SEQUENCE woaho.sec_codigo OWNER TO postgres;

CREATE SEQUENCE woaho.sec_parametro CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 999999 CACHE 1;
ALTER SEQUENCE woaho.sec_parametro OWNER TO postgres;

CREATE SEQUENCE woaho.sec_direccion CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 999999 CACHE 1;
ALTER SEQUENCE woaho.sec_direccion OWNER TO postgres;

CREATE SEQUENCE woaho.sec_categoria CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 999999 CACHE 1;
ALTER SEQUENCE woaho.sec_categoria OWNER TO postgres;

CREATE SEQUENCE woaho.sec_moneda CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 999999 CACHE 1;
ALTER SEQUENCE woaho.sec_moneda OWNER TO postgres;

CREATE SEQUENCE woaho.sec_servicio CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 999999 CACHE 1;
ALTER SEQUENCE woaho.sec_servicio OWNER TO postgres;

CREATE SEQUENCE woaho.sec_tarifa CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 999999 CACHE 1;
ALTER SEQUENCE woaho.sec_tarifa OWNER TO postgres;

CREATE SEQUENCE woaho.sec_codigo_promocional CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 999999 CACHE 1;
ALTER SEQUENCE woaho.sec_codigo_promocional OWNER TO postgres;

CREATE SEQUENCE woaho.sec_promocion CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 999999 CACHE 1;
ALTER SEQUENCE woaho.sec_promocion OWNER TO postgres;

/***************************************************************************************************
	  Zona de tablas
***************************************************************************************************/
CREATE TABLE woaho.tipo
(
    tipo_id integer NOT NULL DEFAULT nextval('woaho.sec_tipo'::regclass),
    tipo_nombre character varying(4000) COLLATE pg_catalog."default",
    CONSTRAINT tipo_pantalla_pkey PRIMARY KEY (tipo_id)
);
ALTER TABLE woaho.tipo
    OWNER to postgres;
COMMENT ON TABLE woaho.tipo
    IS 'Tabla que contiene la información de los tipos de pantalla';
    

CREATE TABLE woaho.pantalla
(
    pantalla_id integer NOT NULL DEFAULT nextval('woaho.sec_pantalla'::regclass),
    pantalla_nombre character varying(4000) COLLATE pg_catalog."default",
    pantalla_imagen character varying(4000) COLLATE pg_catalog."default",
    pantalla_tipo_pantalla integer,
    CONSTRAINT pantalla_pkey PRIMARY KEY (pantalla_id),
    CONSTRAINT "FK_PANTALLA_TIPO_PANTLLA" FOREIGN KEY (pantalla_tipo_pantalla)
        REFERENCES woaho.tipo (tipo_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);
ALTER TABLE woaho.pantalla
    OWNER to postgres;
COMMENT ON TABLE woaho.pantalla
    IS 'Tabla que contiene la información de las pantallas';
    
CREATE TABLE woaho.mensaje
(
    mensaje_id integer NOT NULL DEFAULT nextval('woaho.sec_mensaje'::regclass),
    mensaje_mensaje character varying(4000) COLLATE pg_catalog."default",
    mensaje_tipo integer,
    CONSTRAINT mensaje_pkey PRIMARY KEY (mensaje_id),
    CONSTRAINT "FK_MENSAJE_TIPO" FOREIGN KEY (mensaje_tipo)
        REFERENCES woaho.tipo (tipo_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);
ALTER TABLE woaho.mensaje
    OWNER to postgres;
COMMENT ON TABLE woaho.mensaje
    IS 'Tabla que contiene la información de los mensajes por pantalla';

CREATE TABLE woaho.mensaje_pantalla
(
    mensaje_pantalla_id integer NOT NULL DEFAULT nextval('woaho.sec_mensaje_pantalla'::regclass),
    mensaje_pantalla_pantalla_id integer,
    mensaje_pantalla_mensaje_id integer,
    CONSTRAINT mensaje_pantalla_pkey PRIMARY KEY (mensaje_pantalla_id),
    CONSTRAINT "FK_MENSAJE_PANTALLA_MENSAJE" FOREIGN KEY (mensaje_pantalla_mensaje_id)
        REFERENCES woaho.mensaje (mensaje_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT "FK_MENSAJE_PANTALLA_PANTALLA" FOREIGN KEY (mensaje_pantalla_pantalla_id)
        REFERENCES woaho.pantalla (pantalla_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);
ALTER TABLE woaho.mensaje_pantalla
    OWNER to postgres;
COMMENT ON TABLE woaho.mensaje_pantalla
    IS 'Tabla que contiene la relacion entre mensajes y pantallas';
    
CREATE TABLE woaho.tipo_territorio
(
    tipo_territorio_id integer NOT NULL DEFAULT nextval('woaho.sec_tipo_territorio'::regclass),
    tipo_territorio_nombre character varying(4000),
    CONSTRAINT tipo_territorio_pkey PRIMARY KEY (tipo_territorio_id)
);
ALTER TABLE woaho.tipo_territorio
    OWNER to postgres;
COMMENT ON TABLE woaho.tipo_territorio
    IS 'Tabla que contiene los tipos de territorios';
    
CREATE TABLE woaho.territorio
(
    territorio_id integer NOT NULL DEFAULT nextval('woaho.sec_territorio'::regclass),
    territorio_nombre character varying(4000),
    territorio_padre integer,
    territorio_tipo integer,
    territorio_codigo character varying(4000),
    CONSTRAINT territorio_pkey PRIMARY KEY (territorio_id),
    CONSTRAINT "FK_TERRITORIO_TIPO_TERRITORIO" FOREIGN KEY (territorio_tipo)
        REFERENCES woaho.tipo_territorio (tipo_territorio_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);
ALTER TABLE woaho.territorio
    OWNER to postgres;
COMMENT ON TABLE woaho.territorio
    IS 'Tabla que contiene los territorios registrados en el aplicativo';
    

CREATE TABLE woaho.usuario
(
    usuario_id integer NOT NULL DEFAULT nextval('woaho.sec_usuario'::regclass),
    usuario_nombre character varying(4000),
    usuario_apellido character varying(4000),
    usuario_celular character varying(4000),
    usuario_correo character varying(4000),
    usuario_acepta_terminos character varying(4000),
    usuario_fecha_hora_acepta_terminos TIMESTAMP,
    usuario_clave character varying(4000),
    usuario_direccion integer,
    CONSTRAINT usuario_pkey PRIMARY KEY (usuario_id),
    CONSTRAINT celular_key UNIQUE (usuario_celular)
);
ALTER TABLE woaho.usuario
    OWNER to postgres;
COMMENT ON TABLE woaho.usuario
    IS 'Tabla que contiene los usuarios registrados en el aplicativo';
    
CREATE TABLE woaho.estado
(
    estado_id integer NOT NULL DEFAULT nextval('woaho.sec_estado'::regclass),
    estado_codigo character varying(4000),
    CONSTRAINT estado_pkey PRIMARY KEY (estado_id)
);
ALTER TABLE woaho.estado
    OWNER to postgres;
COMMENT ON COLUMN woaho.estado.estado_codigo IS 'A activo, I inactivo, P pendiente, R rechazado';
COMMENT ON TABLE woaho.estado
    IS 'Tabla que contiene los estados para el aplicativo';
    
CREATE TABLE woaho.codigo
(
    codigo_id integer NOT NULL DEFAULT nextval('woaho.sec_codigo'::regclass),
    codigo_numero character varying(4000),
    codigo_celular character varying(4000),
    codigo_intentos integer,
    codigo_fecha_hora_registro TIMESTAMP,
    codigo_estado integer,
    CONSTRAINT codigo_pkey PRIMARY KEY (codigo_id),
    CONSTRAINT "FK_CODIGO_USUARIO" FOREIGN KEY (codigo_celular)
        REFERENCES woaho.usuario (usuario_celular) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT "FK_CODIGO_ESTADO" FOREIGN KEY (codigo_estado)
        REFERENCES woaho.estado (estado_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
);
ALTER TABLE woaho.codigo
    OWNER to postgres;
COMMENT ON TABLE woaho.codigo
    IS 'Tabla que contiene los codigos generados para completar el registro';
    
CREATE TABLE woaho.direccion
(
    direccion_id integer NOT NULL DEFAULT nextval('woaho.sec_direccion'::regclass),
    direccion_nombre character varying(4000),
    direccion_descripcion character varying(4000),
    direccion_territorio_id integer,
    direccion_edificacion character varying(4000),
    direccion_estado integer,
    direccion_usuario integer,
    direccion_latitud character varying(4000),
    direccion_longitud character varying(4000),
    CONSTRAINT direccion_pkey PRIMARY KEY (direccion_id),
    CONSTRAINT "FK_DIRECCION_TERRITORIO" FOREIGN KEY (direccion_territorio_id)
        REFERENCES woaho.territorio (territorio_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT "FK_DIRECCION_USUARIO" FOREIGN KEY (direccion_usuario)
        REFERENCES woaho.usuario (usuario_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT "FK_DIRECCION_ESTADO" FOREIGN KEY (direccion_estado)
        REFERENCES woaho.estado (estado_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);
ALTER TABLE woaho.direccion
    OWNER to postgres;
COMMENT ON TABLE woaho.direccion
    IS 'Tabla que contiene las direcciones registradas por los usuarios';
    
CREATE TABLE woaho.parametro
(
    parametro_id integer NOT NULL DEFAULT nextval('woaho.sec_parametro'::regclass),
    parametro_nombre character varying(4000),
    parametro_valor character varying(4000),
    parametro_descripcion character varying(4000),
    CONSTRAINT parametro_pkey PRIMARY KEY (parametro_id)
);
ALTER TABLE woaho.parametro
    OWNER to postgres;
COMMENT ON TABLE woaho.parametro
    IS 'Tabla que contiene los parametros del aplicativo';
    
CREATE TABLE woaho.categoria
(
    categoria_id integer NOT NULL DEFAULT nextval('woaho.sec_categoria'::regclass),
    categoria_descripcion character varying(4000),
    categoria_imagen character varying(4000),
    CONSTRAINT categoria_pkey PRIMARY KEY (categoria_id)
);
ALTER TABLE woaho.categoria
    OWNER to postgres;
COMMENT ON TABLE woaho.categoria
    IS 'Tabla que contiene las categorias para el aplicativo';
    
CREATE TABLE woaho.moneda
(
    moneda_id integer NOT NULL DEFAULT nextval('woaho.sec_moneda'::regclass),
    moneda_nombre character varying(4000),
    moneda_territorio integer,
    CONSTRAINT moneda_pkey PRIMARY KEY (moneda_id),
    CONSTRAINT "FK_MONEDA_TERRITORIO" FOREIGN KEY (moneda_territorio)
     REFERENCES woaho.territorio (territorio_id) MATCH SIMPLE
     ON UPDATE NO ACTION
     ON DELETE NO ACTION
);
ALTER TABLE woaho.moneda
    OWNER to postgres;
COMMENT ON TABLE woaho.moneda
    IS 'Tabla que contiene las monedas para el aplicativo';
    
CREATE TABLE woaho.servicio
(
    servicio_id integer NOT NULL DEFAULT nextval('woaho.sec_servicio'::regclass),
    servicio_nombre character varying(4000),
    servicio_imagen character varying(4000),
    servicio_categoria integer,
    CONSTRAINT servicio_pkey PRIMARY KEY (servicio_id),
    CONSTRAINT "FK_SERVICIO_CATEGORIA" FOREIGN KEY (servicio_categoria)
       REFERENCES woaho.categoria (categoria_id) MATCH SIMPLE
       ON UPDATE NO ACTION
       ON DELETE NO ACTION
);
ALTER TABLE woaho.servicio
    OWNER to postgres;
COMMENT ON TABLE woaho.servicio
    IS 'Tabla que contiene los servicios para el aplicativo';
    
CREATE TABLE woaho.tarifa
(
    tarifa_id integer NOT NULL DEFAULT nextval('woaho.sec_tarifa'::regclass),
    tarifa_valor decimal,
    tarifa_moneda integer,
    tarifa_territorio integer,
    tarifa_servicio integer,
    CONSTRAINT tarifa_pkey PRIMARY KEY (tarifa_id),
    CONSTRAINT "FK_TARIFA_MONEDA" FOREIGN KEY (tarifa_moneda)
        REFERENCES woaho.moneda (moneda_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT "FK_TARIFA_TERRITORIO" FOREIGN KEY (tarifa_territorio)
        REFERENCES woaho.territorio (territorio_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT "FK_TARIFA_SERVICIO" FOREIGN KEY (tarifa_servicio)
        REFERENCES woaho.servicio (servicio_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);
ALTER TABLE woaho.tarifa
    OWNER to postgres;
COMMENT ON TABLE woaho.tarifa
    IS 'Tabla que contiene las tarifas para el aplicativo';
    
CREATE TABLE woaho.promocion
(
    promocion_id integer NOT NULL DEFAULT nextval('woaho.sec_promocion'::regclass),
    promocion_descuento integer,
    promocion_tarifa integer,
    promocion_estado integer,
    promocion_descripcion character varying(4000),
    CONSTRAINT promocion_pkey PRIMARY KEY (promocion_id),
    CONSTRAINT "FK_PROM_TARIFA" FOREIGN KEY (promocion_tarifa)
        REFERENCES woaho.tarifa (tarifa_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT "FK_PROM_ESTADO" FOREIGN KEY (promocion_estado)
        REFERENCES woaho.estado (estado_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);
ALTER TABLE woaho.promocion
    OWNER to postgres;
COMMENT ON TABLE woaho.promocion
    IS 'Tabla que contiene las promociones del aplicativo';    
    
CREATE TABLE woaho.codigo_promocional
(
    codigo_promocional_id integer NOT NULL DEFAULT nextval('woaho.sec_codigo_promocional'::regclass),
    codigo_promocional_codigo character varying(4000),
    codigo_promocional_usuario integer,
    codigo_promocional_estado integer,
    codigo_promocional_promocion integer,
    CONSTRAINT codigo_promocional_pkey PRIMARY KEY (codigo_promocional_id),
    CONSTRAINT "FK_COD_PROM_USUARIO" FOREIGN KEY (codigo_promocional_usuario)
        REFERENCES woaho.usuario (usuario_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT "FK_COD_PROM_ESTADO" FOREIGN KEY (codigo_promocional_estado)
        REFERENCES woaho.estado (estado_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT "FK_COD_PROM_PROMOCION" FOREIGN KEY (codigo_promocional_promocion)
        REFERENCES woaho.promocion (promocion_ID) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);
ALTER TABLE woaho.codigo_promocional
    OWNER to postgres;
COMMENT ON TABLE woaho.codigo_promocional
    IS 'Tabla que contiene los codigos promocionales del aplicativo';  