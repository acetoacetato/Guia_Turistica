package main.java;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.google.maps.errors.ApiException;
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
		dbLugares.setUser("cubiUsuario");
		dbLugares.setPassword("c5536652c");
		dbLugares.setServerName("guiaturistica.cxdybqqakuce.sa-east-1.rds.amazonaws.com");
		coneccion = dbLugares.getConnection();
		stmt = coneccion.createStatement();
		stmt.executeQuery("use Guia_Turistica"); 
	}
	
	
	//ingresamos un nuevo lugar a la DB, mySQL verifica si ya se ha ingresado
	public boolean ingresarLugar(Lugar l) throws SQLException, PlaceAlreadyTakenException{
		
		ResultSet rs = stmt.executeQuery("select * from Lugar where id = '" +
							l.getId() + "' or casa = '" +
							l.getDireccionPpal() + "';");
		if(rs.next()) throw new PlaceAlreadyTakenException();
		
		rs = null;		
		stmt.executeUpdate("Insert into Lugar (id, nombre, casa, comuna, region, pais, latitud, longitud, categoria, descripcion)"
				+ "values ('"
				+ l.getId() + "','"
				+ l.getNombreLocal() + "','"
				+ l.getDireccionPpal() + "','"
				+ l.getComuna() + "','"
				+ l.getRegion() + "','"
				+ l.getPais() + "',"
				+ String.valueOf( l.getLat() ) + ", "
				+ String.valueOf( l.getLng() ) + ", '"
				+ l.getCategoria() + "','"
				+ l.getDescripcion() + "');"
				);
		
		return true;
		
		
	}
	
	public int puntuacionLugar(String id) throws SQLException {
		ResultSet rs = stmt.executeQuery("select sum(puntuacion) from Comentario where id = '" + id + "';");
		if(rs.next()) {
			return rs.getInt("sum(puntuacion)");
		}else 
			return 0;
	}
	
	public void registrarUsuario(String usr, String pass) {
		
		Statement stmt;
		try {
			stmt = coneccion.createStatement();
			String query = "insert into Usuario (id, pass, admin) values" +
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
		if(!rs.next()) throw new UserCheckException("Combinación usuario/contraseña inválida.");
		return rs;
	}
	
	public ResultSet verificarRegistro(String usr) throws SQLException, UserRegisterFailureException{
		Statement stmt = coneccion.createStatement();
		System.out.println(usr);
		ResultSet rs = stmt.executeQuery("Select * from Usuario where id = '" + usr + "';");
		if(rs.next()) throw new UserRegisterFailureException("Usuario ya registrado.");
		return rs;
	}
	
	
	public void actualizarLugar(Lugar l) throws SQLException {
		Statement stmt = coneccion.createStatement();
		stmt.executeUpdate("UPDATE Lugar "
				+ "SET nombre = '" + l.getNombreLocal() + "'"
				+ ", casa = '" + l.getDireccionPpal() + "'"
				+ ", comuna = '" + l.getComuna() + "'"
				+ ", region = '" + l.getRegion() + "'"
				+ ", pais = '" + l.getPais() + "'"
				+ ", latitud =" + l.getLat()
				+ ", longitud =" + l.getLng()
				+ ", categoria = '" + l.getCategoria() + "'"
				+ ", descripcion = '" + l.getDescripcion() + "'"
				+ "WHERE id = '" + l.getId() + "'");
	}
	

	
	
	
	//se busca un lugar por su id
	/*public Lugar buscarLugar(String id) throws SQLException {
		Statement stmt = coneccion.createStatement();
		String query = "select * from Lugar where id = '" + id + "';";
		System.out.println(query);
		ResultSet rs = stmt.executeQuery("select * from Lugar where id = '" + id + "';");
		if(rs.next())
			return new Lugar(rs);
		
		else return null;

	}*/
	
	//Se busca un lugar por el nombre del local
	public Lugar buscarLugar(String name) throws SQLException{
		Statement stmt = coneccion.createStatement();
		ResultSet rs = stmt.executeQuery("select * from Lugar where nombre = '" + name + "';");
		
		if(rs.next())
			return new Lugar(rs);
		
		else return null;

	}
	
	public ArrayList<Lugar> buscarLugar(String cat, String ubic) throws SQLException {
		Statement stmt = coneccion.createStatement();
		String query = "SELECT * FROM Lugar WHERE categoria = '"
				 + cat + "' AND comuna = '" + ubic + "';";
		ResultSet rs = stmt.executeQuery( query );
		ArrayList <Lugar> li = new ArrayList<Lugar>();
		while(rs.next()) {
			Lugar l = new Lugar(rs);
			int pt = obtenerPuntuacion(l.getId());
			l.setPuntuacion( pt );
			li.add( l );
		}
		return li;
	}
	
	
	public Lugar autoCompletaLugar(String busqueda) throws SQLException, ApiException, InterruptedException, IOException {
		MapApi mapita = new MapApi();
		Lugar l;

			l = mapita.buscaLugar(busqueda);
			String id = l.getId();
			Statement stmt = coneccion.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Lugar where id = '" + id + "';");
			if(rs.next()) {
				return new Lugar(rs);
			}
			
			else
				return l;

		
		
		
	}
	
	public ArrayList<ArrayList <Lugar>> cargaLugares(String ubicacion) throws SQLException {
		ArrayList<ArrayList<Lugar>> listilla = new ArrayList<ArrayList<Lugar>>();
		Statement stmt = coneccion.createStatement();
		String[] categorias = { "atraccion", "dormir", "comida", "vida_nocturna" };
		for(String i : categorias) {
			listilla.add( buscarLugar(i, ubicacion) );
		}
		return listilla;
	}
	
	public Object buscar(Lugar l) throws SQLException {
		Statement stmt = coneccion.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM Lugar WHERE id = '" + l.getId() + "'");
		if(rs.next()) 
			return new Lugar(rs);
		else return null;
	}
	
	
	//TODO: agregar busqueda de usuarios y comentarios 
	public Object buscar(Usuario usr) {
		return null;
	}
	
	public void eliminar(Lugar l) throws SQLException{
		Statement stmt = coneccion.createStatement();
		stmt.executeUpdate("DELETE FROM Lugar WHERE id = '" + l.getId() + "'");
	}
	
	
	//se busca un comentario por su id
	public ResultSet buscarComentarios(String id) throws SQLException {
		Statement stmt = coneccion.createStatement();
		return stmt.executeQuery("select * from Comentario where idLugar = '" +  id + "';");
	}
	
	public int obtenerPuntuacion(String id) throws SQLException {
		Statement stmt = coneccion.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT SUM(puntuacion) FROM Comentario WHERE id_lugar = '" + id + "';");
		if(rs.next()) 
			return rs.getInt("SUM(puntuacion)");
		else 
			return 0;
	}
	
	public void destroy() {
		dbLugares = null;
		coneccion = null;
		
	}
	
}
