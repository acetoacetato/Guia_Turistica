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
		
		//se genera la conexión con una base de datos
		dbLugares = new MysqlDataSource();
		
		//se setea el usuario y la contraseña para conectarse
		dbLugares.setUser("cubiUsuario");
		dbLugares.setPassword("c5536652c");
		
		//se setea la url de la db
		dbLugares.setServerName("guiaturistica.cxdybqqakuce.sa-east-1.rds.amazonaws.com");
		
		//se conecta a la base de datos
		coneccion = dbLugares.getConnection();
		stmt = coneccion.createStatement();
		
		//se selecciona la base de datos a usar
		stmt.executeQuery("use Guia_Turistica"); 
	}
	
	
	//ingresamos un nuevo lugar a la DB, mySQL verifica si ya se ha ingresado
	public boolean ingresarLugar(Lugar l) throws SQLException, PlaceAlreadyTakenException{
		
		//se ejecuta la consulta
		ResultSet rs = stmt.executeQuery("select * from Lugar where id = '" +
							l.getId() + "' or casa = '" +
							l.getDireccionPpal() + "';");
		
		//el ResultSet comienza en -1, por lo que si existe next es porque había al menos un resultado coincidente
		//por lo que el lugar ya está ingresado y se lanza la excepción
		if(rs.next()) throw new PlaceAlreadyTakenException();
		
		//en caso contrario, se ejecuta la inserción del lugar en la base de datos
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
	
	
	
	
	
	//Registra el usuario en la base de datos, sólo se pueden registrar usuarios normales.
	//recibe el nombre de usuario y la contraseña
	public void registrarUsuario(String usr, String pass) throws UserRegisterFailureException {
		
		Statement stmt;
		try {
			//se crea un statement para generar la consulta
			stmt = coneccion.createStatement();
			
			//si el usuario ya está registrado, se lanza la excepción UserRegisterFailureException
			verificarRegistro(usr);
			String query = "insert into Usuario (id, pass, admin) values" +
					"('" + usr 
					+ "','"+ pass + "', false);";
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		
		
	}
	
	
	//busca la cuenta que coincida con el inicio de sesion (usuario y password)
	public ResultSet iniciarSesion(String usr, String pass) throws SQLException, UserCheckException {
		
		//se crea el statement para generar la consulta
		Statement stmt = coneccion.createStatement();
		
		//se crea la consulta con el usuario y la contraseña para verificar que el usuario existe
		String query = "select * from Usuario where id = '" 
				+ usr + "' and pass = '"
				+ pass + "';";
		
		//se ejecuta la consulta
		ResultSet rs = stmt.executeQuery(query);
		
		//El ResultSet inicia en -1, rs.next() retorna true si existe l menos un resultado coincidente, por lo que si no hay coincidencias, se lanza un UserCheckException
		if(!rs.next()) throw new UserCheckException("Combinación usuario/contraseña inválida.");
		return rs;
	}
	
	//verifica si el usuario ya está registrado
	public ResultSet verificarRegistro(String usr) throws SQLException, UserRegisterFailureException{
		Statement stmt = coneccion.createStatement();
		
		ResultSet rs = stmt.executeQuery("Select * from Usuario where id = '" + usr + "';");
		if(rs.next()) throw new UserRegisterFailureException("Usuario ya registrado.");
		return rs;
	}
	
	
	//Actualiza la info del lugar
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
	
	//busca lugares que coincidan con una categoría y ubicación especificada por parámetro
	//retorna una lista con todas las coincidencias
	public ArrayList<Lugar> buscarLugar(String cat, String ubic) throws SQLException {
		Statement stmt = coneccion.createStatement();
		String query = "SELECT * FROM Lugar WHERE categoria = '"
				 + cat + "' AND comuna = '" + ubic + "';";
		ResultSet rs = stmt.executeQuery( query );
		ArrayList <Lugar> li = new ArrayList<Lugar>();
		
		//recorre todos los resultados y va construyendo los lugares, además de agregándolos a la lista
		while(rs.next()) {
			Lugar l = new Lugar(rs);
			
			//obtiene la puntuación del lugar
			int pt = obtenerPuntuacion(l.getId());
			
			//setea la puntuación al lugar
			l.setPuntuacion( pt );
			
			//adhiere el lugar a la lista
			li.add( l );
		}
		return li;
	}
	
	
	//Genera un lugar, primero buscandolo en la api de google, y si el lugar ya existe en la base de datos, retorna los datos que hay en esta
	public Lugar autoCompletaLugar(String busqueda) throws SQLException, ApiException, InterruptedException, IOException {
		MapApi mapita = new MapApi();
		Lugar l;
		
		//se busca el lugar en la api de googe
		l = mapita.buscaLugar(busqueda);
		
		//Se guarda el id para buscar en la base de datos si el lugar ya existe en esta
		String id = l.getId();
		Statement stmt = coneccion.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM Lugar where id = '" + id + "';");
		
		//si ya existe, se retornan los datos que hay en la base de datos
		if(rs.next()) {
			return new Lugar(rs);
		}
		
		//en caso contrario, se retornan los datos que existen desde la api de google
		else
			return l;
	}
	
	
	//Crea y retorna un arrayList con los lugares de una ubicación, pasada por parámetro, separados por categoría
	public ArrayList<ArrayList <Lugar>> cargaLugares(String ubicacion) throws SQLException {
		ArrayList<ArrayList<Lugar>> listilla = new ArrayList<ArrayList<Lugar>>();
		String[] categorias = { "atraccion", "dormir", "comida", "vida_nocturna" };
		
		//por cada elemento de categorías, se crea una lista del lugar con la categoría y la ubicación y se agrega a la lista
		for(String i : categorias) {
			listilla.add( buscarLugar(i, ubicacion) );
		}
		return listilla;
	}
	
	//busca un lugar en la base de datos
	public Lugar buscar(Lugar l) throws SQLException {
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
	
	
	//elimina el lugar de la base de datos
	public void eliminar(Lugar l) throws SQLException{
		Statement stmt = coneccion.createStatement();
		stmt.executeUpdate("DELETE FROM Lugar WHERE id = '" + l.getId() + "'");
	}
	
	
	//se busca un comentario por su id
	public ResultSet buscarComentarios(String id) throws SQLException {
		Statement stmt = coneccion.createStatement();
		return stmt.executeQuery("select * from Comentario where id_lugar = '" +  id + "';");
	}
	
	public boolean buscarComentario(String idUsr, String idLugar) throws SQLException{
		Statement stmt = coneccion.createStatement();
		ResultSet rs = stmt.executeQuery("Select * from Comentario where id_lugar = '" + idLugar + "' and id_usuario ='" + idUsr + "';");
		return rs.next();
	}
	
	//calcula la puntuación de un lugar, recibe el id de lugar 
	public int obtenerPuntuacion(String id) throws SQLException {
		Statement stmt = coneccion.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT SUM(puntuacion) FROM Comentario WHERE id_lugar = '" + id + "';");
		if(rs.next()) 
			return rs.getInt("SUM(puntuacion)");
		else 
			return 0;
	}
	
	public void modificarComentario(String coment, Comentario c, Lugar l, String p) throws SQLException {
		System.out.println("\n tu mamá es homnbre jaja salu2\n ");
		Statement stmt = coneccion.createStatement();
		String query;
		if(buscarComentario(c.getUsr(), l.getId())) {
			query = "Update Comentario set comentario ='" + coment + "', puntuacion=" + p + " where id_usuario='" + c.getUsr() + "' and id_lugar='" + l.getId() + "';";
			
		}
		else {
			query = "Insert into Comentario (id_usuario, id_lugar, comentario, puntuacion) values( '" + c.getUsr() + "', '" + l.getId() + "', '" + coment + "', " + p+");";
			
		}
		
		stmt.executeUpdate(query);
		
	}
	
	
	
}
