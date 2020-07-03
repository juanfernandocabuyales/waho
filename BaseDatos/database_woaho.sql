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
