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