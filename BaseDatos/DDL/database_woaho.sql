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

CREATE SEQUENCE woaho.sec_unidad_tarifa CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 999999 CACHE 1;
ALTER SEQUENCE woaho.sec_unidad_tarifa OWNER TO postgres;

CREATE SEQUENCE woaho.sec_pedido CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 999999 CACHE 1;
ALTER SEQUENCE woaho.sec_pedido OWNER TO postgres;

CREATE SEQUENCE woaho.sec_profesional CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 999999 CACHE 1;
ALTER SEQUENCE woaho.sec_profesional OWNER TO postgres;

CREATE SEQUENCE woaho.sec_imagen CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 999999 CACHE 1;
ALTER SEQUENCE woaho.sec_imagen OWNER TO postgres;

CREATE SEQUENCE woaho.sec_calificacion CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 999999 CACHE 1;
ALTER SEQUENCE woaho.sec_calificacion OWNER TO postgres;

CREATE SEQUENCE woaho.sec_idioma CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 999999 CACHE 1;
ALTER SEQUENCE woaho.sec_idioma OWNER TO postgres;

CREATE SEQUENCE woaho.sec_ubicacion CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 999999 CACHE 1;
ALTER SEQUENCE woaho.sec_ubicacion OWNER TO postgres;

CREATE SEQUENCE woaho.sec_profesion CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 999999 CACHE 1;
ALTER SEQUENCE woaho.sec_profesion OWNER TO postgres;

CREATE SEQUENCE woaho.sec_etiqueta CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 999999 CACHE 1;
ALTER SEQUENCE woaho.sec_etiqueta OWNER TO postgres; 

CREATE SEQUENCE woaho.sec_medio_pago CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 999999 CACHE 1;
ALTER SEQUENCE woaho.sec_medio_pago OWNER TO postgres;  

CREATE SEQUENCE woaho.sec_cancelacion CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 999999 CACHE 1;
ALTER SEQUENCE woaho.sec_cancelacion OWNER TO postgres;

CREATE SEQUENCE woaho.sec_traduccion CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 999999 CACHE 1;
ALTER SEQUENCE woaho.sec_traduccion OWNER TO postgres;

CREATE SEQUENCE woaho.sec_equivalencia_idioma CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 999999 CACHE 1;
ALTER SEQUENCE woaho.sec_equivalencia_idioma OWNER TO postgres;

CREATE SEQUENCE woaho.sec_servicio_favorito CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 999999 CACHE 1;
ALTER SEQUENCE woaho.sec_servicio_favorito OWNER TO postgres;

CREATE SEQUENCE woaho.sec_medio_pago_usuario CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 999999 CACHE 1;
ALTER SEQUENCE woaho.sec_medio_pago_usuario OWNER TO postgres;

CREATE SEQUENCE woaho.sec_mensaje_correo CYCLE INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 999999 CACHE 1;
ALTER SEQUENCE woaho.sec_mensaje_correo OWNER TO postgres;
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
    IS 'Tabla que contiene la informaci�n de los tipos de pantalla';
    
CREATE TABLE woaho.mensaje_correo
(
    mensaje_correo_id integer NOT NULL DEFAULT nextval('woaho.sec_mensaje_correo'::regclass),
    mensaje_correo_codigo integer,
    mensaje_correo_mensaje character varying(4000),
    mensaje_correo_idioma character varying(4000),
    CONSTRAINT mensaje_correo_pkey PRIMARY KEY (mensaje_correo_id)
);
ALTER TABLE woaho.mensaje_correo
    OWNER to postgres;
COMMENT ON TABLE woaho.mensaje_correo
    IS 'Tabla que contiene los mensajes que se envian por correo electronico';

CREATE TABLE woaho.idioma
(
    idioma_id integer NOT NULL DEFAULT nextval('woaho.sec_idioma'::regclass),
    idioma_nombre character varying(4000),
    idioma_codigo character varying(4000),
    CONSTRAINT idioma_pkey PRIMARY KEY (idioma_id)
);
ALTER TABLE woaho.idioma
    OWNER to postgres;
COMMENT ON TABLE woaho.idioma
    IS 'Tabla que contiene la informaci�n de los idiomas del aplicativo';
    
CREATE TABLE woaho.profesion
(
    profesion_id integer NOT NULL DEFAULT nextval('woaho.sec_profesion'::regclass),
    profesion_nombre character varying(4000),
    CONSTRAINT profesion_pkey PRIMARY KEY (profesion_id)
);
ALTER TABLE woaho.profesion
    OWNER to postgres;
COMMENT ON TABLE woaho.profesion
    IS 'Tabla que contiene la informaci�n de las profesiones del aplicativo';
    
CREATE TABLE woaho.imagen
(
    imagen_id integer NOT NULL DEFAULT nextval('woaho.sec_imagen'::regclass),
    imagen_nombre character varying(4000),
    imagen_ruta character varying(4000),
    imagen_alto character varying(4000),
    imagen_ancho character varying(4000),
    CONSTRAINT imagen_pkey PRIMARY KEY (imagen_id)
);
ALTER TABLE woaho.imagen
    OWNER to postgres;
COMMENT ON TABLE woaho.imagen
    IS 'Tabla que contiene las imagenes para el aplicativo';     

CREATE TABLE woaho.pantalla
(
    pantalla_id integer NOT NULL DEFAULT nextval('woaho.sec_pantalla'::regclass),
    pantalla_nombre character varying(4000) COLLATE pg_catalog."default",
    pantalla_imagen integer,
    pantalla_tipo_pantalla integer,
    CONSTRAINT pantalla_pkey PRIMARY KEY (pantalla_id),
    CONSTRAINT "FK_PANTALLA_TIPO_PANTLLA" FOREIGN KEY (pantalla_tipo_pantalla)
        REFERENCES woaho.tipo (tipo_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
	CONSTRAINT "FK_PANTALLA_IMAGEN" FOREIGN KEY (pantalla_imagen)
        REFERENCES woaho.imagen (imagen_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);
ALTER TABLE woaho.pantalla
    OWNER to postgres;
COMMENT ON TABLE woaho.pantalla
    IS 'Tabla que contiene la informaci�n de las pantallas';
    
CREATE TABLE woaho.mensaje
(
    mensaje_id integer NOT NULL DEFAULT nextval('woaho.sec_mensaje'::regclass),
    mensaje_tipo integer,
    mensaje_codigo character varying(4000),
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
    IS 'Tabla que contiene la informaci�n de los mensajes por pantalla';
    
CREATE TABLE woaho.traduccion(
	traduccion_id integer NOT NULL DEFAULT nextval('woaho.sec_traduccion'::regclass),
	traduccion_codigo_mensaje character varying(4000),
	traduccion_traduccion character varying(4000),
	traduccion_idioma integer,
	CONSTRAINT traduccion_pkey PRIMARY KEY (traduccion_id),
    CONSTRAINT "FK_TRADUCCION_IDIOMA" FOREIGN KEY (traduccion_idioma)
       REFERENCES woaho.idioma (idioma_id) MATCH SIMPLE
       ON UPDATE NO ACTION
       ON DELETE NO ACTION
       NOT VALID
);
ALTER TABLE woaho.traduccion
    OWNER to postgres;
COMMENT ON TABLE woaho.traduccion
    IS 'Tabla que contiene la traduccion de los mensajes del aplicativo';
    
CREATE TABLE woaho.etiqueta
(
    etiqueta_id integer NOT NULL DEFAULT nextval('woaho.sec_etiqueta'::regclass),
    etiqueta_codigo character varying(4000),
    etiqueta_idioma integer,
    CONSTRAINT etiqueta_pkey PRIMARY KEY (etiqueta_id),
    CONSTRAINT "FK_ETIQUETA_IDIOMA" FOREIGN KEY (etiqueta_idioma)
       REFERENCES woaho.idioma (idioma_id) MATCH SIMPLE
       ON UPDATE NO ACTION
       ON DELETE NO ACTION
       NOT VALID
);
ALTER TABLE woaho.etiqueta
    OWNER to postgres;
COMMENT ON TABLE woaho.etiqueta
    IS 'Tabla que contiene las etiquetas del aplicativo';

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
    territorio_imagen integer,
    CONSTRAINT territorio_pkey PRIMARY KEY (territorio_id),
    CONSTRAINT "FK_TERRITORIO_TIPO_TERRITORIO" FOREIGN KEY (territorio_tipo)
        REFERENCES woaho.tipo_territorio (tipo_territorio_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
    CONSTRAINT "FK_TERRITORIO_IMAGEN" FOREIGN KEY (territorio_imagen)
        REFERENCES woaho.imagen (imagen_id) MATCH SIMPLE
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
    usuario_id_suscriptor character varying(4000),
    usuario_referralCode character varying(4000),
    usuario_tipo integer NOT NULL DEFAULT 2,
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
    CONSTRAINT "FK_CODIGO_ESTADO" FOREIGN KEY (codigo_estado)
        REFERENCES woaho.estado (estado_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
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
    direccion_lugar_id character varying(4000),
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
    categoria_imagen integer,
    CONSTRAINT categoria_pkey PRIMARY KEY (categoria_id),
    CONSTRAINT "FK_CATEGORIA_IMAGEN" FOREIGN KEY (categoria_imagen)
     REFERENCES woaho.imagen (imagen_id) MATCH SIMPLE
     ON UPDATE NO ACTION
     ON DELETE NO ACTION
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
    
CREATE TABLE woaho.unidad_tarifa
(
    unidad_tarifa_id integer NOT NULL DEFAULT nextval('woaho.sec_unidad_tarifa'::regclass),
    unidad_tarifa_nombre character varying(4000),
    CONSTRAINT unidad_tarifa_pkey PRIMARY KEY (unidad_tarifa_id)
);
ALTER TABLE woaho.unidad_tarifa
    OWNER to postgres;
COMMENT ON TABLE woaho.unidad_tarifa
    IS 'Tabla que contiene las unidades de tarifas para el aplicativo';
    
CREATE TABLE woaho.servicio
(
    servicio_id integer NOT NULL DEFAULT nextval('woaho.sec_servicio'::regclass),
    servicio_nombre character varying(4000),
    servicio_imagen integer,
    servicio_categoria integer,
    servicio_territorio integer,
    servicio_descripcion character varying(4000), 
    CONSTRAINT servicio_pkey PRIMARY KEY (servicio_id),
    CONSTRAINT "FK_SERVICIO_CATEGORIA" FOREIGN KEY (servicio_categoria)
       REFERENCES woaho.categoria (categoria_id) MATCH SIMPLE
       ON UPDATE NO ACTION
       ON DELETE NO ACTION,
 	CONSTRAINT "FK_SERVICIO_IMAGEN" FOREIGN KEY (servicio_imagen)
       REFERENCES woaho.imagen (imagen_id) MATCH SIMPLE
       ON UPDATE NO ACTION
       ON DELETE NO ACTION,
    CONSTRAINT "FK_SERVICIO_TERRITORIO" FOREIGN KEY (servicio_territorio)
       REFERENCES woaho.territorio (territorio_id) MATCH SIMPLE
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
    tarifa_unidad integer,
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
        ON DELETE NO ACTION,
    CONSTRAINT "FK_TARIFA_UNIDAD" FOREIGN KEY (tarifa_unidad)
        REFERENCES woaho.unidad_tarifa (unidad_tarifa_id) MATCH SIMPLE
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
        REFERENCES woaho.promocion (promocion_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);
ALTER TABLE woaho.codigo_promocional
    OWNER to postgres;
COMMENT ON TABLE woaho.codigo_promocional
    IS 'Tabla que contiene los codigos promocionales del aplicativo';
    
CREATE TABLE woaho.profesional
(
	profesional_id integer NOT NULL DEFAULT nextval('woaho.sec_profesional'::regclass),
	profesional_nombre character varying(4000),
	profesional_apellido character varying(4000),
	profesional_profesiones character varying(4000),
	profesional_nacionalidad integer,
	profesional_servicios character varying(4000),
	profesional_lenguajes character varying(4000),
	profesional_descripcion character varying(4000),
	profesional_imagen_icono integer,
	profesional_cant_estrellas decimal,
	profesional_cant_servicios integer,
	profesional_distancia decimal DEFAULT 0,
	CONSTRAINT profesional_pkey PRIMARY KEY (profesional_id),
	CONSTRAINT "FK_PROFESIONAL_TERRITORIO" FOREIGN KEY (profesional_nacionalidad)
        REFERENCES woaho.territorio (territorio_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT "FK_PROFESIONAL_ICONO" FOREIGN KEY (profesional_imagen_icono)
        REFERENCES woaho.imagen (imagen_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);
ALTER TABLE woaho.profesional
    OWNER to postgres;
COMMENT ON TABLE woaho.profesional
    IS 'Tabla que contiene los profesionales del aplicativo';
    
CREATE TABLE woaho.ubicacion
(
    ubicacion_id integer NOT NULL DEFAULT nextval('woaho.sec_ubicacion'::regclass),
    ubicacion_profesional integer,
    ubicacion_lugar_id character varying(4000),
    ubicacion_latitud character varying(4000),
    ubicacion_longitud character varying(4000),
    CONSTRAINT ubicacion_pkey PRIMARY KEY (ubicacion_id),
    CONSTRAINT "FK_UBICACION_PROFESIONAL" FOREIGN KEY (ubicacion_profesional)
        REFERENCES woaho.profesional (profesional_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION    
);
ALTER TABLE woaho.ubicacion
    OWNER to postgres;
COMMENT ON TABLE woaho.ubicacion
    IS 'Tabla que contiene las ubicaciones de los profesionales';
    
CREATE TABLE woaho.calificacion
(
	calificacion_id integer NOT NULL DEFAULT nextval('woaho.sec_calificacion'::regclass),
	calificacion_usuario integer,
	calificacion_profesional integer,
	calificacion_descripcion character varying(4000),
	calificacion_calificacion integer,
	calificacion_servicio integer,
	CONSTRAINT calificacion_pkey PRIMARY KEY (calificacion_id),
	CONSTRAINT "FK_CALIFICACION_USUARIO" FOREIGN KEY (calificacion_usuario)
        REFERENCES woaho.usuario (usuario_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
     CONSTRAINT "FK_CALIFICACION_PROFESIONAL" FOREIGN KEY (calificacion_profesional)
        REFERENCES woaho.profesional (profesional_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
     CONSTRAINT "FK_CALIFICACION_SERVICIO" FOREIGN KEY (calificacion_servicio)
        REFERENCES woaho.servicio (servicio_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION 
);
ALTER TABLE woaho.calificacion
    OWNER to postgres;
COMMENT ON TABLE woaho.calificacion
    IS 'Tabla que contiene las calificaciones de los profesionales';
    
CREATE TABLE woaho.medio_pago
(
	medio_pago_id integer NOT NULL DEFAULT nextval('woaho.sec_medio_pago'::regclass),
	medio_pago_nombre character varying(4000),
	medio_pago_etiqueta character varying(4000),
	medio_pago_territorio integer,
	CONSTRAINT medio_pago_pkey PRIMARY KEY (medio_pago_id),
	CONSTRAINT "FK_MEDIO_PAGO_TERRITORIO" FOREIGN KEY (medio_pago_territorio)
        REFERENCES woaho.territorio (territorio_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION	
);
ALTER TABLE woaho.medio_pago
    OWNER to postgres;
COMMENT ON TABLE woaho.medio_pago
    IS 'Tabla que contiene los medios de pagos para el aplicativo';
    
CREATE TABLE woaho.pedido
(
    pedido_id integer NOT NULL DEFAULT nextval('woaho.sec_pedido'::regclass),
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
    pedido_fecha_final TIMESTAMP,
    pedido_latitud character varying(4000),
    pedido_longitu character varying(4000),
    CONSTRAINT pedido_pkey PRIMARY KEY (pedido_id),
    CONSTRAINT "FK_PEDIDO_SERVICIO" FOREIGN KEY (pedido_servicio)
        REFERENCES woaho.servicio (servicio_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT "FK_PEDIDO_USUARIO" FOREIGN KEY (pedido_servicio)
        REFERENCES woaho.usuario (usuario_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT "FK_PEDIDO_ESTADO" FOREIGN KEY (pedido_estado)
        REFERENCES woaho.estado (estado_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
     CONSTRAINT "FK_PEDIDO_DIRECCION" FOREIGN KEY (pedido_direccion)
        REFERENCES woaho.direccion (direccion_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
     CONSTRAINT "FK_PEDIDO_PROFESIONAL" FOREIGN KEY (pedido_profesional)
        REFERENCES woaho.profesional (profesional_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
     CONSTRAINT "FK_PEDIDO_MEDIO_PAGO" FOREIGN KEY (pedido_medio_pago)
        REFERENCES woaho.medio_pago (medio_pago_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);
ALTER TABLE woaho.pedido
    OWNER to postgres;
COMMENT ON TABLE woaho.pedido
    IS 'Tabla que contiene los pedidos del aplicativo';
    
CREATE TABLE woaho.cancelacion
(
	cancelacion_id integer NOT NULL DEFAULT nextval('sec_cancelacion'::regclass),
	cancelacion_pedido integer,
	cancelacion_motivo character varying(4000),
	cancelacion_fecha TIMESTAMP,
	CONSTRAINT cancelacion_pkey PRIMARY KEY (cancelacion_id),
	CONSTRAINT "FK_CANCELACION_PEDIDO" FOREIGN KEY (cancelacion_pedido)
        REFERENCES woaho.pedido (pedido_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);
ALTER TABLE woaho.cancelacion
    OWNER to postgres;
COMMENT ON TABLE woaho.cancelacion
    IS 'Tabla que contiene las cancelaciones realizadas';  
    
CREATE TABLE woaho.equivalencia_idioma
(
	equivalencia_idioma_id integer NOT NULL DEFAULT nextval('sec_equivalencia_idioma'::regclass),
	equivalencia_idioma_original character varying(4000),
	equivalencia_idioma_ingles character varying(4000),
	CONSTRAINT equivalencia_idioma_pkey PRIMARY KEY (equivalencia_idioma_id)
);
ALTER TABLE woaho.equivalencia_idioma
    OWNER to postgres;
COMMENT ON TABLE woaho.equivalencia_idioma
    IS 'Tabla que contiene las traducciones de las etiquetas';
    
CREATE TABLE woaho.servicio_favorito
(
	servicio_favorito_id integer NOT NULL DEFAULT nextval('sec_servicio_favorito'::regclass),
	servicio_favorito_servicio integer,
	servicio_favorito_usuario integer,
	CONSTRAINT servicio_favorito_pkey PRIMARY KEY (servicio_favorito_id),
	CONSTRAINT "FK_FAVORITO_SERVICIO" FOREIGN KEY (servicio_favorito_servicio)
        REFERENCES woaho.servicio (servicio_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT "FK_FAVORITO_USUARIO" FOREIGN KEY (servicio_favorito_usuario)
        REFERENCES woaho.usuario (usuario_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);
ALTER TABLE woaho.servicio_favorito
    OWNER to postgres;
COMMENT ON TABLE woaho.servicio_favorito
    IS 'Tabla que contiene los servicios favoritos del usuario';
    
CREATE TABLE woaho.medio_pago_usuario
(
	medio_pago_usuario_id integer NOT NULL DEFAULT nextval('sec_medio_pago_usuario'::regclass),
	medio_pago_usuario_nombre character varying(4000),
	medio_pago_usuario_fecha_vencimiento character varying(4000),
	medio_pago_usuario_cvc character varying(4000),
	medio_pago_usuario_codigo character varying(4000),
	medio_pago_usuario_estado integer,
	medio_pago_usuario_usuario integer,
	medio_pago_usuario_medio_pago integer,
	CONSTRAINT medio_pago_usuario_pkey PRIMARY KEY (medio_pago_usuario_id),
	CONSTRAINT "FK_MEDIO_PAGO_USUARIO_ESTADO" FOREIGN KEY (medio_pago_usuario_estado)
        REFERENCES woaho.estado (estado_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT "FK_MEDIO_PAGO_USUARIO_USUARIO" FOREIGN KEY (medio_pago_usuario_usuario)
        REFERENCES woaho.usuario (usuario_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
   CONSTRAINT "FK_MEDIO_PAGO_USUARIO_MEDIO_PAGO" FOREIGN KEY (medio_pago_usuario_medio_pago)
        REFERENCES woaho.medio_pago (medio_pago_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);
ALTER TABLE woaho.medio_pago_usuario
    OWNER to postgres;
COMMENT ON TABLE woaho.medio_pago_usuario
    IS 'Tabla que contiene los medios de pago para el usuario';  