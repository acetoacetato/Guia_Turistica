package Colecciones;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;


import main.java.Comentario;
import main.java.DbHandler;

public class MapaComentarios{
	protected Hashtable<String, Comentario> mapaComentarios;
	protected String id;
	
	
	
	/**
	 * Crea un mapa de comentarios vacío.
	 */
	public MapaComentarios() {
		mapaComentarios = new Hashtable<String, Comentario>();
	}
	
	/**
	 * Crea el mapa de comentarios de un lugar determinado.
	 * @param id : id del lugar.
	 * @throws SQLException
	 */
	public MapaComentarios(String id) throws SQLException {
		this.setIdLugar(id);
		mapaComentarios = new Hashtable<String, Comentario>();
		importar();
	}
	
	
	
	/**
	 * Llena el mapa con comentarios que tengan el id.
	 * @throws SQLException
	 */
	private void importar() throws SQLException {
		DbHandler db = new DbHandler();
		ResultSet rs = db.buscarComentarios(id);
		while(rs.next()) {
			mapaComentarios.putIfAbsent(rs.getString("id_usuario"), new Comentario(rs));
		}		
	}


	/**
	 * Retorna los Comentarios que están en el mapa
	 * @return Un ArrayList<Comentario> que contiene los comentarios que están en el mapa.
	 */
	public ArrayList<Comentario> valores() {
		return new ArrayList<Comentario>(mapaComentarios.values());
	}


	/**
	 * Busca el comentario y lo retorna.
	 * @param key : la clave del comentario a buscar.
	 * @return El comentario buscado.
	 */
	public Comentario getComentario(String key) {
		return mapaComentarios.get(key);
	}
	
	
	/**
	 * Obtiene un comentario a partir del nombre de usuario y el lugar donde se creó.
	 * @param usr : nombre del usuario.
	 * @param placeId : id del lugar.
	 * @return El comentario buscado.
	 */
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
	
	
	/**
	 * Obtiene los comentarios en el mapa.
	 * @return un arrayList<Comentario> con todos los comentarios en el mapa.
	 */
	public ArrayList<Comentario> getComentarios(){
		return new ArrayList<Comentario> (mapaComentarios.values());
	}
	

	/**
	 * Calcula el puntaje total de los comentarios en el mapa.
	 * @return el puntaje de los comentarios en el mapa, el puntaje es un número que puede ser +1 o -1, el rating se calcula como la suma de dichos puntajes.
	 */
	public int rating() {
		ArrayList<Comentario> l = getComentarios();
		
		int ptje = 0;
		
		for(Comentario c : l) {
			ptje += c.getPt();
		}
		
		return ptje;
	}


	/**
	 * Modifica el comentario de un usuario.
	 * @param comentAct Comentario nuevo.
	 * @param points puntaje nuevo.
	 * @param ids id del comentario a modificar.
	 * @throws SQLException
	 */
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

	/**
	 * Elmina un comentario a partir de la clave.
	 * @param clave clave del comentario a eliminar.
	 */
	public void eliminar(String clave) {
			
		mapaComentarios.remove(clave);
			
	}
		



	public String getIdLugar() {
		return id;
	}




	public void setIdLugar(String idLugar) {
		this.id = idLugar;
	}
	
}
