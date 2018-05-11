package main.java;

import java.io.IOException;
import java.sql.*;

import java.util.ArrayList;
import java.util.Hashtable;

import com.google.maps.errors.ApiException;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import excepciones.PlaceAlreadyTakenException;
import excepciones.PlaceException;
import excepciones.UserCheckException;
import excepciones.UserRegisterFailureException;



public class DbHandler {
	
	//variables necesarias para realizar querys a la db
	private static Connection conexion = null;
	private Statement stmt;
	private ResultSet rs;
	
	
	
	public DbHandler() {
		
		
			conexion = Db.getConexion();
		
		if(conexion == null)
			System.err.println("eerrrrroor");
		
		
		
		
		
	}
	
	public static Connection getConexion() {
		return conexion;
	}
	
	public String[] zonas() throws SQLException{
		CallableStatement cs = conexion.prepareCall("{CALL getZonas()}");
		ResultSet rs = cs.executeQuery();
		
		ArrayList<String> a = new ArrayList<String>();
		while(rs.next()) {
			a.add(rs.getString("zona"));
		}
		
		String[] s = new String[a.size()];
		
		for(int i=0 ; i < s.length ; i++) {
			s[i] = (String) a.get(i);
		}
		
		return s;
	}
	
	public ResultSet categorias() throws SQLException {
		CallableStatement cs = conexion.prepareCall("{CALL getCategorias()}");
		return cs.executeQuery();
	
		
		
	}
	
	
	public ResultSet lugares() throws SQLException {
		CallableStatement cs = conexion.prepareCall("{CALL getLugares()}");
		return cs.executeQuery();
	}
	
	public ResultSet comentarios() throws SQLException{
		CallableStatement cs = conexion.prepareCall("{CALL getComentarios()}");
		return cs.executeQuery();
	}
	
	public ResultSet usuarios() throws SQLException{
		CallableStatement cs = conexion.prepareCall("{CALL getUsuarios()}");
		return cs.executeQuery();
	}
	
	//ingresamos un nuevo lugar a la DB, mySQL verifica si ya se ha ingresado
	public boolean ingresarLugar(Lugar l) throws SQLException, PlaceAlreadyTakenException{
		Statement stmt = conexion.createStatement();
		//se ejecuta la consulta
		ResultSet rs = stmt.executeQuery("select * from Lugar where id = '" +
							l.getId() + "' or casa = '" +
							l.getDireccionPpal() + "';");
		CallableStatement cs = conexion.prepareCall("{CALL agregarLugar(?,?,?,?,?,?,?,?,?,?)}");
		String[] lugar = l.arreglo();
		
		for(int i = 0; i< lugar.length ; i++) {
			cs.setString(i+1, lugar[i]);
		}
		
		return cs.execute();
		/*
		//el ResultSet comienza en -1, por lo que si existe next es porque hab�a al menos un resultado coincidente
		//por lo que el lugar ya est� ingresado y se lanza la excepci�n
		if(rs.next()) throw new PlaceAlreadyTakenException();
		
		//en caso contrario, se ejecuta la inserci�n del lugar en la base de datos
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
		*/
		
	}
	
	
	
	
	
	//Registra el usuario en la base de datos, s�lo se pueden registrar usuarios normales.
	//recibe el nombre de usuario y la contrase�a
	public void registrarUsuario(String usr, String pass) throws UserRegisterFailureException {
		
		Statement stmt;
		try {
			//se crea un statement para generar la consulta
			stmt = conexion.createStatement();
			
			//si el usuario ya est� registrado, se lanza la excepci�n UserRegisterFailureException
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
	public CuentaUsuario iniciarSesion(String usr, String pass) throws SQLException, UserCheckException {
		
		//se crea el statement para generar la consulta
		Statement stmt = conexion.createStatement();
		
		//se crea la consulta con el usuario y la contrase�a para verificar que el usuario existe
		String query = "select * from Usuario where id = '" 
				+ usr + "' and pass = '"
				+ pass + "';";
		
		//se ejecuta la consulta
		ResultSet rs = stmt.executeQuery(query);
		
		if(rs.next()) {
			if(rs.getBoolean("admin"))
				return new Administrador(rs.getString("id"), rs.getString("pass"));
			else
				return new Usuario(rs.getString("id"), rs.getString("pass"));
		}
		//El ResultSet inicia en -1, rs.next() retorna true si existe l menos un resultado coincidente, por lo que si no hay coincidencias, se lanza un UserCheckException
		throw new UserCheckException("Combinaci�n usuario/contrase�a inv�lida.");
		
	}
	
	//verifica si el usuario ya est� registrado
	public ResultSet verificarRegistro(String usr) throws SQLException, UserRegisterFailureException{
		Statement stmt = conexion.createStatement();
		
		ResultSet rs = stmt.executeQuery("Select * from Usuario where id = '" + usr + "';");
		if(rs.next()) throw new UserRegisterFailureException("Usuario ya registrado.");
		return rs;
	}
	
	
	//Actualiza la info del lugar
	public void actualizarLugar(Lugar l) throws SQLException, PlaceException {
		Statement stmt = conexion.createStatement();
		if (this.buscar(l) == null)
			throw new PlaceException("El lugar no existe en la base de datos.");
		
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
		Statement stmt = conexion.createStatement();
		String query = "select * from Lugar where id = '" + id + "';";
		System.out.println(query);
		ResultSet rs = stmt.executeQuery("select * from Lugar where id = '" + id + "';");
		if(rs.next())
			return new Lugar(rs);
		
		else return null;

	}*/
	
	//Se busca un lugar por el nombre del local
	public Lugar buscarLugar(String name) throws SQLException{
		Statement stmt = conexion.createStatement();
		ResultSet rs = stmt.executeQuery("select * from Lugar where nombre = '" + name + "';");
		
		if(rs.next())
			return new Lugar(rs);
		
		else return null;

	}
	
	//busca lugares que coincidan con una categor�a y ubicaci�n especificada por par�metro
	//retorna una lista con todas las coincidencias
	public ResultSet buscarLugar(String cat, String ubic) throws SQLException {
		Statement stmt = conexion.createStatement();
		String query = "SELECT * FROM Lugar WHERE categoria = '"
				 + cat + "' AND comuna = '" + ubic + "';";
		return rs = stmt.executeQuery( query );
		/*ArrayList <Lugar> li = new ArrayList<Lugar>();
		
		//recorre todos los resultados y va construyendo los lugares, adem�s de agreg�ndolos a la lista
		while(rs.next()) {
			Lugar l = new Lugar(rs);
			
			//obtiene la puntuaci�n del lugar
			int pt = obtenerPuntuacion(l.getId());
			
			//setea la puntuaci�n al lugar
			l.setPuntuacion( pt );
			
			//adhiere el lugar a la lista
			li.add( l );
		}
		return li;*/
	}
	
	
	//Genera un lugar, primero buscandolo en la api de google, y si el lugar ya existe en la base de datos, retorna los datos que hay en esta
	public Lugar autoCompletaLugar(String busqueda) throws SQLException, ApiException, InterruptedException, IOException {
		MapApi mapita = new MapApi();
		Lugar l;
		
		//se busca el lugar en la api de googe
		l = mapita.buscaLugar(busqueda);
		
		//Se guarda el id para buscar en la base de datos si el lugar ya existe en esta
		String id = l.getId();
		Statement stmt = conexion.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM Lugar where id = '" + id + "';");
		
		//si ya existe, se retornan los datos que hay en la base de datos
		if(rs.next()) {
			return new Lugar(rs);
		}
		
		//en caso contrario, se retornan los datos que existen desde la api de google
		else
			return l;
	}
	
	
	/*
	
	//Crea y retorna un arrayList con los lugares de una ubicaci�n, pasada por par�metro, separados por categor�a
	public Hashtable<String, ArrayList <Lugar>> cargaLugares(String ubicacion) throws SQLException {
		Hashtable<String, ArrayList<Lugar>> mapita = new Hashtable<String, ArrayList<Lugar>>();
		String[] categorias = { "atraccion", "dormir", "comida", "vida_nocturna" };
		
		//por cada elemento de categor�as, se crea una lista del lugar con la categor�a y la ubicaci�n y se agrega a la lista
		for(String i : categorias) {
			mapita.put(i, buscarLugar(i, ubicacion));
		}
		return mapita;
	}
	*/
	//busca un lugar en la base de datos
	public Lugar buscar(Lugar l) throws SQLException {
		Statement stmt = conexion.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM Lugar WHERE id = '" + l.getId() + "'");
		if(rs.next()) 
			return new Lugar(rs);
		else return null;
	}
	
	
	//TODrO: agregar busqueda de usuarios y comentarios 
	public Object buscar(Usuario usr) {
		return null;
	}
	
	
	//elimina el lugar de la base de datos
	public void eliminar(Lugar l) throws SQLException{
		Statement stmt = conexion.createStatement();
		stmt.executeUpdate("DELETE FROM Lugar WHERE id = '" + l.getId() + "'");
	}
	
	public ResultSet obtenerComentarios() throws SQLException {
		Statement stmt = conexion.createStatement();
		return stmt.executeQuery("SELECT * FROM Comentario;");
	}
	
	
	//se busca un comentario por su id
	public ResultSet buscarComentarios(String id) throws SQLException {
		Statement stmt = conexion.createStatement();
		return stmt.executeQuery("select * from Comentario where id_lugar = '" +  id + "';");
	}
	
	public boolean buscarComentario(String idUsr, String idLugar) throws SQLException{
		Statement stmt = conexion.createStatement();
		ResultSet rs = stmt.executeQuery("Select * from Comentario where id_lugar = '" + idLugar + "' and id_usuario ='" + idUsr + "';");
		return rs.next();
	}
	
	
	public ResultSet obtenerLugares() throws SQLException {
		return stmt.executeQuery("SELECT * FROM Lugar;");
	}
	
	
	/**
	 * Calcula y retorna la puntuación actual de un determinado lugar.
	 * Las posibles puntuaciones de un comentario son 1 y -1,
	 * y el puntaje se calcula sumando todas las puntuaciones.
	 * La suma se hace mediante una consulta simple.
	 * 
	 * @param id : el id del lugar a calcular la puntuación.
	 * @return Un entero de la suma de las puntuaciones.
	 * @throws SQLException
	 */
	public int obtenerPuntuacion(String id) throws SQLException {
		Statement stmt = conexion.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT SUM(puntuacion) FROM Comentario WHERE id_lugar = '" + id + "';");
		if(rs.next()) 
			return rs.getInt("SUM(puntuacion)");
		else 
			return 0;
	}
	
	public void modificarComentario(String coment, Comentario c, Lugar l, String p) throws SQLException {
		Statement stmt = conexion.createStatement();
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
