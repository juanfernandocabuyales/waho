INSERT INTO usuario(usuario_nombre,usuario_fecha_hora_acepta_terminos,usuario_correo,usuario_celular,usuario_apellido,usuario_acepta_terminos)
VALUES ('Juan Fernando',NOW(),'juancabuyales@gmail.com','3147452427','Cabuyales','S');

ALTER SEQUENCE woaho.sec_usuario RESTART WITH 1;