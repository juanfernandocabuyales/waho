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
    
/***************************************************************************************************
	  Zona de inserts iniciales
***************************************************************************************************/
INSERT INTO woaho.tipo_territorio (tipo_territorio_nombre) VALUES ('Pais');
INSERT INTO woaho.tipo_territorio (tipo_territorio_nombre) VALUES ('Departamento');
INSERT INTO woaho.tipo_territorio (tipo_territorio_nombre) VALUES ('Municipio');
INSERT INTO woaho.tipo_territorio (tipo_territorio_nombre) VALUES ('Comuna');
INSERT INTO woaho.tipo_territorio (tipo_territorio_nombre) VALUES ('Barrio');
INSERT INTO woaho.tipo_territorio (tipo_territorio_nombre) VALUES ('Corregimiento');
INSERT INTO woaho.tipo_territorio (tipo_territorio_nombre) VALUES ('Vereda');

INSERT INTO woaho.territorio (territorio_nombre,territorio_padre,territorio_tipo,territorio_codigo) VALUES ('Colombia', NULL, 1,'+57');
INSERT INTO woaho.territorio (territorio_nombre,territorio_padre,territorio_tipo,territorio_codigo) VALUES ('Estados Unidos', NULL, 1,'+1');
INSERT INTO woaho.territorio (territorio_nombre,territorio_padre,territorio_tipo,territorio_codigo) VALUES ('México', NULL, 1,'+52');

INSERT INTO woaho.estado (estado_codigo) VALUES ('A');
INSERT INTO woaho.estado (estado_codigo) VALUES ('I');
INSERT INTO woaho.estado (estado_codigo) VALUES ('P');
INSERT INTO woaho.estado (estado_codigo) VALUES ('R');
