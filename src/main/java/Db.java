package main.java;

import java.sql.*;

public class Db {
	private static Connection conexion = null;
	
	
	/**
	 * Genera conexión y registra el driver de la base de datos.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public Db() throws SQLException, ClassNotFoundException{
		
		Class.forName("com.mysql.jdbc.Driver");
		
		conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/CubiGuiaTuristica?useSSL=false", "root", "c5536652c");		
		
	}
	
	
	public static Connection getConexion() {
		return conexion;
	}
}
