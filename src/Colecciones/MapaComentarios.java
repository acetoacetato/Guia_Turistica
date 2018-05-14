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
	private Hashtable<String, Comentario> mapaComentarios;
	private String idLugar;
	
	public MapaComentarios(String idLugar) throws SQLException {
		this.setIdLugar(idLugar);
		mapaComentarios = new Hashtable<String, Comentario>();
		importar();
	}
	
	
	
	
	private void importar() throws SQLException {
		DbHandler db = new DbHandler();
		ResultSet rs = db.buscarComentarios(idLugar);
		while(rs.next()) {
			mapaComentarios.putIfAbsent(rs.getString("id_usuario"), new Comentario(rs));
		}		
	}


	public ArrayList<Comentario> valores() {
		return new ArrayList<Comentario>(mapaComentarios.values());
	}


	public Comentario getComentario(String key) {
		return mapaComentarios.get(key);
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
	public ArrayList<Comentario> getComentariosUsr(String usr){
		ArrayList<Comentario> l = new ArrayList<Comentario>(mapaComentarios.values());
		ArrayList<Comentario> l2 = new ArrayList<Comentario>();
		for(Comentario c : l) {
			if(c.getUsr().equals(usr))
				l2.add(c);
		}
		
		return l2;
		
	}
	
	public ArrayList<Comentario> getComentariosLugar(String placeId){
		ArrayList<Comentario> l = new ArrayList<Comentario>(mapaComentarios.values());
		ArrayList<Comentario> l2 = new ArrayList<Comentario>();
		
		for(Comentario c : l) {
			if(c.getPlaceId().equals(placeId))
				l2.add(c);
		}
		
		return l2;
	}

	public void agregar(Object o) {
		if(o == null)
			return;
		Comentario c = (Comentario) o;
		mapaComentarios.put(c.getUsr(), c);
		
	}

	public void quitar(Object o) {
		if(o == null)
			return;
		Comentario c = (Comentario) o;
		mapaComentarios.remove(c.getUsr());
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




	public String getIdLugar() {
		return idLugar;
	}




	public void setIdLugar(String idLugar) {
		this.idLugar = idLugar;
	}




	public void modificar(String comentAct, String points, String ussr) throws SQLException {
		DbHandler db=new DbHandler();
		Comentario c=getComentario(ussr,idLugar);
		if(c==null) {
			c=new Comentario(ussr,ussr,comentAct,Float.parseFloat(points));
			mapaComentarios.put(ussr, c);
		}
		else {
			c.setCom(comentAct);
			c.setPt(Float.parseFloat(points));
		}
		db.modificarComentario(comentAct, c, idLugar, points);
	}
	
}
