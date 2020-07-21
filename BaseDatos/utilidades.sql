INSERT INTO usuario(usuario_nombre,usuario_fecha_hora_acepta_terminos,usuario_correo,usuario_celular,usuario_apellido,usuario_acepta_terminos)
VALUES ('Juan Fernando',NOW(),'juancabuyales@gmail.com','3147452427','Cabuyales','S');

ALTER SEQUENCE woaho.sec_usuario RESTART WITH 1;

/** Bloque anonimo que permite ejecutar un procedimiento**/
DO $$ 
<<first_block>>
DECLARE
  salida character varying;
BEGIN 
   call woaho.prdb_generar_codigo_registro('3147453245',salida);
   RAISE NOTICE 'esto salio : %', salida;
END first_block $$;

/** Agregar FK a tabla ya creada **/
ALTER TABLE woaho.usuario
ADD CONSTRAINT FK_USUARIO_DIRECCION
FOREIGN KEY (usuario_direccion)
REFERENCES woaho.direccion(direccion_id)
MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION;
        
/** Cambia el tipo de dato en una colunma**/        
ALTER TABLE woaho.imagen 
ALTER COLUMN imagen_alto TYPE VARCHAR;

/**  Inserts de prueba**/
INSERT INTO woaho.imagen (imagen_nombre,imagen_ruta,imagen_alto,imagen_ancho) VALUES ('cabuyales','cabuyales.png',20,10);

INSERT INTO woaho.imagen (imagen_nombre,imagen_ruta,imagen_alto,imagen_ancho) VALUES ('software','software.png',20,10);

INSERT INTO woaho.profesional (profesional_nombre,profesional_apellido,profesional_profesiones,profesional_nacionalidad,profesional_servicios,profesional_lenguajes,profesional_descripcion,profesional_imagen_icono,profesional_cant_estrellas,profesional_cant_servicios)
					 VALUES ('Juan','Cabuyales','1-2-3',1,'1-3','1-2','Ingeniero experto en desarrollo de software',1,0,0);
					 
INSERT INTO woaho.categoria (categoria_imagen,categoria_descripcion) VALUES (10,'Software');
					 
INSERT INTO woaho.servicio (servicio_nombre,servicio_imagen,servicio_categoria)
							 VALUES ('Desarrollo Software',10,9);
							 
INSERT INTO woaho.tarifa (tarifa_valor,tarifa_moneda,tarifa_territorio,tarifa_servicio,tarifa_unidad)
							 VALUES (20000,1,1,1,1);
					 
INSERT INTO woaho.calificacion (calificacion_usuario,calificacion_profesional,calificacion_descripcion,calificacion_calificacion,calificacion_servicio)
					 VALUES (12,1,'Muy buen servicio',4,1);
					 
INSERT INTO woaho.calificacion (calificacion_usuario,calificacion_profesional,calificacion_descripcion,calificacion_calificacion,calificacion_servicio)
					 VALUES (11,1,'servicio regular',3,1);
					 

					 