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
INSERT INTO woaho.territorio (territorio_nombre,territorio_padre,territorio_tipo,territorio_codigo) VALUES ('Valle del Cauca', 1, 2,'+57');
INSERT INTO woaho.territorio (territorio_nombre,territorio_padre,territorio_tipo,territorio_codigo) VALUES ('Calí', 4, 3,'+57');
INSERT INTO woaho.territorio (territorio_nombre,territorio_padre,territorio_tipo,territorio_codigo) VALUES ('Risaralda', 1, 2,'+57');
INSERT INTO woaho.territorio (territorio_nombre,territorio_padre,territorio_tipo,territorio_codigo) VALUES ('Pereira', 6, 3,'+57');

INSERT INTO woaho.estado (estado_codigo) VALUES ('ACTIVO');
INSERT INTO woaho.estado (estado_codigo) VALUES ('INACTIVO');
INSERT INTO woaho.estado (estado_codigo) VALUES ('PENDIENTE');
INSERT INTO woaho.estado (estado_codigo) VALUES ('REGISTRADO');
INSERT INTO woaho.estado (estado_codigo) VALUES ('FINALIZADO');
INSERT INTO woaho.estado (estado_codigo) VALUES ('CANCELADO');

INSERT INTO woaho.parametro (parametro_nombre,parametro_valor,parametro_descripcion) VALUES ('CANT_INT_COD_REGISTRO','3','Cantidad de intentos permitidos al ingresar el codigo de registro');
INSERT INTO woaho.parametro (parametro_nombre,parametro_valor,parametro_descripcion) VALUES ('TIEMPO_COD_REGISTRO','2','Define el tiempo de valides de un codigo, se debe dar en Minutos');

INSERT INTO woaho.categoria (categoria_imagen,categoria_descripcion) VALUES (1,'Todos');
INSERT INTO woaho.categoria (categoria_imagen,categoria_descripcion) VALUES (2,'Manicure');
INSERT INTO woaho.categoria (categoria_imagen,categoria_descripcion) VALUES (3,'Peluqueria');
INSERT INTO woaho.categoria (categoria_imagen,categoria_descripcion) VALUES (4,'Comida');
INSERT INTO woaho.categoria (categoria_imagen,categoria_descripcion) VALUES (5,'Mascotas');
INSERT INTO woaho.categoria (categoria_imagen,categoria_descripcion) VALUES (6,'Jardineria');
INSERT INTO woaho.categoria (categoria_imagen,categoria_descripcion) VALUES (7,'Arreglos');
INSERT INTO woaho.categoria (categoria_imagen,categoria_descripcion) VALUES (8,'Mecanica');

INSERT INTO woaho.equivalencia_idioma (equivalencia_idioma_original,equivalencia_idioma_ingles) VALUES ('Todos','All');
INSERT INTO woaho.equivalencia_idioma (equivalencia_idioma_original,equivalencia_idioma_ingles) VALUES ('Manicure','Manicure');
INSERT INTO woaho.equivalencia_idioma (equivalencia_idioma_original,equivalencia_idioma_ingles) VALUES ('Peluqueria','Hairdressing');
INSERT INTO woaho.equivalencia_idioma (equivalencia_idioma_original,equivalencia_idioma_ingles) VALUES ('Comida','Food');
INSERT INTO woaho.equivalencia_idioma (equivalencia_idioma_original,equivalencia_idioma_ingles) VALUES ('Mascotas','Pets');
INSERT INTO woaho.equivalencia_idioma (equivalencia_idioma_original,equivalencia_idioma_ingles) VALUES ('Jardineria','Gardening');
INSERT INTO woaho.equivalencia_idioma (equivalencia_idioma_original,equivalencia_idioma_ingles) VALUES ('Arreglos','Repairs');
INSERT INTO woaho.equivalencia_idioma (equivalencia_idioma_original,equivalencia_idioma_ingles) VALUES ('Mecanica','Mechanics');
INSERT INTO woaho.equivalencia_idioma (equivalencia_idioma_original,equivalencia_idioma_ingles) VALUES ('Software','Software');

INSERT INTO woaho.equivalencia_idioma (equivalencia_idioma_original,equivalencia_idioma_ingles) VALUES ('Hora','Hour');
INSERT INTO woaho.equivalencia_idioma (equivalencia_idioma_original,equivalencia_idioma_ingles) VALUES ('Dia','Day');
INSERT INTO woaho.equivalencia_idioma (equivalencia_idioma_original,equivalencia_idioma_ingles) VALUES ('Semana','Week');
INSERT INTO woaho.equivalencia_idioma (equivalencia_idioma_original,equivalencia_idioma_ingles) VALUES ('Mes','Month');
INSERT INTO woaho.equivalencia_idioma (equivalencia_idioma_original,equivalencia_idioma_ingles) VALUES ('Año','Year');

INSERT INTO woaho.equivalencia_idioma (equivalencia_idioma_original,equivalencia_idioma_ingles) VALUES ('Ingeniero','Engineer');
INSERT INTO woaho.equivalencia_idioma (equivalencia_idioma_original,equivalencia_idioma_ingles) VALUES ('Electrico','Electric');
INSERT INTO woaho.equivalencia_idioma (equivalencia_idioma_original,equivalencia_idioma_ingles) VALUES ('Estilista','Stylist');

INSERT INTO woaho.equivalencia_idioma (equivalencia_idioma_original,equivalencia_idioma_ingles) VALUES ('Efectivo','Cash');
INSERT INTO woaho.equivalencia_idioma (equivalencia_idioma_original,equivalencia_idioma_ingles) VALUES ('Credito','Credit');
INSERT INTO woaho.equivalencia_idioma (equivalencia_idioma_original,equivalencia_idioma_ingles) VALUES ('Debito','Debit');

INSERT INTO woaho.equivalencia_idioma (equivalencia_idioma_original,equivalencia_idioma_ingles) VALUES ('Desarrollo Software','Software Development');
INSERT INTO woaho.equivalencia_idioma (equivalencia_idioma_original,equivalencia_idioma_ingles) VALUES ('Mecanica General','General Mechanics');

INSERT INTO woaho.equivalencia_idioma (equivalencia_idioma_original,equivalencia_idioma_ingles) VALUES ('No se han encontrado slides registrados','No slides found');
INSERT INTO woaho.equivalencia_idioma (equivalencia_idioma_original,equivalencia_idioma_ingles) VALUES ('No se han encontrado paises para la busqueda','No country found');
INSERT INTO woaho.equivalencia_idioma (equivalencia_idioma_original,equivalencia_idioma_ingles) VALUES ('No se han encontrado profesionales para la busqueda','No professional found');
INSERT INTO woaho.equivalencia_idioma (equivalencia_idioma_original,equivalencia_idioma_ingles) VALUES ('No se han encontrado servicios disponibles','No services available');
INSERT INTO woaho.equivalencia_idioma (equivalencia_idioma_original,equivalencia_idioma_ingles) VALUES ('No se han encontrado categorias disponibles','No found category vailable');
INSERT INTO woaho.equivalencia_idioma (equivalencia_idioma_original,equivalencia_idioma_ingles) VALUES ('Se ha presentado un inconveniente realizado la operación, favor intentar nuevamente','There has been a problem when the operation has been performed, please try again');
INSERT INTO woaho.equivalencia_idioma (equivalencia_idioma_original,equivalencia_idioma_ingles) VALUES ('Se ha presentado un inconveniente al solicitar la notificacion','There was a problem when requesting the notification');
INSERT INTO woaho.equivalencia_idioma (equivalencia_idioma_original,equivalencia_idioma_ingles) VALUES ('Se ha realizado el registro {0}','Registration has been made {0}');
INSERT INTO woaho.equivalencia_idioma (equivalencia_idioma_original,equivalencia_idioma_ingles) VALUES ('No se ha encontrado un usuario para el {0} {1}','No user found for {0} {1}');
INSERT INTO woaho.equivalencia_idioma (equivalencia_idioma_original,equivalencia_idioma_ingles) VALUES ('El usuario no es valido para registrar la direccion','The user is not valid to register the address');
INSERT INTO woaho.equivalencia_idioma (equivalencia_idioma_original,equivalencia_idioma_ingles) VALUES ('No se ha podido procesar la solicitud ','The request could not be processed');
INSERT INTO woaho.equivalencia_idioma (equivalencia_idioma_original,equivalencia_idioma_ingles) VALUES ('La clave ingresada no corresponde al usuario. Intente nuevamente.','The password entered does not correspond to the user. Try again');
INSERT INTO woaho.equivalencia_idioma (equivalencia_idioma_original,equivalencia_idioma_ingles) VALUES ('No se han encontrado direcciones registradas para el usuario','No registered addresses found for the user');
INSERT INTO woaho.equivalencia_idioma (equivalencia_idioma_original,equivalencia_idioma_ingles) VALUES ('Listado de pedidos vacio y/o invalido','Empty order list');
INSERT INTO woaho.equivalencia_idioma (equivalencia_idioma_original,equivalencia_idioma_ingles) VALUES ('No se han encontrado pedidos para el {0} .','No orders found for {0}');
INSERT INTO woaho.equivalencia_idioma (equivalencia_idioma_original,equivalencia_idioma_ingles) VALUES ('No se ha encontrado un pedido para cancelar','An order to cancel was not found');
INSERT INTO woaho.equivalencia_idioma (equivalencia_idioma_original,equivalencia_idioma_ingles) VALUES ('El número {0} ya se encuentra registrado.','the number {0} is already registered');
INSERT INTO woaho.equivalencia_idioma (equivalencia_idioma_original,equivalencia_idioma_ingles) VALUES ('El nombre del archivo no cumple con el formato esperado.','The file name does not conform to the expected format.');
INSERT INTO woaho.equivalencia_idioma (equivalencia_idioma_original,equivalencia_idioma_ingles) VALUES ('No se han enviando un archivo valido para guardar','They have not sent a valid file to save');


INSERT INTO woaho.moneda (moneda_nombre,moneda_territorio) VALUES ('COP',1);
INSERT INTO woaho.moneda (moneda_nombre,moneda_territorio) VALUES ('USD',2);
INSERT INTO woaho.moneda (moneda_nombre,moneda_territorio) VALUES ('MXN',3);

INSERT INTO woaho.unidad_tarifa (unidad_tarifa_nombre) VALUES ('Hora');
INSERT INTO woaho.unidad_tarifa (unidad_tarifa_nombre) VALUES ('Dia');
INSERT INTO woaho.unidad_tarifa (unidad_tarifa_nombre) VALUES ('Semana');
INSERT INTO woaho.unidad_tarifa (unidad_tarifa_nombre) VALUES ('Mes');
INSERT INTO woaho.unidad_tarifa (unidad_tarifa_nombre) VALUES ('Año');

INSERT INTO woaho.idioma (idioma_nombre,idioma_codigo) VALUES ('INGLES','EN');
INSERT INTO woaho.idioma (idioma_nombre,idioma_codigo) VALUES ('ESPAÑOL','ES');

INSERT INTO woaho.profesion (profesion_nombre) VALUES ('Ingeniero');
INSERT INTO woaho.profesion (profesion_nombre) VALUES ('Electrico');
INSERT INTO woaho.profesion (profesion_nombre) VALUES ('Estilista');

INSERT INTO woaho.etiqueta (etiqueta_valor,etiqueta_idioma,etiqueta_codigo) VALUES ('usuario',2,'1');
INSERT INTO woaho.etiqueta (etiqueta_valor,etiqueta_idioma,etiqueta_codigo) VALUES ('user',1,'1');
INSERT INTO woaho.etiqueta (etiqueta_valor,etiqueta_idioma,etiqueta_codigo) VALUES ('clave',2,'2');
INSERT INTO woaho.etiqueta (etiqueta_valor,etiqueta_idioma,etiqueta_codigo) VALUES ('password',1,'2');

INSERT INTO woaho.medio_pago (medio_pago_nombre,medio_pago_etiqueta,medio_pago_territorio) VALUES ('Efectivo','Efectivo',1);
INSERT INTO woaho.medio_pago (medio_pago_nombre,medio_pago_etiqueta,medio_pago_territorio) VALUES ('Credito','Credito',1);
INSERT INTO woaho.medio_pago (medio_pago_nombre,medio_pago_etiqueta,medio_pago_territorio) VALUES ('Debito','Debito',1);

INSERT INTO woaho.tipo (tipo_nombre) VALUES ('Slide');
INSERT INTO woaho.tipo (tipo_nombre) VALUES ('Pantalla');
INSERT INTO woaho.tipo (tipo_nombre) VALUES ('Titulo');
INSERT INTO woaho.tipo (tipo_nombre) VALUES ('Subtitulo');
INSERT INTO woaho.tipo (tipo_nombre) VALUES ('Pie');

/*****************************************************************************************************************
	Antes de insertar las pantallas, se deben subir las imagenes de cada pantalla para asignarlas en en este
	insert
*****************************************************************************************************************/
INSERT INTO woaho.pantalla (pantalla_nombre,pantalla_imagen,pantalla_tipo_pantalla) VALUES ('Slide uno',14,1);
INSERT INTO woaho.pantalla (pantalla_nombre,pantalla_imagen,pantalla_tipo_pantalla) VALUES ('Slide dos',15,1);
INSERT INTO woaho.pantalla (pantalla_nombre,pantalla_imagen,pantalla_tipo_pantalla) VALUES ('Slide tres',16,1);
INSERT INTO woaho.pantalla (pantalla_nombre,pantalla_imagen,pantalla_tipo_pantalla) VALUES ('Slide cuatro',17,1);

INSERT INTO woaho.mensaje (mensaje_tipo,mensaje_codigo) VALUES (3,'1');
INSERT INTO woaho.mensaje (mensaje_tipo,mensaje_codigo) VALUES (3,'2');
INSERT INTO woaho.mensaje (mensaje_tipo,mensaje_codigo) VALUES (3,'3');
INSERT INTO woaho.mensaje (mensaje_tipo,mensaje_codigo) VALUES (3,'4');

INSERT INTO woaho.mensaje (mensaje_tipo,mensaje_codigo) VALUES (4,'5');
INSERT INTO woaho.mensaje (mensaje_tipo,mensaje_codigo) VALUES (4,'6');
INSERT INTO woaho.mensaje (mensaje_tipo,mensaje_codigo) VALUES (4,'7');
INSERT INTO woaho.mensaje (mensaje_tipo,mensaje_codigo) VALUES (4,'8');

INSERT INTO woaho.mensaje (mensaje_tipo,mensaje_codigo) VALUES (5,'9');
INSERT INTO woaho.mensaje (mensaje_tipo,mensaje_codigo) VALUES (5,'10');
INSERT INTO woaho.mensaje (mensaje_tipo,mensaje_codigo) VALUES (5,'11');
INSERT INTO woaho.mensaje (mensaje_tipo,mensaje_codigo) VALUES (5,'12');

INSERT INTO woaho.mensaje_pantalla (mensaje_pantalla_pantalla_id,mensaje_pantalla_mensaje_id) VALUES (1,1);
INSERT INTO woaho.mensaje_pantalla (mensaje_pantalla_pantalla_id,mensaje_pantalla_mensaje_id) VALUES (1,5);
INSERT INTO woaho.mensaje_pantalla (mensaje_pantalla_pantalla_id,mensaje_pantalla_mensaje_id) VALUES (1,9);
INSERT INTO woaho.mensaje_pantalla (mensaje_pantalla_pantalla_id,mensaje_pantalla_mensaje_id) VALUES (2,2);
INSERT INTO woaho.mensaje_pantalla (mensaje_pantalla_pantalla_id,mensaje_pantalla_mensaje_id) VALUES (2,6);
INSERT INTO woaho.mensaje_pantalla (mensaje_pantalla_pantalla_id,mensaje_pantalla_mensaje_id) VALUES (2,10);
INSERT INTO woaho.mensaje_pantalla (mensaje_pantalla_pantalla_id,mensaje_pantalla_mensaje_id) VALUES (3,3);
INSERT INTO woaho.mensaje_pantalla (mensaje_pantalla_pantalla_id,mensaje_pantalla_mensaje_id) VALUES (3,7);
INSERT INTO woaho.mensaje_pantalla (mensaje_pantalla_pantalla_id,mensaje_pantalla_mensaje_id) VALUES (3,11);
INSERT INTO woaho.mensaje_pantalla (mensaje_pantalla_pantalla_id,mensaje_pantalla_mensaje_id) VALUES (4,4);
INSERT INTO woaho.mensaje_pantalla (mensaje_pantalla_pantalla_id,mensaje_pantalla_mensaje_id) VALUES (4,8);
INSERT INTO woaho.mensaje_pantalla (mensaje_pantalla_pantalla_id,mensaje_pantalla_mensaje_id) VALUES (4,12);

INSERT INTO woaho.pantalla (pantalla_nombre,pantalla_imagen,pantalla_tipo_pantalla) VALUES ('Slide uno',14,1);
INSERT INTO woaho.pantalla (pantalla_nombre,pantalla_imagen,pantalla_tipo_pantalla) VALUES ('Slide dos',15,1);
INSERT INTO woaho.pantalla (pantalla_nombre,pantalla_imagen,pantalla_tipo_pantalla) VALUES ('Slide tres',16,1);
INSERT INTO woaho.pantalla (pantalla_nombre,pantalla_imagen,pantalla_tipo_pantalla) VALUES ('Slide cuatro',17,1);

INSERT INTO woaho.traduccion (traduccion_codigo_mensaje,traduccion_traduccion,traduccion_idioma) values ('1','MANICURE',1);
INSERT INTO woaho.traduccion (traduccion_codigo_mensaje,traduccion_traduccion,traduccion_idioma) values ('1','MANICURE',2);

INSERT INTO woaho.traduccion (traduccion_codigo_mensaje,traduccion_traduccion,traduccion_idioma) values ('2','HAIRDRESSING',1);
INSERT INTO woaho.traduccion (traduccion_codigo_mensaje,traduccion_traduccion,traduccion_idioma) values ('2','PELUQUERÍA',2);

INSERT INTO woaho.traduccion (traduccion_codigo_mensaje,traduccion_traduccion,traduccion_idioma) values ('3','KITCHEN',1);
INSERT INTO woaho.traduccion (traduccion_codigo_mensaje,traduccion_traduccion,traduccion_idioma) values ('3','COCINA',2);

INSERT INTO woaho.traduccion (traduccion_codigo_mensaje,traduccion_traduccion,traduccion_idioma) values ('4','EVERYTHING AT YOUR HANDS',1);
INSERT INTO woaho.traduccion (traduccion_codigo_mensaje,traduccion_traduccion,traduccion_idioma) values ('4','TODO A TUS MANOS',2);

INSERT INTO woaho.traduccion (traduccion_codigo_mensaje,traduccion_traduccion,traduccion_idioma) values ('5','Separate your shift and you will have more than 1000 professionals at your service.',1);
INSERT INTO woaho.traduccion (traduccion_codigo_mensaje,traduccion_traduccion,traduccion_idioma) values ('5','Separa tu turno y tendras más de 1000 profesionales a tu servicio.',2);

INSERT INTO woaho.traduccion (traduccion_codigo_mensaje,traduccion_traduccion,traduccion_idioma) values ('6','Do you need a new style? We have professional service.',1);
INSERT INTO woaho.traduccion (traduccion_codigo_mensaje,traduccion_traduccion,traduccion_idioma) values ('6','Necesitas un nuevo estilo, contamos con servicio profesional.',2);

INSERT INTO woaho.traduccion (traduccion_codigo_mensaje,traduccion_traduccion,traduccion_idioma) values ('7','You no longer need to go to a restaurant, the restaurant goes to you.',1);
INSERT INTO woaho.traduccion (traduccion_codigo_mensaje,traduccion_traduccion,traduccion_idioma) values ('7','Ya no necesitas ir a un restaurante, el restaurante va a ti.',2);

INSERT INTO woaho.traduccion (traduccion_codigo_mensaje,traduccion_traduccion,traduccion_idioma) values ('8','We have qualified professionals to meet your needs.',1);
INSERT INTO woaho.traduccion (traduccion_codigo_mensaje,traduccion_traduccion,traduccion_idioma) values ('8','Contamos con los profesionales calificados para atender tus necesidades.',2);

INSERT INTO woaho.traduccion (traduccion_codigo_mensaje,traduccion_traduccion,traduccion_idioma) values ('9','Separate your shift and you will have more than 1000 professionals at your service.',1);
INSERT INTO woaho.traduccion (traduccion_codigo_mensaje,traduccion_traduccion,traduccion_idioma) values ('9','Separa tu turno y tendras más de 1000 profesionales a tu servicio.',2);

INSERT INTO woaho.traduccion (traduccion_codigo_mensaje,traduccion_traduccion,traduccion_idioma) values ('10','Find and feel renewed.',1);
INSERT INTO woaho.traduccion (traduccion_codigo_mensaje,traduccion_traduccion,traduccion_idioma) values ('10','Encuentra y sientete renovado.',2);

INSERT INTO woaho.traduccion (traduccion_codigo_mensaje,traduccion_traduccion,traduccion_idioma) values ('11','Find the best chefs and delight your palate.',1);
INSERT INTO woaho.traduccion (traduccion_codigo_mensaje,traduccion_traduccion,traduccion_idioma) values ('11','Encuentra los mejores chef y deleita tu paladar.',2);

INSERT INTO woaho.traduccion (traduccion_codigo_mensaje,traduccion_traduccion,traduccion_idioma) values ('12','Find everything you need, without leaving your home.',1);
INSERT INTO woaho.traduccion (traduccion_codigo_mensaje,traduccion_traduccion,traduccion_idioma) values ('12','Encuentra todo lo que necesitas, sin salir de tu casa.',2);