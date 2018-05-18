package main.java;

import java.sql.SQLException;

import javax.swing.JOptionPane;

import ventanas.VentanaInicioSesion;

public class Main {
	private  static SistemaMapa sistema;
	public static void main(String[] args) {
		try {
			new Db();
			sistema = new SistemaMapa();
			VentanaInicioSesion ventana = new VentanaInicioSesion(sistema);
			ventana.setVisible(true);
		}catch(ClassNotFoundException | SQLException e) {
			System.err.println(e.getMessage());
			JOptionPane.showMessageDialog(null, "Error al conectarse a la base de datos. Verifique que tiene una conexi�n a internet distinta y que el puerto 3306 est� abierto", 
					"No se pudo ingresar a la base de datos",
                    JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
	}
	
	
}
