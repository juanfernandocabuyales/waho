/***************************************************************************************************
	  Inserts iniciales
***************************************************************************************************/
INSERT INTO woaho.tipo_territorio (tipo_territorio_nombre) VALUES ('Pais');
INSERT INTO woaho.tipo_territorio (tipo_territorio_nombre) VALUES ('Departamento');
INSERT INTO woaho.tipo_territorio (tipo_territorio_nombre) VALUES ('Municipio');
INSERT INTO woaho.tipo_territorio (tipo_territorio_nombre) VALUES ('Comuna');
INSERT INTO woaho.tipo_territorio (tipo_territorio_nombre) VALUES ('Barrio');
INSERT INTO woaho.tipo_territorio (tipo_territorio_nombre) VALUES ('Corregimiento');
INSERT INTO woaho.tipo_territorio (tipo_territorio_nombre) VALUES ('Vereda');

INSERT INTO woaho.imagen (imagen_nombre,imagen_ruta,imagen_alto,imagen_ancho) VALUES ('globe-outline','url',0,0);
INSERT INTO woaho.imagen (imagen_nombre,imagen_ruta,imagen_alto,imagen_ancho) VALUES ('brush-outline','url',0,0);
INSERT INTO woaho.imagen (imagen_nombre,imagen_ruta,imagen_alto,imagen_ancho) VALUES ('cut-outline','url',0,0);
INSERT INTO woaho.imagen (imagen_nombre,imagen_ruta,imagen_alto,imagen_ancho) VALUES ('restaurant-outline','url',0,0);
INSERT INTO woaho.imagen (imagen_nombre,imagen_ruta,imagen_alto,imagen_ancho) VALUES ('paw-outline','url',0,0);
INSERT INTO woaho.imagen (imagen_nombre,imagen_ruta,imagen_alto,imagen_ancho) VALUES ('rose-outline','url',0,0);
INSERT INTO woaho.imagen (imagen_nombre,imagen_ruta,imagen_alto,imagen_ancho) VALUES ('hammer-outline','url',0,0);
INSERT INTO woaho.imagen (imagen_nombre,imagen_ruta,imagen_alto,imagen_ancho) VALUES ('build-outline','url',0,0);

INSERT INTO woaho.territorio (territorio_nombre,territorio_padre,territorio_tipo,territorio_codigo) VALUES ('Colombia', NULL, 1,'+57');
INSERT INTO woaho.territorio (territorio_nombre,territorio_padre,territorio_tipo,territorio_codigo) VALUES ('Estados Unidos', NULL, 1,'+1');
INSERT INTO woaho.territorio (territorio_nombre,territorio_padre,territorio_tipo,territorio_codigo) VALUES ('México', NULL, 1,'+52');

INSERT INTO woaho.estado (estado_codigo) VALUES ('A');
INSERT INTO woaho.estado (estado_codigo) VALUES ('I');
INSERT INTO woaho.estado (estado_codigo) VALUES ('P');
INSERT INTO woaho.estado (estado_codigo) VALUES ('R');

INSERT INTO woaho.parametro (parametro_nombre,parametro_valor,parametro_descripcion) VALUES ('CANT_INT_COD_REGISTRO','3','Cantidad de intentos permitidos al ingresar el codigo de registro')
INSERT INTO woaho.parametro (parametro_nombre,parametro_valor,parametro_descripcion) VALUES ('TIEMPO_COD_REGISTRO','2','Define el tiempo de valides de un codigo, se debe dar en Minutos');

INSERT INTO woaho.categoria (categoria_imagen,categoria_descripcion) VALUES (1,'Todos');
INSERT INTO woaho.categoria (categoria_imagen,categoria_descripcion) VALUES (2,'Manicure');
INSERT INTO woaho.categoria (categoria_imagen,categoria_descripcion) VALUES (3,'Peluqueria');
INSERT INTO woaho.categoria (categoria_imagen,categoria_descripcion) VALUES (4,'Comida');
INSERT INTO woaho.categoria (categoria_imagen,categoria_descripcion) VALUES (5,'Mascotas');
INSERT INTO woaho.categoria (categoria_imagen,categoria_descripcion) VALUES (6,'Jardineria');
INSERT INTO woaho.categoria (categoria_imagen,categoria_descripcion) VALUES (7,'Arreglos');
INSERT INTO woaho.categoria (categoria_imagen,categoria_descripcion) VALUES (9,'Mecanica');

INSERT INTO woaho.moneda (moneda_nombre,moneda_territorio) VALUES ('COP',1);
INSERT INTO woaho.moneda (moneda_nombre,moneda_territorio) VALUES ('USD',2);
INSERT INTO woaho.moneda (moneda_nombre,moneda_territorio) VALUES ('MXN',3);

INSERT INTO woaho.unidad_tarifa (unidad_tarifa_nombre) VALUES ('Hora');
INSERT INTO woaho.unidad_tarifa (unidad_tarifa_nombre) VALUES ('Dia');
INSERT INTO woaho.unidad_tarifa (unidad_tarifa_nombre) VALUES ('Semana');
INSERT INTO woaho.unidad_tarifa (unidad_tarifa_nombre) VALUES ('Mes');
INSERT INTO woaho.unidad_tarifa (unidad_tarifa_nombre) VALUES ('Año');

INSERT INTO woaho.idioma (idioma_nombre,idioma_codigo) VALUES ('INGLES','ENG');
INSERT INTO woaho.idioma (idioma_nombre,idioma_codigo) VALUES ('ESPAÑOL','SPN');

INSERT INTO woaho.profesion (profesion_nombre) VALUES ('Ingeniero');
INSERT INTO woaho.profesion (profesion_nombre) VALUES ('Electrico');
INSERT INTO woaho.profesion (profesion_nombre) VALUES ('Estilista');

INSERT INTO woaho.etiqueta (etiqueta_valor,etiqueta_idioma,etiqueta_codigo) VALUES ('usuario',2,'1');
INSERT INTO woaho.etiqueta (etiqueta_valor,etiqueta_idioma,etiqueta_codigo) VALUES ('user',1,'1');
INSERT INTO woaho.etiqueta (etiqueta_valor,etiqueta_idioma,etiqueta_codigo) VALUES ('clave',2,'2');
INSERT INTO woaho.etiqueta (etiqueta_valor,etiqueta_idioma,etiqueta_codigo) VALUES ('password',1,'2');

