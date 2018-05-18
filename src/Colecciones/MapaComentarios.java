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
	protected Hashtable<String, Comentario> mapaComentarios;
	protected String id;
	public MapaComentarios() {
		mapaComentarios = new Hashtable<String, Comentario>();
	}
	
	public MapaComentarios(String id) throws SQLException {
		this.setIdLugar(id);
		mapaComentarios = new Hashtable<String, Comentario>();
		importar();
	}
	
	
	
	
	private void importar() throws SQLException {
		DbHandler db = new DbHandler();
		ResultSet rs = db.buscarComentarios(id);
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
	
	public ArrayList<Comentario> getComentarios(){
		return new ArrayList<Comentario> (mapaComentarios.values());
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
		
	}





	@Override
	public String reportePantalla() {
		return null;
	}




	public void reporte(Busqueda b) {
		
	}




	public String getIdLugar() {
		return id;
	}




	public void setIdLugar(String idLugar) {
		this.id = idLugar;
	}


	public int rating() {
		ArrayList<Comentario> l = getComentarios();
		
		int ptje = 0;
		
		for(Comentario c : l) {
			ptje += c.getPt();
		}
		
		return ptje;
	}


	public void modificar(String comentAct, String points, String ids) throws SQLException {
		DbHandler db=new DbHandler();
		Comentario c=getComentario(ids,id);
		if(c==null) {
			c=new Comentario(ids,ids,comentAct,Float.parseFloat(points));
			mapaComentarios.put(ids, c);
		}
		else {
			c.setCom(comentAct);
			c.setPt(Float.parseFloat(points));
		}
		db.modificarComentario(comentAct, c, id, points);
	}

	public void eliminar(String clave) {
			
		mapaComentarios.remove(clave);
			
	}
		
	
}
