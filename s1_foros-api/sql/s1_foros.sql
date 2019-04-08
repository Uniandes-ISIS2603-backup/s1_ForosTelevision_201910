delete from UsuarioEntity;
delete from MultimediaEntity;
delete from ArchivoEntity;

INSERT INTO PRODUCTORAENTITY
(ID,NOMBRE)VALUES(1,'Productora 1');

INSERT INTO 

INSERT INTO PRODUCCIONENTITY
(ID,CALIFICACIONPROMEDIO,CLASIFICACIONAUDIENCIA,DESCRIPCION,NOMBRE)
VALUES(1,0,12,'es una comedia de situación estadounidense estrenada el 24 de septiembre de 2007 por la cadena CBS. Está producida por la Warner Bros y Chuck Lorre.1​2​','The Big Bang Theory')

INSERT INTO USUARIOENTITY
(ID, CLAVE,NOMBRE,PRIVILEGIO,EMAIL)
VALUES(1,'Contrasena132','Juan Perez','USUARIO', 'juanperez@gmail.com')

INSERT INTO RESENAENTITY
(ID,CALIFICACIONPRODUCCION,CALIFICACIONRESENA,DESCRIPCION,FECHA,RECOMENDADA,PRODUCCIONRESENA_ID,USUARIORESENA_ID)
VALUES(1,4.2.0,0,'Es una serie muy entretenida, me encantó','29/03/2019','true','1','1')

INSERT INTO EMISIONENTITY
(ID,fechafin,fechainicio,rating)
VALUES(1,TO_DATE('2019-06-05', 'YYYY-MM-DD').TO_DATE('2019-01-05', 'YYYY-MM-DD'),3.0

INSERT INTO CANALENTITY
(nombre,rating,emision_id)
VALUES('Canal 1', 2.5,1)

INSERT INTO CANALENTITY
(nombre,rating,emision_id)
VALUES('tbs', 2.5,1)

INSERT INTO CANALENTITY
(nombre,rating,emision_id)
VALUES('Fox', 2.5,1)

INSERT INTO CANALENTITY
(nombre,rating,emision_id)
VALUES('Canal 2', 2.5,1)