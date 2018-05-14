package Colecciones;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

import Interfaces.Reportable;
import excepciones.PlaceAlreadyTakenException;
import main.java.Busqueda;
import main.java.DbHandler;
import main.java.Lugar;
import ventanas.VentanaReporte;

public class MapaLugares implements Reportable {

	private Hashtable<String, Lugar> mapaLugares; 
	
	public MapaLugares(String cat, String zona) throws SQLException {
		mapaLugares = new Hashtable<String, Lugar>();
		importar(cat, zona);
	}
	
	
	public Lugar agregar(String id, String nombre, String[] dir, String cat, float lat, float lng, String desc) throws PlaceAlreadyTakenException {
		
		Lugar l = new Lugar(id, nombre, dir, cat, lat, lng, desc);
		Lugar l2 = mapaLugares.putIfAbsent(id, l);
		
		if(l2 != null)
			throw new PlaceAlreadyTakenException();
		
		return l;
	
	}
	
	public void agregar(Lugar l) {
		mapaLugares.put(l.getId(), l);
		
	}
	
	public void modificar(String id, String nombre, String[] dir, String cat, float lat, float lng, String desc) {
		if(mapaLugares.get(id) != null) {
			mapaLugares.put(id, new Lugar(id, nombre, dir, cat, lat, lng, desc));
		}
		return;
	}
	
	public void mofificar(String id, String nombre, String desc) {
		
	}
	

	public void quitar(Object o) {
		if(o == null)
			return;
		Lugar l = (Lugar) o;
		mapaLugares.remove(l.getId());

	}

	public Lugar eliminarLugar(String id) {
		Lugar l = mapaLugares.get(id);
		if(l == null)		
			return null;
		mapaLugares.remove(id);
		return l;
	}
	
	public Lugar buscarLugar(String idLugar) {
		return mapaLugares.get(idLugar);
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
