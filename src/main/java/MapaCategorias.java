package main.java;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

import ventanas.VentanaReporte;

public class MapaCategorias implements Reportable {

	private Hashtable<String, MapaLugares> mapaCat;
	
	public MapaCategorias(String zona) throws SQLException {
		mapaCat = new Hashtable<String, MapaLugares>();
		importar(zona);
	}
	
	public void agregar(String id, String nombre, String[] dir, String cat, float lat, float lng, String desc ) throws SQLException {
		
		MapaLugares m = mapaCat.get(cat);
		if(m != null) {
			m.agregar(id, nombre, dir, cat, lat, lng, desc);
			return;
		}
		m = new MapaLugares(cat, dir[1]);
		mapaCat.putIfAbsent(cat, m);
		m.agregar(id, nombre, dir, cat, lat, lng, desc);
		
	}
	
	public void modificar(String id, String nombre, String[] dir, String cat, float lat, float lng, String desc ) {
		MapaLugares m = mapaCat.get(cat);
		if(m == null) 
			return;

		m.modificar(id, nombre, dir, cat, lat, lng, desc);
		
		
		
	}
	
	public boolean eliminarLugar(String id) {
ArrayList<MapaLugares> l = new ArrayList<MapaLugares>(mapaCat.values());
		
		for(MapaLugares m : l) {
			if(m.eliminarLugar(id))
				return true;
		}
		
		return false;
	}

	public void quitar(Object o) {
		// TODO Auto-generated method stub
		return;   

	}

	public void modificar(Object o) {
		// TODO Auto-generated method stub

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
		return null;
	}

	@Override
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

}
