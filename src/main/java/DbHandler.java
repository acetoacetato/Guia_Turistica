package main.java;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import excepciones.PlaceAlreadyTakenException;
import excepciones.UserCheckException;
import excepciones.UserRegisterFailureException;



public class DbHandler {
	
	//variables necesarias para realizar querys a la db
	MysqlDataSource dbLugares;
	Connection coneccion;
	Statement stmt;
	ResultSet rs;
	
	public DbHandler() throws SQLException {
		dbLugares = new MysqlDataSource();
		dbLugares.setUser("sql10226436");
		dbLugares.setPassword("SS1Yd6sPyi");
		dbLugares.setServerName("sql10.freemysqlhosting.net");
		coneccion = dbLugares.getConnection();
		stmt = coneccion.createStatement();
		stmt.executeQuery("use sql10226436"); 
	}
	
	
	//ingresamos un nuevo lugar a la DB, mySQL verifica si ya se ha ingresado
	public boolean ingresarLugar(Lugar l) throws SQLException, PlaceAlreadyTakenException{
		
		ResultSet rs = stmt.executeQuery("select * from Lugar where id = '" +
							l.getId() + "' or casa = '" +
							l.getDireccionPpal() + "';");
		if(rs.next()) throw new PlaceAlreadyTakenException();
		
		rs = null;		
		stmt.executeUpdate("Insert into Lugar (id, categoria, casa, comuna, region, pais, latitud, longitud)"
				+ "values ('"
				+ l.getId() + "','"
				+ l.getCategoria() + "','"
				+ l.getDireccionPpal() + "','"
				+ l.getComuna() + "','"
				+ l.getRegion() + "','"
				+ l.getPais() + "',"
				+ String.valueOf( l.getLat() ) + ", "
				+ String.valueOf( l.getLng() ) + ");"
				);
		
		return true;
		
		
	}
	
	public float puntuacionLugar(String id) throws SQLException {
		ResultSet rs = stmt.executeQuery("select avg(puntuacion) from Comentario where id = '" + id + "';");
		if(rs.next()) {
			return rs.getFloat("avg(puntuacion)");
		}else 
			return 0f;
	}
	
	public void registrarUsuario(String usr, String pass) {
		
		Statement stmt;
		try {
			stmt = coneccion.createStatement();
			String query = "insert into Usuario (id, pass, adm) values" +
					"('" + usr 
					+ "','"+ pass + "', false);";
			System.out.println(query);
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		
	}
	
	
	//busca la cuenta que coincida con el inicio de sesion (usuario y password)
	public ResultSet iniciarSesion(String usr, String pass) throws SQLException, UserCheckException {
		Statement stmt = coneccion.createStatement();
		String query = "select * from Usuario where id = '" 
				+ usr + "' and pass = '"
				+ pass + "';";
		ResultSet rs = stmt.executeQuery(query);
		if(!rs.next()) throw new UserCheckException("Combinaci�n usuario/contrase�a inv�lida.");
		return rs;
	}
	
	public ResultSet verificarRegistro(String usr) throws SQLException, UserRegisterFailureException{
		Statement stmt = coneccion.createStatement();
		System.out.println(usr);
		ResultSet rs = stmt.executeQuery("Select * from Usuario where id = '" + usr + "';");
		if(rs.next()) throw new UserRegisterFailureException("Usuario ya registrado.");
		return rs;
	}
	
	
	//se busca un lugar por su id
	public ResultSet buscarLugar(String id) throws SQLException {
		Statement stmt = coneccion.createStatement();
		return stmt.executeQuery("select * from Lugar where id = '" + id + "';");

	}
	
	//se busca un comentario por su id
	public ResultSet buscarComentarios(String id) throws SQLException {
		Statement stmt = coneccion.createStatement();
		return stmt.executeQuery("select * from Comentario where idLugar = '" +  id + "';");
	}
	
	public void destroy() {
		dbLugares = null;
		coneccion = null;
		
	}
	
}
