INSERT INTO USUARIOENTITY(ID,CLAVE,EMAIL,NOMBRE,PRIVILEGIO)
VALUES(1,'Admin2019Master','email@email.com','Admin','ADMINISTRADOR');
INSERT INTO USUARIOENTITY(ID,CLAVE,EMAIL,NOMBRE,PRIVILEGIO)
VALUES(2,'Admin2019Master','usuario@usuario.com','Usuario','USUARIO');
INSERT INTO USUARIOENTITY(ID,CLAVE,EMAIL,NOMBRE,PRIVILEGIO)
VALUES(3,'Admin2019Master','usuario2@usuario.com','Usuario 2','USUARIO');

INSERT INTO PRODUCTORAENTITY
(ID,NOMBRE)VALUES(1,'Productora 1');


INSERT INTO CANALENTITY
(id,nombre,rating)
VALUES(1,'Canal 1', 2.5);
INSERT INTO CANALENTITY
(id,nombre,rating,)
VALUES(2,'tbs', 2.5);
INSERT INTO CANALENTITY
(id,nombre,rating)
VALUES(3,'Fox', 2.5);
INSERT INTO CANALENTITY
(id,nombre,rating,)
VALUES(4,'Canal 2', 2.5);

INSERT INTO ARCHIVOENTITY(ID,URL)
VALUES(1, 'https://firebasestorage.googleapis.com/v0/b/foros-ad042.appspot.com/o/5c78551827816.jpeg?alt=media&token=7fb3842a-a648-4dc7-9282-5d77de36be57');
INSERT INTO ARCHIVOENTITY(ID,URL)
VALUES(2, 'https://firebasestorage.googleapis.com/v0/b/foros-ad042.appspot.com/o/1.jpg?alt=media&token=25b6d102-426c-4d6e-bcc8-433f9e5c56a7');
INSERT INTO ARCHIVOENTITY(ID,URL)
VALUES(3, 'https://firebasestorage.googleapis.com/v0/b/foros-ad042.appspot.com/o/2.jpg?alt=media&token=505f41f6-c9ee-420d-bd90-d241638b1158');

INSERT INTO MULTIMEDIAENTITY(ID,PORTADA,VIDEO)
VALUES(1,'https://firebasestorage.googleapis.com/v0/b/foros-ad042.appspot.com/o/1.jpg?alt=media&token=25b6d102-426c-4d6e-bcc8-433f9e5c56a7','https://www.youtube.com/watch?v=rlR4PJn8b8I');

INSERT INTO MULTIMEDIAENTITY_ARCHIVOENTITY(MULTIMEDIAENTITY_ID,IMAGENES_ID)
VALUES(1,1);
INSERT INTO MULTIMEDIAENTITY_ARCHIVOENTITY(MULTIMEDIAENTITY_ID,IMAGENES_ID)
VALUES(1,2);
INSERT INTO MULTIMEDIAENTITY_ARCHIVOENTITY(MULTIMEDIAENTITY_ID,IMAGENES_ID)
VALUES(1,3);

INSERT INTO PRODUCCIONENTITY
(ID,CALIFICACIONPROMEDIO,CLASIFICACIONAUDIENCIA,DESCRIPCION,NOMBRE)
VALUES(1,1,'ADULTOS','Game of Thrones es una serie de televisión estadounidense de fantasía medieval creada por David Benioff y D. B. Weiss','Game of Thrones');

INSERT INTO CAPITULOENTITY(ID,DESCRIPCION,DURACION)
VALUES(1,'Winter Is Coming',40);
INSERT INTO CAPITULOENTITY(ID,DESCRIPCION,DURACION)
VALUES(2,'The Kingsroad',40);
INSERT INTO CAPITULOENTITY(ID,DESCRIPCION,DURACION)
VALUES(3,'Lorn Snow',40);

INSERT INTO RESENAENTITY
(ID,CALIFICACIONPRODUCCION,CALIFICACIONRESENA,DESCRIPCION,FECHA,RECOMENDADA,PRODUCCIONRESENA_ID,USUARIORESENA_ID)
VALUES(1,4.2.0,0,'Es una serie muy entretenida, me encantó','29/03/2019','true',1,1)

INSERT INTO EMISIONENTITY
(ID,fechafin,fechainicio,rating)
VALUES(1,'2019-06-05','2019-01-05',3.0);

INSERT INTO STAFFENTITY(ID,DESCRIPCION,FOTO,NOMBRE,ROL)
VALUES(1,'Es un actor de cine y televisión británico,','https://upload.wikimedia.org/wikipedia/commons/thumb/a/aa/Sean_Bean_TIFF_2015.jpg/250px-Sean_Bean_TIFF_2015.jpg','Shaun Mark Bean','ACTOR');
INSERT INTO STAFFENTITY(ID,DESCRIPCION,FOTO,NOMBRE,ROL)
VALUES(2,'Es un actor escocés, más conocido por interpretar a Robb Stark ','https://upload.wikimedia.org/wikipedia/commons/thumb/2/27/Richard_Madden_%289347943769%29_%28cropped%29.jpg/800px-Richard_Madden_%289347943769%29_%28cropped%29.jpg','Richard Madden','ACTOR');
INSERT INTO STAFFENTITY(ID,DESCRIPCION,FOTO,NOMBRE,ROL)
VALUES(3,'Es una actriz inglesa. Su papel debut interpretando a Sansa Stark ','https://upload.wikimedia.org/wikipedia/commons/thumb/b/b5/Sophie_Turner_by_Gage_Skidmore_2.jpg/800px-Sophie_Turner_by_Gage_Skidmore_2.jpg','Sophie Turner','ACTOR');

INSERT INTO USUARIOENTITY_EMSIONENTITY(USUARIOENTITY_ID,PARRILLA_ID)
VALUES(1,1);

INSERT INTO USUARIOENTITY_USUARIOENTITY(USUARIOENTITY_ID,SEGUIDOS_ID)
VALUES(2,3);

