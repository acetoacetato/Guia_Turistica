package main.java;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class BusquedaLugares {
	
	private HashMap<String, Lugar> mapaLugares;
	
	public BusquedaLugares(DbHandler db){
		
		try {
			ResultSet rs = db.cargaLugares();
			while(rs.next()) {
				Lugar l = new Lugar(rs.getString("id"),
									rs.getString("nombre"),
									rs.getString("casa"),
									rs.getString("comuna"),
									rs.getString("region"),
									rs.getString("pais"),
									rs.getFloat("latitud"),
									rs.getFloat("longitud"),
									rs.getString("categoria"),
									rs.getString("descripcion"));
				mapaLugares.put(l.getId(), l);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
}
