package Colecciones;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

import Interfaces.Reportable;
import main.java.Busqueda;
import main.java.Comentario;
import main.java.DbHandler;

public class MapaComentarios implements Reportable {
	private Hashtable<Integer, Comentario> mapaComentarios;
	
	
	public MapaComentarios() throws SQLException {
		mapaComentarios = new Hashtable<Integer, Comentario>();
		DbHandler db = new DbHandler();
		ResultSet rs = db.comentarios();
		while(rs.next()) {
			mapaComentarios.putIfAbsent(rs.getInt("id"), new Comentario(rs));
		}
	}
	
	
	
	
	public Comentario getComentario(int key) {
		Comentario c = mapaComentarios.get(key);
		return c;
	}
	
	public Comentario getComentario(String usr, String placeId) {
		
		ArrayList<Comentario> l = new ArrayList<Comentario>(mapaComentarios.values());
		for(Comentario i : l) {
			if(i.getUsr().equals(usr) && i.getPlaceId().equals(placeId))
				return i;
		}
		
		return null;
		
	}
	/**
	 * 
	 * @param usr : el nombre de usuario
	 * @return ArrayList<Comentario> con los comentarios hechos por el usuario usr
	 */
	public ArrayList<Comentario> getComentario(String usr){
		ArrayList<Comentario> l = new ArrayList<Comentario>(mapaComentarios.values());
		ArrayList<Comentario> l2 = new ArrayList<Comentario>();
		for(Comentario c : l) {
			if(c.getUsr().equals(usr))
				l2.add(c);
		}
		
		return l2;
		
	}

	public void agregar(Object o) {
		if(o == null)
			return;
		Comentario c = (Comentario) o;
		mapaComentarios.put(c.getId(), c);
		
	}

	public void quitar(Object o) {
		if(o == null)
			return;
		Comentario c = (Comentario) o;
		mapaComentarios.remove(c.getId());
	}

	public void modificar(Object o) {
		if(o == null)
			return;
		Comentario c = (Comentario) o;
		
		mapaComentarios.replace(c.getId(), c);
		
		
	}




	@Override
	public void generarReporte(String path) {
		// TODO Auto-generated method stub
		
	}





	@Override
	public String reportePantalla() {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public void reporte(Busqueda b) {
		// TODO Auto-generated method stub
		
	}
	
}
