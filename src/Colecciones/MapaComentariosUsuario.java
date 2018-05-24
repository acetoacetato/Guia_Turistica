package Colecciones;

import java.sql.ResultSet;
import java.sql.SQLException;

import main.java.Comentario;
import main.java.DbHandler;

public class MapaComentariosUsuario extends MapaComentarios {

	/**
	 * Constructor de los mapas del usuario
	 * @param id : id del usuario.
	 * @throws SQLException
	 */
	public MapaComentariosUsuario(String id) throws SQLException {
		importar();
	}
	
	/**
	 * importa los comentarios del ususairo desde la base de datos.
	 * @throws SQLException
	 */
	private void importar() throws SQLException {
		DbHandler db = new DbHandler();
		ResultSet rs = db.buscarComentariosUsuario(id);
		while(rs.next()) {
			mapaComentarios.putIfAbsent(rs.getString("id_lugar"), new Comentario(rs));
		}
	}

	
	

	
}
