package Colecciones;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

import Interfaces.Reportable;
import excepciones.PlaceAlreadyTakenException;
import excepciones.PlaceException;
import main.java.Busqueda;
import main.java.DbHandler;
import main.java.Lugar;
import ventanas.VentanaReporte;

public class MapaCategorias implements Reportable {

	private Hashtable<String, MapaLugares> mapaCat;
	
	public MapaCategorias(String zona) throws SQLException {
		mapaCat = new Hashtable<String, MapaLugares>();
		importar(zona);
	}
	
	public Lugar agregar(String id, String nombre, String[] dir, String cat, float lat, float lng, String desc ) throws SQLException, PlaceAlreadyTakenException {
		
		MapaLugares m = mapaCat.get(cat);
		if(m != null) {
			return m.agregar(id, nombre, dir, cat, lat, lng, desc);
		}
		m = new MapaLugares(cat, dir[1]);
		mapaCat.put(cat, m);
		return m.agregar(id, nombre, dir, cat, lat, lng, desc);
		
	}
	
	
	/**
	 * Modifica un lugar del sistema
	 * @param id : id del lugar
	 * @param nombre : nuevo nombre del local
	 * @param cat : nueva categoria del local
	 * @param zona : nueva zona del local
	 * @param desc : nueva descripcion del local
	 * @throws PlaceException
	 * @throws SQLException
	 */
	public Lugar modificar(String id, String nombre, String cat, String desc ) throws PlaceException, SQLException {
		
		Lugar l = buscarLugar(id);
		
		if(l == null)
			throw new PlaceException("El lugar no existe en el sistema");
		
		l.actualizar(nombre, cat, desc);
		
		eliminarLugar(id);
		
		MapaLugares m = mapaCat.get(l.getCategoria());
		if(m == null) {
			m = new MapaLugares(l.getCategoria(), l.getComuna());
			mapaCat.put(l.getCategoria(), m);
		}
		
		m.agregar(l);
		
		return l;
		
		

	}
	
	
	
	public Lugar eliminarLugar(String id) {
		ArrayList<MapaLugares> l = new ArrayList<MapaLugares>(mapaCat.values());
		Lugar lugar;
		for(MapaLugares m : l) {
			if( (lugar = m.eliminarLugar(id)) != null )
				return lugar;
		}
		
		return null;
	}

	public Lugar buscarLugar(String idLugar) throws PlaceException {
		ArrayList<MapaLugares> l = new ArrayList<MapaLugares>(mapaCat.values());
		
		for(MapaLugares m : l) {
			Lugar lugar = m.buscarLugar(idLugar);
			if(lugar != null)
				return lugar;
		}
		
		throw new PlaceException("La categoría del lugar no se encuentra en el sistema.");
	}
	
	public void quitar(Object o) {
		return;   

	}

	public void modificar(Object o) {

	}
	
	public ArrayList<Lugar> obtenerLugares(String cat){
		MapaLugares m = mapaCat.get(cat);
		if(m == null)
			return new ArrayList<Lugar>();
		return m.obtenerLugares();
	}
	
	private void importar(String zona) throws SQLException {
		DbHandler db = new DbHandler();
		
		ResultSet rs = db.categorias();
		String s;
		
		while(rs.next()) {
			s = rs.getString("categoria");
			mapaCat.putIfAbsent(s, new MapaLugares(s, zona));
		}
	}

	@Override
	public void generarReporte(String path) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String reportePantalla() {
		// TODO Auto-generated method stub
		return "the game";
	}

	
	public void reporte(Busqueda b) {
		
		if(b.getTipo().equals("Categorias")) {
			VentanaReporte v = new VentanaReporte(this);
			v.setVisible(true);
			return;
		}else {
			String s = b.getParametro();
			MapaLugares m = mapaCat.get(s);
			m.reporte(b);
			
		}
		
			
		
		
		
		
	}

	public void eliminarComentario(String usr) throws SQLException {
		ArrayList<MapaLugares> l = new ArrayList<MapaLugares>(mapaCat.values());
		
		for(MapaLugares m : l) {
			m.eliminarComentario(usr);
		}
	}

}
