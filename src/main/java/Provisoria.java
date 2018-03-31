package main.java;

import java.sql.SQLException;
import java.util.ArrayList;

import ventanas.VentanaLugares;

public class Provisoria {

	public static void main(String[] args) {
		
		try {
			DbHandler db = new DbHandler();
			Lugar l = db.buscarLugar("Cafeta");
			System.out.println(l.getCategoria());
			l.setCategoria("comida");
			//db.actualizarLugar(l);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		ArrayList<Lugar> l = new ArrayList<Lugar>();
		
		for(int i=0 ; i<17 ; i++) {
			l.add(new Lugar("00ano123", "anoanopotto", "thegame", "tevaiaecharesawea", "ano",0.69, 70.0, "ona", "santiago", "jochimil"));
		}
		
		VentanaLugares ventanitaquesevaaromper = new VentanaLugares("trasero", l, new Usuario("tumama", "ano"), 0);
		ventanitaquesevaaromper.setVisible(true);
	}

}
