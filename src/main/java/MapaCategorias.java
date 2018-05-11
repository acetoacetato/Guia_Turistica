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
	
	public void agregar(Object o) {
		// TODO Auto-generated method stub
		return;

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
