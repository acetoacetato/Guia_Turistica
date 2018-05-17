package Colecciones;

import java.sql.ResultSet;
import java.sql.SQLException;

import main.java.Comentario;
import main.java.DbHandler;

public class MapaComentariosUsuario extends MapaComentarios {

	
	public MapaComentariosUsuario(String id) throws SQLException {
		importar();
	}
	
	private void importar() throws SQLException {
		DbHandler db = new DbHandler();
		ResultSet rs = db.buscarComentariosUsuario(id);
		while(rs.next()) {
			mapaComentarios.putIfAbsent(rs.getString("id_lugar"), new Comentario(rs));
			//System.out.println(rs.getString("id_usuario")+" "+rs.getString("id_lugar")+" "+rs.getString("comentario")+" "+rs.getInt("puntuacion"));
		}
	}

	
}
