
/*
DROP DATABASE CubiGuiaTuristica;

CREATE DATABASE CubiGuiaTuristica;
use CubiGuiaTuristica;


create table Lugar( id varchar(500) primary key,
					nombre varchar(100) default '',
                    casa varchar(100) default '',
                    comuna varchar(100) default '',
                    region varchar(100) default '',
                    pais varchar(100) default '',
                    latitud float default 0.0,
                    longitud float default 0.0,
                    categoria varchar(50) default 'atraccion',
                    descripcion varchar(300) default 'No se ha proporcionado descripción a este lugar.'
					
                    
                    );
                    
create table Usuario(
						id varchar(30) primary key,
                        pass varchar(30) not null,
                        admin bool not null

					);
                    
create table Comentario(
							id int primary key auto_increment,
                            id_usuario varchar(30) unique references Usuario(id),
                            id_lugar varchar(500) references Lugar(id),
                            comentario varchar(300) default 'No se ha proporcionado comentario.',
                            puntuacion int not null
							


						);
                        
 
                        
                        
                        
use CubiGuiaTuristica;
                        
                        
DELIMITER $$
DROP PROCEDURE IF EXISTS getUsuario$$
CREATE PROCEDURE getUsuario(id varchar(30), pass varchar(30))
BEGIN
    SELECT *
    FROM Usuario
    WHERE Usuario.id= id AND Usuario.pass=pass;
END
$$                        


DELIMITER $$
DROP PROCEDURE IF EXISTS getLugares$$
CREATE PROCEDURE getLugares()
BEGIN
    SELECT *
    FROM Lugar;
END
$$ 


DELIMITER $$
DROP PROCEDURE IF EXISTS getLugar$$
CREATE PROCEDURE getLugar(id varchar(30))
BEGIN
    SELECT *
    FROM Lugar
    WHERE Lugar.id= id;
END
$$ 


DELIMITER $$
DROP PROCEDURE IF EXISTS getUsuarios$$
CREATE PROCEDURE getUsuarios()
BEGIN
    SELECT id as nombre, pass as contrasena, admin as adm
    FROM Usuario;
END
$$ 

call getUsuarios();


DELIMITER $$
DROP PROCEDURE IF EXISTS getComentario$$
CREATE PROCEDURE getComentario(idUsuario varchar(30), idLugar varchar(500))
BEGIN
    SELECT *
    FROM Comentario
    WHERE Comentario.id_usuario= idUsuario AND Comentario.id_lugar=idLugar;
END
$$ 


DELIMITER $$
DROP PROCEDURE IF EXISTS getComentarios$$
CREATE PROCEDURE getComentarios()
BEGIN
    SELECT *
    FROM Comentario;
END
$$ 

DELIMITER $$
DROP PROCEDURE IF EXISTS getLugaresCategoria$$
CREATE PROCEDURE getLugaresCategoria(cat varchar(30))
BEGIN
    SELECT *
    FROM Lugar
    WHERE Lugar.categoria= cat;
END
$$ 

DELIMITER $$
DROP PROCEDURE IF EXISTS agregarLugar$$
CREATE PROCEDURE agregarLugar(idL varchar(500),
				 nombreL varchar(100), 
                 casaL varchar(100), 
                 comunaL varchar(100), 
                 regionL varchar(100), 
                 paisL varchar(100), 
                 latitudL float, 
                 longitudL float, 
                 categoriaL varchar(50), 
                 descripcionL varchar(300))
BEGIN
    insert into Lugar(id, nombre, casa, comuna, region, pais, latitud, longitud, categoria, descripcion)
    values(idL, nombreL, casaL, comunaL, regionL, paisL, latitudL, longitudL, categoriaL, descripcionL);
END
$$ 

DELIMITER $$
DROP PROCEDURE IF EXISTS agregarUsuario$$
CREATE PROCEDURE agregarUsuario(idUsuario varchar(30), passUsuario varchar(30), adminUsuario boolean)
BEGIN
	INSERT INTO Usuario(id, pass, admin) 
    VALUES (idUsuario, passUsuario, adminUsuario);
END
$$ 

DELIMITER $$
DROP PROCEDURE IF EXISTS agregarComentario$$
CREATE PROCEDURE agregarComentario(idUsuario varchar(30), idLugar varchar(500), com varchar(300), pt int)
BEGIN
    insert into Comentario(id_usuario, id_lugar, comentario, puntuacion)
    VALUES(idUsuario, idLugar, com, pt);
END
$$ 

DELIMITER $$
DROP PROCEDURE IF EXISTS eliminarLugar$$
CREATE PROCEDURE eliminarLugar(idL varchar(500))
BEGIN
    DELETE FROM Lugar WHERE id=idL;
END
$$ 

DELIMITER $$
DROP PROCEDURE IF EXISTS eliminarUsuario$$
CREATE PROCEDURE elimminarUsuario(idUsuario varchar(30))
BEGIN
    DELETE FROM Usuario WHERE id=idUsuario;
END
$$ 


DELIMITER $$
DROP PROCEDURE IF EXISTS eliminarComentario$$
CREATE PROCEDURE elimminarComentario(idComentario int)
BEGIN
    DELETE FROM Comentario WHERE id=idComentario;
END
$$ 


DELIMITER $$
DROP PROCEDURE IF EXISTS modificarLugar$$
CREATE PROCEDURE modificarLugar(idL varchar(500),
				 nombreL varchar(100), 
                 casaL varchar(100), 
                 comunaL varchar(100), 
                 regionL varchar(100), 
                 paisL varchar(100), 
                 latitudL float, 
                 longitudL float, 
                 categoriaL varchar(50), 
                 descripcionL varchar(300))
BEGIN
    UPDATE Lugar
    SET nombre = nombreL,
		casa = casaL , 
        comuna = comunaL , 
        region = regionL , 
        pais = paisL ,
        latitud = latitudL , 
        longitud = longitudL , 
        categoria = categoriaL , 
        descripcion = descripcionL
	
    WHERE id = idL;
END
$$ 


DELIMITER $$
DROP PROCEDURE IF EXISTS modificarUsuario$$
CREATE PROCEDURE modificarUsuario(idUsuario varchar(30), passUsuario varchar(30), adminUsuario boolean)
BEGIN
    UPDATE Usuario
    SET pass = passUsuario,
        admin = adminUsuario
	where id = idUsuario;
END
$$ 

DELIMITER $$
DROP PROCEDURE IF EXISTS modificarComentario$$
CREATE PROCEDURE modificarComentario(idUsuario varchar(30), idLugar varchar(500), com varchar(300), pt int)
BEGIN
    UPDATE Comentario
    SET comentario = com,
		puntuacion = pt       
	WHERE id_lugar = idLugar AND id_usuario = idUsuario;
END
$$ 

DELIMITER $$
DROP PROCEDURE IF EXISTS getCategorias$$
CREATE PROCEDURE getCategorias()
BEGIN
    SELECT DISTINCT categoria 
    FROM Lugar;
END
$$

DELIMITER $$
DROP PROCEDURE IF EXISTS getZonas$$
CREATE PROCEDURE getZonas()
BEGIN
    SELECT DISTINCT comuna as Zona
    FROM Lugar;
END
$$





call agregarUsuario('admin', 'toor', true);
call agregarUsuario('alen', 'alen1', false);
*/
use CubiGuiaTuristica;

call getLugares();



