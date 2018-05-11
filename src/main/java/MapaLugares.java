package main.java;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

import ventanas.VentanaReporte;

public class MapaLugares implements Reportable {

	private Hashtable<String, Lugar> mapaLugares; 
	
	public MapaLugares(String cat, String zona) throws SQLException {
		mapaLugares = new Hashtable<String, Lugar>();
		importar(cat, zona);
	}
	
	
	public void agregar(Object o) {
		
		Lugar l = (Lugar) o;
		if(l == null)
			return;
		mapaLugares.putIfAbsent(l.getId(), l);
		
	}

	public void quitar(Object o) {
		if(o == null)
			return;
		Lugar l = (Lugar) o;
		mapaLugares.remove(l.getId());

	}

	public void modificar(Object o) {
		if(o == null)
			return;
		Lugar l = (Lugar) o;
		
		mapaLugares.replace(l.getId(), l);
		

	}
	
	public ArrayList<Lugar> obtenerLugares(){
		return new ArrayList<Lugar>(mapaLugares.values());
	}
	
	private void importar(String cat, String zona) throws SQLException {
		DbHandler db = new DbHandler();
		ResultSet rs = db.buscarLugar(cat, zona);
		while(rs.next()) {
			Lugar l = new Lugar(rs);
			mapaLugares.putIfAbsent(l.getId(),l);
		}
	}


	@Override
	public void generarReporte(String path) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public String reportePantalla() {
		// TODO Auto-generated method stub
		return "prueba";
	}


	@Override
	public void reporte(Busqueda b) {
		
		if(b.getTipo().equals("Lugares")) {
			VentanaReporte v = new VentanaReporte(this);
			v.setVisible(true);
			return;
		}else {
			String s = b.getParametro();
			Lugar l = mapaLugares.get(s);
			l.reporte(b);
		}
		
	}
}
