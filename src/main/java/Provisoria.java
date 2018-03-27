package main.java;

import java.util.ArrayList;

import ventanas.VentanaLugares;

public class Provisoria {

	public static void main(String[] args) {
		
		
		ArrayList<Lugar> l = new ArrayList<Lugar>();
		
		for(int i=0 ; i<17 ; i++) {
			l.add(new Lugar("00ano123", "anoanopotto", "thegame", "tevaiaecharesawea", "ano", "ona", "santiago", 0.69, 70.0, "jochimil"));
		}
		
		VentanaLugares ventanitaquesevaaromper = new VentanaLugares("trasero", l, new Usuario("tumama", "ano"), 0);
		ventanitaquesevaaromper.setVisible(true);
	}

}
