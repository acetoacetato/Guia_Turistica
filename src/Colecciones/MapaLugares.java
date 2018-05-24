package Colecciones;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

import excepciones.PlaceAlreadyTakenException;
import excepciones.PlaceException;
import main.java.DbHandler;
import main.java.Lugar;

public class MapaLugares  {

	private Hashtable<String, Lugar> mapaLugares; 
	
	
	/**
	 * Crea un mapa de los lugares con categoría y zona(comuna) coincidentes.
	 * @param cat : categoría del mapa.
	 * @param zona : zona del mapa.
	 * @throws SQLException
	 */
	public MapaLugares(String cat, String zona) throws SQLException {
		mapaLugares = new Hashtable<String, Lugar>();
		importar(cat, zona);
	}
	
	
	/**
	 * Agrega un lugar a la colección.
	 * @param id : id del lugar a agregar.
	 * @param nombre : nombre del lugar a agregar.
	 * @param dir : String[] conteniendo la dirección del lugar, se asume que es de largo 4 y contiene los valores en el orden de: nombre de calle + número, comuna, región, país.
	 * @param cat : categoría del lugar a agregar.
	 * @param lat : latitud del lugar.
	 * @param lng : longitud del lugar.
	 * @param desc : descripción del lugar.
	 * @return Una instancia del lugar agregado.
	 * @throws SQLException
	 * @throws PlaceAlreadyTakenException En caso de que el lugar ya esté en el Mapa, se lanza la excepción.
	 */
	public Lugar agregar(String id, String nombre, String[] dir, String cat, float lat, float lng, String desc) throws PlaceAlreadyTakenException, SQLException {
		
		Lugar l = new Lugar(id, nombre, dir, cat, lat, lng, desc);
		Lugar l2 = mapaLugares.putIfAbsent(id, l);
		
		if(l2 != null)
			throw new PlaceAlreadyTakenException();
		
		return l;
	
	}
	
	/**
	 * Agrega un lugar almapa.
	 * @param l : Lugar a agregar
	 */
	public void agregar(Lugar l) {
		mapaLugares.put(l.getId(), l);
		
	}
	
	
	/**
	 * Modifica un lugar.
	 * @param id : id del lugar a modificar.
	 * @param nombre : nuevo nombre del lugar.
	 * @param dir : nueva dirección, es un String[] de tamaño 4 con orden: nombre de la calle + número, comuna, región, país
	 * @param cat : nueva categoría.
	 * @param lat : nueva latitud.
	 * @param lng : nueva longitud.
	 * @param desc : nueva descripción.
	 * @throws SQLException
	 */
	public void modificar(String id, String nombre, String[] dir, String cat, float lat, float lng, String desc) throws SQLException {
		if(mapaLugares.get(id) != null) {
			mapaLugares.put(id, new Lugar(id, nombre, dir, cat, lat, lng, desc));
		}
		return;
	}
	
	
	

	
	/**
	 * Elimina un lugar del mapa
	 * @param id : el id del lugar a eliminar.
	 * @return Retorna una referencia al lugar eliminado del mapa.
	 */
	public Lugar eliminarLugar(String id) {
		Lugar l = mapaLugares.get(id);
		if(l == null)		
			return null;
		mapaLugares.remove(id);
		return l;
	}
	
	public Lugar buscarLugar(String idLugar) throws PlaceException {
		
		Lugar l = mapaLugares.get(idLugar);
		
		if(l != null)
			return l;
		
		throw new PlaceException("El lugar no se encuentra en el sistema.");
	}
	
	
	public ArrayList<Lugar> obtenerLugares(){
		return new ArrayList<Lugar>(mapaLugares.values());
	}
	
	
	/**
	 * Inserta todos los lugares con categoría y zona (comuna) determinada en el mapa.
	 * @param cat : la categoría de los lugares a ingresar.
	 * @param zona : la zona de los lugares a ingresar.
	 * @throws SQLException
	 */
	private void importar(String cat, String zona) throws SQLException {
		DbHandler db = new DbHandler();
		ResultSet rs = db.buscarLugar(cat, zona);
		while(rs.next()) {
			Lugar l = new Lugar(rs);
			mapaLugares.putIfAbsent(l.getId(),l);
		}
	}


	
	

	/**
	 * Elimina los comentario de un usuario.
	 * @param usr : el usuario de los comentarios a los que se van a eliminar.
	 * @throws SQLException
	 */
	public void eliminarComentario(String usr) throws SQLException {
		ArrayList<Lugar> l = new ArrayList<Lugar>(mapaLugares.values());
		DbHandler db = new DbHandler();
		for(Lugar lugar : l) {
			lugar.eliminarComentario(usr);
			db.eliminarComentario( lugar.getId(), usr );
		}
		
	}
}
