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

INSERT INTO woaho.parametro (parametro_nombre,parametro_valor,parametro_descripcion) VALUES ('CANT_INT_COD_REGISTRO','3','Cantidad de intentos permitidos al ingresar el codigo de registro')
INSERT INTO woaho.parametro (parametro_nombre,parametro_valor,parametro_descripcion) VALUES ('TIEMPO_COD_REGISTRO','2','Define el tiempo de valides de un codigo, se debe dar en Minutos');

INSERT INTO woaho.categoria (categoria_imagen,categoria_descripcion) VALUES ('globe-outline','Todos');
INSERT INTO woaho.categoria (categoria_imagen,categoria_descripcion) VALUES ('brush-outline','Manicure');
INSERT INTO woaho.categoria (categoria_imagen,categoria_descripcion) VALUES ('cut-outline','Peluqueria');
INSERT INTO woaho.categoria (categoria_imagen,categoria_descripcion) VALUES ('restaurant-outline','Comida');
INSERT INTO woaho.categoria (categoria_imagen,categoria_descripcion) VALUES ('paw-outline','Mascotas');
INSERT INTO woaho.categoria (categoria_imagen,categoria_descripcion) VALUES ('rose-outline','Jardineria');
INSERT INTO woaho.categoria (categoria_imagen,categoria_descripcion) VALUES ('hammer-outline','Arreglos');
INSERT INTO woaho.categoria (categoria_imagen,categoria_descripcion) VALUES ('build-outline','Mecanica');

INSERT INTO woaho.moneda (moneda_nombre,moneda_territorio) VALUES ('COP',1);
INSERT INTO woaho.moneda (moneda_nombre,moneda_territorio) VALUES ('USD',2);
INSERT INTO woaho.moneda (moneda_nombre,moneda_territorio) VALUES ('MXN',3);